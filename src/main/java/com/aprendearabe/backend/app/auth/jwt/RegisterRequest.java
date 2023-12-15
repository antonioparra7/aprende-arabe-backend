package com.aprendearabe.backend.app.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	private String firstName;
	private String lastName;
	private String birthdate;
	private String gender;
	private String email;
	private String phone;
	private String username;
	private String password;
	private Long countryId;
	private Boolean isTeacher;
}
