package com.seeder.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class ContractDTO {

	private long id;
	private String name;
	private String type;
	private int termLength;
	private int availableAmount;
	private int financedAmount;
	private int interestRate;
	private String status;
	private int userId;

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

	public int getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(int availableAmount) {
		this.availableAmount = availableAmount;
	}

	public int getFinancedAmount() {
		return financedAmount;
	}

	public void setFinancedAmount(int financedAmount) {
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
