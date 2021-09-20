package infrastructure

import org.apache.spark.sql.{Dataset, Row}

class ElasticDataSender(_spark:SparkFacade,idName:String,indexName:String) extends DataSender(_spark) {
  def Send(data:Dataset[Row]):Unit = {
    if(data.select(idName).distinct().count() == data.count()){
      _spark.sparkWriteDataElastic(data,indexName,idName)
      ClassifierLogger.printInfo("data written to elastic at index " + indexName + " with unique identifier " + idName)
    } else {
      _spark.sparkWriteDataElastic(data,indexName)
      ClassifierLogger.printWarning("data written to elastic at index " + indexName + " because " + idName + " is not a key" )  
    }
  }
}
