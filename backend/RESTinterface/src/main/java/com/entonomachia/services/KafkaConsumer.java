package com.entonomachia.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	
	@KafkaListener(topics = "codeLabels")
	public String consumeFromTopic(String message) {
		//TODO control if message is the one searched
		return message;
	}
}
