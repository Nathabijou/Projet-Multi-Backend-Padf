package com.natha.dev.Controller;

import com.natha.dev.Dto.BeneficiaireModuleDto;
import com.natha.dev.IService.IModuleParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModuleParticipationController {

    private final IModuleParticipationService moduleParticipationService;

    @Autowired
    public ModuleParticipationController(IModuleParticipationService moduleParticipationService) {
        this.moduleParticipationService = moduleParticipationService;
    }

    /**
     * Jwenn lis benefisyè ki patisipe nan yon modil
     * @param moduleId ID modil la
     * @return Lis benefisyè yo ak si yo te prezan oswa ou pa
     */
    @GetMapping("/api/modules/{moduleId}/beneficiaires")
    public ResponseEntity<List<BeneficiaireModuleDto>> getBeneficiairesByModuleId(
            @PathVariable String moduleId) {
        List<BeneficiaireModuleDto> beneficiaires = moduleParticipationService.getBeneficiairesByModuleId(moduleId);
        return ResponseEntity.ok(beneficiaires);
    }
}
