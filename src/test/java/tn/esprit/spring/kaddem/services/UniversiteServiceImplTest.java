package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;
    private Departement departement;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniv(1);
        universite.setNomUniv("Test University");
        universite.setDepartements(new HashSet<>());

        departement = new Departement();
        departement.getIdDepart();
        departement.setNomDepart("Test Department");
    }

    @Test
    void testRetrieveAllUniversites() {
        // Arrange
        List<Universite> expectedUniversites = new ArrayList<>();
        expectedUniversites.add(universite);
        when(universiteRepository.findAll()).thenReturn(expectedUniversites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(universite.getIdUniv(), result.get(0).getIdUniv());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversite() {
        // Arrange
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        Universite result = universiteService.addUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals(universite.getIdUniv(), result.getIdUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        // Arrange
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversite(1);

        // Assert
        assertNotNull(result);
        assertEquals(universite.getIdUniv(), result.getIdUniv());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteUniversite() {
        // Arrange
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        doNothing().when(universiteRepository).delete(any(Universite.class));

        // Act
        universiteService.deleteUniversite(1);

        // Assert
        verify(universiteRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        // Arrange
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        // Act
        universiteService.assignUniversiteToDepartement(1, 1);

        // Assert
        assertTrue(universite.getDepartements().contains(departement));
        verify(universiteRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveDepartementsByUniversite() {
        // Arrange
        Set<Departement> expectedDepartements = new HashSet<>();
        expectedDepartements.add(departement);
        universite.setDepartements(expectedDepartements);
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        // Act
        Set<Departement> result = universiteService.retrieveDepartementsByUniversite(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(departement));
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testNomDeLaMethode() {
        // Arrange (préparation)
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        // Act (action)
        Universite result = universiteService.retrieveUniversite(1);

        // Assert (vérification)
        assertNotNull(result);
        assertEquals(universite.getIdUniv(), result.getIdUniv());
        verify(universiteRepository, times(1)).findById(1);
    }
} 