package com.seeder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import com.seeder.dto.CashKickDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.CashKick;
import com.seeder.model.Contract;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.CashKickRepository;
import com.seeder.repository.ContractRepository;
import com.seeder.repository.UserRepository;
import com.seeder.service.CashKickService;

@SpringBootTest
@AutoConfigureMockMvc
class CashKickControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CashKickService cashKickService;

	@MockBean
	private CashKickRepository cashKickRepository;

	@MockBean
	private ContractRepository contractRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	DataMapper mapper;

	@InjectMocks
	private CashKickController cashKickController;

	private CashKickDTO cashKickDTO;

	private List<CashKickDTO> cashKickDTOList;

	private final static String NOT_FOUND_ERROR = "CashKick not found in ";

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

		CashKickDTO cashKickDto = new CashKickDTO();
		cashKickDto.setContractIds(Collections.singletonList(1L));
		cashKickDto.setUserId(2L);

		CashKick cashKickEntity = new CashKick();
		cashKickEntity.setId(1L);

		User user = new User();
		user.setId(2L);

		when(contractRepository.findById(1L)).thenReturn(Optional.of(new Contract()));
		when(userRepository.findById(2L)).thenReturn(Optional.of(user));
		when(mapper.toCashKickEntity(cashKickDto)).thenReturn(cashKickEntity);
		when(cashKickRepository.save(cashKickEntity)).thenReturn(cashKickEntity);

		ResponseEntity<Response> response = cashKickService.addCashKick(cashKickDTO);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isSuccess());
		assertEquals(cashKickDTO, response.getBody().getData());
		assertNotNull(response.getBody().getTime());

		mockMvc.perform(MockMvcRequestBuilders.post("/cash-kicks")
				.content(new ObjectMapper().writeValueAsString(cashKickDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdateCashKick() throws Exception {
		cashKickDTO.setName("New Contract");
		ResponseEntity<Response> expectedResponse = new ResponseEntity<Response>(
				new Response(true, cashKickDTO, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
		when(cashKickService.updateCashKick(cashKickDTO.getId(), cashKickDTO)).thenReturn(expectedResponse);

		Long id = 1L;
		CashKick existingCashKick = new CashKick();
		existingCashKick.setId(id);

		CashKick updatedCashKick = new CashKick();
		updatedCashKick.setId(id);
		when(cashKickRepository.findById(cashKickDTO.getId())).thenReturn(Optional.of(existingCashKick));
		when(cashKickRepository.save(any(CashKick.class))).thenReturn(updatedCashKick);
		when(mapper.toCashKickDto(updatedCashKick)).thenReturn(cashKickDTO);
		ResponseEntity<Response> response = cashKickService.updateCashKick(cashKickDTO.getId(), cashKickDTO);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isSuccess());
		assertEquals(cashKickDTO, response.getBody().getData());
		assertNotNull(response.getBody().getTime());

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
	void testGetCashKickById() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/cash-kicks/{id}", cashKickDTO.getId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetCashKickByIdResourceNotFoundException() throws Exception {
		long id = 1000;
		ResourceNotFoundException exception = new ResourceNotFoundException(NOT_FOUND_ERROR + id);

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
		ResourceNotFoundException exception = new ResourceNotFoundException(NOT_FOUND_ERROR + id);

		when(cashKickService.deleteCashKick(id)).thenThrow(exception);

		mockMvc.perform(MockMvcRequestBuilders.delete("/cash-kicks/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
