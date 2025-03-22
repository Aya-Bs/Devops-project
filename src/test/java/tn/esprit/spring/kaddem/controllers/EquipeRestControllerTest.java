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
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.services.IEquipeService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipeRestControllerTest {

	@Mock
	private IEquipeService equipeService;

	@InjectMocks
	private EquipeRestController equipeRestController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(equipeRestController).build();
	}

	@Test
	public void testGetEquipes() throws Exception {
		// Arrange
		Equipe equipe1 = new Equipe();
		equipe1.setIdEquipe(1);
		Equipe equipe2 = new Equipe();
		equipe2.setIdEquipe(2);
		List<Equipe> equipes = Arrays.asList(equipe1, equipe2);

		when(equipeService.retrieveAllEquipes()).thenReturn(equipes);

		// Act & Assert
		mockMvc.perform(get("/equipe"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idEquipe").value(1))
				.andExpect(jsonPath("$[1].idEquipe").value(2));

		verify(equipeService, times(1)).retrieveAllEquipes();
	}

	@Test
	public void testRetrieveEquipe() throws Exception {
		// Arrange
		Equipe equipe = new Equipe();
		equipe.setIdEquipe(1);

		when(equipeService.retrieveEquipe(1)).thenReturn(equipe);

		// Act & Assert
		mockMvc.perform(get("/equipe/retrieve-equipe/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idEquipe").value(1));

		verify(equipeService, times(1)).retrieveEquipe(1);
	}

	@Test
	public void testAddEquipe() throws Exception {
		// Arrange
		Equipe equipe = new Equipe();
		equipe.setIdEquipe(1);

		when(equipeService.addEquipe(any(Equipe.class))).thenReturn(equipe);

		// Act & Assert
		mockMvc.perform(post("/equipe/add-equipe")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"idEquipe\": 1}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idEquipe").value(1));

		verify(equipeService, times(1)).addEquipe(any(Equipe.class));
	}

	@Test
	public void testRemoveEquipe() throws Exception {
		// Arrange
		doNothing().when(equipeService).deleteEquipe(1);

		// Act & Assert
		mockMvc.perform(delete("/equipe/remove-equipe/1"))
				.andExpect(status().isOk());

		verify(equipeService, times(1)).deleteEquipe(1);
	}

	@Test
	public void testUpdateEquipe() throws Exception {
		// Arrange
		Equipe equipe = new Equipe();
		equipe.setIdEquipe(1);

		when(equipeService.updateEquipe(any(Equipe.class))).thenReturn(equipe);

		// Act & Assert
		mockMvc.perform(put("/equipe/update-equipe")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"idEquipe\": 1}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idEquipe").value(1));

		verify(equipeService, times(1)).updateEquipe(any(Equipe.class));
	}

	@Test
	public void testFaireEvoluerEquipes() throws Exception {
		// Arrange
		doNothing().when(equipeService).evoluerEquipes();

		// Act & Assert
		mockMvc.perform(put("/equipe/faireEvoluerEquipes"))
				.andExpect(status().isOk());

		verify(equipeService, times(1)).evoluerEquipes();
	}
}