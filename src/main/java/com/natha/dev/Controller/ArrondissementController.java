package com.natha.dev.Controller;

import com.natha.dev.Dto.ArrondissementDto;
import com.natha.dev.IService.ArrondissementIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArrondissementController {

    @Autowired
    private ArrondissementIService arrondissementIService;

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/arrondissement")
    List<ArrondissementDto> arrondissementList(){
        return arrondissementIService.findAll();
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @PostMapping("/arrondissement/create")
    public ArrondissementDto create(@RequestBody ArrondissementDto dto) {
        return arrondissementIService.save(dto);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/arrondissement/departement/{id}")
    public List<ArrondissementDto> getByDepartementId(@PathVariable Long id) {
        return arrondissementIService.getByDepartementId(id);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/arrondissement/departement/{departementId}/avec-projets")
    public List<ArrondissementDto> getByDepartementIdWithProjects(@PathVariable Long departementId) {
        return arrondissementIService.findByDepartementIdWithProjects(departementId);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/arrondissement/all")
    public List<ArrondissementDto> all() {
        return arrondissementIService.getAll();
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @DeleteMapping("/arrondissement/{id}")
    public void delete(@PathVariable Long id) {
        arrondissementIService.deleteById(id);
    }
}
