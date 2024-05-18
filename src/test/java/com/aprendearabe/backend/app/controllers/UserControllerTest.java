package com.aprendearabe.backend.app.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1)
	public void testGetUserByIdValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(2)
	public void testGetUserByIdError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/2")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Order(3)
	public void testGetUserByUsernameValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/username/johndoe")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(4)
	public void testGetUserByUsernameError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/username/prueba")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Order(5)
	public void testGetUserByEmailValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/johndoe@gmail.com")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(6)
	public void testGetUserByEmailError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/prueba@gmail.com")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Order(7)
	public void testDeleteUserValid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@Order(8)
	public void testDeleteUserError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/2")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

}
