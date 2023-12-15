package com.aprendearabe.backend.app.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.auth.jwt.AuthenticationRequest;
import com.aprendearabe.backend.app.auth.jwt.AuthenticationResponse;
import com.aprendearabe.backend.app.auth.jwt.AuthenticationService;
import com.aprendearabe.backend.app.auth.jwt.RegisterRequest;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) throws ParseException{
		try {
			return new ResponseEntity<AuthenticationResponse>(authenticationService.register(request),HttpStatus.OK);
		}
		catch (DataAccessException e) {
			return new ResponseEntity<String>(
					String.format("Error al crear usuario en base de datos: ".concat(e.getMessage())),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(String.format("Se ha producido un error: ".concat(e.getMessage())),HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
		try {
			return new ResponseEntity<AuthenticationResponse>(authenticationService.authenticate(request),HttpStatus.OK);
		}
		catch (BadCredentialsException e) {
			return new ResponseEntity<String>("La contraseña introducida no es correcta",HttpStatus.BAD_REQUEST);
		}
		catch(DataAccessException e) {
			return new ResponseEntity<String>(String.format("Error al realizar consultar las credenciales del usuario en base de datos: ".concat(e.getMessage())),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(String.format("Se ha producido un error: ".concat(e.getMessage())),HttpStatus.NOT_FOUND);
		}
	}
}
