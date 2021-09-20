package infrastructure

import org.apache.spark.sql.{SparkSession,Row,Dataset,SaveMode}
import org.apache.spark.sql.functions.{udf,col}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.IntegerType

object MainClassification extends App{

    val bugsFile = FileResolver.getDataDirectory() + "final.json"

    val spark = new SparkFacade("Main")

    try {
        val bugs: Dataset[Row]=  spark.sparkReadJson(bugsFile)
      
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
        //val kafkaStreamingReceiver = new KafkaEventConsumer(spark)
        //val schema=new StructType().add("ids",IntegerType).add("user",StringType).add("group",StringType).add("code",StringType)
        //val newCode = kafkaStreamingReceiver.Consume("usercode",schema)
//
        //val newpredictions:Dataset[Row] = clusClass.ClassifyCode(newCode)
        //val consoleStream = spark.sparkWriteStreamConsole(newpredictions)  //TODO refactor in  console event class
//
        //val kafkaStreamSender = spark.sparkWriteStreamKafka(newpredictions,"labelledcode").awaitTermination    //TODO refactor in  kafka event sender class and understand what to send and to what topic(create a topic for every user, send everything to one topic, sending only identifiers,users,groups and labelMutant/Error)
        val fulldf = spark.spark.readStream.format("kafka").option("kafka.bootstrap.servers","kafka:9092").option("subscribe","usercode").load()
        val StringDF = fulldf.selectExpr("CAST(value AS STRING)")
        val schema = new StructType().add("ids",IntegerType).add("user",StringType).add("group",StringType).add("code",StringType)
        import spark.spark.implicits._
        import org.apache.spark.sql.functions._
        val UserCode:Dataset[Row] = CodeCleaner.cleanCode(StringDF.select(from_json(col("value"),schema).as("data")).select("data.*"),"code","cleanedCode")

        val newpredictions:Dataset[Row] = clusClass.ClassifyCode(UserCode)

        newpredictions.writeStream.format("console").outputMode("append").start()

        newpredictions.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").  //casting ?? TODO control
            writeStream.
            format("kafka").
            option("kafka.bootstrap.servers", "kafka:9092").
            option("topic", "test").
            start().
            awaitTermination()
        //val repositoriesTyped = newpredictions.select(col("url"),col("owner"), col("stars"),col("prediction").cast(IntegerType).as("label")).as[repositorieClassified]
        

    } catch {
        case e:Exception=>{spark.stop()}
    }
}
