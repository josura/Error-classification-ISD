package infrastructure

import org.apache.spark.sql.{Dataset,Row,SparkSession}
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.StructType

class ParquetEventSender(val _spark:SparkFacade,var folderName:String) extends EventSender(_spark){
  
  override def Send(data: Dataset[Row]): StreamingQuery = {
      _spark.sparkWriteStreamParquet(data,folderName)
  }

  override def changeTarget(newTarget: String): Unit = {
      folderName=newTarget
  }
}
