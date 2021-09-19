package infrastructure

import org.apache.spark.sql.{SparkSession,Row,Dataset,SaveMode}
import org.apache.spark.sql.functions.{udf,col}


object MainClassification extends App{

    val bugsFile = FileResolver.getDataDirectory() + "final.json"

    val spark = new SparkFacade("Main")

    try {
        val bugs: Dataset[Row]=  spark.sparkReadJson(bugsFile)
      
        val bugsCleaned = CodeCleaner.cleanCode(CodeCleaner.cleanCode(bugs,"CodeWithNoComments","cleanedCode"),"SolutionWithNoComments","cleanedSolution")
        bugsCleaned.cache()

        val clusClass = new DirectClassifier(spark,bugsCleaned)

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
                                                        col("labelError"),col("probabilityErrorString"),col("rawPredictionErrorString"),             //OPTIONAL
                                                        col("labelMutation"),col("probabilityMutationString"),col("rawPredictionMutationString"),    //OPTIONAL
                                                        col("featureString")
                                                    )

        elasticSender.Send( sendedClassified )
        //val newCode = CodeCleaner.cleanCode(...,"code","cleanedCode")

        //val repositoriesTyped = newpredictions.select(col("url"),col("owner"), col("stars"),col("prediction").cast(IntegerType).as("label")).as[repositorieClassified]
        

    } catch {
        case e:Exception=>{spark.stop()}
    }
}
