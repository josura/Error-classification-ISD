package infrastructure

import org.apache.spark.sql.{Dataset, Row,SparkSession}
import org.apache.spark.sql.types.{StructType,StringType,IntegerType}

import org.apache.spark.sql.functions._

class KafkaEventConsumer(val _spark:SparkSession) extends EventConsumer(_spark){
  override def Consume(topics:String,schema:StructType): Dataset[Row] = {
      if(topics.isEmpty())throw new IllegalArgumentException("topics should not be an empty string")
      val fulldf = spark.readStream.format("kafka").option("kafka.bootstrap.servers","kafka:9092").option("subscribe",topics).load()
      val StringDF = fulldf.selectExpr("CAST(value AS STRING)")
      val UserCode= CodeCleaner.cleanCode(StringDF.select(from_json(col("value"),schema).as("data")).select("data.*"))
      UserCode
  }
}
