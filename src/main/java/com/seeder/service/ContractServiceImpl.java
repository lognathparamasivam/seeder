package com.seeder.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.seeder.dto.ContractDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.Response;
import com.seeder.model.Contract;
import com.seeder.repository.ContractRepository;

@Component
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	DataMapper mapper;

	@Override
	public ResponseEntity<Response> addContract(ContractDTO contractDto) {
		Contract contract = contractRepository.save(mapper.toContractEntity(contractDto));
		return new ResponseEntity<Response>(
				new Response(true, mapper.toContractDto(contract), null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Response> updateContract(Long id, ContractDTO contractDto) {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			throw new ResourceNotFoundException("Contract not found in " + id);
		}
		Contract updatedContract = contractRepository.save(mapper.toContractEntity(contractDto));
		return new ResponseEntity<Response>(new Response(true, mapper.toContractDto(updatedContract), null,
				new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getContractById(Long id) {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			throw new ResourceNotFoundException("Contract not found in " + id);
		}
		return new ResponseEntity<Response>(new Response(true, mapper.toContractDto(contract.get()), null,
				new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getContracts() {
		List<Contract> contracts = contractRepository.findAll();
		List<ContractDTO> contractsDto = contracts.stream().map((contract) -> mapper.toContractDto(contract))
				.collect(Collectors.toList());
		return new ResponseEntity<Response>(
				new Response(true, contractsDto, null, new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteContract(Long id) {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			throw new ResourceNotFoundException("Contract not found in " + id);
		}
		contractRepository.deleteById(id);
		return new ResponseEntity<Response>(new Response(true, null, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

}
