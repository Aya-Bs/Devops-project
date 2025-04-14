package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteServiceImplTest {

    @InjectMocks
    UniversiteServiceImpl universiteService;

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    DepartementRepository departementRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUniversite() {
        Universite u = new Universite();
        when(universiteRepository.save(u)).thenReturn(u);
        Universite result = universiteService.addUniversite(u);
        assertNotNull(result);
        verify(universiteRepository).save(u);
    }

    @Test
    void testRetrieveAllUniversites() {
        List<Universite> universites = Arrays.asList(new Universite(), new Universite());
        when(universiteRepository.findAll()).thenReturn(universites);
        List<Universite> result = universiteService.retrieveAllUniversites();
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateUniversite() {
        Universite u = new Universite();
        when(universiteRepository.save(u)).thenReturn(u);
        Universite updated = universiteService.updateUniversite(u);
        assertNotNull(updated);
    }

    @Test
    void testRetrieveUniversite() {
        Universite u = new Universite();
        u.setIdUniv(1);
        when(universiteRepository.findById(1)).thenReturn(Optional.of(u));
        Universite result = universiteService.retrieveUniversite(1);
        assertEquals(1, result.getIdUniv());
    }

    @Test
    void testDeleteUniversite() {
        Universite u = new Universite();
        u.setIdUniv(1);
        when(universiteRepository.findById(1)).thenReturn(Optional.of(u));
        universiteService.deleteUniversite(1);
        verify(universiteRepository).delete(u);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        Universite u = new Universite();
        Departement d = new Departement();
        Set<Departement> deps = new HashSet<>();
        u.setDepartements(deps);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(u));
        when(departementRepository.findById(2)).thenReturn(Optional.of(d));
        universiteService.assignUniversiteToDepartement(1, 2);
        assertTrue(u.getDepartements().contains(d));
    }

    @Test
    void testRetrieveDepartementsByUniversite() {
        Departement d = new Departement();
        Set<Departement> deps = new HashSet<>();
        deps.add(d);
        Universite u = new Universite();
        u.setDepartements(deps);
        when(universiteRepository.findById(1)).thenReturn(Optional.of(u));
        Set<Departement> result = universiteService.retrieveDepartementsByUniversite(1);
        assertEquals(1, result.size());
    }
}
