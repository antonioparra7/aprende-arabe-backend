package com.aprendearabe.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aprendearabe.backend.app.services.ScrapingService;

@Controller
@RequestMapping("/api/v1/scraping")
public class ScrapingController {
	@Autowired
	private ScrapingService scrapingService;
	
	@PostMapping("/themes")
	public ResponseEntity<String> insertThemes(){
		scrapingService.insertThemes();
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}

}
