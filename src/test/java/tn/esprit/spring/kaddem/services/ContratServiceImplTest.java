package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContratServiceImplTest {

	@Mock
	private ContratRepository contratRepository;

	@InjectMocks
	private ContratServiceImpl contratService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRetrieveAllContrats() {
		// Arrange
		Contrat contrat1 = new Contrat(1, new Date(), new Date(), Specialite.IA, false, 1000);
		Contrat contrat2 = new Contrat(2, new Date(), new Date(), Specialite.CLOUD, false, 2000);
		when(contratRepository.findAll()).thenReturn(Arrays.asList(contrat1, contrat2));

		// Act
		List<Contrat> contrats = contratService.retrieveAllContrats();

		// Assert
		assertEquals(2, contrats.size());
		verify(contratRepository, times(1)).findAll();
	}

	@Test
	public void testAddContrat() {
		// Arrange
		Contrat contrat = new Contrat(3, new Date(), new Date(), Specialite.IA, false, 1000);
		when(contratRepository.save(contrat)).thenReturn(contrat);

		// Act
		Contrat savedContrat = contratService.addContrat(contrat);

		// Assert
		assertNotNull(savedContrat);
		assertEquals(Specialite.IA, savedContrat.getSpecialite());
		verify(contratRepository, times(1)).save(contrat);
	}

	@Test
	public void testRetrieveContrat() {
		// Arrange
		Contrat contrat = new Contrat(1, new Date(), new Date(), Specialite.IA, false, 1000);
		when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

		// Act
		Contrat foundContrat = contratService.retrieveContrat(1);

		// Assert
		assertNotNull(foundContrat);
		assertEquals(Specialite.IA, foundContrat.getSpecialite());
		verify(contratRepository, times(1)).findById(1);
	}

	@Test
	public void testRemoveContrat() {
		// Arrange
		doNothing().when(contratRepository).deleteById(1);

		// Act
		contratService.removeContrat(1);

		// Assert
		verify(contratRepository, times(1)).deleteById(1);
	}

	@Test
	public void testNbContratsValides() {
		// Arrange
		Date startDate = new Date();
		Date endDate = new Date();
		when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(5);

		// Act
		Integer nbContrats = contratService.nbContratsValides(startDate, endDate);

		// Assert
		assertEquals(5, nbContrats);
		verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
	}

	@Test
	public void testGetChiffreAffaireEntreDeuxDates() {
		// Arrange
		Date startDate = new Date();
		Date endDate = new Date();
		Contrat contrat1 = new Contrat(1, startDate, endDate, Specialite.IA, false, 1000);
		Contrat contrat2 = new Contrat(2, startDate, endDate, Specialite.CLOUD, false, 2000);
		when(contratRepository.findAll()).thenReturn(Arrays.asList(contrat1, contrat2));

		// Act
		float chiffreAffaire = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

		// Assert
		assertTrue(chiffreAffaire > 0); // Add specific assertions based on your logic
		verify(contratRepository, times(1)).findAll();
	}

	@Test
	public void testRetrieveAndUpdateStatusContrat() {
		// Arrange
		Contrat contrat = new Contrat(1, new Date(), new Date(), Specialite.IA, false, 1000);
		when(contratRepository.findAll()).thenReturn(Arrays.asList(contrat));

		// Act
		contratService.retrieveAndUpdateStatusContrat();

		// Assert
		verify(contratRepository, times(1)).findAll();
		verify(contratRepository, times(1)).save(contrat);
	}
}