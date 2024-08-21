package com.motta.salary_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.motta.salary_service.util.SalaryConstants.*;

import com.motta.salary_service.exception.*;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
@Slf4j
public class SalaryServiceImplementation implements SalaryService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SalaryMapper salaryMapper;

	@Value("${salary.id.initialValue}")
	private Integer initialValueOfPrimaryKey;

	@Value("${salary.per.day.min}")
	private Integer salaryPerDayMin;

	@Value("${salary.total.min}")
	private Integer salaryTotalMin;

	@Autowired
	private SalaryRepository repository;

	@Override
	@Transactional
	public SalaryDTO createSalary(SalaryDTO salaryDTO) {
		validateSalaryDTO(salaryDTO);
		// CHeck if id already exists
		Optional<Salary> salary = repository.findById(salaryDTO.getId());
		log.info(LOG_MESSAGE_SALARY_ALREADY_EXISTS, salaryDTO.getId());

		if (salary.isPresent())
			throw new SalaryAlreadyExistsException(EXCEPTION_MESSAGE_SALARY_ALREADY_EXISTS);

		// Convert SalaryDTO into User JPA Entity
		Salary newSalary = salaryMapper.mapToSalary(salaryDTO);
		Salary savedSalary = repository.save(newSalary);
		log.info(LOG_MESSAGE_SALARY_PERSISTED, newSalary.getId());

		// Convert Salary JPA entity to UserDto
        return salaryMapper.mapToSalaryDTO(savedSalary);
	}

	@Override
	public SalaryDTO retrieveSalaryById(Integer id) {
		Salary salary = repository.findById(id).orElse(null);
		if (salary == null) {
			throw new SalaryNotFoundException(EXCEPTION_MESSAGE_SALARY_NOT_FOUND);
		}
		log.error(LOG_MESSAGE_SALARY_NOT_FOUND, id);
        return salaryMapper.mapToSalaryDTO(salary);
	}

	@Override
	public List<SalaryDTO> retrieveAllSalaries() {
		List<Salary> salaries = repository.findAll();
		return salaries.stream().map(salaryMapper::mapToSalaryDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public SalaryDTO updateSalary(SalaryDTO salaryDTO) {
		validateSalaryDTO(salaryDTO);
		Salary existingSalary = repository.findById(salaryDTO.getId()).orElse(new Salary());
		BeanUtils.copyProperties(existingSalary, salaryDTO);
		Salary updatedScheme = repository.save(existingSalary);
		log.error(LOG_MESSAGE_SALARY_UPDATE_FAILED, existingSalary.getId());
		return salaryMapper.mapToSalaryDTO(updatedScheme);
	}

	@Override
	@Transactional
	public void deleteSalary(Integer id) {
		repository.deleteById(id);
		log.error(LOG_MESSAGE_SALARY_DELETE_FAILED,  String.valueOf(id));
	}

	@Override
	public List<SalaryDTO> retrieveAllSalariesByCurrency(String currency) {
		List<Salary> salaries = repository.findAll();
		return salaries.stream().filter(emp -> emp.getCurrency().equalsIgnoreCase(currency))
				.map(salaryMapper::mapToSalaryDTO).collect(Collectors.toList());
	}

	@Override
	public SalaryDTO calculateSalary(Integer employeeId) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// Call employee-service
		ResponseEntity<EmployeeDTO> employeeResponseEntity = restTemplate.exchange(URL_GET_SALARY_BY_EMPLOYEE_BY_ID + String.valueOf(employeeId), HttpMethod.GET, entity, EmployeeDTO.class);
		EmployeeDTO employeeDTO = employeeResponseEntity.getBody();
		if (employeeDTO == null) {
			throw new EmployeeNotFoundException(EXCEPTION_MESSAGE_EMPLOYEE_NOT_FOUND);
		}
		Integer salaryId = employeeDTO.getSalaryId();

		// Call salary-
		SalaryDTO salaryDTO = retrieveSalaryById(salaryId);
		if (salaryDTO == null) {
			throw new SalaryNotFoundException(LOG_MESSAGE_SALARY_NOT_FOUND);
		}

		// Call attendance-service
		ResponseEntity<AttendanceDTO> attendancResponseEntity = new RestTemplate().exchange(
				URL_GET_ATTENDANCE_BY_EMPLOYEE_ID + String.valueOf(employeeId), HttpMethod.GET, entity, AttendanceDTO.class);
		AttendanceDTO attendanceDTO = attendancResponseEntity.getBody();

		if (attendanceDTO == null) {
			log.error(LOG_MESSAGE_ATTENDANCE_NOT_FOUND);
			throw new AttendanceNotFoundException("Attendance not found");
		}

		// return salary object with updated salary
		double totalSalary = salaryDTO.getSalaryPerDay() * attendanceDTO.getNumberOfWorkingDays();
		salaryDTO.setTotalSalary(totalSalary);
		return salaryDTO;
	}

	@Override
	public void validateSalaryDTO(SalaryDTO salaryDTO) {

		if (salaryDTO.getId() == 0){
			throw new InvalidSalaryException(EXCEPTION_SALARY_ID_MANDATORY);
		}

		if (salaryDTO.getId()<initialValueOfPrimaryKey) {
			throw new InvalidSalaryException(EXCEPTION_SALARY_ID_INVALID + initialValueOfPrimaryKey);
		}

		if (salaryDTO.getCurrency().isEmpty()) {
			throw new InvalidSalaryException(EXCEPTION_CURRENCY_MANDATORY);
		}
		if (salaryDTO.getSalaryPerDay() == 0.0) {
			throw new InvalidSalaryException(EXCEPTION_SALARY_PER_DAY_MANDATORY);
		}

		if (salaryDTO.getSalaryPerDay()<salaryPerDayMin) {
			throw new InvalidSalaryException(EXCEPTION_SALARY_PER_DAY_BELOW_MIN_INVALID);
		}

		if (salaryDTO.getTotalSalary()<salaryTotalMin) {
			throw new InvalidSalaryException(EXCEPTION_TOTAL_SALARY_BELOW_MIN_INVALID);
		}
	}

}
