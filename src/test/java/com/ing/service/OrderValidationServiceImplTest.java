package com.ing.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import com.ing.exception.InsufficientStockException;
import com.ing.exception.InvalidRequestException;
import com.ing.model.dto.OrderDto;
import com.ing.model.dto.OrderQuantitiesDto;
import com.ing.model.dto.StockDto;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderValidationServiceImplTest {

	@Mock
	private FlockService flockService;

	@InjectMocks
	private OrderValidateServiceImpl fixture;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		fixture = new OrderValidateServiceImpl(flockService);
	}

	@Test(expected = InvalidRequestException.class)
	public void validate_negativeValue_fail() {
		//given
		final OrderDto orderDto = OrderDto.builder().customer("CUSTOMER").order(OrderQuantitiesDto.builder().milk(-10).build()).build();

		//when
		fixture.validateOrder(orderDto);

		//then
		fail();
	}

	@Test(expected = InvalidRequestException.class)
	public void validate_noValue_fail() {
		//given
		final OrderDto orderDto = OrderDto.builder().customer("CUSTOMER").order(OrderQuantitiesDto.builder().build()).build();

		//when
		fixture.validateOrder(orderDto);

		//then
		fail();
	}

	@Test(expected = InsufficientStockException.class)
	public void validate_notEnoughStock_fail() {
		//given
		final OrderDto orderDto = OrderDto.builder().customer("CUSTOMER").order(OrderQuantitiesDto.builder().wool(100).build()).build();
		when(flockService.getAvailableStock()).thenReturn(StockDto.builder().milk(10).wool(10).build());

		//when
		fixture.validateOrder(orderDto);

		//then
		fail();
	}

	@Test
	public void validate_enoughStock_success() {
		//given
		final OrderDto orderDto = OrderDto.builder().customer("CUSTOMER").order(OrderQuantitiesDto.builder().wool(100).build()).build();
		when(flockService.getAvailableStock()).thenReturn(StockDto.builder().milk(1000).wool(1000).build());

		//when
		fixture.validateOrder(orderDto);

		//then
	}

}
