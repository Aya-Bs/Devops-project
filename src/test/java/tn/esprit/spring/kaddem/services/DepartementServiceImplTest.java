package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import javax.persistence.EntityNotFoundException;
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
        departement.setNomDepart("Computer Science");
    }

    @Test
    void retrieveAllDepartements_ShouldReturnList() {
        // Arrange
        List<Departement> departements = Arrays.asList(departement);
        when(departementRepository.findAll()).thenReturn(departements);

        // Act
        List<Departement> result = departementService.retrieveAllDepartements();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Computer Science", result.get(0).getNomDepart());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void addDepartement_ShouldSaveAndReturnDepartement() {
        // Arrange
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        // Act
        Departement result = departementService.addDepartement(departement);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void updateDepartement_ShouldUpdateAndReturnDepartement() {
        // Arrange
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        // Act
        Departement result = departementService.updateDepartement(departement);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void retrieveDepartement_ShouldReturnDepartement() {
        // Arrange
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Act
        Departement result = departementService.retrieveDepartement(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdDepart());
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    void retrieveDepartement_NotFound_ShouldThrowException() {
        // Arrange
        when(departementRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            departementService.retrieveDepartement(99);
        });
        verify(departementRepository, times(1)).findById(99);
    }

    @Test
    void deleteDepartement_ShouldDeleteDepartement() {
        // Arrange
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        doNothing().when(departementRepository).delete(any(Departement.class));

        // Act
        departementService.deleteDepartement(1);

        // Assert
        verify(departementRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).delete(departement);
    }

    @Test
    void deleteDepartement_NotFound_ShouldThrowException() {
        // Arrange
        when(departementRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            departementService.deleteDepartement(99);
        });
        verify(departementRepository, times(1)).findById(99);
        verify(departementRepository, never()).delete(any());
    }
}