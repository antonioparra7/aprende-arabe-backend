package com.aprendearabe.backend.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.models.entities.Theme;
import com.aprendearabe.backend.app.services.LevelService;
import com.aprendearabe.backend.app.services.ThemeService;

@RestController
@RequestMapping("/api/v1/themes")
public class ThemeController {
	@Autowired
	private LevelService levelService;
	@Autowired
	private ThemeService themeService;
	
	@GetMapping("")
	public List<Theme> getThemes(){
		return themeService.getAll();
	}
	
	@GetMapping("/{id}")
	public Theme getThemeById(@PathVariable Long id){
		return themeService.getById(id);
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Theme createTheme(@RequestBody Theme theme) {
		Level level = levelService.getById(theme.getLevel().getId());
		if (level!=null) {
			theme.setLevel(level);
			return themeService.save(theme);
		}
		else {
			return null;
		}
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Theme updateTheme(@RequestBody Theme theme,@PathVariable Long id) {
		Theme themeActual = themeService.getById(id);
		if (themeActual!=null) {
			themeActual.setName(theme.getName());
			themeActual.setImage(theme.getImage());
			return themeService.save(themeActual);
		}
		else {
			return null;
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTheme(@PathVariable Long id) {
		themeService.deleteById(id);
	}
}
