package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomEt("Test");
        etudiant.setPrenomEt("User");
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
        doNothing().when(etudiantRepository).deleteById(anyInt());

        // When
        etudiantService.removeEtudiant(1);

        // Then
        verify(etudiantRepository, times(1)).deleteById(anyInt());
    }
} 