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
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.services.IDepartementService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartementRestControllerTest {

	@Mock
	private IDepartementService departementService;

	@InjectMocks
	private DepartementRestController departementRestController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(departementRestController).build();
	}

	@Test
	public void testGetDepartements() throws Exception {
		// Arrange
		Departement departement1 = new Departement();
		departement1.setIdDepart(1);
		Departement departement2 = new Departement();
		departement2.setIdDepart(2);
		List<Departement> departements = Arrays.asList(departement1, departement2);

		when(departementService.retrieveAllDepartements()).thenReturn(departements);

		// Act & Assert
		mockMvc.perform(get("/departement"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idDepart").value(1))
				.andExpect(jsonPath("$[1].idDepart").value(2));

		verify(departementService, times(1)).retrieveAllDepartements();
	}

	@Test
	public void testRetrieveDepartement() throws Exception {
		// Arrange
		Departement departement = new Departement();
		departement.setIdDepart(1);

		when(departementService.retrieveDepartement(1)).thenReturn(departement);

		// Act & Assert
		mockMvc.perform(get("/departement/retrieve-departement/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idDepart").value(1));

		verify(departementService, times(1)).retrieveDepartement(1);
	}

	@Test
	public void testAddDepartement() throws Exception {
		// Arrange
		Departement departement = new Departement();
		departement.setIdDepart(1);

		when(departementService.addDepartement(any(Departement.class))).thenReturn(departement);

		// Act & Assert
		mockMvc.perform(post("/departement/add-departement")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"idDepart\": 1}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idDepart").value(1));

		verify(departementService, times(1)).addDepartement(any(Departement.class));
	}

	@Test
	public void testRemoveDepartement() throws Exception {
		// Arrange
		doNothing().when(departementService).deleteDepartement(1);

		// Act & Assert
		mockMvc.perform(delete("/departement/remove-departement/1"))
				.andExpect(status().isOk());

		verify(departementService, times(1)).deleteDepartement(1);
	}

	@Test
	public void testUpdateDepartement() throws Exception {
		// Arrange
		Departement departement = new Departement();
		departement.setIdDepart(1);

		when(departementService.updateDepartement(any(Departement.class))).thenReturn(departement);

		// Act & Assert
		mockMvc.perform(put("/departement/update-departement")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"idDepart\": 1}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idDepart").value(1));

		verify(departementService, times(1)).updateDepartement(any(Departement.class));
	}
}