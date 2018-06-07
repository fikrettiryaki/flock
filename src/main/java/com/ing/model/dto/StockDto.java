package com.ing.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDto {

	private int milk;
	private int wool;
}
