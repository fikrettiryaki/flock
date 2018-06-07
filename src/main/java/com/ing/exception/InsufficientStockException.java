package com.ing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when an order is greater then current stock
 */
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InsufficientStockException extends RuntimeException {

	/**
	 * Parameterized constructor with custom message
	 *
	 * @param message custom error message
	 */
	public InsufficientStockException(final String message ) {
		super(message);
	}
}
