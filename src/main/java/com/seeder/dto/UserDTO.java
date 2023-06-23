package com.seeder.dto;

import java.math.BigDecimal;
import java.util.List;

import com.seeder.model.CashKick;
import com.seeder.model.Contract;

public class UserDTO {

	private long id;
	private String name;
	private String email;
	private String password;
	private int termLength;
	private int interestRate;
	private List<Contract> contracts;
	private List<CashKick> cashKicks;
	private BigDecimal availableCredit;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTermLength() {
		return termLength;
	}

	public void setTermLength(int termLength) {
		this.termLength = termLength;
	}

	public int getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}

	public List<CashKick> getCashKicks() {
		return cashKicks;
	}

	public void setCashKicks(List<CashKick> cashKicks) {
		this.cashKicks = cashKicks;
	}

}
