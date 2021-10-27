package infrastructure

import org.apache.spark.sql.{Dataset,Row,SparkSession}
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.StructType

class KafkaEventSender(val _spark:SparkFacade, var topics:String) extends EventSender(_spark){

  override def Send(data: Dataset[Row]): StreamingQuery = {
      _spark.sparkWriteStreamKafka(data,topics)
  }

  override def changeTarget(newTarget: String): Unit = {
    topics=newTarget
  }
}
