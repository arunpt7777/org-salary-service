package com.motta.salary_service.model;

public class SalaryDTO {

	private Integer id;

	private String currency;

	private Double amount;

	public SalaryDTO() {
	}

	public SalaryDTO(Integer id, String currency, Double amount) {
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
		return "SalaryDTO [id=" + id + ", currency=" + currency + ", amount=" + amount + "]";
	}

}
