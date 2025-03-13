package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.repositories.ContratRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    private Contrat contrat;

    @BeforeEach
    void setUp() {
        contrat = new Contrat();
        contrat.setIdContrat(1);
    }

    @Test
    void retrieveAllContrats() {
        List<Contrat> contrats = Arrays.asList(contrat, new Contrat());
        when(contratRepository.findAll()).thenReturn(contrats);
        List<Contrat> result = contratService.retrieveAllContrats();
        assertEquals(2, result.size());
    }

    @Test
    void addContrat() {
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat savedContrat = contratService.addContrat(contrat);
        assertNotNull(savedContrat);
        assertEquals(1, savedContrat.getIdContrat());
    }

    @Test
    void retrieveContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        Contrat retrievedContrat = contratService.retrieveContrat(1);
        assertNotNull(retrievedContrat);
        assertEquals(1, retrievedContrat.getIdContrat());
    }

    @Test
    void removeContrat() {
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        doNothing().when(contratRepository).delete(contrat);

        contratService.removeContrat(1);
        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void updateContrat() {
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat updatedContrat = contratService.updateContrat(contrat);
        assertNotNull(updatedContrat);
        assertEquals(1, updatedContrat.getIdContrat());
    }


}