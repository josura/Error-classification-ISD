package infrastructure

import org.apache.spark.sql.{SparkSession,Row,Dataset,SaveMode}
import org.apache.spark.sql.functions.{udf,col,concat,lit}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.IntegerType

import redis.clients.jedis.Jedis

object MainClassification extends App{

    val jedis:Jedis = new Jedis("redis")

    val bugsFile = FileResolver.getDataDirectory() + "final.json"
    val userSharedData = FileResolver.getDataDirectory() + "usershared/test1.parquet"

    val spark = new SparkFacade("Main")

    try {
        val parqDF = spark.sparkReadParquet(userSharedData).withColumn("ids",col("ids").cast(IntegerType))
        parqDF.show()
        parqDF.printSchema

        val bugs: Dataset[Row]=  spark.sparkReadJson(bugsFile).union(parqDF).withColumn("ids",col("ids").cast(IntegerType))
        bugs.printSchema
      
        val bugsCleaned = CodeCleaner.cleanCode(CodeCleaner.cleanCode(bugs,"CodeWithNoComments","cleanedCode"),"SolutionWithNoComments","cleanedSolution")
        bugsCleaned.cache()

        val clusClass = new ClusterClassifier(spark,bugsCleaned)

        val defectRecordsClassified = clusClass.ClassifyCode(bugsCleaned)

        //sending the errors and mutation to elasticSearch 
        // TODO add ids to new data
        val elasticSender = new ElasticDataSender(spark,"ids","primarydirect/classified")

        val vecToStringUDF = udf((vec:org.apache.spark.ml.linalg.DenseVector)=> { 
                    vec.toArray.mkString(" ")
            })

        val sendedClassified = defectRecordsClassified.withColumn("featureString",vecToStringUDF(defectRecordsClassified("features"))).
                                                    withColumn("probabilityErrorString",vecToStringUDF(defectRecordsClassified("probabilityError"))).
                                                    withColumn("rawPredictionErrorString",vecToStringUDF(defectRecordsClassified("rawPredictionError"))).
                                                    withColumn("probabilityMutationString",vecToStringUDF(defectRecordsClassified("probabilityMutation"))).
                                                    withColumn("rawPredictionMutationString",vecToStringUDF(defectRecordsClassified("rawPredictionMutation"))).
                                                    select(col("ids"),col("code"),col("solution"),col("source"),col("user"),col("group"),
                                                        col("labelError"),
                                                        col("probabilityErrorString"),col("rawPredictionErrorString"),             //OPTIONAL
                                                        col("labelMutation"),
                                                        col("probabilityMutationString"),col("rawPredictionMutationString"),    //OPTIONAL
                                                        col("featureString")
                                                    )

        elasticSender.Send( sendedClassified )

        // receiving streming data from kafka from the client
        val kafkaStreamingReceiver = new KafkaEventConsumer(spark)

        val schema=new StructType().
                //add("ids",IntegerType).
                add("ids",StringType).
                add("user",StringType).add("group",StringType).add("code",StringType)
        val newCode = kafkaStreamingReceiver.Consume("usercode",schema)

        val consoleStreamafter = spark.sparkWriteStreamConsole(newCode)

        try{
            

            val newpredictions:Dataset[Row] = clusClass.ClassifyCode(newCode,streaming = true)

            val kafkaSendedClassified = newpredictions.select(col("ids"),concat(newpredictions("labelError")
                                                                            ,lit(" "),newpredictions("labelMutation")
                                                                            ,lit(" "),newpredictions("user")
                                                                            ,lit(" "),newpredictions("group")).as("labels"))
        
            val consoleStreamFinal = spark.sparkWriteStreamConsole(kafkaSendedClassified)

            val kafkaStreamSender = spark.sparkWriteStreamKafka(kafkaSendedClassified,"labelledcode")
        

        } catch {
            case e: Throwable => {

                val typedNewCode = spark.sparkTypeDataset(newCode.select(newCode("ids"),newCode("code"),newCode("cleanedCode")))
                    typedNewCode.take(newCode.count.toInt).foreach(t => {
                    val transactionName = "Transaction:" + t.ids;
                    jedis.hset(transactionName, "status", "ERROR");
                    jedis.hset(transactionName, "resultError", "error during the prediction, stacktrace:\n" + e.toString() + "\n\n resulting code and cleaned code is: \n\t code --> " + t.code + "\n\t code --> " + t.cleanedCode );
                    jedis.hset(transactionName, "resultMutation", "error, read result error for details" );
                })

                
            }
        }
        spark.spark.streams.awaitAnyTermination()
        
    } catch {
        case e:Exception=>{
            ClassifierLogger.printError("Error in MainClassification",e)
        }
    } finally {
        spark.stop()
    }
}
