package infrastructure

import org.apache.spark.sql.{Dataset,Row,SparkSession}
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.StructType

class ParquetEventSender(val _spark:SparkFacade) extends EventSender(_spark){
  override def Send(data: Dataset[Row], topics: String): StreamingQuery = {
      _spark.sparkWriteStreamParquet(data,topics)
  }
}
