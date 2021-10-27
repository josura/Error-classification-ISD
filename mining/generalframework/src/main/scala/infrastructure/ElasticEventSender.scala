package infrastructure

import org.apache.spark.sql.{Dataset, Row}
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.StructType

//elastic
import org.elasticsearch.spark.sql._

class ElasticEventSender(_spark:SparkFacade,var _index:String) extends EventSender(_spark){ 

    def Send(data: Dataset[Row]): StreamingQuery = {
      _spark.sparkWriteStreamElastic(data,_index)
    }

    override def changeTarget(newTarget: String): Unit = {
        _index = newTarget
    }
}
