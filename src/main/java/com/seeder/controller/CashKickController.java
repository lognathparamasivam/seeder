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

import com.seeder.dto.CashKickDTO;
import com.seeder.logger.SeederLogger;
import com.seeder.model.Response;
import com.seeder.service.CashKickService;

@RestController
@RequestMapping("/cash-kicks")
public class CashKickController {

	@Autowired
	CashKickService cashKickService;
	
	@Autowired
	SeederLogger logger;

	@PostMapping
	public ResponseEntity<Response> addCashKick(@RequestBody CashKickDTO cashKickDto) {
		logger.info(this.getClass(), "Add Cash Kick API");
		return cashKickService.addCashKick(cashKickDto);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Response> updateCashKick(@PathVariable Long id,@RequestBody CashKickDTO cashKickDto) {
		logger.info(this.getClass(), "Update Cash Kick API");
		return cashKickService.updateCashKick(id,cashKickDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getCashKickById(@PathVariable Long id) {
		logger.info(this.getClass(), "Get Cash Kick By Id API");
		return cashKickService.getCashKickById(id);
	}

	@GetMapping
	public ResponseEntity<Response> getCashKicks() {
		logger.info(this.getClass(), "Get Cash Kicks API");
		return cashKickService.getCashKicks();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteCashKick(@PathVariable Long id) {
		logger.info(this.getClass(), "Delete Cash Kick API");
		return cashKickService.deleteCashKick(id);
	}
	
	


}
