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
import com.aprendearabe.backend.app.services.LevelService;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/levels")
public class LevelController {
	@Autowired
	private LevelService levelService;
	
	@GetMapping("")
	public List<Level> getLevels(){
		return levelService.getAll();
	}
	
	@GetMapping("/{id}")
	public Level getLevelById(@PathVariable Long id){
		Level level = levelService.getById(id);
		if (level!=null) {
			return level;
		}
		else {
			return null;
		}
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Level createLevel(@RequestBody Level level) {
		return levelService.save(level);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Level updateLevel(@RequestBody Level level,@PathVariable Long id) {
		Level levelActual = levelService.getById(id);
		if (levelActual!=null) {
			levelActual.setName(level.getName());
			levelActual.setImage(level.getImage());
			return levelService.save(levelActual);
		}
		else {
			return null;
		}
		
	}	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLevel(@PathVariable Long id) {
		levelService.deleteById(id);
	}
}
