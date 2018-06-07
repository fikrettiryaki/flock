package com.ing.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.ing.model.dto.OrderDto;
import com.ing.model.entity.CustomerOrder;
import com.ing.repository.CustomerOrderRepository;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderServiceImplTest {

	@Mock
	private CustomerOrderRepository customerOrderRepository;

	@InjectMocks
	private OrderServiceImpl fixture;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		fixture = new OrderServiceImpl(customerOrderRepository);
	}

	@Test
	public void findAllAnimals_existing_success() {
		//given
		List<CustomerOrder> orders = Lists.newArrayList(
			CustomerOrder.builder().customer("test customer 1").milk(100).build(),
			CustomerOrder.builder().customer("test customer 2").wool(100).build());

		when(customerOrderRepository.findAll()).thenReturn(orders);
		//when
		final List<OrderDto> orderDtos = fixture.findAllOrders();
		//then
		assertEquals(orderDtos.size(), 2);
		assertEquals(orderDtos.get(0).getOrder().getMilk().intValue(), 100);
	}

}
