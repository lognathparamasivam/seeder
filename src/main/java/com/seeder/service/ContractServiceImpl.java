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

import com.seeder.dto.ContractDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.Contract;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.ContractRepository;
import com.seeder.repository.UserRepository;

@Component
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DataMapper mapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private String notFoundError = "Contract not found in ";

	@Override
	public ResponseEntity<Response> addContract(ContractDTO contractDto) {
		Optional<User> user = userRepository.findById(contractDto.getUserId());
		if(user.isEmpty()) {
			logger.error("User not found in {}",contractDto.getUserId());
			throw new ResourceNotFoundException("User not found in " + contractDto.getUserId());
		}
		Contract contract = mapper.toContractEntity(contractDto);
		contract.setUsers(user.get());
		contractRepository.save(contract);
		contractDto.setUserId(user.get().getId());
		return new ResponseEntity<>(new Response(true, contractDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Response> updateContract(Long id, ContractDTO contractDto) {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		Contract updatedContract = contractRepository.save(mapper.toContractEntity(contractDto));
		return new ResponseEntity<>(new Response(true, mapper.toContractDto(updatedContract), null,
				new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getContractById(Long id) {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		ContractDTO contractDto = mapper.toContractDto(contract.get());
		contractDto.setUserId(contract.get().getUsers().getId());
		return new ResponseEntity<>(new Response(true, contractDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getContracts() {
		List<Contract> contracts = contractRepository.findAll();
		List<ContractDTO> contractsDto = contracts.stream().map(contract -> {
			ContractDTO contractDto = mapper.toContractDto(contract);
			contractDto.setUserId(contract.getUsers().getId());
			return contractDto;
		}).toList();
		return new ResponseEntity<>(new Response(true, contractsDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteContract(Long id) {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		contractRepository.deleteById(id);
		return new ResponseEntity<>(new Response(true, null, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

}
