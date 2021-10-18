package com.entonomachia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemp;
	
	public void publishToTopic(String message,String kafkaTopicProducer) {
		//TODO logging
		this.kafkaTemp.send(kafkaTopicProducer,message);
	}
	
}
