package com.seeder.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.seeder.model.Payment;
import com.seeder.model.Response;
import com.seeder.model.User;
import com.seeder.repository.CashKickRepository;
import com.seeder.repository.ContractRepository;
import com.seeder.repository.PaymentRepository;
import com.seeder.repository.UserRepository;

@Component
public class CashKickServiceImpl implements CashKickService {

	@Autowired
	CashKickRepository cashKickRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	PaymentRepository paymentRepository;

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
			logger.error("User not found in {}", cashKickDto.getUserId());
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
		cashKick = cashKickRepository.save(cashKick);

		int termLength = 12;
		double interestRate = 12;
		double principleAmount = 10000;
		interestRate = interestRate / (12 * 100);
		double emi = (principleAmount * interestRate * Math.pow(1 + interestRate, termLength))
				/ (Math.pow(1 + interestRate, termLength) - 1);
		BigDecimal emiBig = BigDecimal.valueOf(emi).setScale(2, RoundingMode.HALF_UP);
		BigDecimal totalAmount = emiBig.multiply(new BigDecimal(termLength));

		List<Payment> paymentList = new ArrayList<>();
		for (int i = 1; i <= termLength; i++) {
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.MONTH, i);
			Date dateAfterOneMonth = calendar.getTime();

			BigDecimal tempOutStanding = totalAmount.subtract(emiBig);
			totalAmount = tempOutStanding;

			Payment payment = new Payment();
			payment.setExpectedAmount(emiBig);
			payment.setStatus("Pending");
			payment.setDueDate(dateAfterOneMonth);
			payment.setOutstandingAmount(tempOutStanding);
			payment.setCashKickId((int) cashKick.getId());
			payment.setUsers(user.get());
			paymentList.add(payment);
		}
		paymentRepository.saveAll(paymentList);
		return new ResponseEntity<>(new Response(true, cashKickDto, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Response> updateCashKick(Long id, CashKickDTO cashKickDto) {
		Optional<CashKick> cashKick = cashKickRepository.findById(id);
		if (cashKick.isEmpty()) {
			logger.error(notFoundError, id);
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
			logger.error(notFoundError, id);
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
			logger.error(notFoundError, id);
			throw new ResourceNotFoundException(notFoundError + id);
		}
		cashKickRepository.deleteById(id);
		return new ResponseEntity<>(new Response(true, null, null, new Timestamp(System.currentTimeMillis())),
				HttpStatus.OK);
	}

}
