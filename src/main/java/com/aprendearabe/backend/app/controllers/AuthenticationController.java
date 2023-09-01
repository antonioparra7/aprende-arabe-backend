package com.aprendearabe.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aprendearabe.backend.app.auth.jwt.AuthenticationRequest;
import com.aprendearabe.backend.app.auth.jwt.AuthenticationResponse;
import com.aprendearabe.backend.app.auth.jwt.AuthenticationService;
import com.aprendearabe.backend.app.auth.jwt.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(authenticationService.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}
