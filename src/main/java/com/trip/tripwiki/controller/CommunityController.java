package com.trip.tripwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommunityController {
	
	@GetMapping(value= {"/trip/communitys"})
	public String com1() {
	return "index.html";
	}
	
	@GetMapping(value= {"/trip/communitys/{num}"})
	public String com2(@PathVariable int num) {
		return "index.html";
	}
	
	@GetMapping(value= {"/trip/boards/down"})
	public String com3() {
		return "index.html";
	}
	
	@GetMapping(value= {"/trip/replyView"})
	public String com4() {
		return "index.html";
	}
	
	@GetMapping(value= {"/trip/comcomments"})
	public String com5() {
		return "index.html";
	}
	
	@GetMapping(value= {"/trip/communityWrite"})
	public String com6() {
		return "index.html";
	}

}
