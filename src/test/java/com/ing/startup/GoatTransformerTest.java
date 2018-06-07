package com.ing.startup;

import static org.junit.Assert.assertEquals;

import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import lombok.SneakyThrows;

public class GoatTransformerTest {
	private GoatTransformer fixture;

	public static Document fromString(String xml) throws IOException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
	}

	@Before
	public void setUp() {
		fixture = new GoatTransformer();
	}

	@Test
	@SneakyThrows
	public void convertXmlElementToAnimal_male_noMilk() {
		//given
		Document doc = fromString("<goat name=\"Stanislas\" sex=\"m\"/>");

		//when
		Animal animal = fixture.convertXmlElementToAnimal(doc.getDocumentElement());

		//then
		assertEquals(animal.getMilk(), null);
		assertEquals(animal.getName(), "Stanislas");
		assertEquals(animal.getAnimalType(), AnimalType.goat);
		assertEquals(animal.getWool(), null);
		assertEquals(animal.getSex(), "m");

	}

	@Test
	@SneakyThrows
	public void convertXmlElementToAnimal_female_withMilk() {
		//given
		Document doc = fromString("<goat name=\"XXX\" sex=\"f\"/>");

		//when
		Animal animal = fixture.convertXmlElementToAnimal(doc.getDocumentElement());

		//then
		assertEquals(animal.getMilk(), Integer.valueOf(50));
		assertEquals(animal.getName(), "XXX");
		assertEquals(animal.getAnimalType(), AnimalType.goat);
		assertEquals(animal.getWool(), null);
		assertEquals(animal.getSex(), "f");

	}

}
