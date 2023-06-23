package com.seeder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeder.dto.UserDTO;
import com.seeder.logger.SeederLogger;
import com.seeder.model.Response;
import com.seeder.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	SeederLogger logger;

	@PostMapping
	public ResponseEntity<Response> addUser(@RequestBody UserDTO userDto) {
		logger.info(this.getClass(), "Add User API");
		return userService.addUser(userDto);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Response> updateUser(@PathVariable Long id,@RequestBody UserDTO userDto) {
		logger.info(this.getClass(), "Update User API");
		return userService.updateUser(id,userDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getUserById(@PathVariable Long id) {
		logger.info(this.getClass(), "Get User By Id API");
		return userService.getUserById(id);
	}

	@GetMapping
	public ResponseEntity<Response> getUsers() {
		logger.info(this.getClass(), "Get Users API");
		return userService.getUsers();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
		logger.info(this.getClass(), "Delete User API");
		return userService.deleteUser(id);
	}
	
	
	


}
