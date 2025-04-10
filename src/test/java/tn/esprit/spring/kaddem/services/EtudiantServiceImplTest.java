package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;
    private Departement departement;
    private Contrat contrat;
    private Equipe equipe;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Test");
        etudiant.setPrenomE("Student");


        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Test Department");

        contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setMontantContrat(10);

        equipe = new Equipe();
        equipe.setIdEquipe(1);
        equipe.setNomEquipe("Test Team");
        equipe.setEtudiants(new HashSet<>());
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Arrange
        List<Etudiant> expectedEtudiants = new ArrayList<>();
        expectedEtudiants.add(etudiant);
        when(etudiantRepository.findAll()).thenReturn(expectedEtudiants);

        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(etudiant.getIdEtudiant(), result.get(0).getIdEtudiant());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testAddEtudiant() {
        // Arrange
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals(etudiant.getIdEtudiant(), result.getIdEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(1);

        // Assert
        assertNotNull(result);
        assertEquals(etudiant.getIdEtudiant(), result.getIdEtudiant());
        verify(etudiantRepository, times(1)).findById(1);
    }

    @Test
    void testRemoveEtudiant() {
        // Arrange
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        doNothing().when(etudiantRepository).delete(any(Etudiant.class));

        // Act
        etudiantService.removeEtudiant(1);

        // Assert
        verify(etudiantRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testAssignEtudiantToDepartement() {
        // Arrange
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        etudiantService.assignEtudiantToDepartement(1, 1);

        // Assert
        assertEquals(departement, etudiant.getDepartement());
        verify(etudiantRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testAddAndAssignEtudiantToEquipeAndContract() {
        // Arrange
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        // Act
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Assert
        assertNotNull(result);
        assertEquals(etudiant, contrat.getEtudiant());
        assertTrue(equipe.getEtudiants().contains(etudiant));
        verify(contratRepository, times(1)).findById(1);
        verify(equipeRepository, times(1)).findById(1);
    }

    @Test
    void testGetEtudiantsByDepartement() {
        // Arrange
        List<Etudiant> expectedEtudiants = new ArrayList<>();
        expectedEtudiants.add(etudiant);
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(1)).thenReturn(expectedEtudiants);

        // Act
        List<Etudiant> result = etudiantService.getEtudiantsByDepartement(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(etudiant.getIdEtudiant(), result.get(0).getIdEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(1);
    }
} 