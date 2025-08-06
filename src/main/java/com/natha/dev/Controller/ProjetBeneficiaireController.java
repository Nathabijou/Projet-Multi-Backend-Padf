package com.natha.dev.Controller;

import com.natha.dev.Dto.AddBeneficiaireToProjetRequestDto;
import com.natha.dev.Dto.ProjetBeneficiaireDto;
import com.natha.dev.IService.ProjetBeneficiaireIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projet-beneficiaire")
public class ProjetBeneficiaireController {

    private final ProjetBeneficiaireIService projetBeneficiaireIService;

    @Autowired
    public ProjetBeneficiaireController(ProjetBeneficiaireIService projetBeneficiaireIService) {
        this.projetBeneficiaireIService = projetBeneficiaireIService;
    }

    @PostMapping("/add")
    public ResponseEntity<ProjetBeneficiaireDto> addBeneficiaireToProjet(@RequestBody AddBeneficiaireToProjetRequestDto requestDto) {
        ProjetBeneficiaireDto nouvelleRelation = projetBeneficiaireIService.addBeneficiaireToProjet(requestDto);
        return new ResponseEntity<>(nouvelleRelation, HttpStatus.CREATED);
    }

    @GetMapping("/projet/{projetId}/beneficiaires")
    public ResponseEntity<List<ProjetBeneficiaireDto>> getBeneficiairesByProjet(@PathVariable String projetId) {
        List<ProjetBeneficiaireDto> list = projetBeneficiaireIService.findByProjetId(projetId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
