package com.trip.tripwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GalleryPathController {
	
	@GetMapping(value="/gallery")
	public String gallery() {
		return "index.html";
	}
	
	@GetMapping(value="/galleryWrite")
	public String galleryWrite() {
		return "index.html";
	}
	
	@GetMapping(value="/galleryDetail/{num}")
	public String galleryDetail(@PathVariable int num) {
		return "index.html";
	}
	
	@GetMapping(value="/galleryModify/{num}")
	public String galleryModify(@PathVariable int num) {
		return "index.html";
	}
	
	
	
	
	
}
