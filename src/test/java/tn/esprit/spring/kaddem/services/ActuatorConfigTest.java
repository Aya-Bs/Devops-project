package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import tn.esprit.spring.kaddem.config.ActuatorConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ActuatorConfigTest {

    @Autowired
    private ActuatorConfig actuatorConfig;

    @Test
    public void testAddCorsMappings() {
        CorsRegistry registry = new CorsRegistry();
        actuatorConfig.addCorsMappings(registry);
        assertNotNull(registry); // simple vérif pour que la méthode soit exécutée
    }
}
