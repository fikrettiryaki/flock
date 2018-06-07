package com.ing.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ing.model.enums.AnimalType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Animal {
	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private AnimalType animalType;

	@NotNull
	private String name;

	@NotNull
	private String sex;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer wool;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer milk;

}
