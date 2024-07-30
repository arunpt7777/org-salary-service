package com.motta.salary_service.model;

public class SalaryDTO {

	private Integer id;

	private String currency;

	private Double salaryPerDay;

	private Double totalSalary;

	public SalaryDTO() {
	}

	public SalaryDTO(Integer id, String currency, Double salaryPerDay, Double totalSalary) {
		super();
		this.id = id;
		this.currency = currency;
		this.salaryPerDay = salaryPerDay;
		this.totalSalary = totalSalary;
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

	public Double getSalaryPerDay() {
		return salaryPerDay;
	}

	public void setSalaryPerDay(Double salaryPerDay) {
		this.salaryPerDay = salaryPerDay;
	}

	public Double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(Double totalSalary) {
		this.totalSalary = totalSalary;
	}

	@Override
	public String toString() {
		return "SalaryDTO [id=" + id + ", currency=" + currency + ", salaryPerDay=" + salaryPerDay + ", totalSalary="
				+ totalSalary + "]";
	}

}
