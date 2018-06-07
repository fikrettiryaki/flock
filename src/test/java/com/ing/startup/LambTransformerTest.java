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

public class LambTransformerTest {
	private LambTransformer fixture;

	public static Document fromString(String xml) throws IOException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
	}

	@Before
	public void setUp() {
		fixture = new LambTransformer();
	}

	@Test
	@SneakyThrows
	public void convertXmlElementToAnimal_male_noMilk() {
		//given
		Document doc = fromString("<lamb name=\"Teemo\" sex=\"m\" wool=\"5\"/>");

		//when
		Animal animal = fixture.convertXmlElementToAnimal(doc.getDocumentElement());

		//then
		assertEquals(animal.getMilk(), null);
		assertEquals(animal.getName(), "Teemo");
		assertEquals(animal.getAnimalType(), AnimalType.lamb);
		assertEquals(animal.getWool(), Integer.valueOf(5));
		assertEquals(animal.getSex(), "m");

	}

	@Test
	@SneakyThrows
	public void convertXmlElementToAnimal_female_withMilk() {
		//given
		Document doc = fromString("<lamb name=\"FFF\" sex=\"f\" wool=\"6\"/>");

		//when
		Animal animal = fixture.convertXmlElementToAnimal(doc.getDocumentElement());

		//then
		assertEquals(animal.getMilk(), null);
		assertEquals(animal.getName(), "FFF");
		assertEquals(animal.getAnimalType(), AnimalType.lamb);
		assertEquals(animal.getWool(), Integer.valueOf(6));
		assertEquals(animal.getSex(), "f");

	}

}
