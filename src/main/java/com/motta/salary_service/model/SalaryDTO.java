package com.motta.salary_service.model;

import lombok.Data;

@Data
public class SalaryDTO {

	private int id;

	private String currency;

	private double salaryPerDay;

	private double totalSalary;

	public SalaryDTO() {
	}

	public SalaryDTO(int id, String currency, double salaryPerDay, double totalSalary) {
		this.id = id;
		this.currency = currency;
		this.salaryPerDay = salaryPerDay;
		this.totalSalary = totalSalary;
	}
}
