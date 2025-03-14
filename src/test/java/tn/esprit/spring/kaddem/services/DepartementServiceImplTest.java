package tn.esprit.spring.kaddem.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    private Departement departement;

    @BeforeEach
    void setUp() {
        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Informatique");
    }

    @Test
    void retrieveAllDepartements() {
        List<Departement> departements = Arrays.asList(departement, new Departement());
        when(departementRepository.findAll()).thenReturn(departements);

        List<Departement> result = departementService.retrieveAllDepartements();

        assertEquals(2, result.size());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void addDepartement() {
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement result = departementService.addDepartement(departement);

        assertNotNull(result);
        assertEquals("Informatique", result.getNomDepart());
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void updateDepartement() {
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement updated = departementService.updateDepartement(departement);

        assertNotNull(updated);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void retrieveDepartement() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        Departement result = departementService.retrieveDepartement(1);

        assertNotNull(result, "Le département ne devrait pas être null !");
        assertEquals(1, result.getIdDepart(), "L'ID du département devrait être 1 !");
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    void deleteDepartement() {
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        doNothing().when(departementRepository).delete(any(Departement.class));

        departementService.deleteDepartement(1);

        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).delete(departement);
    }
}