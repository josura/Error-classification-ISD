package infrastructure

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.StreamingQuery

//elastic streaming 
import org.elasticsearch.spark.sql._

// kafka streaming
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


class SparkProxy(appName:String) {
    var started:Boolean=true
    lazy val spark:SparkSession=SparkSession.builder.appName(appName).
            config("es.nodes","elastic-search").config("es.index.auto.create", "true").config("es.port","9200").//config("es.nodes.wan.only", "true").
            //config("spark.sql.crossJoin.enabled", "true").
            master("local[*]").getOrCreate()
    
    import spark.implicits._


    def sparkInit():Unit = {
        spark.sparkContext.setLogLevel("ERROR")
    }


    def sparkReadStreamKafka(topics:String):Dataset[Row] = {
        if(started) sparkInit()
        spark.readStream.format("kafka").option("kafka.bootstrap.servers","kafka:9092").option("subscribe",topics).load()
    }

    def sparkReadJson(fileName:String):Dataset[Row] = {
        if(started) sparkInit()
        spark.read.json(fileName)
    }

    def sparkWriteStreamElastic(data:Dataset[Row]):StreamingQuery = {
        if(started) sparkInit()
        data.writeStream.outputMode("append").
            format("es").option("checkpointLocation","/tmp").
            option("es.mapping.id","url").start("code/classified")
    }

    def sparkWriteStreamConsole(data:Dataset[Row]):StreamingQuery = {
        if(started) sparkInit()
        data.writeStream.format("console").outputMode("append").start()
    }

    def stop():Unit = {
        spark.stop()
        
    }
}
