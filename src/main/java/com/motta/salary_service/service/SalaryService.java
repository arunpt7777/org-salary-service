package com.motta.salary_service.service;

import java.util.List;

import com.motta.salary_service.model.SalaryDTO;

public interface SalaryService {

	SalaryDTO createSalary(SalaryDTO salaryDTO);

	SalaryDTO retrieveSalaryById(Integer id);

	List<SalaryDTO> retrieveAllSalaries();

	SalaryDTO updateSalary(SalaryDTO salaryDTO);

	void deleteSalary(Integer id);

	List<SalaryDTO> retrieveAllSalariesByCurrency(String currency);

}
