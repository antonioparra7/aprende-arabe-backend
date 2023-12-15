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

import com.aprendearabe.backend.app.models.entities.Theme;
import com.aprendearabe.backend.app.models.entities.Lesson;
import com.aprendearabe.backend.app.services.ThemeService;
import com.aprendearabe.backend.app.services.LessonService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/lessons")
public class LessonController {
	@Autowired
	private ThemeService themeService;
	@Autowired
	private LessonService lessonService;

	@GetMapping("")
	public ResponseEntity<?> getLessons() {
		List<Lesson> lessons = null;
		try {
			lessons = lessonService.getAll();
			return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/themeId/{id}")
	public ResponseEntity<?> getLessonsByThemeId(@PathVariable Long id) {
		List<Lesson> lessons = null;
		try {
			if (themeService.getById(id) != null) {
				lessons = lessonService.getAllByThemeId(id);
				return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(String.format("Theme con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getLessonById(@PathVariable Long id) {
		Lesson lesson = null;
		try {
			lesson = lessonService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (lesson != null) {
			return new ResponseEntity<Lesson>(lesson, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Lesson con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createLesson(@RequestParam String name, @RequestParam Long themeId) throws IOException {
		Lesson lessonAdded = null;
		try {
			Theme theme = themeService.getById(themeId);
			if (theme != null) {
				Lesson lesson = new Lesson(name, theme);
				lessonAdded = lessonService.save(lesson);
			} else {
				return new ResponseEntity<String>(String.format("Theme con id: %d no existe en base de datos", themeId),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (lessonAdded != null && lessonAdded.getId() > 0) {
			return new ResponseEntity<String>("Lesson añadida con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir lesson", HttpStatus.NOT_FOUND);
		}
	}

	// Añadir metodo update

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
		try {
			Lesson lesson = lessonService.getById(id);
			if (lesson == null) {
				return new ResponseEntity<String>(String.format("Lesson con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			lessonService.deleteById(id);
			return new ResponseEntity<String>("Lesson eliminado con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
