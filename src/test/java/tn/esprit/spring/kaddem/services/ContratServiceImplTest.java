package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    private Contrat contrat;
    private Etudiant etudiantDTO;



    @BeforeEach
    void setUp() {
        contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setArchive(false);
        contrat.setSpecialite(Specialite.IA); // Ensure Specialite is set
        contrat.setDateDebutContrat(new Date()); // Set a valid start date
        contrat.setDateFinContrat(new Date(System.currentTimeMillis() + 86400000)); // Set a valid end date (1 day later)
        contrat.setMontantContrat(1000); // Set a valid montantContrat

        etudiantDTO = new Etudiant();
        etudiantDTO.setNomE("John");
        etudiantDTO.setPrenomE("Doe");
        etudiantDTO.setContrats(new HashSet<>());
    }

    @Test
    void retrieveAllContrats_ShouldReturnList() {
        // Arrange
        List<Contrat> contrats = Collections.singletonList(contrat);
        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        List<Contrat> result = contratService.retrieveAllContrats();

        // Assert
        assertEquals(1, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void addContrat_ShouldSaveAndReturnContrat() {
        // Arrange
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.addContrat(contrat);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdContrat());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void updateContrat_ShouldUpdateAndReturnContrat() {
        // Arrange
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.updateContrat(contrat);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdContrat());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void retrieveContrat_ShouldReturnContrat() {
        // Arrange
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        // Act
        Contrat result = contratService.retrieveContrat(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdContrat());
        verify(contratRepository, times(1)).findById(1);
    }

    @Test
    void removeContrat_ShouldDeleteContrat() {
        // Arrange
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        doNothing().when(contratRepository).delete(any(Contrat.class));

        // Act
        contratService.removeContrat(1);

        // Assert
        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void affectContratToEtudiant_ShouldAssignContrat() {
        // Arrange
        when(etudiantRepository.findByNomEAndPrenomE("John", "Doe")).thenReturn(etudiantDTO);
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        Contrat result = contratService.affectContratToEtudiant(1, "John", "Doe");

        // Assert
        assertNotNull(result);
        assertEquals(etudiantDTO, result.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void nbContratsValides_ShouldReturnCount() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(5);

        // Act
        Integer result = contratService.nbContratsValides(startDate, endDate);

        // Assert
        assertEquals(5, result);
        verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
    }

    @Test
    void retrieveAndUpdateStatusContrat_ShouldUpdateStatus() {
        // Arrange
        contrat.setIdContrat(1);
        contrat.setArchive(false);
        contrat.setSpecialite(Specialite.IA);
        contrat.setDateDebutContrat(new Date()); // Set a valid start date
        contrat.setDateFinContrat(new Date()); // Set dateFinContrat to the current date to trigger archiving
        contrat.setMontantContrat(1000);

        List<Contrat> contrats = Collections.singletonList(contrat);
        when(contratRepository.findAll()).thenReturn(contrats);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        // Act
        contratService.retrieveAndUpdateStatusContrat();

        // Assert
        verify(contratRepository, times(1)).findAll();
        verify(contratRepository, times(1)).save(contrat);
        assertTrue(contrat.getArchive(), "Expected contract to be archived");
    }

    @Test
    void getChiffreAffaireEntreDeuxDates_ShouldCalculateRevenue() {
        // Arrange
        Date startDate = new Date(); // Current date
        Date endDate = new Date(System.currentTimeMillis() + 86400000); // 1 day later
        List<Contrat> contrats = Collections.singletonList(contrat);
        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        // Assert
        assertTrue(result > 0, "Expected revenue to be greater than 0");
        verify(contratRepository, times(1)).findAll();
    }
}