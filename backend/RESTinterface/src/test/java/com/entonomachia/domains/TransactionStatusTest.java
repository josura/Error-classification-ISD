package com.entonomachia.domains;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TransactionStatusTest {
	
    TransactionStatus RECORD_1 = new TransactionStatus("1", "PENDING", "if(test=true){logger.info(\"bad\")}", "if(test==true){logger.info(\"bad\")}");
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
    @BeforeEach
    void setUp() {

    }
    
    private boolean testJsonStringEquals(String json1, TransactionStatus tran2) {
    	//TransactionStatus tran1 = gson.fromJson(json1, TransactionStatus.class);
    	String json2 = gson.toJson(tran2); 
    	//return tran1.equals();
    	return json1.equals(json2);
    }
    
	@Test
    @DisplayName("TransactionStatus toString should return a json with the correct schema")
	void testToString() {
		assertTrue(testJsonStringEquals( RECORD_1.toString().replaceAll("(\n|\t)", ""),RECORD_1 ) );
	}
}
