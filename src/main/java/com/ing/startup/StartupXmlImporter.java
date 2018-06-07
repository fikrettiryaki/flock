package com.ing.startup;

import com.ing.exception.UnsupportedAnimalException;
import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;
import com.ing.repository.AnimalRepository;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class StartupXmlImporter {

	private final AnimalRepository animalRepository;
	private Map<AnimalType, XmlToAnimalTransformer> animalTransformerMap;
	private String xmlPath;

	public StartupXmlImporter(List<XmlToAnimalTransformer> xmlToAnimalTransformers, AnimalRepository animalRepository,
		@Value("${xml.path}") final String xmlPath) {
		this.animalTransformerMap = xmlToAnimalTransformers.stream().collect(
			Collectors.toMap(x -> x.getTargetAnimal(), x -> x));
		this.animalRepository = animalRepository;
		this.xmlPath = xmlPath;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void readAndSaveFlock() {
		try {

			File xmlFile = new File(xmlPath);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();

			final Element element = document.getDocumentElement();
			final NodeList nodeList = element.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				final Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					final XmlToAnimalTransformer animalImporter = animalTransformerMap.get(AnimalType.valueOf(node.getNodeName()));
					if (animalImporter == null) {
						throw new UnsupportedAnimalException(
							String.format("Animal of type %s is not supported", AnimalType.valueOf(node.getNodeName())));
					}

					final Animal animal = animalImporter.convertXmlElementToAnimal((Element) node);
					animalRepository.saveAndFlush(animal);
					log.info("A {} with name {} has been created.", animal.getAnimalType(), animal.getName());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
