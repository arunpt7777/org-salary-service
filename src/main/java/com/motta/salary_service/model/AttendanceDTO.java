package com.motta.salary_service.model;

import java.sql.Timestamp;

public class AttendanceDTO {

	private Integer id;

	private Integer numberOfWorkingDays;

	private Timestamp createdAt;

	private Timestamp modifiedAt;

	private Integer employeeId;

	public AttendanceDTO(Integer id, Integer numberOfWorkingDays, Timestamp createdAt, Timestamp modifiedAt,
			Integer employeeId) {
		super();
		this.id = id;
		this.numberOfWorkingDays = numberOfWorkingDays;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.employeeId = employeeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumberOfWorkingDays() {
		return numberOfWorkingDays;
	}

	public void setNumberOfWorkingDays(Integer numberOfWorkingDays) {
		this.numberOfWorkingDays = numberOfWorkingDays;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "AttendanceDTO [id=" + id + ", numberOfWorkingDays=" + numberOfWorkingDays + ", createdAt=" + createdAt
				+ ", modifiedAt=" + modifiedAt + ", employeeId=" + employeeId + "]";
	}

}
