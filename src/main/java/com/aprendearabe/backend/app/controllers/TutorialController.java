package com.aprendearabe.backend.app.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.models.entities.Tutorial;
import com.aprendearabe.backend.app.services.TutorialService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/tutorials")
public class TutorialController {
	@Autowired
	private TutorialService tutorialService;
	
	@GetMapping("")
	public ResponseEntity<?> getTutorials(){
		List<Tutorial> tutorials = null;
		try {
			tutorials = tutorialService.getAll();
			return new ResponseEntity<List<Tutorial>>(tutorials,HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTutorialById(@PathVariable Long id){
		Tutorial tutorial = null;
		try {
			tutorial = tutorialService.getById(id);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tutorial!=null) {
			return new ResponseEntity<Tutorial>(tutorial,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(String.format("Tutorial con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<String> createTutorial(@RequestParam String name, @RequestParam String description, @RequestParam String link, @RequestParam String destinedTo) throws IOException {
		Tutorial tutorialAdded = null;
		try {
			Tutorial tutorial = new Tutorial(name, description, link, destinedTo);
			tutorialAdded = tutorialService.save(tutorial);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (tutorialAdded!=null && tutorialAdded.getId()>0) {
			return new ResponseEntity<String>("Tutorial añadido con éxito",HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>("Error al añadir tutorial",HttpStatus.NOT_FOUND);
		}
	}
	
	// Añadir metodo update
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTutorial(@PathVariable Long id) {
		try {
			Tutorial tutorial = tutorialService.getById(id);
			if(tutorial==null) {
				return new ResponseEntity<String>(String.format("Tutorial con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
			}
			tutorialService.deleteById(id);
			return new ResponseEntity<String>("Tutorial eliminado con éxito",HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
