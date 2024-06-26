package com.motta.salary_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.motta.salary_service.model.SalaryDTO;
import com.motta.salary_service.service.SalaryService;

import jakarta.validation.Valid;

@RestController
public class SalaryController {

	@Autowired
	private SalaryService salaryService;

	// create Salary REST API
	@PostMapping("/salaries")
	public ResponseEntity<SalaryDTO> createSalary(@Valid @RequestBody SalaryDTO salaryDTO) {
		SalaryDTO savedSalary = salaryService.createSalary(salaryDTO);
		return new ResponseEntity<>(savedSalary, HttpStatus.CREATED);
	}

	// Retrieve Salary by id REST API
	@GetMapping("/salaries/{id}")
	public ResponseEntity<SalaryDTO> retrieveSalaryById(@PathVariable("id") Integer id) {
		SalaryDTO salary = salaryService.retrieveSalaryById(id);
		return new ResponseEntity<>(salary, HttpStatus.OK);
	}

	// Retrieve Salary by id using RequestParam REST API
	// For example, http://localhost:8080/salary?id=10001
	@GetMapping("/salary")
	public ResponseEntity<SalaryDTO> retrieveSalaryByIdRequestParam(@RequestParam Integer id) {
		SalaryDTO salary = salaryService.retrieveSalaryById(id);
		return new ResponseEntity<>(salary, HttpStatus.OK);
	}

	// Retrieve All Salaries REST API
	@GetMapping("/salaries")
	public ResponseEntity<List<SalaryDTO>> retrieveAllSalaries() {
		List<SalaryDTO> salaries = salaryService.retrieveAllSalaries();
		return new ResponseEntity<>(salaries, HttpStatus.OK);
	}

	// Update Salary REST API
	@PutMapping("/salaries/{id}")
	public ResponseEntity<SalaryDTO> updateSalary(@PathVariable("id") Integer id, @RequestBody SalaryDTO salary) {
		salary.setId(id);
		SalaryDTO updatedSalary = salaryService.updateSalary(salary);
		return new ResponseEntity<>(updatedSalary, HttpStatus.OK);
	}

	// Delete Salary REST API
	@DeleteMapping("/salaries/{id}")
	public ResponseEntity<String> deleteSalary(@PathVariable("id") Integer id) {
		salaryService.deleteSalary(id);
		return new ResponseEntity<>("Salary successfully deleted!", HttpStatus.OK);
	}

	// Retrieve All Salaries by currency REST API
	@GetMapping("/salariesByCurrency/{currency}")
	public ResponseEntity<List<SalaryDTO>> getAllSalariesByGender(@PathVariable("gender") String currency) {
		List<SalaryDTO> maleSalaries = salaryService.retrieveAllSalariesByCurrency(currency);
		return new ResponseEntity<>(maleSalaries, HttpStatus.OK);
	}
}
