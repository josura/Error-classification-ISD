package com.entonomachia.controllers;

import javax.validation.Valid;

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

@RestController
@RequestMapping("/")
public class InterfaceController {
	@GetMapping("")
	public @ResponseBody String getUserGroupRepo (
			@RequestParam String user,
			@RequestParam String group) {
		
		return "Test";
	}
	
	/* CRUD OPERATIONS */
	
	// CREATE
	@PostMapping("")
	CodeToBeClassified createAlien(@Valid @RequestBody CodeToBeClassified newAlien) {
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
