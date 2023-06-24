package com.seeder.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.seeder.dto.CashKickDTO;
import com.seeder.mapper.DataMapper;
import com.seeder.model.CashKick;
import com.seeder.model.Contract;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.CashKickRepository;
import com.seeder.repository.ContractRepository;
import com.seeder.repository.UserRepository;

@Component
public class CashKickServiceImpl implements CashKickService {

	@Autowired
	CashKickRepository cashKickRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DataMapper mapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private String notFoundError = "Cashkick not found in ";

	@Override
	public ResponseEntity<Response> addCashKick(CashKickDTO cashKickDto) {

		Optional<User> user = userRepository.findById(cashKickDto.getUserId());
		if (user.isEmpty()) {
			logger.error("User not found in {}",cashKickDto.getUserId());
			throw new ResourceNotFoundException("User not found in " + cashKickDto.getUserId());
		}

		Set<Contract> contractSet = new HashSet<>();
		for (Long contractIds : cashKickDto.getContractIds()) {
			Optional<Contract> contract = contractRepository.findById(contractIds);
			contract.ifPresent(contractSet::add);
		}
		CashKick cashKick = mapper.toCashKickEntity(cashKickDto);
		cashKick.setContracts(contractSet);
		cashKick.setUsers(user.get());
		cashKickRepository.save(cashKick);
		return new ResponseEntity<>(new Response(true, cashKickDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Response> updateCashKick(Long id, CashKickDTO cashKickDto) {
		Optional<CashKick> cashKick = cashKickRepository.findById(id);
		if (cashKick.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		CashKick updatedCashKick = cashKickRepository.save(mapper.toCashKickEntity(cashKickDto));
		return new ResponseEntity<>(new Response(true, mapper.toCashKickDto(updatedCashKick), null,
				new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getCashKickById(Long id) {
		Optional<CashKick> cashKick = cashKickRepository.findById(id);
		if (cashKick.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		return new ResponseEntity<>(new Response(true, mapper.toCashKickDto(cashKick.get()), null,
				new Timestamp(System.currentTimeMillis())), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getCashKicks() {
		List<CashKick> cashKicks = cashKickRepository.findAll();
		List<CashKickDTO> cashKicksDto = cashKicks.stream().map(cashKick -> mapper.toCashKickDto(cashKick)).toList();
		return new ResponseEntity<>(new Response(true, cashKicksDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteCashKick(Long id) {
		Optional<CashKick> cashKick = cashKickRepository.findById(id);
		if (cashKick.isEmpty()) {
			logger.error(notFoundError,id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		cashKickRepository.deleteById(id);
		return new ResponseEntity<>(new Response(true, null, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

}
