package infrastructure


import org.apache.spark.sql.{Dataset,Row,SparkSession}
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.StructType



abstract class EventSender(var spark:SparkFacade) {
  def Send(data:Dataset[Row]):StreamingQuery

  def changeTarget(newTarget:String):Unit={

  }
}
