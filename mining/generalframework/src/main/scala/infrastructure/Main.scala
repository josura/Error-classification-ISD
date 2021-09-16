package infrastructure

import org.apache.spark.sql.{SparkSession,Row,Dataset,SaveMode}

object Main extends App{

    val spark = new SparkFacade("Main")

    val bugs: Dataset[Row]=  spark.sparkReadJson(FileResolver.getDataDirectory() + "final.json")
    bugs cache
      
      

    try {

        //val repositoriesTyped = newpredictions.select(col("url"),col("owner"), col("stars"),col("prediction").cast(IntegerType).as("label")).as[repositorieClassified]
        

    } catch {
        case e:Exception=>{spark.stop()}
    }
}
