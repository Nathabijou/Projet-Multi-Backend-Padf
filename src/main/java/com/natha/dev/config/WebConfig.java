package com.natha.dev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Pèmèt tout pwen final yo (endpoints)
                .allowedOrigins(allowedOrigins) // Itilize adrès ki nan application.properties yo
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Pèmèt tout metòd HTTP ki nesesè yo
                .allowedHeaders("*") // Pèmèt tout header yo
                .allowCredentials(true);
    }
}
