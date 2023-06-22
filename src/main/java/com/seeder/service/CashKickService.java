package com.seeder.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seeder.dto.CashKickDTO;
import com.seeder.model.Response;

@Service
public interface CashKickService {

	ResponseEntity<Response> addCashKick(CashKickDTO cashKickDto);

	ResponseEntity<Response> getCashKicks();

	ResponseEntity<Response> updateCashKick(Long id, CashKickDTO cashKickDto);

	ResponseEntity<Response> getCashKickById(Long id);

	ResponseEntity<Response> deleteCashKick(Long id);

}
