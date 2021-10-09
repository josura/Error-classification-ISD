package com.entonomachia.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Properties;
import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.entonomachia.server.ElasticInterface;
import com.entonomachia.server.QueryResultDTO;


//this class will not be used but will be used as template in the spring backend application
public class ElasticClient {
	private static ElasticInterface ElFac;
	
	public static void main(String[] args) {
		
		//Kafka consumer properties
		Properties props = new Properties();
		props.put("bootstrap.servers", "kafka:9092");
		props.put("group.id", "orchestratorgroup");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer",         
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", 
	         "org.apache.kafka.common.serialization.StringDeserializer");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		
		try {
			  //RMI client to elastic-interface setup
			  System.setProperty("java.rmi.server.hostname","elastic-facade-server");
			  //ElFac = (ElasticInterface)Naming.lookup("ElasticFacade");
			  Registry reg=LocateRegistry.getRegistry("elastic-facade-server",1099);
			  ElFac = (ElasticInterface)reg.lookup("ElasticFacade");
		      
		      consumer.subscribe(Arrays.asList("codeLabels"));
		      System.out.println("Subscribed to topic " + " codeLabels");
		      int i = 0;
		         
		      while (true) {
		         ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
		            for (ConsumerRecord<String, String> record : records) {
		                System.out.printf("offset = %d, key = %s, value = %s\n", 
		            		   record.offset(), record.key(), record.value());

			   			System.out.println(ElFac.findCodeByUserSyncString("user"));
			   			QueryResultDTO res = ElFac.findCodeByLabelErrorSync(40.0);
			   			System.out.println(res.code[0]);
		               
		            }
		      }
			
			
			//ElFac = (ElasticInterface)Naming.lookup("//localhost/ElasticFacade");
			//ElFac.findCodeByLabelErrorSync(14.0);
			//System.out.println(ElFac.getResult());
//			System.out.println(ElFac.findCodeByUserSyncString("user"));
//			QueryResultDTO res = ElFac.findCodeByLabelErrorSync(40.0);
//			System.out.println(res.code[0]);
		} catch(Exception e) {
			consumer.close();
			e.printStackTrace();
		} 
	}

}
