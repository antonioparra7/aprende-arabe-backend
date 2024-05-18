package com.aprendearabe.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.auth.jwt.JwtService;
import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.services.UserService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;

	@GetMapping("")
	public ResponseEntity<?> getData(@RequestHeader("Authorization") String authorization) {
		try {
			String token = authorization.split(" ")[1];
			if (!jwtService.isTokenExpired(token)) {
				String username = jwtService.extractUsername(token);
				User user = userService.getByUsername(username);
				if(user!=null) {
					return new ResponseEntity<User>(user,HttpStatus.OK);
				}
				else {
					return new ResponseEntity<String>(String.format("Usuario con username %s no encontrado",username),HttpStatus.NOT_FOUND);
				}
				
			} else {
				return new ResponseEntity<String>("Token inválido", HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al realizar consulta en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<String>(
					String.format("Se ha producido un error: ".concat(e.getMessage())),
					HttpStatus.NOT_FOUND);
		}
	}
}