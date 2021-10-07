package com.entonomachia.controllers;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entonomachia.domains.Code;

@Controller
@RequestMapping("/")
public class MainPageController {
	@GetMapping
	public String mainPage() {
		return "mainPage";
	}
	
	@PostMapping
	 public String processCode(@Valid @ModelAttribute("code") Code code, Errors errors) {
		 if (errors.hasErrors()) {
			 return "design";
		 }
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String currentPrincipalName = authentication.getName();
		 //TODO get the group from a database, for now every user is in SELF group
		 code.setUser(currentPrincipalName);
		 code.setGroup("SELF");
		 return "redirect:/orders/current";
	 }
}
