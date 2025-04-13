package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import tn.esprit.spring.kaddem.config.ActuatorConfig;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActuatorConfigTest {

    @Test
    void testAddCorsMappings() {
        // Arrange
        ActuatorConfig actuatorConfig = new ActuatorConfig();
        CorsRegistry registry = mock(CorsRegistry.class);
        CorsRegistration corsRegistration = mock(CorsRegistration.class);

        // Mock behavior
        when(registry.addMapping("/actuator/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins("http://localhost:4200", "https://tonfrontend.com")).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods("GET", "POST")).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders("*")).thenReturn(corsRegistration);

        // Act
        actuatorConfig.addCorsMappings(registry);

        // Assert
        verify(registry).addMapping("/actuator/**");
        verify(corsRegistration).allowedOrigins("http://localhost:4200", "https://tonfrontend.com");
        verify(corsRegistration).allowedMethods("GET", "POST");
        verify(corsRegistration).allowedHeaders("*");
    }
}
