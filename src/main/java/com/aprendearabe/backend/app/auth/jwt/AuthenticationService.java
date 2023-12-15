package com.aprendearabe.backend.app.auth.jwt;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aprendearabe.backend.app.models.entities.Gender;
import com.aprendearabe.backend.app.models.entities.Role;
import com.aprendearabe.backend.app.models.entities.User;
import com.aprendearabe.backend.app.models.repositories.IUserRepository;
import com.aprendearabe.backend.app.services.CountryService;
import com.aprendearabe.backend.app.services.ParameterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired 
	private CountryService countryService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final JwtService jwtService;
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) throws ParseException {
		User user;
		Date birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthdate());
		Integer age = calculateAge(birthdate);
		Gender gender = (request.getGender().toLowerCase().equals("male"))?Gender.MALE:Gender.FEMALE;
		if(request.getIsTeacher()) {
			user = User.builder()
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.birthdate(birthdate)
					.age(age)
					.gender(gender)
					.email(request.getEmail())
					.phone(request.getPhone())
					.username(request.getUsername())
					.password(passwordEncoder.encode(request.getPassword()))
					.image(parameterService.getByName("IMAGE DEFAULT").getImage())
					.verified(false)
					.role(Role.TEACHER)
					.country(countryService.getById(request.getCountryId()))
					.level(null)
					.build();
		}
		else {
			user = User.builder()
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.birthdate(birthdate)
					.age(age)
					.gender(gender)
					.email(request.getEmail())
					.phone(request.getPhone())
					.username(request.getUsername())
					.password(passwordEncoder.encode(request.getPassword()))
					.image(parameterService.getByName("IMAGE DEFAULT").getImage())
					.verified(false)
					.role(Role.USER)
					.country(countryService.getById(request.getCountryId()))
					.level(null)
					.build();
		}
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
		
	}
	
	public Integer calculateAge(Date birthdate) {
		Calendar old = Calendar.getInstance(Locale.US);
		old.setTime(birthdate);
		Calendar now = Calendar.getInstance(Locale.US);
		now.setTime(new Date());
		Integer age = now.get(Calendar.YEAR) - old.get(Calendar.YEAR);
		if(old.get(Calendar.MONTH)>now.get(Calendar.MONTH) || 
				(old.get(Calendar.MONTH)==now.get(Calendar.MONTH) && old.get(Calendar.DAY_OF_MONTH)>now.get(Calendar.DAY_OF_MONTH))) {
			age--;
		}
		return age;
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
