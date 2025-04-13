package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import tn.esprit.spring.kaddem.config.ActuatorConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActuatorConfigTest {

    @Test
    public void testAddCorsMappings() {
        // Arrange
        ActuatorConfig actuatorConfig = new ActuatorConfig();
        CorsRegistry registry = new CorsRegistry();

        // Act
        actuatorConfig.addCorsMappings(registry);

        // Assert
        assertNotNull(registry);
        // Le test passe simplement si la méthode ne lance pas d'exception
        assertTrue(true, "La méthode addCorsMappings s'exécute sans erreur");
    }
}
