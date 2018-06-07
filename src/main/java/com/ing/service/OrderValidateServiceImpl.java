package com.ing.service;

import com.ing.exception.InsufficientStockException;
import com.ing.exception.InvalidRequestException;
import com.ing.model.dto.OrderDto;
import com.ing.model.dto.StockDto;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OrderValidateServiceImpl implements OrderValidateService {

	private final FlockService flockService;

	public OrderValidateServiceImpl(final FlockService flockService) {
		this.flockService = flockService;
	}

	/**
	 * Validations provided in the order of execution
	 * <p>
	 * At least one order greater then 0 is present
	 * Orders are positive values.
	 * There is sufficient amount available for both orders
	 *
	 * @param order customer order details
	 */
	@Override
	public void validateOrder(final OrderDto order) {
		final Optional<Integer> milkOrder = Optional.ofNullable(order.getOrder().getMilk());

		final Optional<Integer> woolOrder = Optional.ofNullable(order.getOrder().getWool());

		if (!((milkOrder.isPresent() && milkOrder.get() > 0) || (woolOrder.isPresent() && woolOrder.get() > 0))) {
			throw new InvalidRequestException("Either Milk or Wool amount should be present");
		}

		if ((milkOrder.isPresent() && milkOrder.get() < 0) || (woolOrder.isPresent() && woolOrder.get() < 0)) {
			throw new InvalidRequestException("Orders should be a positive value");
		}

		final StockDto stock = flockService.getAvailableStock();

		if (milkOrder.isPresent()) {
			if (milkOrder.get() > stock.getMilk()) {

				throw new InsufficientStockException(String.format("Max milk order can not be more than %d liters", stock.getMilk()));
			}
		}

		if (woolOrder.isPresent()) {
			if (woolOrder.get() > stock.getWool()) {

				throw new InsufficientStockException(String.format("Max wool order can not be more than %d kg", stock.getWool()));
			}
		}
	}
}
