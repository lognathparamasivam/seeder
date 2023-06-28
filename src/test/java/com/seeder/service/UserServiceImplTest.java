package com.seeder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.seeder.dto.PaymentDTO;
import com.seeder.dto.UserDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.Payment;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.PaymentRepository;
import com.seeder.repository.UserRepository;

@SpringBootTest
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private DataMapper mapper;

	@Mock
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	private String notFoundError = "User not found in ";

	@Test
	void testAddUser() {
		UserDTO userDto = new UserDTO();

		User user = new User();
		user.setId(1L);

		when(userRepository.save(any(User.class))).thenReturn(user);
		when(mapper.toUserDto(user)).thenReturn(userDto);

		ResponseEntity<Response> response = userService.addUser(userDto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
	}

	@Test
	void testUpdateUser() {
		Long userId = 1L;
		UserDTO userDto = new UserDTO();

		User existingUser = new User();
		existingUser.setId(userId);

		User updatedUser = new User();
		updatedUser.setId(userId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);
		when(mapper.toUserDto(updatedUser)).thenReturn(userDto);

		ResponseEntity<Response> response = userService.updateUser(userId, userDto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
	}

	@Test
	void testUpdateCashKick_ResourceNotFoundException() {
		Long userId = 1L;
		UserDTO userDto = new UserDTO();

		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userId, userDto));
		verify(logger).error(notFoundError,userId);
	}

	@Test
	void testGetUserById() {
		Long userId = 1L;
		User user = new User();
		user.setId(userId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(mapper.toUserDto(user)).thenReturn(new UserDTO());

		ResponseEntity<Response> response = userService.getUserById(userId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
		assertEquals(UserDTO.class, response.getBody().getData().getClass());
	}

	@Test
	void testGetCashKickById_ResourceNotFoundException() {
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
		verify(logger).error(notFoundError,userId);
	}

	@Test
	void testGetUsers() {
		List<User> users = Arrays.asList(new User(), new User());

		when(userRepository.findAll()).thenReturn(users);
		when(mapper.toUserDto(any(User.class))).thenReturn(new UserDTO());

		ResponseEntity<Response> response = userService.getUsers();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
	}

	@Test
	void testDeleteUser() {
		Long userId = 1L;
		User user = new User();
		user.setId(userId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		ResponseEntity<Response> response = userService.deleteUser(userId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
		assertEquals(null, response.getBody().getData());
	}

	@Test
	void testDeleteContract_ResourceNotFoundException() {
		Long userId = 1L;

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
		verify(logger).error(notFoundError,userId);
	}
	
	@Test
	void testGetUserPaymentsById() {
		Long userId = 1L;
		List<Payment> payments = Arrays.asList(new Payment(), new Payment());
		User user = new User();
		user.setId(userId);
		
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(paymentRepository.findByUsers_Id(userId)).thenReturn(payments);
		when(mapper.toPaymentDto(any(Payment.class))).thenReturn(new PaymentDTO());

		ResponseEntity<Response> response = userService.getUserPaymentsById(userId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isSuccess());
	}

	@Test
	void testGetUserPaymentsById_ResourceNotFoundException() {
		Long userId = 1L;

		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> userService.getUserPaymentsById(userId));
		verify(logger).error(notFoundError,userId);
	}

}
