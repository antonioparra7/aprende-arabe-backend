package com.aprendearabe.backend.app.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aprendearabe.backend.app.models.entities.Role;
import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.models.repositories.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final JwtService jwtService;
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
		
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

}
