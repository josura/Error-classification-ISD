package infrastructure

import org.apache.spark.sql.{Dataset, Row,SparkSession}
import org.apache.spark.sql.types.{StructType,StringType,IntegerType}

import org.apache.spark.sql.functions._

class KafkaEventConsumer(val _spark:SparkFacade,var streaming:Boolean = true) extends EventConsumer(_spark){  //streaming is to default true because this class handles streaming data

  override def Consume(topics:String,schema:StructType): Dataset[Row] = {
      if(topics.isEmpty())throw new IllegalArgumentException("topics should not be an empty string")
      ClassifierLogger.printInfo("Consuming kafka events from topic " + topics)
      val fulldf = spark.sparkReadStreamKafka(topics)
      val StringDF = fulldf.selectExpr("CAST(value AS STRING)")
      val UserCode= CodeCleaner.cleanCode(StringDF.select(from_json(col("value"),schema).as("data")).select("data.*"),"code","cleanedCode",streaming)  //problems if I do not send json data
      UserCode
  }
}
