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

import redis.clients.jedis.Jedis;


//this class will not be used but will be used as template in the spring backend application
public class ElasticClient {
	private static ElasticInterface ElFac;
	
	public static void main(String[] args) {
		System.out.println("Starting orchestrator...");
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
		System.out.println("Kafka consumer created");
		
		Jedis jedis = new Jedis("redis", 6379);
	    //jedis.auth("password");   //right now the redis repository does not have a password, TODO?
    	System.out.println("Redis connection returns " + jedis.ping());
		try {
			  //RMI client to elastic-interface setup
			  System.setProperty("java.rmi.server.hostname","elastic-facade-server");
			  //ElFac = (ElasticInterface)Naming.lookup("ElasticFacade");
			  Registry reg=LocateRegistry.getRegistry("elastic-facade-server",1099);
			  ElFac = (ElasticInterface)reg.lookup("ElasticFacade");
		      
		      consumer.subscribe(Arrays.asList("labelledcode"));
		      System.out.println("Subscribed to topic " + " labelledcode");
		      
		      System.out.println("testing elastic server functionalities:");
		      System.out.println(ElFac.findCodeByUserSyncString("user"));
			  QueryResultDTO res = ElFac.findCodeByLabelErrorSync(40.0);
			  System.out.println(res.code[0]);
			  System.out.println("\n Polling kafka for labels\n");
		      while (true) {
		    	  
		        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
	            for (ConsumerRecord<String, String> record : records) {
	                System.out.printf("Consuming record : offset = %d, key(transactionId) = %s, value(labels) = %s\n", 
	            		   record.offset(), record.key(), record.value());
	                String labelsString = record.value();
	                
	                String[] labels = labelsString.split(" ");
	                Double labelError = Double.parseDouble(labels[0]);
	                Double labelMutation = Double.parseDouble(labels[1]);
		            String user = labels[2];
		            String group = labels[3];
	                
		   			//System.out.println(ElFac.findCodeByUserSyncString("user"));
	                //TODO returns only useful parts of the full json, like the hits array
//		   			QueryResultDTO resError = ElFac.findCodeByLabelErrorSync(labelError);
//		   			QueryResultDTO resMutation = ElFac.findCodeByLabelMutantSync(labelMutation);
		   			String transactionName = "Transaction:" + record.key();

		            try {
		            	QueryResultDTO resError = ElFac.findCodeByLabelErrorCredentialsSync(labelError,user,group);
			   			QueryResultDTO resMutation = ElFac.findCodeByLabelMutantCredentialsSync(labelMutation,user,group);
			   			
			   			//jedis.hset("Transaction:", "id", "valore");
			   			
		                jedis.hset(transactionName, "status", "ERROR");
		                jedis.hset(transactionName, "resultError", resError.onlyHitsJson);
		                jedis.hset(transactionName, "resultMutation", resMutation.onlyHitsJson);
		        		
		            } catch(Exception e) {

		                jedis.hset(transactionName, "status", "ERROR");
		                jedis.hset(transactionName, "resultError", "error during the orchestrator call to get the predictions in elastic-search, stacktrace:\n" + e.toString() );
		                jedis.hset(transactionName, "resultMutation", "error, for more information see resultError field");
		            }
		   			//System.out.println(resError.fullJson);
		   			//System.out.println(resError.onlyHitsJson);
	               
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
		} finally {
			jedis.close();
		}
	}

}
