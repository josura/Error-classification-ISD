package infrastructure

import org.apache.spark.sql.{Dataset,Row,SparkSession}
import org.apache.spark.sql.types.StructType

class KafkaEventSender(val _spark:SparkProxy) extends EventSender(_spark){
  override def Send(data: Dataset[Row], topics: String, schema: StructType): Unit = {
      
  }
}
