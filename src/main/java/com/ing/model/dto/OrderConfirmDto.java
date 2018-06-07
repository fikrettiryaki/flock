package com.ing.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderConfirmDto {

	/**
	 * Order id as present in the database
	 */
	private long OrderID;

	/**
	 *
	 */
	private OrderQuantitiesDto order;

}
