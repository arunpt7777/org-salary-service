package com.motta.salary_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;

@Entity
@SequenceGenerator(name = "Custom_Sequence", sequenceName = "custom_sequence", initialValue = 4, allocationSize = 1)
public class Salary {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Custom_Sequence")
	private Integer id;

	@NotEmpty(message = "Currency must not be empty")
	private String currency;

	@NotEmpty(message = "Amount must not be empty")
	private Double amount;

	public Salary() {
	}

	public Salary(Integer id, String currency, Double amount) {
		super();
		this.id = id;
		this.currency = currency;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Salary [id=" + id + ", currency=" + currency + ", amount=" + amount + "]";
	}
}
