package com.natha.dev.IService;

import com.natha.dev.Dto.ProjetBeneficiaireEntrepriseDto;
import com.natha.dev.Dto.BeneficiaireSimpleDto;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Entreprise;

import java.util.List;

public interface ProjetBeneficiaireEntrepriseIService {
    List<ProjetBeneficiaireEntrepriseDto> findEntreprisesByProjetBeneficiaireId(String projetBeneficiaireId);
    List<ProjetBeneficiaireEntrepriseDto> findProjetBeneficiairesByEntrepriseId(Long entrepriseId);
    
    /**
     * Jwenn lis tout antrepriz ki nan yon pwojè
     * @param projetId ID pwojè a
     * @return Lis antrepriz ki nan pwojè a
     */
    List<Entreprise> findEntreprisesByProjetId(String projetId);
    
    /**
     * Jwenn lis tout benefisyè ki nan yon antrepriz
     * @param entrepriseId ID antrepriz la
     * @return Lis benefisyè ki nan antrepriz la
     */
    List<Beneficiaire> findBeneficiairesByEntrepriseId(Long entrepriseId);
    
    /**
     * Jwenn lis tout benefisyè ki nan yon antrepriz kòm DTO
     * @param entrepriseId ID antrepriz la
     * @return Lis benefisyè ki nan antrepriz la kòm DTO
     */
    List<BeneficiaireSimpleDto> findBeneficiairesDtoByEntrepriseId(Long entrepriseId);
    
    /**
     * Ajoute yon antrepriz nan yon pwojè benefisyè
     * @param projetBeneficiaireId ID pwojè benefisyè a
     * @param entrepriseId ID antrepriz la
     * @return antite ki fèk kreye a
     */
    ProjetBeneficiaireEntrepriseDto addEntrepriseToProjetBeneficiaire(String projetBeneficiaireId, Long entrepriseId);

}
