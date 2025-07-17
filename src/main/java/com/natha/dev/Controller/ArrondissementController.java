package com.natha.dev.Controller;

import com.natha.dev.Dto.ArrondissementDto;
import com.natha.dev.IService.ArrondissementIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ArrondissementController {

    @Autowired
    private ArrondissementIService arrondissementIService;

    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/arrondissement")
    List<ArrondissementDto> arrondissementList(){
        return arrondissementIService.findAll();
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/arrondissement/create")
    public ArrondissementDto create(@RequestBody ArrondissementDto dto) {
        return arrondissementIService.save(dto);
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER')")
    @GetMapping("/arrondissement/departement/{id}")
    public List<ArrondissementDto> getByDepartementId(@PathVariable Long id) {
        return arrondissementIService.getByDepartementId(id);
    }

    @GetMapping("/arrondissement/departement/{departementId}/avec-projets")
    public List<ArrondissementDto> getByDepartementIdWithProjects(@PathVariable Long departementId) {
        return arrondissementIService.findByDepartementIdWithProjects(departementId);
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/arrondissement/all")
    public List<ArrondissementDto> all() {
        return arrondissementIService.getAll();
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/arrondissement/{id}")
    public void delete(@PathVariable Long id) {
        arrondissementIService.deleteById(id);
    }
}
