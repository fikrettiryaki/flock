package com.ing.controller;

import com.ing.model.dto.FlockDto;
import com.ing.model.dto.OrderConfirmDto;
import com.ing.model.dto.OrderDto;
import com.ing.model.dto.StockDto;
import com.ing.service.FlockService;
import com.ing.service.OrderService;
import com.ing.service.OrderValidateService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("farmshop")
@AllArgsConstructor
public class FarmshopRestController {

	private final FlockService flockService;

	private final OrderService orderService;

	private final OrderValidateService orderValidateService;

	/**
	 * Returns FlockDto which has the information of all animals in the flock
	 *
	 * @return FlockDto
	 */
	@GetMapping("flock")
	@ApiOperation("Get flock information")
	public ResponseEntity<FlockDto> findAll() {

		log.info("Flock request has arrived");
		return ResponseEntity.ok(flockService.findAllAnimals());
	}

	/**
	 * Returns StockDto which has to actual available stocks
	 *
	 * @return StockDto
	 */
	@GetMapping("stock")
	@ApiOperation("Get current available stock")
	public ResponseEntity<StockDto> findStock() {
		log.info("Stock request has arrived");
		return ResponseEntity.ok(flockService.getAvailableStock());
	}

	/**
	 * Returns a list of previous orders
	 *
	 * @return
	 */
	@GetMapping("orders")
	@ApiOperation("Get all orders")
	public ResponseEntity<List<OrderDto>> findOrders() {

		log.info("Find orders history request has arrived");

		return ResponseEntity.ok(orderService.findAllOrders());
	}

	/**
	 * Creates a new order
	 *
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("orders")
	@ApiOperation("Create an order ")
	@Transactional
	public ResponseEntity<OrderConfirmDto> createOrder(@RequestBody @Valid final OrderDto order, final HttpServletRequest request) {

		log.info("Create order request has arrived with parameter {}", order);
		orderValidateService.validateOrder(order);
		return ResponseEntity.ok(orderService.createOrder(order));
	}

}
