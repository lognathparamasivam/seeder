package com.seeder.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.seeder.model.Contract;

public class CashKickDTO {

	private long id;
	private String name;
	private BigDecimal receivedAmount;
	private Date maturityDate;
	private String status;
	private long userId;
	private List<Long> contractIds;
	private Set<Contract> contracts;

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

	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
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

	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}

	public List<Long> getContractIds() {
		return contractIds;
	}

	public void setContractIds(List<Long> contractIds) {
		this.contractIds = contractIds;
	}

}
