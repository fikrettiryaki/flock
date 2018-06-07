/**
 *
 */
package com.ing.service;

import com.ing.model.dto.FlockDto;
import com.ing.model.dto.StockDto;

public interface FlockService {

	/**
	 * Returns all animals existing in the flock
	 * @return
	 */
	FlockDto findAllAnimals();

	/**
	 * Returns available stock
	 * Available stock means the amount of stock which is ready to be sold at that moment
	 *
	 *
	 * @return
	 */
	StockDto getAvailableStock();
}
