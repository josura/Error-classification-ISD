package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Row

class ConsoleSender(_spark:SparkProxy) extends EventSender(_spark){
    def Send(data: Dataset[Row], topics: String, schema: StructType): Unit = {

    }
}