/**
 *
 */
package com.ing.service;

import com.ing.model.dto.OrderConfirmDto;
import com.ing.model.dto.OrderDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

	/**
	 * Customer order request.
	 * Customer information and order details are taken as parameter
	 * An order confirmation response is returned.
	 * Any order blocking issue is signalled by exception
	 *
	 * @param stock
	 * @return
	 */
	OrderConfirmDto createOrder(OrderDto stock);

	List<OrderDto> findAllOrders();

}
