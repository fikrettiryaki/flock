package com.ing.startup;

import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;

import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LambTransformer implements XmlToAnimalTransformer {

	private static final AnimalType TARGET_ANIMAL = AnimalType.lamb;

	@Override
	public AnimalType getTargetAnimal() {
		return TARGET_ANIMAL;
	}

	@Override
	public Animal convertXmlElementToAnimal(final Element element) {
		return Animal.builder()
			.animalType(TARGET_ANIMAL)
			.name(element.getAttribute("name"))
			.sex(element.getAttribute("sex"))
			.wool(Integer.parseInt(element.getAttribute("wool")))
			.build();
	}
}
