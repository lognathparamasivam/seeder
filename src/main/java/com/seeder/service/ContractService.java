package com.seeder.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seeder.dto.ContractDTO;
import com.seeder.model.Response;

@Service
public interface ContractService {

	ResponseEntity<Response> addContract(ContractDTO contractDto);
	
	ResponseEntity<Response> updateContract(Long id, ContractDTO contractDto);

	ResponseEntity<Response> getContractById(Long id);

	ResponseEntity<Response> getContracts();

	ResponseEntity<Response> deleteContract(Long id);

	

}
