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

public class SheepTransformerTest {
	private SheepTransformer fixture;

	public static Document fromString(String xml) throws IOException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
	}

	@Before
	public void setUp() {
		fixture = new SheepTransformer();
	}

	@Test
	@SneakyThrows
	public void convertXmlElementToAnimal_male_noMilk() {
		//given
		Document doc = fromString("<sheep name=\"Robert\" sex=\"m\" wool=\"34\"/>");

		//when
		Animal animal = fixture.convertXmlElementToAnimal(doc.getDocumentElement());

		//then
		assertEquals(animal.getMilk(), null);
		assertEquals(animal.getName(), "Robert");
		assertEquals(animal.getAnimalType(), AnimalType.sheep);
		assertEquals(animal.getWool(), Integer.valueOf(34));
		assertEquals(animal.getSex(), "m");

	}

	@Test
	@SneakyThrows
	public void convertXmlElementToAnimal_female_withMilk() {
		//given
		Document doc = fromString(" <sheep name=\"Betty\" sex=\"f\" wool=\"28\"/>");

		//when
		Animal animal = fixture.convertXmlElementToAnimal(doc.getDocumentElement());

		//then
		assertEquals(animal.getMilk(), Integer.valueOf(30));
		assertEquals(animal.getName(), "Betty");
		assertEquals(animal.getAnimalType(), AnimalType.sheep);
		assertEquals(animal.getWool(), Integer.valueOf(28));
		assertEquals(animal.getSex(), "f");

	}

}
