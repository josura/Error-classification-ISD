package infrastructure

import org.apache.spark.sql.{Dataset, Row}
import org.apache.spark.sql.types.StructType

//elastic
import org.elasticsearch.spark.sql._

class ElasticEventSender(_spark:SparkFacade) extends EventSender(_spark){
    def Send(data: Dataset[Row], topics: String, schema: StructType): Unit = {

    }
}
