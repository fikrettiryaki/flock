package com.ing.startup;

import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;

import org.w3c.dom.Element;

public interface XmlToAnimalTransformer {

	AnimalType getTargetAnimal();

	Animal convertXmlElementToAnimal(Element element);
}
