package com.seeder.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contracts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties("users")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "type", nullable = false, length = 50)
	private String type;

	@Column(name = "term_length", nullable = false)
	private int termLength;

	@Column(name = "available_amount", nullable = false)
	private BigDecimal availableAmount;

	@Column(name = "financed_amount", nullable = false)
	private BigDecimal financedAmount;

	@Column(name = "interest_rate", nullable = false)
	private int interestRate;

	@Column(name = "status", nullable = false, length = 50)
	private String status;

	@Column(name = "created_date")
	@CreatedDate
	private Date createdDate;

	@Column(name = "updated_date")
	@LastModifiedDate
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User users;

	@ManyToMany(mappedBy = "contracts")
	private Set<CashKick> cash_kicks = new HashSet<>();

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

}
