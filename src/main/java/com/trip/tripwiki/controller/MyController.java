package com.trip.tripwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MyController {

	@GetMapping(value="/trip/myInfo/{user_id}")
	public String myInfo(@PathVariable String user_id) {
		return "index.html";
	}
	
	@GetMapping(value="/trip/Memberlist")
	public String Memberlist() {
		return "index.html";
	}
}
	
