package infrastructure

import org.apache.spark.sql.{Dataset, Row}

class ElasticDataSender(_spark:SparkFacade) extends DataSender(_spark) {
  def Send(data:Dataset[Row]):Unit = {

  }
}
