package com.ing.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ing.exception.InsufficientStockException;
import com.ing.model.dto.FlockDto;
import com.ing.model.dto.OrderDto;
import com.ing.model.dto.OrderQuantitiesDto;
import com.ing.model.dto.StockDto;
import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;
import com.ing.service.FlockService;
import com.ing.service.OrderService;
import com.ing.service.OrderValidateService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(value = FarmshopRestController.class, secure = false)
public class FarmshopRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FlockService flockService;

	@MockBean
	private OrderService orderService;

	@MockBean
	private OrderValidateService orderValidateService;

	@Autowired
	private FarmshopRestController farmshopRestController;

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetFlock_ValidRequest_Success() throws Exception {
		String expected = "{\"flock\":[{\"id\":1,\"animalType\":\"goat\",\"name\":\"TESTGOAT\",\"sex\":\"m\",\"milk\":10}]}";

		when(flockService.findAllAnimals())
			.thenReturn(
				FlockDto.builder()
					.flock(Lists.newArrayList(Animal.builder().id(1l).animalType(AnimalType.goat).milk(10).name("TESTGOAT").sex("m").build()))
					.build());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/farmshop/flock")
			.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();

		verify(flockService, times(1)).findAllAnimals();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetOrders_ValidRequest_Success() throws Exception {
		String expected = "[{\"customer\":\"TESTCUSTOMER\",\"order\":{\"milk\":5,\"wool\":10}}]";

		when(orderService.findAllOrders())
			.thenReturn(
				Lists.newArrayList(OrderDto.builder().customer("TESTCUSTOMER").order(OrderQuantitiesDto.builder().wool(10).milk(5).build()).build())
			);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/farmshop/orders")
			.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();

		verify(orderService, times(1)).findAllOrders();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testGetStock_ValidRequest_Success() throws Exception {

		//given
		String expected = "{\"milk\":5,\"wool\":10}";
		when(flockService.getAvailableStock())
			.thenReturn(StockDto.builder().wool(10).milk(5).build());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/farmshop/stock")
			.accept(MediaType.APPLICATION_JSON);

		//when
		MvcResult result = mvc.perform(requestBuilder).andReturn();

		//then
		verify(flockService, times(1)).getAvailableStock();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreateOrder_InvalidRequest_Fail() throws Exception {

		//Given
		OrderDto orderDto = OrderDto.builder().order(OrderQuantitiesDto.builder().milk(100).build()).build();
		doThrow(new InsufficientStockException("Insufficient")).when(orderValidateService).validateOrder(anyObject());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/farmshop/orders").content(asJsonString(orderDto)).contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON);

		//when
		MvcResult result = mvc.perform(requestBuilder).andReturn();

		//then
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), result.getResponse().getStatus());
	}

}