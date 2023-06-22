package com.seeder.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seeder.dto.UserDTO;
import com.seeder.model.Response;

@Service
public interface UserService {

	ResponseEntity<Response> addUser(UserDTO userDto);

	ResponseEntity<Response> getUsers();

	ResponseEntity<Response> updateUser(Long id, UserDTO userDto);

	ResponseEntity<Response> getUserById(Long id);

	ResponseEntity<Response> deleteUser(Long id);
}
