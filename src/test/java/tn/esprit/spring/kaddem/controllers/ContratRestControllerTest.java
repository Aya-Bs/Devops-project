package tn.esprit.spring.kaddem.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.IContratService;
import tn.esprit.spring.kaddem.entities.Specialite;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContratRestControllerTest {



	@InjectMocks
	private ContratRestController contratRestController;


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IContratService contratService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(contratRestController).build();
	}

	// Test for retrieving all contracts
	@Test
	public void testGetContrats() throws Exception {
		// Arrange
		Contrat contrat1 = new Contrat(1,new Date(), new Date(), Specialite.IA, false, 1000);
		Contrat contrat2 = new Contrat(2,new Date(), new Date(), Specialite.CLOUD, false, 2000);
		when(contratService.retrieveAllContrats()).thenReturn(Arrays.asList(contrat1, contrat2));

		// Act & Assert
		mockMvc.perform(get("/contrat"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2))
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())); // Debug response
	}

	// Test for retrieving a contract by ID
	@Test
	public void testRetrieveContrat() throws Exception {
		// Arrange
		Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1000);
		when(contratService.retrieveContrat(1)).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(get("/contrat/retrieve-contrat/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.specialite").value("IA"))
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())); // Debug response
	}

	// Test for adding a contract
	@Test
	public void testAddContrat() throws Exception {
		// Arrange
		Contrat contrat = new Contrat(3,new Date(), new Date(), Specialite.IA, false, 1000);
		when(contratService.addContrat(any(Contrat.class))).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(post("/contrat/add-contrat")
						.contentType("application/json")
						.content("{\"dateDebutContrat\": \"2025-03-22\", \"dateFinContrat\": \"2025-03-22\", \"specialite\": \"IA\", \"archive\": false, \"montantContrat\": 1000}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.specialite").value("IA"))
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())); // Debug response
	}

	// Test for removing a contract
	@Test
	public void testRemoveContrat() throws Exception {
		// Arrange
		doNothing().when(contratService).removeContrat(1);

		// Act & Assert
		mockMvc.perform(delete("/contrat/remove-contrat/1"))
				.andExpect(status().isOk())
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())); // Debug response
	}

	// Test for updating a contract
	@Test
	public void testUpdateContrat() throws Exception {
		// Arrange
		Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 1000);
		when(contratService.updateContrat(any(Contrat.class))).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(put("/contrat/update-contrat")
						.contentType("application/json")
						.content("{\"dateDebutContrat\": \"2025-03-22\", \"dateFinContrat\": \"2025-03-22\", \"specialite\": \"IA\", \"archive\": false, \"montantContrat\": 1000}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.specialite").value("IA"))
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())); // Debug response
	}

	// Test for assigning a contract to a student
	@Test
	public void testAssignContratToEtudiant() throws Exception {
		// Arrange
		Contrat contrat = new Contrat();
		contrat.setSpecialite(Specialite.IA); // Set other fields as needed
		when(contratService.affectContratToEtudiant(1, "John", "Doe")).thenReturn(contrat);

		// Act & Assert
		mockMvc.perform(put("/contrat/assignContratToEtudiant/1/John/Doe"))
				.andExpect(status().isOk())
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())) // Debug response
				.andExpect(jsonPath("$.specialite").value("IA"));
	}

	// Test for getting the number of valid contracts between two dates
	@Test
	public void testGetNbContratsValides() throws Exception {
		// Arrange
		when(contratService.nbContratsValides(any(Date.class), any(Date.class))).thenReturn(5);

		// Act & Assert
		mockMvc.perform(get("/contrat/getnbContratsValides/2025-03-22/2025-03-22"))
				.andExpect(status().isOk())
				.andExpect(content().string("5"))
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())); // Debug response
	}

	// Test for calculating revenue between two dates
	@Test
	public void testCalculChiffreAffaireEntreDeuxDates() throws Exception {
		// Arrange
		when(contratService.getChiffreAffaireEntreDeuxDates(any(Date.class), any(Date.class))).thenReturn(1000.0f);

		// Act & Assert
		mockMvc.perform(get("/contrat/calculChiffreAffaireEntreDeuxDate/2025-03-22/2025-03-22"))
				.andExpect(status().isOk())
				.andExpect(content().string("1000.0"))
				.andDo(result -> System.out.println("Response Body: " + result.getResponse().getContentAsString())); // Debug response
	}
}