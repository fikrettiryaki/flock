package com.ing.model.dto;

import com.ing.model.entity.Animal;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlockDto {

	private List<Animal> flock;

}
