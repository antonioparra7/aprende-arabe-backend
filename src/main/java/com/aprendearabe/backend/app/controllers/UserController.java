package com.aprendearabe.backend.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.models.entities.Level;
import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.services.LevelService;
import com.aprendearabe.backend.app.services.UserService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired 
	private UserService userService;
	@Autowired
	private LevelService levelService;
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username){
		User user = null;
		try {
			user = userService.getByUsername(username);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (user!=null) {
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(String.format("User con username: %s no existe en base de datos",username),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getLevelId(@PathVariable Long id){
		User user = null;
		try {
			user = userService.getById(id);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (user!=null) {
			Long levelId = (user.getLevel()!=null)?user.getLevel().getId():null;
			if(levelId!=null) {
				return new ResponseEntity<Long>(levelId,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("El usuario no ha seleccionado ningún nivel",HttpStatus.NO_CONTENT);
			}
			
		}
		else {
			return new ResponseEntity<String>(String.format("User con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable String email){
		User user = null;
		try {
			user = userService.getByEmail(email);
		}catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (user!=null) {
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(String.format("User con email: %s no existe en base de datos",email),HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateLevelUser(@RequestBody Map<String, Long> request, @PathVariable Long id) {
		User userActual = null;
		Level level = null;
		try {
			userActual = userService.getById(id);
			if (userActual!=null) {
				level = levelService.getById(request.get("levelId"));
				if(level!=null) {
					userActual.setLevel(level);
					return new ResponseEntity<User>(userService.save(userActual),HttpStatus.OK);
				}
				else {
					return new ResponseEntity<String>(String.format("Level con id: %d no existe en base de datos",request.get("levelId")),HttpStatus.NOT_FOUND);
				}
			}
			else {
				return new ResponseEntity<String>(String.format("User con id: %d no existe en base de datos",id),HttpStatus.NOT_FOUND);
			}
		} catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(String.format("Ha ocurrido un error: %s",e.getMessage()),HttpStatus.NOT_FOUND);
		}
		
	}
	
}
