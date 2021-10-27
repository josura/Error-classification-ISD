package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Row

class ConsoleSender(_spark:SparkFacade) extends EventSender(_spark){
    def Send(data: Dataset[Row]): StreamingQuery = {
        _spark.sparkWriteStreamConsole(data)
    }
}