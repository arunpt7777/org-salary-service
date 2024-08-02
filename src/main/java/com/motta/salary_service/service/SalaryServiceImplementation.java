package com.motta.salary_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.motta.salary_service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.motta.salary_service.entity.Salary;
import com.motta.salary_service.mapper.SalaryMapper;
import com.motta.salary_service.model.AttendanceDTO;
import com.motta.salary_service.model.EmployeeDTO;
import com.motta.salary_service.model.SalaryDTO;
import com.motta.salary_service.repository.SalaryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class SalaryServiceImplementation implements SalaryService {

	private static final Logger log = LoggerFactory.getLogger(SalaryServiceImplementation.class);

	@Value("${salary.id.initialValue}")
	private Integer initialValueOfPrimaryKey;

	@Value("${salary.per.day.min}")
	private Integer salaryPerDayMin;

	@Value("${salary.total.min}")
	private Integer salaryTotalMin;

	@Autowired
	private SalaryRepository repository;

	@Override
	public SalaryDTO createSalary(SalaryDTO salaryDTO) {

		// CHeck if id already exists
		Optional<Salary> salary = repository.findById(salaryDTO.getId());
		log.info("Association id = {} not found.", salaryDTO.getId());

		if (salary.isPresent())
			throw new SalaryAlreadyExistsException("Salary id = " + salaryDTO.getId() + " already Exists!");

		// Convert SalaryDTO into User JPA Entity
		Salary newSalary = SalaryMapper.mapToSalary(salaryDTO);
		Salary savedSalary = repository.save(newSalary);
		log.info("Salary id = {} has been persisted.", newSalary.getId());

		// Convert Salary JPA entity to UserDto
        return SalaryMapper.mapToSalaryDTO(savedSalary);
	}

	@Override
	public SalaryDTO retrieveSalaryById(Integer id) {
		Salary salary = repository.findById(id).get();
		log.error("Salary id = {} not found. Please enter different id", id);
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
		log.info("Salary id = {} has been fetched.", salaryDTO.getId());

        existingSalary.setSalaryPerDay(salaryDTO.getSalaryPerDay());
		existingSalary.setTotalSalary(salaryDTO.getTotalSalary());
		existingSalary.setCurrency(salaryDTO.getCurrency());

		Salary updatedSalary = repository.save(existingSalary);
		log.info("Updating Salary id = {} is complete.", existingSalary.getId());
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
			log.error("Fetching salary id = {} has failed.", salaryDTO.getId());
			throw new SalaryNotFoundException("Salary not found");
		}

		// Call attendance-service
		ResponseEntity<AttendanceDTO> attendancResponseEntity = new RestTemplate().getForEntity(
				"http://localhost:9000/attendancesByEmployeeId/{employeeId}", AttendanceDTO.class, employeeId);
		AttendanceDTO attendanceDTO = attendancResponseEntity.getBody();

		if (attendanceDTO == null) {
			log.error("Fetching Attendance id has failed.");
			throw new AttendanceNotFoundException("Attendance not found");
		}

		// return salary object with updated salary
		Double totalSalary = salaryDTO.getSalaryPerDay() * attendanceDTO.getNumberOfWorkingDays();
		salaryDTO.setTotalSalary(totalSalary);
		return salaryDTO;
	}

	@Override
	public void validateSalaryDTO(SalaryDTO salaryDTO) {

		if (salaryDTO.getId()==null) {
			throw new InvalidSalaryException("Salary Id is mandatory");
		}

		if (salaryDTO.getId()<initialValueOfPrimaryKey) {
			throw new InvalidSalaryException("Salary Id must not be less than the initial value of: " + initialValueOfPrimaryKey);
		}

		if (salaryDTO.getCurrency()==null) {
			throw new InvalidSalaryException("Currency is mandatory");
		}
		if (salaryDTO.getSalaryPerDay()==null) {
			throw new InvalidSalaryException("Salary Per Day is mandatory");
		}

		if (salaryDTO.getSalaryPerDay()<salaryPerDayMin) {
			throw new InvalidSalaryException("Salary Per Day is mandatory");
		}

		if (salaryDTO.getTotalSalary()<salaryTotalMin) {
			throw new InvalidSalaryException("Total Salary is mandatory");
		}

	}

}
