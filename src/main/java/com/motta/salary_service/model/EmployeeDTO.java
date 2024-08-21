package com.motta.salary_service.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EmployeeDTO {

	private int id;
	private int employeeNumber;
	private int age;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String gender;
	private int salaryId;
	public Timestamp modifiedAt;
	public Timestamp createdAt;

	public EmployeeDTO() {
	}

	public EmployeeDTO(int id, int employeeNumber, int age, String firstName, String lastName, String email, String phone, String gender, int salaryId, Timestamp modifiedAt, Timestamp createdAt) {
		this.id = id;
		this.employeeNumber = employeeNumber;
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.salaryId = salaryId;
		this.modifiedAt = modifiedAt;
		this.createdAt = createdAt;
	}
}
