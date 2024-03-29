package com.entonomachia.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entonomachia.domains.CodeToBeClassified;
import com.entonomachia.domains.ImproveModelData;
import com.entonomachia.domains.TransactionStatus;
import com.entonomachia.repositories.TransactionRepository;
import com.entonomachia.services.KafkaProducer;
import com.entonomachia.services.RedisInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/")
public class InterfaceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceController.class);
	
	Gson gson = null;

	@Autowired
	KafkaProducer kafProd;
	
	@Autowired
	RedisInterface idGen;
	
	@Autowired
	TransactionRepository tranRep;
	
	public InterfaceController() {
		gson = new GsonBuilder().disableHtmlEscaping().create();
	}
	
	@GetMapping("")
	public @ResponseBody String getRoot () {
//		TODO create a simple homepage
		return "ENTONOMACHIA REST API";
	}
	
	/* CRUD OPERATIONS */
	
	// CREATE
	@PostMapping("")
	String classifyCode(@Valid @RequestBody CodeToBeClassified newCode) {
		
		String placeholder="";
		String newId = idGen.getNewId().toString();
		newCode.setIds(newId);
		placeholder = gson.toJson(newCode);
		kafProd.publishToTopic(placeholder,"usercode");	
		

		TransactionStatus tran = new TransactionStatus();
		tran.setId(newId);
		tran.setStatus("PENDING");
		tran.setResultError("");
		tran.setResultMutation("");
		tranRep.save(tran);
		return tran.toString();

	}
	
	// Sharing code
	@PutMapping("")
	String PUTsendImproveModelData(@Valid @RequestBody ImproveModelData newCode) {
		
		String placeholder="";
		String newId = idGen.getNewId().toString();
		newCode.setIds(newId);
		newCode.setCodeWithNoComments(newCode.getCode());
		newCode.setSolutionWithNoComments(newCode.getSolution());
		placeholder = gson.toJson(newCode);
		kafProd.publishToTopic(placeholder,"improvemodelcode");	
		

		TransactionStatus tran = new TransactionStatus();
		tran.setId(newId);
		tran.setStatus("FINISHED");
		tran.setResultError("Thanks for the contribution");
		tran.setResultMutation("Thanks for the contribution");
		tranRep.save(tran);
		return tran.toString();

	}

	
	@PostMapping("/share")
	String sendImproveModelData(@Valid @RequestBody ImproveModelData newCode) {
		
		String placeholder="";
		String newId = idGen.getNewId().toString();
		newCode.setIds(newId);
		newCode.setCodeWithNoComments(newCode.getCode());
		newCode.setSolutionWithNoComments(newCode.getSolution());
		placeholder = gson.toJson(newCode);
		kafProd.publishToTopic(placeholder,"improvemodelcode");	
		

		TransactionStatus tran = new TransactionStatus();
		tran.setId(newId);
		tran.setStatus("FINISHED");
		tran.setResultError("Thanks for the contribution");
		tran.setResultMutation("Thanks for the contribution");
		tranRep.save(tran);
		return tran.toString();

	}
	
	// READ
	@GetMapping("/{id}")
	public @ResponseBody String getTransactionStatus (
			@PathVariable String id) {
		if(tranRep.findById(id).isPresent())
			return tranRep.findById(id).get().toString();
		return "Transaction ID not found";
	}
	
	// UPDATE
	@PutMapping("/{user}")
	public @ResponseBody String updateUser(
			@PathVariable String id,
			@Valid @RequestBody CodeToBeClassified newUser) {
		return null;
		//TODO i do not know if this method is useful to me
	}
	
	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAlien(@PathVariable String id) {
		if(tranRep.existsById(id)) {
			tranRep.deleteById(id);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//TODO other methods to delete single repositories by user, other things
}
