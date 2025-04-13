package tn.esprit.spring.kaddem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ActuatorConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/actuator/**")
                .allowedOrigins("http://localhost:4200", "https://tonfrontend.com")

                .allowedMethods("GET", "POST")
                .allowedHeaders("*");
    }
} 