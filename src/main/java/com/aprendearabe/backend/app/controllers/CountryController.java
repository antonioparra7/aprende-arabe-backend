package com.aprendearabe.backend.app.controllers;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.models.entities.Country;
import com.aprendearabe.backend.app.services.CountryService;

import jakarta.annotation.PostConstruct;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
	@Autowired
	private CountryService countryService;
	
	@PostConstruct
	public void init() throws IOException {
		if (countryService.getAll().size()==0) {
			countryService.addAllCountriesApi();
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getCountries(){
		List<Country> countries = null;
		try {
			countries = countryService.getAll();
			return new ResponseEntity<List<Country>>(countries,HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCountryById(@PathVariable Long id) {
		Country country = null;
		try {
			country = countryService.getById(id);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (country!=null) {
			return new ResponseEntity<Country>(country,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(String.format("Country con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
		}
	}
}
