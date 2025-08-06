package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.EntrepriseDao;
import com.natha.dev.Dao.ProjetBeneficiaireEntrepriseDao;
import com.natha.dev.Dto.ProjetBeneficiaireEntrepriseDto;
import com.natha.dev.IService.ProjetBeneficiaireEntrepriseIService;
import com.natha.dev.Model.ProjetBeneficiaireEntreprise;
import com.natha.dev.Model.Entreprise;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireEntrepriseId;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Dto.BeneficiaireSimpleDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetBeneficiaireEntrepriseServiceImpl implements ProjetBeneficiaireEntrepriseIService {

    private final ProjetBeneficiaireEntrepriseDao projetBeneficiaireEntrepriseDao;
    private final ProjetBeneficiaireDao projetBeneficiaireDao;
    private final EntrepriseDao entrepriseDao;

    @Autowired
    public ProjetBeneficiaireEntrepriseServiceImpl(
            ProjetBeneficiaireEntrepriseDao projetBeneficiaireEntrepriseDao,
            ProjetBeneficiaireDao projetBeneficiaireDao,
            EntrepriseDao entrepriseDao) {
        this.projetBeneficiaireEntrepriseDao = projetBeneficiaireEntrepriseDao;
        this.projetBeneficiaireDao = projetBeneficiaireDao;
        this.entrepriseDao = entrepriseDao;
    }

    @Override
    public List<ProjetBeneficiaireEntrepriseDto> findEntreprisesByProjetBeneficiaireId(String projetBeneficiaireId) {
        List<ProjetBeneficiaireEntreprise> relations = projetBeneficiaireEntrepriseDao
                .findByProjetBeneficiaireId(projetBeneficiaireId);
        
        return relations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjetBeneficiaireEntrepriseDto> findProjetBeneficiairesByEntrepriseId(Long entrepriseId) {
        List<ProjetBeneficiaireEntreprise> relations = projetBeneficiaireEntrepriseDao
                .findByEntrepriseId(entrepriseId);
        
        return relations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProjetBeneficiaireEntrepriseDto convertToDto(ProjetBeneficiaireEntreprise pbe) {
        if (pbe == null) return null;
        
        ProjetBeneficiaireEntrepriseDto dto = new ProjetBeneficiaireEntrepriseDto();
        dto.setIdProjetBeneficiaire(pbe.getProjetBeneficiaire().getIdProjetBeneficiaire());
        dto.setIdEntreprise(pbe.getEntreprise().getId());
        dto.setNomEntreprise(pbe.getEntreprise().getNom());
        dto.setDescriptionEntreprise(pbe.getEntreprise().getDescription());
        dto.setDateCreation(pbe.getDateCreation());
        return dto;
    }
    
    @Override
    public List<Entreprise> findEntreprisesByProjetId(String projetId) {
        return projetBeneficiaireEntrepriseDao.findEntreprisesByProjetId(projetId);
    }
    

    
    @Override
    @Transactional
    public ProjetBeneficiaireEntrepriseDto addEntrepriseToProjetBeneficiaire(String projetBeneficiaireId, Long entrepriseId) {
        // Tcheke si relasyon an deja egziste
        if (projetBeneficiaireEntrepriseDao.existsByProjetBeneficiaireIdAndEntrepriseId(projetBeneficiaireId, entrepriseId)) {
            throw new IllegalStateException("Antrepriz sa a deja asosye ak pwojè benefisyè sa a");
        }
        
        // Jwenn ProjetBeneficiaire ak Entreprise yo
        ProjetBeneficiaire projetBeneficiaire = projetBeneficiaireDao.findById(projetBeneficiaireId)
            .orElseThrow(() -> new IllegalArgumentException("Pwojè benefisyè pa jwenn ak ID: " + projetBeneficiaireId));
            
        Entreprise entreprise = entrepriseDao.findById(entrepriseId)
            .orElseThrow(() -> new IllegalArgumentException("Antrepriz pa jwenn ak ID: " + entrepriseId));
        
        // Kreye nouvo relasyon ak konstriktè a
        ProjetBeneficiaireEntreprise pbe = new ProjetBeneficiaireEntreprise(projetBeneficiaire, entreprise);
        
        // Sove relasyon an
        ProjetBeneficiaireEntreprise saved = projetBeneficiaireEntrepriseDao.save(pbe);
        
        // Retounen DTO a
        return convertToDto(saved);
    }
    
    @Override
    public List<Beneficiaire> findBeneficiairesByEntrepriseId(Long entrepriseId) {
        // Jwenn tout benefisyè ki gen relasyon ak antrepriz la
        List<Beneficiaire> beneficiaires = projetBeneficiaireEntrepriseDao.findBeneficiairesByEntrepriseId(entrepriseId);
        
        // Asire ke tout relasyon yo chaje kòrèkteman
        if (beneficiaires != null) {
            beneficiaires.forEach(b -> {
                // Fòse chargement lazy si nesesè
                if (b != null) {
                    Hibernate.initialize(b.getTelephoneContact());
                }
            });
        }
        
        return beneficiaires;
    }
    
    /**
     * Konvèti yon Beneficiaire an BeneficiaireSimpleDto
     * @param beneficiaire Beneficiaire a konvèti
     * @return BeneficiaireSimpleDto ki koresponn lan
     */
    private BeneficiaireSimpleDto convertToBeneficiaireSimpleDto(Beneficiaire beneficiaire) {
        if (beneficiaire == null) {
            return null;
        }
        
        BeneficiaireSimpleDto dto = new BeneficiaireSimpleDto();
        dto.setIdBeneficiaire(beneficiaire.getIdBeneficiaire());
        dto.setNom(beneficiaire.getNom());
        dto.setPrenom(beneficiaire.getPrenom());
        dto.setSexe(beneficiaire.getSexe());
        dto.setCommuneResidence(beneficiaire.getCommuneResidence());
        dto.setFiliere(beneficiaire.getFiliere());
        dto.setDateNaissance(beneficiaire.getDateNaissance());
        dto.setDomaineDeFormation(beneficiaire.getDomaineDeFormation());
        dto.setTypeIdentification(beneficiaire.getTypeIdentification());
        dto.setIdentification(beneficiaire.getIdentification());
        dto.setQualification(beneficiaire.getQualification());
        dto.setTelephoneContact(beneficiaire.getTelephoneContact());
        dto.setTelephonePaiement(beneficiaire.getTelephonePaiement());
        dto.setOperateurPaiement(beneficiaire.getOperateurPaiement());
        dto.setLienNaissance(beneficiaire.getLienNaissance());
        dto.setTypeBeneficiaire(beneficiaire.getTypeBeneficiaire());
        
        return dto;
    }
    
    @Override
    /**
     * Jwenn lis tout benefisyè ki nan yon antrepriz kòm DTO
     * @param entrepriseId ID antrepriz la
     * @return Lis benefisyè ki nan antrepriz la kòm DTO
     */
    public List<BeneficiaireSimpleDto> findBeneficiairesDtoByEntrepriseId(Long entrepriseId) {
        List<Beneficiaire> beneficiaires = findBeneficiairesByEntrepriseId(entrepriseId);
        return beneficiaires.stream()
            .map(this::convertToBeneficiaireSimpleDto)
            .collect(Collectors.toList());
    }
}
