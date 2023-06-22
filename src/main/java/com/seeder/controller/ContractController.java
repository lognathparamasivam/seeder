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

import com.seeder.dto.ContractDTO;
import com.seeder.model.Response;
import com.seeder.service.ContractService;

@RestController
@RequestMapping("/contracts")
public class ContractController {

	@Autowired
	ContractService contractService;

	@PostMapping
	public ResponseEntity<Response> addContract(@RequestBody ContractDTO contractDto) {
		return contractService.addContract(contractDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Response> updateContract(@PathVariable Long id,@RequestBody ContractDTO contractDto) {
		return contractService.updateContract(id,contractDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getContractById(@PathVariable Long id) {
		return contractService.getContractById(id);
	}

	@GetMapping
	public ResponseEntity<Response> getContracts() {
		return contractService.getContracts();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteContract(@PathVariable Long id) {
		return contractService.deleteContract(id);
	}

}
