package com.seeder.dto;

import java.math.BigDecimal;

public class ContractDTO {

	private long id;
	private String name;
	private String type;
	private int termLength;
	private BigDecimal availableAmount;
	private BigDecimal financedAmount;
	private int interestRate;
	private String status;
	private long userId;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTermLength() {
		return termLength;
	}

	public void setTermLength(int termLength) {
		this.termLength = termLength;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getFinancedAmount() {
		return financedAmount;
	}

	public void setFinancedAmount(BigDecimal financedAmount) {
		this.financedAmount = financedAmount;
	}

	public int getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
