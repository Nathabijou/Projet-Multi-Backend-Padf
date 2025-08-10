package com.natha.dev.Controller;

import com.natha.dev.Dto.ModuleBeneficiaireDto;
import com.natha.dev.IService.IModuleBeneficiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/module-beneficiaires")
public class ModuleBeneficiaireController {

    private final IModuleBeneficiaireService moduleBeneficiaireService;

    @Autowired
    public ModuleBeneficiaireController(IModuleBeneficiaireService moduleBeneficiaireService) {
        this.moduleBeneficiaireService = moduleBeneficiaireService;
    }

    @PostMapping("/module/{moduleId}/beneficiaire/{beneficiaireId}")
//    @PreAuthorize("hasRole('Admin', 'Manager', 'User')")
    public ResponseEntity<ModuleBeneficiaireDto> addBeneficiaireToModule(
            @PathVariable String moduleId,
            @PathVariable String beneficiaireId) {
        ModuleBeneficiaireDto dto = moduleBeneficiaireService.addBeneficiaireToModule(moduleId, beneficiaireId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/module/{moduleId}/beneficiaire/{beneficiaireId}")
    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('Manager')")
    public ResponseEntity<Void> removeBeneficiaireFromModule(
            @PathVariable String moduleId,
            @PathVariable String beneficiaireId) {
        moduleBeneficiaireService.removeBeneficiaireFromModule(moduleId, beneficiaireId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/module/{moduleId}/beneficiaires")
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('Manager') or hasRole('User')")
    public ResponseEntity<List<ModuleBeneficiaireDto>> getBeneficiairesByModule(
            @PathVariable String moduleId) {
        List<ModuleBeneficiaireDto> beneficiaires = moduleBeneficiaireService.getBeneficiairesByModule(moduleId);
        return ResponseEntity.ok(beneficiaires);
    }

    @GetMapping("/beneficiaire/{beneficiaireId}/modules")
    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('Manager') or hasRole('User')")
    public ResponseEntity<List<ModuleBeneficiaireDto>> getModulesByBeneficiaire(
            @PathVariable String beneficiaireId) {
        List<ModuleBeneficiaireDto> modules = moduleBeneficiaireService.getModulesByBeneficiaire(beneficiaireId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/module/{moduleId}/beneficiaire/{beneficiaireId}")
    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('Manager') or hasRole('User')")
    public ResponseEntity<Boolean> isBeneficiaireInModule(
            @PathVariable String moduleId,
            @PathVariable String beneficiaireId) {
        boolean isInModule = moduleBeneficiaireService.isBeneficiaireInModule(moduleId, beneficiaireId);
        return ResponseEntity.ok(isInModule);
    }

    @PutMapping("/module/{moduleId}/beneficiaire/{beneficiaireId}/complete")
    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('Manager')")
    public ResponseEntity<ModuleBeneficiaireDto> markModuleAsCompleted(
            @PathVariable String moduleId,
            @PathVariable String beneficiaireId,
            @RequestParam boolean completed) {
        ModuleBeneficiaireDto dto = moduleBeneficiaireService.markModuleAsCompleted(moduleId, beneficiaireId, completed);
        return ResponseEntity.ok(dto);
    }
}
