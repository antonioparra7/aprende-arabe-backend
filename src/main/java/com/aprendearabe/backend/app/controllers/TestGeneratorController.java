package com.aprendearabe.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aprendearabe.backend.app.services.TestGeneratorService;

@Controller
@RequestMapping("/api/v1/testGenerator")
public class TestGeneratorController {
	@Autowired
	private TestGeneratorService testGeneratorService;
	
	@PostMapping("/tests")
	public ResponseEntity<String> insertTests(){
		testGeneratorService.testGenerator();
		return ResponseEntity.status(HttpStatus.OK).body("OK");
	}

}
