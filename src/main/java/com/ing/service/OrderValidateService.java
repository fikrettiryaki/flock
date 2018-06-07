package com.ing.service;

import com.ing.model.dto.OrderDto;

public interface OrderValidateService {

	/**
	 * Order validations
	 * Throws exception for unstatisfied validations
	 * @param order
	 */
	void validateOrder(OrderDto order);

}
