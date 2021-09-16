package infrastructure

import org.apache.spark.sql.{Dataset,Row,SparkSession}

abstract class DataSender(var spark:SparkFacade) {
  def Send(data:Dataset[Row])
}
