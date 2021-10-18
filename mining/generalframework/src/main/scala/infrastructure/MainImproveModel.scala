package infrastructure

import org.apache.spark.sql.{SparkSession,Row,Dataset,SaveMode}
import org.apache.spark.sql.functions.{udf,col,concat,lit}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.IntegerType

object MainImproveModel extends App{

    val spark = new SparkFacade("Main improve model")

    val bugs: Dataset[Row]=  spark.sparkReadJson(FileResolver.getDataDirectory() + "final.json")
    bugs cache
      

    try {

        val kafkaStreamingReceiver = new KafkaEventConsumer(spark)
        val schema=new StructType().
            add("ids",StringType).
            add("error",StringType).
            add("source",StringType).
            add("code",StringType).
            add("solution",StringType).
            add("CodeWithNoComments",StringType).
            add("SolutionWithNoComments",StringType).
            add("user",StringType).add("group",StringType)
        val newCode = kafkaStreamingReceiver.Consume("improvemodelcode",schema).drop("cleanedCode")

        val parquetWriteStream = spark.sparkWriteStreamParquet(newCode,FileResolver.getDataDirectory() + "usershared/test1.parquet")

        val consoleStream = spark.sparkWriteStreamConsole(newCode)

        spark.spark.streams.awaitAnyTermination()        

    } catch {
        case e: Exception => ClassifierLogger.printError("Exception in MainImproveModel",e)
    } finally {
        spark.stop()
    }
}
