package com.ing.startup;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.ing.model.entity.Animal;
import com.ing.model.enums.AnimalType;
import com.ing.repository.AnimalRepository;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StartupXmlImporterTest {

	private final String xmlPath = "src/test/resources/flock/flock-test.xml";
	Animal goat = Animal.builder().animalType(AnimalType.goat).build();
	Animal sheep = Animal.builder().animalType(AnimalType.sheep).build();
	Animal lamb = Animal.builder().animalType(AnimalType.lamb).build();
	@Mock
	private XmlToAnimalTransformer mockTransformerGoat;
	@Mock
	private XmlToAnimalTransformer mockTransformerLamb;
	@Mock
	private XmlToAnimalTransformer mockTransformerSheep;
	@Mock
	private AnimalRepository animalRepository;
	private StartupXmlImporter startupXmlImporter;

	@Before
	public void setUp() throws Exception {
		final List<XmlToAnimalTransformer> transformers = ImmutableList.of(mockTransformerGoat, mockTransformerLamb, mockTransformerSheep);

		when(mockTransformerGoat.getTargetAnimal()).thenReturn(AnimalType.goat);
		when(mockTransformerGoat.convertXmlElementToAnimal(any())).thenReturn(goat);
		when(mockTransformerLamb.getTargetAnimal()).thenReturn(AnimalType.lamb);
		when(mockTransformerLamb.convertXmlElementToAnimal(any())).thenReturn(lamb);
		when(mockTransformerSheep.getTargetAnimal()).thenReturn(AnimalType.sheep);
		when(mockTransformerSheep.convertXmlElementToAnimal(any())).thenReturn(sheep);

		startupXmlImporter = new StartupXmlImporter(transformers, animalRepository, xmlPath);
	}

	@Test
	public void readAndSaveFlock() throws Exception {
		//given

		//when
		startupXmlImporter.readAndSaveFlock();

		//then
		verify(animalRepository, times(2)).saveAndFlush(goat);
		verify(animalRepository, times(2)).saveAndFlush(sheep);
		verify(animalRepository, times(1)).saveAndFlush(lamb);
	}

}
