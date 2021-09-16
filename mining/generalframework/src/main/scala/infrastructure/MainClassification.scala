package infrastructure

import org.apache.spark.sql.{SparkSession,Row,Dataset,SaveMode}

object MainClassification extends App{

    val bugsFile = FileResolver.getDataDirectory() + "final.json"

    val spark = new SparkFacade("Main")

    val bugs: Dataset[Row]=  spark.sparkReadJson(bugsFile)
    bugs cache
      
    val bugsCleaned = CodeCleaner.cleanCode(CodeCleaner.cleanCode(bugs,"CodeWithNoComments","cleanedCode"),"SolutionWithNoComments","cleanedSolution")

    try {
        //val newCode = CodeCleaner.cleanCode(...,"code","cleanedCode")

        //val repositoriesTyped = newpredictions.select(col("url"),col("owner"), col("stars"),col("prediction").cast(IntegerType).as("label")).as[repositorieClassified]
        

    } catch {
        case e:Exception=>{spark.stop()}
    }
}
