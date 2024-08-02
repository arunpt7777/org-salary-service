package com.motta.salary_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidSalaryException extends RuntimeException {

	public InvalidSalaryException(String message) {
		super(message);
	}
}
