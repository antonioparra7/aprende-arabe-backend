package com.aprendearabe.backend.app.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.models.entities.Country;
import com.aprendearabe.backend.app.services.CountryService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	@PostMapping("/addCountriesApi")
	public ResponseEntity<String> addCountriesApi() throws IOException{
		boolean res = countryService.addAllCountriesApi();
		if (res) {
			return ResponseEntity.ok("Paises insertados en base de datos correctamente");
		}
		else {
			return ResponseEntity.ok("ERROR");
		}
	}
	
	@GetMapping("")
	public List<Country> countries(){
		return countryService.getAll();
	}
	
	@GetMapping("/{id}")
	public Country getCountryById(@PathVariable Long id) {
		return countryService.getById(id);
	}
	
	
}
