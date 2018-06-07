package com.ing.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderQuantitiesDto {

	private Integer milk;
	private Integer wool;

}
