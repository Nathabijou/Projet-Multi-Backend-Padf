package com.natha.dev.Controller;

import com.natha.dev.Dto.ModuleCreationDto;
import com.natha.dev.Dto.ModuleDto;
import com.natha.dev.IService.IModuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModuleController {

    private final IModuleService moduleService;

    @Autowired
    public ModuleController(IModuleService moduleService) {
        this.moduleService = moduleService;
    }

    /**
     * Jwenn tout modil ki nan yon chapit
     * @param chapitreId ID chapit la
     * @return Lis modil yo nan lòd yo
     */
    @GetMapping("/api/modules/chapitre/{chapitreId}")
    public ResponseEntity<List<ModuleDto>> getModulesByChapitreId(@PathVariable String chapitreId) {
        List<ModuleDto> modules = moduleService.getModulesByChapitreId(chapitreId);
        return ResponseEntity.ok(modules);
    }
    
    /**
     * Kreye yon nouvo modil nan yon chapit
     * @param chapitreId ID chapit kote wap ajoute modil la
     * @param moduleDto Done pou kreye modil la
     * @return Modil ki fèk kreye a
     */
    @PostMapping("/api/modules/chapitre/{chapitreId}")
    public ResponseEntity<ModuleDto> createModule(
            @PathVariable String chapitreId,
            @Valid @RequestBody ModuleCreationDto moduleDto) {
        ModuleDto createdModule = moduleService.createModule(chapitreId, moduleDto);
        return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
    }
}
