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
import org.springframework.web.multipart.MultipartFile;

import com.aprendearabe.backend.app.models.entities.Test;
import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.services.TestService;
import com.aprendearabe.backend.app.services.LevelService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/tests")
public class TestController {
	@Autowired
	private LevelService levelService;
	@Autowired
	private TestService testService;

	@GetMapping("")
	public ResponseEntity<?> getTests() {
		List<Test> tests = null;
		try {
			tests = testService.getAll();
			return new ResponseEntity<List<Test>>(tests, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/levelId/{id}")
	public ResponseEntity<?> getTestsByLevelId(@PathVariable Long id) {
		List<Test> tests = null;
		try {
			if (testService.getById(id) != null) {
				tests = testService.getAllByLevelId(id);
				return new ResponseEntity<List<Test>>(tests, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(String.format("Level con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTestById(@PathVariable Long id) {
		Test test = null;
		try {
			test = testService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (test != null) {
			return new ResponseEntity<Test>(test, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Test con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createTest(@RequestParam String name, @RequestParam MultipartFile image,
			@RequestParam Long levelId) throws IOException {
		Test testAdded = null;
		try {
			Level level = levelService.getById(levelId);
			if (level != null) {
				Test test = new Test(name, level);
				testAdded = testService.save(test);
			} else {
				return new ResponseEntity<String>(
						String.format("Level con id: %d no existe en base de datos", levelId), HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (testAdded != null && testAdded.getId() > 0) {
			return new ResponseEntity<String>("Test añadido con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir test", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTest(@PathVariable Long id) {
		try {
			Test test = testService.getById(id);
			if (test == null) {
				return new ResponseEntity<String>(String.format("Test con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			testService.deleteById(id);
			return new ResponseEntity<String>("Test eliminado con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}