package com.ing.startup;

import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;

import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SheepTransformer implements XmlToAnimalTransformer {

	private static final int FEMALE_MILK_PRODCUTION = 30;
	private static final String FEMALE_SYMBOL = "f";
	private static AnimalType TARGET_ANIMAL = AnimalType.sheep;

	@Override
	public AnimalType getTargetAnimal() {
		return TARGET_ANIMAL;
	}

	@Override
	public Animal convertXmlElementToAnimal(Element element) {
		final String sex = element.getAttribute("sex");

		return Animal.builder()
			.animalType(TARGET_ANIMAL)
			.name(element.getAttribute("name"))
			.sex(sex)
			.wool(Integer.parseInt(element.getAttribute("wool")))
			.milk(FEMALE_SYMBOL.equals(sex) ? FEMALE_MILK_PRODCUTION : null)
			.build();
	}
}
