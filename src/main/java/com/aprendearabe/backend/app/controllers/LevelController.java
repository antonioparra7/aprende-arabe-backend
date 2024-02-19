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
import com.aprendearabe.backend.app.services.LevelService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/levels")
public class LevelController {
	@Autowired
	private LevelService levelService;
	
	@GetMapping("")
	public ResponseEntity<?> getLevels(){
		List<Level> levels = null;
		try {
			levels = levelService.getAll();
			return new ResponseEntity<List<Level>>(levels,HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getLevelById(@PathVariable Long id){
		Level level = null;
		try {
			level = levelService.getById(id);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (level!=null) {
			return new ResponseEntity<Level>(level,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(String.format("Level con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<String> createLevel(@RequestParam String name, @RequestParam MultipartFile image) throws IOException {
		Level levelAdded = null;
		try {
			Level level = new Level(name, image.getBytes());
			levelAdded = levelService.save(level);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar insert en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (levelAdded!=null && levelAdded.getId()>0) {
			return new ResponseEntity<String>("Level añadido con éxito",HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>("Error al añadir level",HttpStatus.NOT_FOUND);
		}
	}
	
	// Añadir metodo update
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteLevel(@PathVariable Long id) {
		try {
			Level level = levelService.getById(id);
			if(level==null) {
				return new ResponseEntity<String>(String.format("Level con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
			}
			levelService.deleteById(id);
			return new ResponseEntity<String>("Level eliminado con éxito",HttpStatus.OK);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar delete en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
