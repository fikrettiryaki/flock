/**
 *
 */
package com.ing.service;

import com.ing.model.dto.OrderConfirmDto;
import com.ing.model.dto.OrderDto;
import com.ing.model.dto.OrderQuantitiesDto;
import com.ing.model.entity.CustomerOrder;
import com.ing.repository.CustomerOrderRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

	private final CustomerOrderRepository customerOrderRepository;

	@Override
	public OrderConfirmDto createOrder(final OrderDto orderDto) {
		CustomerOrder order = CustomerOrder
			.builder().customer(orderDto.getCustomer()).milk(orderDto.getOrder().getMilk()).wool(orderDto.getOrder().getWool()).build();
		order = customerOrderRepository.saveAndFlush(order);
		return OrderConfirmDto.builder().OrderID(order.getId()).order(orderDto.getOrder()).build();
	}

	@Override
	public List<OrderDto> findAllOrders() {
		List<CustomerOrder> orders = customerOrderRepository.findAll();
		return orders.stream()
			.map(customerOrder -> convertToDto(customerOrder)).collect(Collectors.toList());

	}

	private OrderDto convertToDto(final CustomerOrder customerOrder) {
		return OrderDto.builder().customer(customerOrder.getCustomer())
			.order(OrderQuantitiesDto.builder().milk(customerOrder.getMilk()).wool(customerOrder.getWool()).build()).build();
	}
}

