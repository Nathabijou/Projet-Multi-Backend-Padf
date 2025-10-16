package com.natha.dev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(
        value = "/healthz",
        produces = "text/plain"
    )
    public String healthz() {
        return "OK";
    }
    
    @GetMapping(
        value = "/actuator/health",
        produces = "application/json"
    )
    public String health() {
        return "{\"status\":\"UP\"}";
    }
    
    @GetMapping("/")
    public String root() {
        return "Service is running";
    }
}
