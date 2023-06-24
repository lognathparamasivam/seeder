package com.seeder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.seeder.dto.CashKickDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.CashKick;
import com.seeder.model.Contract;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.CashKickRepository;
import com.seeder.repository.ContractRepository;
import com.seeder.repository.UserRepository;

class CashKickServiceImplTest {

	@Mock
	private CashKickRepository cashKickRepository;

	@Mock
	private ContractRepository contractRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private DataMapper mapper;

	@Mock
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@InjectMocks
	private CashKickServiceImpl cashKickService;

	private String notFoundError = "Cashkick not found in ";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddCashKick() {

		Long userId = 1L;
		Long contractId = 1L;
		CashKickDTO cashKickDto = new CashKickDTO();
		cashKickDto.setUserId(userId);
		cashKickDto.setContractIds(Collections.singletonList(contractId));
		User user = new User();
		user.setId(userId);
		Contract contract = new Contract();
		contract.setId(contractId);
		CashKick cashKick = new CashKick();
		cashKick.setUsers(user);
		cashKick.setContracts(Collections.singleton(contract));

		when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(mapper.toCashKickEntity(cashKickDto)).thenReturn(cashKick);

		ResponseEntity<Response> response = cashKickService.addCashKick(cashKickDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertEquals(cashKickDto, response.getBody().getData());
		assertNotNull(response.getBody().getTime());

		verify(cashKickRepository).save(cashKick);
	}

	@Test
	void testAddCashKick_ResourceNotFoundException() {
		CashKickDTO cashKickDto = new CashKickDTO();
		cashKickDto.setUserId(1L);

		when(userRepository.findById(cashKickDto.getUserId())).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> cashKickService.addCashKick(cashKickDto));
		assertEquals("User not found in 1", exception.getMessage());
		verify(logger).error("User not found in {}",1L);
		verify(cashKickRepository, never()).save(any(CashKick.class));
		verify(mapper, never()).toCashKickEntity(any(CashKickDTO.class));
	}

	@Test
	void testUpdateCashKick() {

		Long cashKickId = 1L;
		CashKickDTO cashKickDto = new CashKickDTO();
		cashKickDto.setId(cashKickId);
		CashKick cashKick = new CashKick();
		cashKick.setId(cashKickId);

		when(cashKickRepository.findById(cashKickId)).thenReturn(Optional.of(cashKick));
		when(cashKickRepository.save(any(CashKick.class))).thenReturn(cashKick);
		when(mapper.toCashKickDto(cashKick)).thenReturn(cashKickDto);

		ResponseEntity<Response> response = cashKickService.updateCashKick(cashKickId, cashKickDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertNotNull(response.getBody().getTime());

	}

	@Test
	void testUpdateCashKick_ResourceNotFoundException() {

		Long cashKickId = 1L;
		CashKickDTO cashKickDto = new CashKickDTO();
		when(cashKickRepository.findById(cashKickId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> cashKickService.updateCashKick(cashKickId, cashKickDto));
		verify(logger).error(notFoundError,cashKickId);
	}

	@Test
	void testGetCashKickById() {

		Long cashKickId = 1L;
		CashKick cashKick = new CashKick();
		cashKick.setId(cashKickId);

		when(cashKickRepository.findById(cashKickId)).thenReturn(Optional.of(cashKick));
		when(mapper.toCashKickDto(cashKick)).thenReturn(new CashKickDTO());

		ResponseEntity<Response> response = cashKickService.getCashKickById(cashKickId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertNotNull(response.getBody().getData());
		assertNotNull(response.getBody().getTime());
	}

	@Test
	void testGetCashKickById_ResourceNotFoundException() {
		Long cashKickId = 1L;
		when(cashKickRepository.findById(cashKickId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> cashKickService.getCashKickById(cashKickId));
		verify(logger).error(notFoundError,cashKickId);
	}

	@Test
	void testGetCashKicks() {
		List<CashKick> cashKicks = new ArrayList<>();
		cashKicks.add(new CashKick());
		cashKicks.add(new CashKick());

		when(cashKickRepository.findAll()).thenReturn(cashKicks);
		when(mapper.toCashKickDto(any(CashKick.class))).thenReturn(new CashKickDTO());

		ResponseEntity<Response> response = cashKickService.getCashKicks();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertNotNull(response.getBody().getData());
		assertNotNull(response.getBody().getTime());
	}

	@Test
	void testDeleteCashKick() {
		Long cashKickId = 1L;
		when(cashKickRepository.findById(cashKickId)).thenReturn(Optional.of(new CashKick()));

		ResponseEntity<Response> response = cashKickService.deleteCashKick(cashKickId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertNull(response.getBody().getData());
		assertNotNull(response.getBody().getTime());
		verify(cashKickRepository).deleteById(cashKickId);
	}

	@Test
	void testDeleteCashKick_ResourceNotFoundException() {
		Long cashKickId = 1L;
		when(cashKickRepository.findById(cashKickId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> cashKickService.deleteCashKick(cashKickId));
		verify(logger).error(notFoundError,cashKickId);
	}

}
