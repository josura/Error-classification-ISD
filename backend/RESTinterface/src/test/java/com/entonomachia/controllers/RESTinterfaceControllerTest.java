package com.entonomachia.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.entonomachia.controllers.InterfaceController;
import com.entonomachia.domains.TransactionStatus;
import com.entonomachia.repositories.TransactionRepository;
import com.entonomachia.services.KafkaProducer;
import com.entonomachia.services.RedisInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(InterfaceController.class)
public class RESTinterfaceControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    TransactionRepository mockTranRep;
    
    @MockBean
    KafkaProducer mockKafkaProd;
    
    @MockBean
    RedisInterface mockRedisInterface;
    
    TransactionStatus RECORD_1 = new TransactionStatus("1", "PENDING", "if(test=true){logger.info(\"bad\")}", "if(test==true){logger.info(\"bad\")}");
    TransactionStatus RECORD_2 = new TransactionStatus("2",  "FINISHED", "if(test=true){logger.info(\"bad\")}", "if(test==true){logger.info(\"bad\")}");
    TransactionStatus RECORD_3 = new TransactionStatus("3",  "ERROR", "if(test=true){logger.info(\"bad\")}", "if(test==true){logger.info(\"bad\")}");
    
    @Test
    public void getPendingTransaction_success() throws Exception {        
        
        
        Mockito.when(mockTranRep.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/1")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
		        .andExpect(jsonPath("$", CoreMatchers.notNullValue()))
		        .andExpect(jsonPath("$.status", is("PENDING")));
    }
    
    @Test
    public void getFinishedTransaction_success() throws Exception {        
        
        
        Mockito.when(mockTranRep.findById(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/2")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
		        .andExpect(jsonPath("$", CoreMatchers.notNullValue()))
		        .andExpect(jsonPath("$.status", is("FINISHED")));
    }
    
    @Test
    public void getErrorTransaction_success() throws Exception {        
        
        
        Mockito.when(mockTranRep.findById(RECORD_3.getId())).thenReturn(Optional.of(RECORD_3));
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/3")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
		        .andExpect(jsonPath("$", CoreMatchers.notNullValue()))
		        .andExpect(jsonPath("$.status", is("ERROR")));
    }
}
