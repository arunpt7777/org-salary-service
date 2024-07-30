package com.motta.salary_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.motta.salary_service.entity.Salary;
import com.motta.salary_service.exception.AttendanceNotFoundException;
import com.motta.salary_service.exception.SalaryNotFoundException;
import com.motta.salary_service.model.AttendanceDTO;

@RestController
public class SalaryCalculationController {

	// Method to calculate total Salary by getting number of working days from
	// attendance-service and multiplying with salary slab

	@GetMapping("/calculateSalary")
	public Salary calculateSalary() {

		Integer salaryId = 1;
		Integer attendanceId = 1;

		// Call salary-service
		ResponseEntity<Salary> salaryResponseEntity = new RestTemplate()
				.getForEntity("http://localhost:8000/salaries/{salaryId}", Salary.class, salaryId);
		Salary salary = salaryResponseEntity.getBody();
		if (salary == null) {
			throw new SalaryNotFoundException("Salary not found");
		}

		// Call attendance-service
		ResponseEntity<AttendanceDTO> attendancResponseEntity = new RestTemplate()
				.getForEntity("http://localhost:9000/attendances/{attendanceId}", AttendanceDTO.class, attendanceId);
		AttendanceDTO attendanceDTO = attendancResponseEntity.getBody();

		if (attendanceDTO == null) {
			throw new AttendanceNotFoundException("Attendance not found");
		}

		// return salary object with updated salary
		Double totalSalary = salary.getSalaryPerDay() * attendanceDTO.getNumberOfWorkingDays();
		salary.setTotalSalary(totalSalary);

		return salary;

	}
}
