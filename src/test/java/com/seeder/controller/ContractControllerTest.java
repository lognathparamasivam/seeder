package com.seeder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeder.dto.ContractDTO;
import com.seeder.model.Contract;
import com.seeder.model.Response;
import com.seeder.service.ContractService;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContractService contractService;

	@InjectMocks
	private ContractController contractController;

	private ContractDTO contractDTO;

	private List<ContractDTO> contractDTOList;

	private String contract;

	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		contractDTO = new ContractDTO();
		contractDTO.setId(1);
		contractDTO.setName("Test Contract");
		contractDTO.setStatus("Pending");
		contractDTO.setTermLength(12);
		contractDTO.setType("Monthly");
		contractDTO.setInterestRate(12);
		contractDTO.setAvailableAmount(100);
		contractDTO.setFinancedAmount(10);

		contractDTOList = new ArrayList<>();
		contractDTOList.add(contractDTO);
	}

	@Test
	public void testaddContract() throws Exception {
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, contractDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.CREATED);
		when(contractService.addContract(contractDTO)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/contracts")
				.content(new ObjectMapper().writeValueAsString(contractDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testupdateContract() throws Exception {
		contractDTO.setName("New Contract");
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, contractDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(contractService.updateContract(contractDTO.getId(), contractDTO)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.patch("/contracts/{id}", contractDTO.getId())
				.content(new ObjectMapper().writeValueAsString(contractDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void testgetContracts() throws Exception {
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, contractDTOList, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(contractService.getContracts()).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/contracts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetContractById() throws Exception {

		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, contractDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(contractService.getContractById(contractDTO.getId())).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/contracts/{id}", contractDTO.getId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testdeleteContract() throws Exception {
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, contractDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(contractService.updateContract(contractDTO.getId(), contractDTO)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.delete("/contracts/{id}", contractDTO.getId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
