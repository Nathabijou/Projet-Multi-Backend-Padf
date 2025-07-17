package com.natha.dev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // La configuration CORS est désormais gérée de manière centralisée dans WebSecurityConfiguration
    // pour éviter les conflits et assurer la cohérence avec Spring Security.

}
