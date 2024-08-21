package com.motta.salary_service.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AttendanceDTO {

	private int id;
	private int numberOfWorkingDays;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	private int employeeId;

	public AttendanceDTO(int id, int numberOfWorkingDays, Timestamp createdAt, Timestamp modifiedAt, int employeeId) {
		this.id = id;
		this.numberOfWorkingDays = numberOfWorkingDays;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.employeeId = employeeId;
	}
}
