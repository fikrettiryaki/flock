package com.ing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when an animal specific parser is not available
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UnsupportedAnimalException extends RuntimeException {

	/**
	 * Parameterized constructor with custom message
	 *
	 * @param message custom error message
	 */
	public UnsupportedAnimalException(final String message ) {
		super(message);
	}
}
