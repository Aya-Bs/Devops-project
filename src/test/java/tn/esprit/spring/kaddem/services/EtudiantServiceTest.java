package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.*;
import tn.esprit.spring.kaddem.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

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
        etudiant.setPrenomE("User");

        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Test Department");

        contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setMontantContrat(1000);

        equipe = new Equipe();
        equipe.setIdEquipe(1);
        equipe.setNomEquipe("Test Team");
        equipe.setEtudiants(new HashSet<>());
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Given
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // When
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(etudiant, result.get(0));
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testAddEtudiant() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // When
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Then
        assertNotNull(result);
        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }

    @Test
    void testUpdateEtudiant() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // When
        Etudiant result = etudiantService.updateEtudiant(etudiant);

        // Then
        assertNotNull(result);
        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }

    @Test
    void testRetrieveEtudiant() {
        // Given
        when(etudiantRepository.findById(anyInt())).thenReturn(Optional.of(etudiant));

        // When
        Etudiant result = etudiantService.retrieveEtudiant(1);

        // Then
        assertNotNull(result);
        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).findById(anyInt());
    }

    @Test
    void testRemoveEtudiant() {
        // Given
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        doNothing().when(etudiantRepository).delete(etudiant);

        // When
        etudiantService.removeEtudiant(1);

        // Then
        verify(etudiantRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testRemoveEtudiantNotFound() {
        // Given
        when(etudiantRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            etudiantService.removeEtudiant(1);
        });
        verify(etudiantRepository, times(1)).findById(1);
        verify(etudiantRepository, never()).delete(any());
    }

    @Test
    void testAssignEtudiantToDepartement() {
        // Given
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // When
        etudiantService.assignEtudiantToDepartement(1, 1);

        // Then
        assertEquals(departement, etudiant.getDepartement());
        verify(etudiantRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testAssignEtudiantToDepartementNotFound() {
        // Given
        when(etudiantRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            etudiantService.assignEtudiantToDepartement(1, 1);
        });
        verify(etudiantRepository, times(1)).findById(1);
        verify(departementRepository, never()).findById(anyInt());
    }

    @Test
    void testAddAndAssignEtudiantToEquipeAndContract() {
        // Given
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        // When
        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        // Then
        assertNotNull(result);
        assertEquals(etudiant, contrat.getEtudiant());
        assertTrue(equipe.getEtudiants().contains(etudiant));
        verify(contratRepository, times(1)).findById(1);
        verify(equipeRepository, times(1)).findById(1);
    }

    @Test
    void testAddAndAssignEtudiantToEquipeAndContractNotFound() {
        // Given
        when(contratRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);
        });
        verify(contratRepository, times(1)).findById(1);
        verify(equipeRepository, never()).findById(anyInt());
    }

    @Test
    void testGetEtudiantsByDepartement() {
        // Given
        List<Etudiant> expectedEtudiants = new ArrayList<>();
        expectedEtudiants.add(etudiant);
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(1)).thenReturn(expectedEtudiants);

        // When
        List<Etudiant> result = etudiantService.getEtudiantsByDepartement(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(etudiant, result.get(0));
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(1);
    }

    @Test
    void testGetEtudiantsByDepartementEmpty() {
        // Given
        when(etudiantRepository.findEtudiantsByDepartement_IdDepart(1)).thenReturn(new ArrayList<>());

        // When
        List<Etudiant> result = etudiantService.getEtudiantsByDepartement(1);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(etudiantRepository, times(1)).findEtudiantsByDepartement_IdDepart(1);
    }
}