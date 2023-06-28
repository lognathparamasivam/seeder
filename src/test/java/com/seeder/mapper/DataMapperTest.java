package com.seeder.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.seeder.dto.CashKickDTO;
import com.seeder.dto.ContractDTO;
import com.seeder.dto.PaymentDTO;
import com.seeder.dto.UserDTO;
import com.seeder.model.CashKick;
import com.seeder.model.Contract;
import com.seeder.model.Payment;
import com.seeder.model.User;

class DataMapperTest {

	private DataMapper dataMapper;

	@BeforeEach
	void setup() {
		dataMapper = new DataMapper();
		ModelMapper modelMapper = new ModelMapper();
		dataMapper.setModelMapper(modelMapper);
	}

	@Test
	void testToContractDto() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setName("Contract 1");

		ContractDTO contractDto = dataMapper.toContractDto(contract);

		assertNotNull(contractDto);
		assertEquals(contract.getId(), contractDto.getId());
		assertEquals(contract.getName(), contractDto.getName());
	}

	@Test
	void testToContractEntity() {
		ContractDTO contractDto = new ContractDTO();
		contractDto.setId(1L);
		contractDto.setName("Contract 1");

		Contract contract = dataMapper.toContractEntity(contractDto);

		assertNotNull(contract);
		assertEquals(contractDto.getId(), contract.getId());
		assertEquals(contractDto.getName(), contract.getName());
	}

	@Test
	void testToCashKickEntity() {
		CashKickDTO cashKickDto = new CashKickDTO();
		cashKickDto.setId(1L);
		cashKickDto.setName("CashKick 1");

		CashKick cashKick = dataMapper.toCashKickEntity(cashKickDto);

		assertNotNull(cashKick);
		assertEquals(cashKickDto.getId(), cashKick.getId());
		assertEquals(cashKickDto.getName(), cashKick.getName());
	}

	@Test
	void testToCashKickDto() {
		CashKick cashKick = new CashKick();
		cashKick.setId(1L);
		cashKick.setName("CashKick 1");

		CashKickDTO cashKickDto = dataMapper.toCashKickDto(cashKick);

		assertNotNull(cashKickDto);
		assertEquals(cashKick.getId(), cashKickDto.getId());
		assertEquals(cashKick.getName(), cashKickDto.getName());
	}

	@Test
	void testToUserEntity() {
		UserDTO userDto = new UserDTO();
		userDto.setId(1L);
		userDto.setName("John Doe");

		User user = dataMapper.toUserEntity(userDto);

		assertNotNull(user);
		assertEquals(userDto.getId(), user.getId());
		assertEquals(userDto.getName(), user.getName());
	}

	@Test
	void testToUserDto() {
		User user = new User();
		user.setId(1L);
		user.setName("John Doe");

		UserDTO userDto = dataMapper.toUserDto(user);

		assertNotNull(userDto);
		assertEquals(user.getId(), userDto.getId());
		assertEquals(user.getName(), userDto.getName());
	}

	@Test
	void testToPaymentEntity() {
		PaymentDTO paymentDto = new PaymentDTO();
		paymentDto.setId(1L);
		paymentDto.setStatus("Pending");

		Payment payment = dataMapper.toPaymentEntity(paymentDto);

		assertNotNull(payment);
		assertEquals(paymentDto.getId(), payment.getId());
		assertEquals(paymentDto.getStatus(), payment.getStatus());
	}

	@Test
	void testToPaymentDto() {
		Payment payment = new Payment();
		payment.setId(1L);
		payment.setStatus("Pending");

		PaymentDTO paymentDto = dataMapper.toPaymentDto(payment);

		assertNotNull(paymentDto);
		assertEquals(payment.getId(), paymentDto.getId());
		assertEquals(payment.getStatus(), paymentDto.getStatus());
	}
}
