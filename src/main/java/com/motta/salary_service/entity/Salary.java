package com.motta.salary_service.entity;

import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@SequenceGenerator(name = "Custom_Sequence", sequenceName = "custom_sequence", initialValue = 4, allocationSize = 1)
@Data
public class Salary {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Custom_Sequence")
	private int id;

	@NotEmpty(message = "Currency must not be empty")
	private String currency;

	@Size(min = 1, message = "Salary per day must not be empty")
	private double salaryPerDay;

	@Size(min = 1, message = "Total Salary must not be empty")
	private double totalSalary;

	public Salary() {
	}

	public Salary(int id, String currency, double salaryPerDay, double totalSalary) {
		this.id = id;
		this.currency = currency;
		this.salaryPerDay = salaryPerDay;
		this.totalSalary = totalSalary;
	}
}
