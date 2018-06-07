package com.ing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a order request is not valid
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "Invalid request";

	/**
	 * Parameterized constructor with custom message
	 *
	 * @param message custom error message
	 */
	public InvalidRequestException(final String message) {
		super(message);
	}
}
