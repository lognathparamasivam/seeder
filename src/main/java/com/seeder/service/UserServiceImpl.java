package com.seeder.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.seeder.dto.PaymentDTO;
import com.seeder.dto.UserDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.Payment;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.PaymentRepository;
import com.seeder.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DataMapper mapper;
	
	@Autowired
	PaymentRepository paymentRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private String notFoundError = "User not found in ";

	@Override
	public ResponseEntity<Response> addUser(UserDTO userDto) {
		User user = userRepository.save(mapper.toUserEntity(userDto));
		return new ResponseEntity<>(
				new Response(true, mapper.toUserDto(user), null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Response> updateUser(Long id, UserDTO userDto) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		User updatedUser = userRepository.save(mapper.toUserEntity(userDto));
		return new ResponseEntity<>(new Response(true, mapper.toUserDto(updatedUser), null,
				new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		UserDTO userDto = mapper.toUserDto(user.get());
		userDto.setAvailableCredit(userRepository.getAvailableCredit(user.get().getId()));
		return new ResponseEntity<>(new Response(true, userDto, null,
				new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> usersDto = users.stream().map(user -> mapper.toUserDto(user)).toList();
		usersDto.forEach(userDto -> userDto.setAvailableCredit(userRepository.getAvailableCredit(userDto.getId())));
		return new ResponseEntity<>(new Response(true, usersDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		userRepository.deleteById(id);
		return new ResponseEntity<>(new Response(true, null, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getUserPaymentsById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		List<Payment> paymentList = paymentRepository.findByUsers_Id(id);
		List<PaymentDTO> paymentsDto = paymentList.stream().map(payments -> mapper.toPaymentDto(payments)).toList();
		return new ResponseEntity<>(new Response(true, paymentsDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}


}
