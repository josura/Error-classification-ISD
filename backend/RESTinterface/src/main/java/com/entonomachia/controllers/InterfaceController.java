package com.entonomachia.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.entonomachia.domains.TransactionStatus;
import com.entonomachia.repositories.TransactionRepository;
import com.entonomachia.services.KafkaConsumer;
import com.entonomachia.services.KafkaProducer;
import com.entonomachia.services.RedisInterface;

@RestController
@RequestMapping("/")
public class InterfaceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceController.class);
	
	@Autowired
	KafkaConsumer kafCons;

	@Autowired
	KafkaProducer kafProd;
	
	@Autowired
	RedisInterface idGen;
	
	@Autowired
	TransactionRepository tranRep;
	
	@GetMapping("")
	public @ResponseBody String getRoot () {
		//idGen.testConnection();
		TransactionStatus tran = new TransactionStatus();
		tran.setId("pippo1");
		tran.setStatus("FINISHED");
		tran.setResult("[{test=\"test\"},{test=\"visione\"}]");
		tranRep.save(tran);
		return tranRep.findById("pippo1").get().toString();
	}
	
//	@GetMapping("")
//	public @ResponseBody String getIdTransactionStatus (
//			@RequestParam String id) {
//		return tranRep.findById(id).get().toString();
//	}
	
//	@GetMapping("")
//	public @ResponseBody String getUserGroupRepo (
//			@RequestParam String user,
//			@RequestParam String group) {
//		//idGen.testConnection();
//		System.out.println( idGen.getNewId());
//		return "Test";
//	}
	
	/* CRUD OPERATIONS */
	
	// CREATE
	@PostMapping("")
	CodeToBeClassified classifyCode(@Valid @RequestBody CodeToBeClassified newAlien) {
		
		String placeholder="";
		//TODO Marshalling of codeToBeClassified
		kafProd.publishToTopic(placeholder);
		
		//TODO fix consumer to take only the right record(the one that was sent)
		return null;
		//TODO
	}
	
	// READ
	@GetMapping("/{user}")
	public @ResponseBody String getUserRepositories (
			@PathVariable String id) {
		return null;
		//TODO
	}
	
	// UPDATE
	@PutMapping("/{user}")
	public @ResponseBody String updateUser(
			@PathVariable String id,
			@Valid @RequestBody CodeToBeClassified newAlien) {
		return null;
		//TODO i do not know if this method is useful to me
	}
	
	// DELETE
	@DeleteMapping("/{user}")
	void deleteAlien(@PathVariable String user) {
		//TODO delete user repositories
	}
	
	//TODO other methods to delete single repositories by id, other things
}
