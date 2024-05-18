package com.aprendearabe.backend.app.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Triplet;
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

import jakarta.annotation.PostConstruct;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/tutorials")
public class TutorialController {
	@Autowired
	private TutorialService tutorialService;

	@PostConstruct
	public void init() {
		if (tutorialService.getAll().size() == 0) {
			List<Triplet<String, String, String>> triplets = new ArrayList<>();
			triplets.add(new Triplet<String, String, String>("Iniciar sesión en AprendeArabe",
					"Aprende a acceder a la aplicación AprendeArabe", "mIk5oCKIuzU"));
			triplets.add(new Triplet<String, String, String>("Registrarse en AprendeArabe",
					"Aprende a acceder a la aplicación AprendeArabe", "V4jq-E2e4Oc"));
			triplets.add(new Triplet<String, String, String>("Realizar una lección en AprendeArabe",
					"Aprende a realizar una lección en la aplicación AprendeArabe", "8vRJ7-Hu8RA"));
			triplets.add(new Triplet<String, String, String>("Realizar un test en AprendeArabe",
					"Aprende a realizar un test en la aplicación AprendeArabe", "-SG9Dj7D0Us"));
			for (Triplet<String, String, String> triplet : triplets) {
				Tutorial tutorial = new Tutorial(triplet.getValue0(), triplet.getValue1(), triplet.getValue2(), "");
				tutorialService.save(tutorial);
			}
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getTutorials() {
		List<Tutorial> tutorials = null;
		try {
			tutorials = tutorialService.getAll();
			return new ResponseEntity<List<Tutorial>>(tutorials, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTutorialById(@PathVariable Long id) {
		Tutorial tutorial = null;
		try {
			tutorial = tutorialService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tutorial != null) {
			return new ResponseEntity<Tutorial>(tutorial, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Tutorial con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/link/{link}")
	public ResponseEntity<?> getTutorialByLink(@PathVariable String link) {
		Tutorial tutorial = null;
		try {
			tutorial = tutorialService.getByLink(link);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tutorial != null) {
			return new ResponseEntity<Tutorial>(tutorial, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Tutorial con link: %s no existe en base de datos", link),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createTutorial(@RequestParam String name, @RequestParam String description,
			@RequestParam String link, @RequestParam String destinedTo) throws IOException {
		Tutorial tutorialAdded = null;
		try {
			Tutorial tutorial = new Tutorial(name, description, link, destinedTo);
			tutorialAdded = tutorialService.save(tutorial);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (tutorialAdded != null && tutorialAdded.getId() > 0) {
			return new ResponseEntity<String>("Tutorial añadido con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir tutorial", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTutorial(@PathVariable Long id) {
		try {
			Tutorial tutorial = tutorialService.getById(id);
			if (tutorial == null) {
				return new ResponseEntity<String>(String.format("Tutorial con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			tutorialService.deleteById(id);
			return new ResponseEntity<String>("Tutorial eliminado con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
