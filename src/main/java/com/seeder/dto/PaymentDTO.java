package com.seeder.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDTO {

	private long id;
	private String dueDate;
	private Date expectedAmount;
	private BigDecimal outstandingAmount;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public Date getExpectedAmount() {
		return expectedAmount;
	}
	public void setExpectedAmount(Date expectedAmount) {
		this.expectedAmount = expectedAmount;
	}
	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
