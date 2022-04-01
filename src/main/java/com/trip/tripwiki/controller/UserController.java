package com.trip.tripwiki.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/users")
	public Map<String, Object> call() {
		Map<String, Object> test = new HashMap<String,Object>();
		test.put("id", "admin");
		test.put("password", "1234");
		return test;
	}
	
	
}
