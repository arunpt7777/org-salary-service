package com.motta.salary_service.mapper;

import com.motta.salary_service.entity.Salary;
import com.motta.salary_service.model.SalaryDTO;

public class SalaryMapper {

	// Convert Salary JPA Entity into SalaryDTO
	public static SalaryDTO mapToSalaryDTO(Salary salary) {
		SalaryDTO salaryDTO = new SalaryDTO(salary.getId(), salary.getCurrency(), salary.getAmount());
		return salaryDTO;
	}

	// Convert SalaryDTO into Salary JPA Entity
	public static Salary mapToSalary(SalaryDTO salaryDTO) {
		Salary salary = new Salary(salaryDTO.getId(), salaryDTO.getCurrency(), salaryDTO.getAmount());
		return salary;
	}
}
