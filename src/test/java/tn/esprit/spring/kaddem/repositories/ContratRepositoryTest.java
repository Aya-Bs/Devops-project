package tn.esprit.spring.kaddem.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ContratRepositoryTest {

    @Autowired
    private ContratRepository contratRepository;

    @Test
    public void testFindAll() {
        // Arrange
        Contrat contrat1 = new Contrat(new Date(), new Date(), Specialite.IA, false, 1000);
        Contrat contrat2 = new Contrat(new Date(), new Date(), Specialite.CLOUD, false, 2000);
        contratRepository.save(contrat1);
        contratRepository.save(contrat2);

        // Act
        List<Contrat> contrats = contratRepository.findAll();

        // Assert
        assertEquals(2, contrats.size());
    }

    @Test
    public void testGetnbContratsValides() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        Contrat contrat = new Contrat(startDate, endDate, Specialite.IA, false, 1000);
        contratRepository.save(contrat);

        // Act
        Integer nbContrats = contratRepository.getnbContratsValides(startDate, endDate);

        // Assert
        assertEquals(1, nbContrats);
    }
}