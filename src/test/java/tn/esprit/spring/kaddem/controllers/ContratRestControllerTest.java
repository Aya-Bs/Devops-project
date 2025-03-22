package tn.esprit.spring.kaddem.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.IContratService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContratRestControllerTest {

	@Mock
	private IContratService contratService;

	@InjectMocks
	private ContratRestController contratRestController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(contratRestController).build();
	}

	@Test
	public void testGetContrats() throws Exception {
		// Arrange
		Contrat contrat1 = new Contrat();
		contrat1.setIdContrat(1);
		Contrat contrat2 = new Contrat();
		contrat2.setIdContrat(2);
		List<Contrat> contrats = Arrays.asList(contrat1, contrat2);

		when(contratService.retrieveAllContrats()).thenReturn(contrats);

		// Act & Assert
		mockMvc.perform(get("/contrat"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idContrat").value(1))
				.andExpect(jsonPath("$[1].idContrat").value(2));

		verify(contratService, times(1)).retrieveAllContrats();
	}

	@Test
	public void testRetrieveContrat() throws Exception {
		// Arrange
		Contrat contrat = new Contrat();
		contrat.setIdContrat(1);

		when(contratService.retrieveContrat(1)).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(get("/contrat/retrieve-contrat/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idContrat").value(1));

		verify(contratService, times(1)).retrieveContrat(1);
	}

	@Test
	public void testAddContrat() throws Exception {
		// Arrange
		Contrat contrat = new Contrat();
		contrat.setIdContrat(1);

		when(contratService.addContrat(any(Contrat.class))).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(post("/contrat/add-contrat")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"idContrat\": 1}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idContrat").value(1));

		verify(contratService, times(1)).addContrat(any(Contrat.class));
	}

	@Test
	public void testRemoveContrat() throws Exception {
		// Arrange
		doNothing().when(contratService).removeContrat(1);

		// Act & Assert
		mockMvc.perform(delete("/contrat/remove-contrat/1"))
				.andExpect(status().isOk());

		verify(contratService, times(1)).removeContrat(1);
	}

	@Test
	public void testUpdateContrat() throws Exception {
		// Arrange
		Contrat contrat = new Contrat();
		contrat.setIdContrat(1);

		when(contratService.updateContrat(any(Contrat.class))).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(put("/contrat/update-contrat")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"idContrat\": 1}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idContrat").value(1));

		verify(contratService, times(1)).updateContrat(any(Contrat.class));
	}

	@Test
	public void testAssignContratToEtudiant() throws Exception {
		// Arrange
		Contrat contrat = new Contrat();
		contrat.setIdContrat(1);

		when(contratService.affectContratToEtudiant(1, "John", "Doe")).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(put("/contrat/assignContratToEtudiant/1/John/Doe"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idContrat").value(1));

		verify(contratService, times(1)).affectContratToEtudiant(1, "John", "Doe");
	}

	@Test
	public void testGetNbContratsValides() throws Exception {
		// Arrange
		Date startDate = new Date();
		Date endDate = new Date();

		when(contratService.nbContratsValides(startDate, endDate)).thenReturn(5);

		// Act & Assert
		mockMvc.perform(get("/contrat/getnbContratsValides/{startDate}/{endDate}", startDate, endDate))
				.andExpect(status().isOk())
				.andExpect(content().string("5"));

		verify(contratService, times(1)).nbContratsValides(startDate, endDate);
	}

	@Test
	public void testCalculChiffreAffaireEntreDeuxDates() throws Exception {
		// Arrange
		Date startDate = new Date();
		Date endDate = new Date();

		when(contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate)).thenReturn(1000.0f);

		// Act & Assert
		mockMvc.perform(get("/contrat/calculChiffreAffaireEntreDeuxDate/{startDate}/{endDate}", startDate, endDate))
				.andExpect(status().isOk())
				.andExpect(content().string("1000.0"));

		verify(contratService, times(1)).getChiffreAffaireEntreDeuxDates(startDate, endDate);
	}
}