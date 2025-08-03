package com.natha.dev.Controller;

import com.natha.dev.Dto.ProjetBeneficiaireEntrepriseDto;
import com.natha.dev.Dto.BeneficiaireSimpleDto;
import com.natha.dev.IService.ProjetBeneficiaireEntrepriseIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Entreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjetBeneficiaireEntrepriseController {

    private final ProjetBeneficiaireEntrepriseIService service;

    @Autowired
    public ProjetBeneficiaireEntrepriseController(ProjetBeneficiaireEntrepriseIService service) {
        this.service = service;
    }

    @GetMapping("/api/projet-beneficiaire-entreprises/projet-beneficiaire/{projetBeneficiaireId}")
    public ResponseEntity<List<ProjetBeneficiaireEntrepriseDto>> getEntreprisesByProjetBeneficiaire(
            @PathVariable String projetBeneficiaireId) {
        List<ProjetBeneficiaireEntrepriseDto> entreprises = service.findEntreprisesByProjetBeneficiaireId(projetBeneficiaireId);
        return ResponseEntity.ok(entreprises);
    }

    @GetMapping("/api/projet-beneficiaire-entreprises/entreprise/{entrepriseId}")
    public ResponseEntity<List<ProjetBeneficiaireEntrepriseDto>> getProjetBeneficiairesByEntreprise(
            @PathVariable Long entrepriseId) {
        List<ProjetBeneficiaireEntrepriseDto> projetBeneficiaires = service.findProjetBeneficiairesByEntrepriseId(entrepriseId);
        return ResponseEntity.ok(projetBeneficiaires);
    }
    
    /**
     * Jwenn lis tout antrepriz ki nan yon pwojè
     * @param projetId ID pwojè a
     * @return Lis antrepriz ki nan pwojè a
     */
    @GetMapping("/api/projet-beneficiaire-entreprises/projet/{projetId}/entreprises")
    public ResponseEntity<List<Entreprise>> getEntreprisesByProjet(
            @PathVariable String projetId) {
        List<Entreprise> entreprises = service.findEntreprisesByProjetId(projetId);
        return ResponseEntity.ok(entreprises);
    }
    
    /**
     * Ajoute yon antrepriz nan yon pwojè benefisyè
     * @param projetBeneficiaireId ID pwojè benefisyè a
     * @param entrepriseId ID antrepriz la
     * @return antite ki fèk kreye a
     */
    @PostMapping("/api/projet-beneficiaire-entreprises/projet-beneficiaire/{projetBeneficiaireId}/entreprise/{entrepriseId}")
    public ResponseEntity<ProjetBeneficiaireEntrepriseDto> addEntrepriseToProjetBeneficiaire(
            @PathVariable String projetBeneficiaireId,
            @PathVariable Long entrepriseId) {
        ProjetBeneficiaireEntrepriseDto dto = service.addEntrepriseToProjetBeneficiaire(projetBeneficiaireId, entrepriseId);
        return ResponseEntity.ok(dto);
    }
    
    /**
     * Jwenn lis tout benefisyè ki nan yon antrepriz
     * @param entrepriseId ID antrepriz la
     * @return Lis benefisyè ki nan antrepriz la
     */
    @GetMapping("/api/projet-beneficiaire-entreprises/entreprise/{entrepriseId}/beneficiaires")
    public ResponseEntity<List<BeneficiaireSimpleDto>> getBeneficiairesByEntrepriseId(
            @PathVariable Long entrepriseId) {
        List<BeneficiaireSimpleDto> beneficiaires = service.findBeneficiairesDtoByEntrepriseId(entrepriseId);
        return ResponseEntity.ok(beneficiaires);
    }
}
