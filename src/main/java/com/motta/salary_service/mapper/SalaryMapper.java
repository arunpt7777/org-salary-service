package com.motta.salary_service.mapper;

import com.motta.salary_service.entity.Salary;
import com.motta.salary_service.model.SalaryDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SalaryMapper {

	// Convert Salary JPA Entity into SalaryDTO
	public SalaryDTO mapToSalaryDTO(Salary salary) {
		SalaryDTO salaryDTO = new SalaryDTO();
		BeanUtils.copyProperties(salary, salaryDTO);
		return salaryDTO;
	}

	// Convert Salary JPA Entity into SalaryDTO
	public Salary mapToSalary(SalaryDTO salaryDTO) {
		Salary salary = new Salary();
		BeanUtils.copyProperties(salaryDTO, salary);
		return salary;
	}
}
