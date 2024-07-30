package com.motta.salary_service.entity;

import javax.validation.constraints.Size;

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

	@Size(min = 1, message = "Salary per day must not be empty")
	private Double salaryPerDay;

	@Size(min = 1, message = "Total Salary must not be empty")
	private Double totalSalary;

	public Salary() {
	}

	public Salary(Integer id, @NotEmpty(message = "Currency must not be empty") String currency, Double salaryPerDay,
			Double totalSalary) {
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
		return "Salary [id=" + id + ", currency=" + currency + ", salaryPerDay=" + salaryPerDay + ", totalSalary="
				+ totalSalary + "]";
	}

}
