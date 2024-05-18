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

import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.models.entities.Theme;
import com.aprendearabe.backend.app.services.LevelService;
import com.aprendearabe.backend.app.services.ThemeService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/themes")
public class ThemeController {
	@Autowired
	private LevelService levelService;
	@Autowired
	private ThemeService themeService;

	@GetMapping("")
	public ResponseEntity<?> getThemes() {
		List<Theme> themes = null;
		try {
			themes = themeService.getAll();
			return new ResponseEntity<List<Theme>>(themes, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/levelId/{id}")
	public ResponseEntity<?> getThemesByLevelId(@PathVariable Long id) {
		List<Theme> themes = null;
		try {
			if (levelService.getById(id) != null) {
				themes = themeService.getAllByLevelId(id);
				return new ResponseEntity<List<Theme>>(themes, HttpStatus.OK);
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
	public ResponseEntity<?> getThemeById(@PathVariable Long id) {
		Theme theme = null;
		try {
			theme = themeService.getById(id);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (theme != null) {
			return new ResponseEntity<Theme>(theme, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format("Theme con id: %d no existe en base de datos", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> createTheme(@RequestParam String name, @RequestParam MultipartFile image,
			@RequestParam Long levelId) throws IOException {
		Theme themeAdded = null;
		try {
			Level level = levelService.getById(levelId);
			if (level != null) {
				Theme theme = new Theme(name, image.getBytes(), level);
				themeAdded = themeService.save(theme);
			} else {
				return new ResponseEntity<String>(String.format("Level con id: %d no existe en base de datos", levelId),
						HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (themeAdded != null && themeAdded.getId() > 0) {
			return new ResponseEntity<String>("Theme añadido con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al añadir theme", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTheme(@PathVariable Long id) {
		try {
			Theme theme = themeService.getById(id);
			if (theme == null) {
				return new ResponseEntity<String>(String.format("Theme con id: %d no existe en base de datos", id),
						HttpStatus.NOT_FOUND);
			}
			themeService.deleteById(id);
			return new ResponseEntity<String>("Theme eliminado con éxito", HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}