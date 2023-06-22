package com.seeder.exception;

import java.sql.Timestamp;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.seeder.model.Response;

@ControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> handleException(ResourceNotFoundException e) {
		return new ResponseEntity<Object>(
				new Response(false, null, e.getMessage(), new Timestamp(System.currentTimeMillis())),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleException(Exception e) {
		return new ResponseEntity<Object>(
				new Response(false, null, e.getMessage(), new Timestamp(System.currentTimeMillis())),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
