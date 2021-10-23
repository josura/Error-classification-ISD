package com.entonomachia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.entonomachia.controllers.InterfaceController;
import com.entonomachia.domains.TransactionStatus;
import com.entonomachia.repositories.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(InterfaceController.class)
public class RESTinterfaceControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    TransactionRepository mockTranRep;
    
    TransactionStatus RECORD_1 = new TransactionStatus("1", "PENDING", "if(test=true){logger.info(\"bad\")}", "if(test==true){logger.info(\"bad\")}");
    TransactionStatus RECORD_2 = new TransactionStatus("2",  "FINISHED", "if(test=true){logger.info(\"bad\")}", "if(test==true){logger.info(\"bad\")}");
    TransactionStatus RECORD_3 = new TransactionStatus("3",  "ERROR", "if(test=true){logger.info(\"bad\")}", "if(test==true){logger.info(\"bad\")}");
    
    // ... Test methods TBA
}
