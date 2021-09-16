package infrastructure

import org.apache.spark.sql.{Dataset,Row,SparkSession}
import org.apache.spark.sql.types.StructType

abstract class EventConsumer(var spark:SparkFacade) {
  def Consume(topics:String,schema:StructType):Dataset[Row]

}
