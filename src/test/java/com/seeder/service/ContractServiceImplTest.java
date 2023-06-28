package com.seeder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.seeder.dto.ContractDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.Contract;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.ContractRepository;
import com.seeder.repository.UserRepository;

@SpringBootTest
class ContractServiceImplTest {

	@Mock
	private ContractRepository contractRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private DataMapper mapper;

	@Mock
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@InjectMocks
	private ContractServiceImpl contractService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	private String notFoundError = "Contract not found in ";

	@Test
	void testAddContract() {
		ContractDTO contractDto = new ContractDTO();
		contractDto.setUserId(1L);

		User user = new User();
		user.setId(1L);

		Contract contract = new Contract();
		contract.setId(1L);
		contract.setUsers(user);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(mapper.toContractEntity(contractDto)).thenReturn(contract);

		ResponseEntity<Response> response = contractService.addContract(contractDto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
		assertEquals(contractDto, response.getBody().getData());
	}

	@Test
	void testAddCashKick_ResourceNotFoundException() {
		ContractDTO contractDto = new ContractDTO();
		contractDto.setUserId(1L);

		User user = new User();
		user.setId(1L);

		Contract contract = new Contract();
		contract.setId(1L);
		contract.setUsers(user);

		when(userRepository.findById(contractDto.getUserId())).thenReturn(Optional.empty());
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> contractService.addContract(contractDto));

		assertEquals("User not found in 1", exception.getMessage());
		verify(logger).error("User not found in {}",1L);
		verify(contractRepository, never()).save(any(Contract.class));
		verify(mapper, never()).toContractEntity(any(ContractDTO.class));
	}

	@Test
	void testUpdateContract() {
		Long contractId = 1L;
		ContractDTO contractDto = new ContractDTO();

		Contract existingContract = new Contract();
		existingContract.setId(contractId);

		Contract updatedContract = new Contract();
		updatedContract.setId(contractId);

		when(contractRepository.findById(contractId)).thenReturn(Optional.of(existingContract));
		when(contractRepository.save(any(Contract.class))).thenReturn(updatedContract);
		when(mapper.toContractDto(updatedContract)).thenReturn(contractDto);

		ResponseEntity<Response> response = contractService.updateContract(contractId, contractDto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
	}

	@Test
	void testUpdateCashKick_ResourceNotFoundException() {
		Long contractId = 1L;
		ContractDTO contractDto = new ContractDTO();

		when(contractRepository.findById(contractId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> contractService.updateContract(contractId, contractDto));
		verify(logger).error(notFoundError,contractId);
	}

	@Test
	void testGetContractById() {
		Long contractId = 1L;
		Contract contract = new Contract();
		contract.setId(contractId);
		User user = new User();
		user.setId(1L);
		contract.setUsers(user);

		when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));
		when(mapper.toContractDto(contract)).thenReturn(new ContractDTO());

		ResponseEntity<Response> response = contractService.getContractById(contractId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
		assertEquals(ContractDTO.class, response.getBody().getData().getClass());

	}

	@Test
	void testGetCashKickById_ResourceNotFoundException() {
		Long contractId = 1L;

		when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> contractService.getContractById(contractId));
		verify(logger).error(notFoundError,contractId);
	}

	@Test
	void testGetContracts() {
		Long contractId = 1L;
		Contract contract = new Contract();
		contract.setId(contractId);
		User user = new User();
		user.setId(1L);
		contract.setUsers(user);
		List<Contract> contracts = Arrays.asList(contract);

		when(contractRepository.findAll()).thenReturn(contracts);
		when(mapper.toContractDto(any(Contract.class))).thenReturn(new ContractDTO());

		ResponseEntity<Response> response = contractService.getContracts();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
	}

	@Test
	void testDeleteContract() {
		Long contractId = 1L;
		Contract contract = new Contract();
		contract.setId(contractId);

		when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));

		ResponseEntity<Response> response = contractService.deleteContract(contractId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
		assertEquals(null, response.getBody().getData());
	}

	@Test
	void testDeleteContract_ResourceNotFoundException() {
		Long contractId = 1L;

		when(contractRepository.findById(contractId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> contractService.deleteContract(contractId));
		verify(logger).error(notFoundError,contractId);
	}

}
