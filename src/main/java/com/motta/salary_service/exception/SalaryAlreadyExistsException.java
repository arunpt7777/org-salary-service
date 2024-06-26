package com.motta.salary_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class SalaryAlreadyExistsException extends RuntimeException {

	public SalaryAlreadyExistsException(String message) {
		super(message);
	}
}
