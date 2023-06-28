package com.seeder.controller;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeder.dto.PaymentDTO;
import com.seeder.dto.UserDTO;
import com.seeder.model.Response;
import com.seeder.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private UserDTO userDTO;

	private List<UserDTO> userDTOList;

	private String notFoundError = "User not found in ";

	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setName("Test User");
		userDTO.setEmail("test@gmail.com");
		userDTO.setTermLength(12);
		userDTO.setPassword("Test@123");
		userDTO.setInterestRate(12);

		userDTOList = new ArrayList<>();
		userDTOList.add(userDTO);
	}

	@Test
	void testAddUser() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/users").content(new ObjectMapper().writeValueAsString(userDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdateUser() throws Exception {
		userDTO.setName("New User");
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, userDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(userService.updateUser(userDTO.getId(), userDTO)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.patch("/users/{id}", userDTO.getId())
				.content(new ObjectMapper().writeValueAsString(userDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void testGetUsers() throws Exception {
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, userDTOList, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(userService.getUsers()).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetUserById() throws Exception {

		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, userDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(userService.getUserById(userDTO.getId())).thenReturn(expectedResponse);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/users/{id}", userDTO.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetUserByIdResourceNotFoundException() throws Exception {
		long id = 1000;
		ResourceNotFoundException exception = new ResourceNotFoundException(notFoundError + id);

		when(userService.getUserById(id)).thenThrow(exception);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testDeleteUser() throws Exception {
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, null, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(userService.deleteUser(userDTO.getId())).thenReturn(expectedResponse);

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/users/{id}", userDTO.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testDeleteUserResourceNotFoundException() throws Exception {
		long id = 1000;
		ResourceNotFoundException exception = new ResourceNotFoundException(notFoundError + id);

		when(userService.deleteUser(id)).thenThrow(exception);

		mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetUserPaymentsById() throws Exception {
		List<PaymentDTO> paymentsDto = Arrays.asList(new PaymentDTO(), new PaymentDTO());
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, paymentsDto, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(userService.getUserPaymentsById(userDTO.getId())).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/payments", userDTO.getId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetUserPaymentsByIdResourceNotFoundException() throws Exception {
		long id = 1000;
		ResourceNotFoundException exception = new ResourceNotFoundException(notFoundError + id);

		when(userService.getUserPaymentsById(id)).thenThrow(exception);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/users/{id}/payments", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
