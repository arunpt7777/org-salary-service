package com.motta.salary_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.motta.salary_service.entity.Salary;
import com.motta.salary_service.exception.AttendanceNotFoundException;
import com.motta.salary_service.exception.EmployeeNotFoundException;
import com.motta.salary_service.exception.SalaryAlreadyExistsException;
import com.motta.salary_service.exception.SalaryNotFoundException;
import com.motta.salary_service.mapper.SalaryMapper;
import com.motta.salary_service.model.AttendanceDTO;
import com.motta.salary_service.model.EmployeeDTO;
import com.motta.salary_service.model.SalaryDTO;
import com.motta.salary_service.repository.SalaryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaryServiceImplementation implements SalaryService {

	@Autowired
	private SalaryRepository repository;

	@Override
	public SalaryDTO createSalary(SalaryDTO salaryDTO) {

		// CHeck if id already exists
		Optional<Salary> salary = repository.findById(salaryDTO.getId());
		if (salary.isPresent())
			throw new SalaryAlreadyExistsException("Salary id = " + salaryDTO.getId() + " already Exists!");

		// Convert SalaryDTO into User JPA Entity
		Salary newSalary = SalaryMapper.mapToSalary(salaryDTO);
		Salary savedSalary = repository.save(newSalary);

		// Convert Salary JPA entity to UserDto
		SalaryDTO savedSalaryDTO = SalaryMapper.mapToSalaryDTO(savedSalary);
		return savedSalaryDTO;
	}

	@Override
	public SalaryDTO retrieveSalaryById(Integer id) {
		Salary salary = repository.findById(id).get();
		if (salary == null)
			throw new SalaryNotFoundException("Salary id = " + id + " not found. Please enter different id");
		return SalaryMapper.mapToSalaryDTO(salary);
	}

	@Override
	public List<SalaryDTO> retrieveAllSalaries() {
		List<Salary> salarys = repository.findAll();
		return salarys.stream().map(SalaryMapper::mapToSalaryDTO).collect(Collectors.toList());
	}

	@Override
	public SalaryDTO updateSalary(SalaryDTO salaryDTO) {
		Salary existingSalary = repository.findById(salaryDTO.getId()).get();
		if (existingSalary == null)
			throw new SalaryNotFoundException(
					"Salary id = " + salaryDTO.getId() + " not found. Please enter different id");

		existingSalary.setSalaryPerDay(salaryDTO.getSalaryPerDay());
		existingSalary.setTotalSalary(salaryDTO.getTotalSalary());
		existingSalary.setCurrency(salaryDTO.getCurrency());

		Salary updatedSalary = repository.save(existingSalary);
		return SalaryMapper.mapToSalaryDTO(updatedSalary);
	}

	@Override
	public void deleteSalary(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<SalaryDTO> retrieveAllSalariesByCurrency(String currency) {
		List<Salary> salarys = repository.findAll();
		return salarys.stream().filter(emp -> emp.getCurrency().equalsIgnoreCase(currency))
				.map(SalaryMapper::mapToSalaryDTO).collect(Collectors.toList());
	}

	@Override
	public SalaryDTO calculateSalary(Integer employeeId) {

		// Call employee-service
		ResponseEntity<EmployeeDTO> employeeResponseEntity = new RestTemplate()
				.getForEntity("http://localhost:8080/employees/{employeeId}", EmployeeDTO.class, employeeId);
		EmployeeDTO employeeDTO = employeeResponseEntity.getBody();
		if (employeeDTO == null) {
			throw new EmployeeNotFoundException("Employee not found");
		}

		Integer salaryId = employeeDTO.getSalaryId();

		// Call salary-service
		ResponseEntity<SalaryDTO> salaryResponseEntity = new RestTemplate()
				.getForEntity("http://localhost:8000/salaries/{salaryId}", SalaryDTO.class, salaryId);
		SalaryDTO salaryDTO = salaryResponseEntity.getBody();
		if (salaryDTO == null) {
			throw new SalaryNotFoundException("Salary not found");
		}

		// Call attendance-service
		ResponseEntity<AttendanceDTO> attendancResponseEntity = new RestTemplate().getForEntity(
				"http://localhost:9000/attendancesByEmployeeId/{employeeId}", AttendanceDTO.class, employeeId);
		AttendanceDTO attendanceDTO = attendancResponseEntity.getBody();

		if (attendanceDTO == null) {
			throw new AttendanceNotFoundException("Attendance not found");
		}

		// return salary object with updated salary
		Double totalSalary = salaryDTO.getSalaryPerDay() * attendanceDTO.getNumberOfWorkingDays();
		salaryDTO.setTotalSalary(totalSalary);
		return salaryDTO;
	}
}
