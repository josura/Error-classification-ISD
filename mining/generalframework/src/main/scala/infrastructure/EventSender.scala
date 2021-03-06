package infrastructure


import org.apache.spark.sql.{Dataset,Row,SparkSession}
import org.apache.spark.sql.types.StructType



abstract class EventSender(var spark:SparkProxy) {
  def Send(data:Dataset[Row],topics:String,schema:StructType)
}
