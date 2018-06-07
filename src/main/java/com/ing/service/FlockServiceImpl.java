/**
 *
 */
package com.ing.service;

import com.ing.model.dto.FlockDto;
import com.ing.model.dto.StockDto;
import com.ing.repository.AnimalRepository;
import com.ing.repository.CustomerOrderRepository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class FlockServiceImpl implements FlockService {

	private final AnimalRepository animalRepository;
	private final CustomerOrderRepository customerOrderRepository;

	@Override
	public FlockDto findAllAnimals() {

		return FlockDto.builder().flock(animalRepository.findAll()).build();

	}

	@Override
	public StockDto getAvailableStock() {

		final int availableMilk = animalRepository.findTotalMilk() - customerOrderRepository.findTotalMilkOrder();
		final int availableWool = animalRepository.findTotalWool() - customerOrderRepository.findTotalWoolOrder();

		return StockDto.builder()
			.milk(availableMilk)
			.wool(availableWool)
			.build();
	}

}
