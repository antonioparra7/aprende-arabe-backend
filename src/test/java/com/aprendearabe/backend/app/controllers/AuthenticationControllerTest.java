package com.aprendearabe.backend.app.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.aprendearabe.backend.app.auth.jwt.AuthenticationRequest;
import com.aprendearabe.backend.app.auth.jwt.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	private RegisterRequest createValidRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setBirthdate("1990-01-01");
        request.setGender("male");
        request.setEmail("johndoe@gmail.com");
        request.setPhone("123456789");
        request.setUsername("johndoe");
        request.setPassword("password");
        request.setCountryId(3L);
        request.setIsTeacher(false);
        return request;
    }
	@Test
	@Order(1)
	public void testRegisterValid() throws Exception {
		RegisterRequest request = createValidRegisterRequest();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}
	
	private RegisterRequest createErrorUsernameExistsRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setBirthdate("1990-01-01");
        request.setGender("male");
        request.setEmail("john.doe1@example.com");
        request.setPhone("123456780");
        request.setUsername("johndoe");
        request.setPassword("password");
        request.setCountryId(3L);
        request.setIsTeacher(false);
        return request;
    }
	@Test
	@Order(2)
	public void testRegisterErrorUsernameExists() throws Exception {
		RegisterRequest request = createErrorUsernameExistsRegisterRequest();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
	}
	
	private RegisterRequest createErrorSizeRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("D");
        request.setBirthdate("1990-01-01");
        request.setGender("male");
        request.setEmail("john.doe1@example.com");
        request.setPhone("123456780");
        request.setUsername("johndoe1");
        request.setPassword("password");
        request.setCountryId(3L);
        request.setIsTeacher(false);
        return request;
    }
	@Test
	@Order(3)
	public void testRegisterErrorSize() throws Exception {
		RegisterRequest request = createErrorSizeRegisterRequest();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
	}
	
	private AuthenticationRequest createErrorCredentialsAuthenticationRequest() {
		AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("johndoe");
        request.setPassword("password1");
        return request;
    }
	@Test
	@Order(4)
	public void testAuthenticateErrorCredentials() throws Exception {
		AuthenticationRequest request = createErrorCredentialsAuthenticationRequest();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
	}
	
	private AuthenticationRequest createValidAuthenticationRequest() {
		AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("johndoe");
        request.setPassword("password");
        return request;
    }
	@Test
	@Order(5)
	public void testAuthenticateValid() throws Exception {
		AuthenticationRequest request = createValidAuthenticationRequest();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}
	
	private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}