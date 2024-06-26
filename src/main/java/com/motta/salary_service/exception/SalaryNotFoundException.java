package com.motta.salary_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SalaryNotFoundException extends RuntimeException {

	public SalaryNotFoundException(String message) {
		super(message);
	}
}
