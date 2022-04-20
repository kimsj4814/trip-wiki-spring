package com.trip.tripwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserVueController {

	@GetMapping("/trip/login")
	public String LoginView() {
		return "index.html";
	}
	@GetMapping("/trip/Idsearch")
	public String LoginFinder() {
		return "index.html";
	}
	@GetMapping("/trip/PwdSearch")
	public String PwdSearch() {
		return "index.html";
	}
	@GetMapping("/trip/join")
	public String Join() {
		return "index.html";
	}
	
	
	@GetMapping("/trip/auth")
	public String mailAuth() {
		return "index.html";	
	}
}
