package com.seeder.controller;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.seeder.dto.CashKickDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.Response;
import com.seeder.service.CashKickService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CashKickControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CashKickService cashKickService;

	@MockBean
	DataMapper mapper;

	@Mock
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@InjectMocks
	private CashKickController cashKickController;

	private CashKickDTO cashKickDTO;

	private List<CashKickDTO> cashKickDTOList;

	private String notFoundError = "CashKick not found in ";

	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		cashKickDTO = new CashKickDTO();
		cashKickDTO.setId(1);
		cashKickDTO.setName("Test Contract");
		cashKickDTO.setStatus("Pending");
		cashKickDTO.setReceivedAmount(BigDecimal.valueOf(100));
		List<Long> contractIdList = new ArrayList<>();
		contractIdList.add(1L);
		cashKickDTO.setContractIds(contractIdList);
		cashKickDTO.setUserId(1);
		cashKickDTOList = new ArrayList<>();
		cashKickDTOList.add(cashKickDTO);
	}

	@Test
	void testAddCashKick() throws Exception {
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, cashKickDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.CREATED);
		when(cashKickService.addCashKick(cashKickDTO)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/cash-kicks")
				.content(new ObjectMapper().writeValueAsString(cashKickDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testHandleException_DefaultException() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/cash-kicks").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.time").exists());
	}

	@Test
	void testUpdateCashKick() throws Exception {
		cashKickDTO.setName("New Contract");
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, cashKickDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(cashKickService.updateCashKick(cashKickDTO.getId(), cashKickDTO)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.patch("/cash-kicks/{id}", cashKickDTO.getId())
				.content(new ObjectMapper().writeValueAsString(cashKickDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void testGetCashKicks() throws Exception {
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, cashKickDTOList, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(cashKickService.getCashKicks()).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/cash-kicks").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void testGetCashKickById() {

		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/cash-kicks/{id}", cashKickDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetCashKickByIdResourceNotFoundException() throws Exception {
		long id = 1000;
		ResourceNotFoundException exception = new ResourceNotFoundException(notFoundError + id);

		when(cashKickService.getCashKickById(id)).thenThrow(exception);

		mockMvc.perform(MockMvcRequestBuilders.get("/cash-kicks/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	void testDeleteCashKick() {
		try {
			ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
					new Response(true, null, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
			when(cashKickService.deleteCashKick(cashKickDTO.getId())).thenReturn(expectedResponse);

			mockMvc.perform(MockMvcRequestBuilders.delete("/cash-kicks/{id}", cashKickDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeleteContractResourceNotFoundException() throws Exception {
		long id = 1000;
		ResourceNotFoundException exception = new ResourceNotFoundException(notFoundError + id);

		when(cashKickService.deleteCashKick(id)).thenThrow(exception);

		mockMvc.perform(MockMvcRequestBuilders.delete("/cash-kicks/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
