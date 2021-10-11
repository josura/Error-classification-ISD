package com.entonomachia.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisInterface {
	
	@Autowired
    private RedisTemplate<String, Object> template;
    
    Boolean initialized = false;
	
	String key = "currentId";
	
	
	public Integer getNewId() {
		if(!template.hasKey(key)) {
				template.opsForValue().set(key, "400");
		}
	 	template.boundValueOps(key).increment();
		return Integer.valueOf((String) template.opsForValue().get(key)) ;
		
	}


	public void testConnection() {
		System.out.println(template.getConnectionFactory().getConnection().ping()); 
	}
}
