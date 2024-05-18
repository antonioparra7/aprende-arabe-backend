package com.aprendearabe.backend.app.controllers;

import java.io.IOException;


import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.services.LevelService;

import jakarta.annotation.PostConstruct;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1/levels")
public class LevelController {
	@Autowired
	private LevelService levelService;

	@PostConstruct
	public void init() throws IOException {
		if(levelService.getById(1L)==null) {
			Resource resourceA1 = new ClassPathResource("static/img/level_a1.PNG");
			byte[] imageA1DefaultBytes = Files.readAllBytes(resourceA1.getFile().toPath());
			Resource resourceA2 = new ClassPathResource("static/img/level_a2.PNG");
			byte[] imageA2DefaultBytes = Files.readAllBytes(resourceA2.getFile().toPath());
			Resource resourceB1 = new ClassPathResource("static/img/level_b1.PNG");
			byte[] imageB1DefaultBytes = Files.readAllBytes(resourceB1.getFile().toPath());
			Resource resourceB2 = new ClassPathResource("static/img/level_b2.PNG");
			byte[] imageB2DefaultBytes = Files.readAllBytes(resourceB2.getFile().toPath());
			Resource resourceC1 = new ClassPathResource("static/img/level_c1.PNG");
			byte[] imageC1DefaultBytes = Files.readAllBytes(resourceC1.getFile().toPath());
			Level levelA1 = new Level("A1", imageA1DefaultBytes);
			levelService.save(levelA1);
			Level levelA2 = new Level("A2", imageA2DefaultBytes);
			levelService.save(levelA2);
			Level levelB1 = new Level("B1", imageB1DefaultBytes);
			levelService.save(levelB1);
			Level levelB2 = new Level("B2", imageB2DefaultBytes);
			levelService.save(levelB2);
			Level levelC1 = new Level("C1", imageC1DefaultBytes);
			levelService.save(levelC1);
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getLevels() {
		List<Level> levels = null;
		try {
			levels = levelService.getAll();
			return new ResponseEntity<List<Level>>(levels, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getLevelById(@PathVariable Long id) {
		Level level = null;
		try {
			level = levelService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (level != null) {
			return new ResponseEntity<Level>(level, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Level con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createLevel(@RequestParam String name, @RequestParam MultipartFile image)
			throws IOException {
		Level levelAdded = null;
		try {
			Level level = new Level(name, image.getBytes());
			levelAdded = levelService.save(level);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (levelAdded != null && levelAdded.getId() > 0) {
			return new ResponseEntity<String>("Level añadido con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir level", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteLevel(@PathVariable Long id) {
		try {
			Level level = levelService.getById(id);
			if (level == null) {
				return new ResponseEntity<String>(String.format("Level con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			levelService.deleteById(id);
			return new ResponseEntity<String>("Level eliminado con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}