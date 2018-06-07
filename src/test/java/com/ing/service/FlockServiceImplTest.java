package com.ing.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.ing.model.dto.FlockDto;
import com.ing.model.dto.StockDto;
import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;
import com.ing.repository.AnimalRepository;
import com.ing.repository.CustomerOrderRepository;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FlockServiceImplTest {

	@Mock
	private AnimalRepository animalRepository;

	@Mock
	private CustomerOrderRepository customerOrderRepository;

	@InjectMocks
	private FlockServiceImpl fixture;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		fixture = new FlockServiceImpl(animalRepository, customerOrderRepository);
	}

	@Test
	public void findAllAnimals_existing_success() {
		//given
		List<Animal> animals = Lists.newArrayList(
			Animal.builder().animalType(AnimalType.goat).name("GOAT").build(),
			Animal.builder().animalType(AnimalType.lamb).name("LAMB").build());

		when(animalRepository.findAll()).thenReturn(animals);
		//when
		final FlockDto flock = fixture.findAllAnimals();
		//then
		assertEquals(flock.getFlock().size(), 2);
		assertEquals(flock.getFlock().get(0).getAnimalType(), AnimalType.goat);
	}

	@Test
	public void getAvailableStock_existing_success() {
		//given
		final int milkStock = 100;
		final int woolStock = 50;
		final int milkOrder = 10;
		final int woolOrder = 20;

		when(animalRepository.findTotalMilk()).thenReturn(milkStock);
		when(animalRepository.findTotalWool()).thenReturn(woolStock);

		when(customerOrderRepository.findTotalMilkOrder()).thenReturn(milkOrder);
		when(customerOrderRepository.findTotalWoolOrder()).thenReturn(woolOrder);
		//when
		final StockDto stock = fixture.getAvailableStock();
		//then
		assertEquals(stock.getMilk(), 90);
		assertEquals(stock.getWool(), 30);
	}
}
