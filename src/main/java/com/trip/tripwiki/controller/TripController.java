package com.trip.tripwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TripController {

	
	
	@GetMapping(value= {"/trip/TripList"})
	 public String TripList(String keyword) { 
	    return "index.html"; 
	}
	
	@GetMapping(value= {"/trip/TripDetail/{contentId}/{areacode}"})
	 public String Detail(@PathVariable String contentId, String areacode) { 
	    return "index.html"; 
	}


}
