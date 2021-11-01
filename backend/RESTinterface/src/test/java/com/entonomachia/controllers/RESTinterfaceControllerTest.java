package com.entonomachia.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.entonomachia.controllers.InterfaceController;
import com.entonomachia.domains.CodeToBeClassified;
import com.entonomachia.domains.ImproveModelData;
import com.entonomachia.domains.TransactionStatus;
import com.entonomachia.repositories.TransactionRepository;
import com.entonomachia.services.KafkaProducer;
import com.entonomachia.services.RedisInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
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
    
    @Test
    public void postClassifyRequest_success() throws Exception {
        CodeToBeClassified record = new CodeToBeClassified();
                record.setIds("4");
                record.setUser("TEST");
                record.setGroup("SELF");
                record.setCode("testing.code");
        TransactionStatus RECORD_4 = new TransactionStatus("4", "PENDING", "", "");

                
        Mockito.when(mockRedisInterface.getNewId()).thenReturn(4);
                
        Mockito.doNothing().doThrow(new RuntimeException()).when(mockKafkaProd).publishToTopic(gson.toJson(record),"usercode");

        Mockito.when(mockTranRep.save(RECORD_4)).thenReturn(RECORD_4);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.id", is("4")))
        		.andExpect(jsonPath("$.status", is("PENDING")));
        }
    
    @Test
    public void postImproveRequest_success() throws Exception {
        ImproveModelData record = new ImproveModelData();
                record.setIds("5");
                record.setUser("TEST");
                record.setGroup("SELF");
                record.setCode("testing.code()");
                record.setError("testing.code(morning)");
        TransactionStatus RECORD_5 = new TransactionStatus("5", "FINISHED", "Thanks for the contribution", "Thanks for the contribution");

                
        Mockito.when(mockRedisInterface.getNewId()).thenReturn(4);
                
        Mockito.doNothing().doThrow(new RuntimeException()).when(mockKafkaProd).publishToTopic(gson.toJson(record),"improvemodelcode");

        Mockito.when(mockTranRep.save(RECORD_5)).thenReturn(RECORD_5);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/share")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.status", is("FINISHED")));
    }

    @Test
    public void putImproveRequest_success() throws Exception {
        ImproveModelData record = new ImproveModelData();
                record.setIds("5");
                record.setUser("TEST");
                record.setGroup("SELF");
                record.setCode("testing.code()");
                record.setError("testing.code(morning)");
        TransactionStatus RECORD_5 = new TransactionStatus("5", "FINISHED", "Thanks for the contribution", "Thanks for the contribution");

                
        Mockito.when(mockRedisInterface.getNewId()).thenReturn(4);
                
        Mockito.doNothing().doThrow(new RuntimeException()).when(mockKafkaProd).publishToTopic(gson.toJson(record),"improvemodelcode");

        Mockito.when(mockTranRep.save(RECORD_5)).thenReturn(RECORD_5);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.status", is("FINISHED")));
    }

    
    @Test
    public void deleteTransaction_success() throws Exception {
    	Mockito.when(mockTranRep.existsById("6")).thenReturn(true);
    	Mockito.doNothing().doThrow(new RuntimeException()).when(mockTranRep).deleteById("6");
    	
    	mockMvc.perform(MockMvcRequestBuilders
                .delete("/6")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk());
    }
    
    @Test
    public void deleteTransaction_notexists() throws Exception {
    	Mockito.when(mockTranRep.existsById("7")).thenReturn(false);
    	
    	mockMvc.perform(MockMvcRequestBuilders
                .delete("/7")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound());
    }
}
