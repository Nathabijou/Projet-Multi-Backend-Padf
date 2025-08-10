package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.*;
import com.natha.dev.Dto.AddBeneficiaireToProjetRequestDto;
import com.natha.dev.Dto.AddFormationToProjetRequestDto;
import com.natha.dev.Dto.FormationDto;
import com.natha.dev.Dto.ProjetBeneficiaireDto;
import com.natha.dev.Dto.ProjetBeneficiaireFormationDto;
import com.natha.dev.IService.ProjetBeneficiaireIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Formation;
import com.natha.dev.Model.Projet;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireFormation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetBeneficiaireImpl implements ProjetBeneficiaireIService {

    private final ProjetDao projetDao;
    private final BeneficiaireDao beneficiaireDao;
    private final ProjetBeneficiaireDao projetBeneficiaireDao;
    private final ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao;
    private final FormationDao formationDao;

    @Autowired
    public ProjetBeneficiaireImpl(ProjetDao projetDao, 
                                 BeneficiaireDao beneficiaireDao, 
                                 ProjetBeneficiaireDao projetBeneficiaireDao,
                                 ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao,
                                 FormationDao formationDao) {
        this.projetDao = projetDao;
        this.beneficiaireDao = beneficiaireDao;
        this.projetBeneficiaireDao = projetBeneficiaireDao;
        this.projetBeneficiaireFormationDao = projetBeneficiaireFormationDao;
        this.formationDao = formationDao;
    }

    @Override
    public ProjetBeneficiaireDto save(ProjetBeneficiaireDto dto) {
        ProjetBeneficiaire projetBeneficiaire = convertToEntity(dto);
        ProjetBeneficiaire savedProjetBeneficiaire = projetBeneficiaireDao.save(projetBeneficiaire);
        return convertToDto(savedProjetBeneficiaire);
    }

    @Override
    public List<ProjetBeneficiaireDto> findAll() {
        return projetBeneficiaireDao.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProjetBeneficiaireDto> findById(String idProjetBeneficiaire) {
        return projetBeneficiaireDao.findById(idProjetBeneficiaire)
                .map(this::convertToDto);
    }

    @Override
    public void deleteById(String idProjetBeneficiaire) {
        projetBeneficiaireDao.deleteById(idProjetBeneficiaire);
    }

    @Override
    public ProjetBeneficiaireDto creerRelationProjetBeneficiaire(String projetId, String beneficiaireId) {
        Projet projet = projetDao.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Pwojè pa jwenn ak ID: " + projetId));

        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + beneficiaireId));

        ProjetBeneficiaire nouvelleRelation = new ProjetBeneficiaire();
        nouvelleRelation.setProjet(projet);
        nouvelleRelation.setBeneficiaire(beneficiaire);

        ProjetBeneficiaire savedRelation = projetBeneficiaireDao.save(nouvelleRelation);
        return convertToDto(savedRelation);
    }

    @Override
    public ProjetBeneficiaireDto addBeneficiaireToProjet(AddBeneficiaireToProjetRequestDto requestDto) {
        // Etap 1: Verifye si relasyon an egziste deja
        projetBeneficiaireDao.findByProjetAndBeneficiaire(
                requestDto.getIdProjet(),
                requestDto.getIdBeneficiaire()
        ).ifPresent(pb -> {
            throw new IllegalStateException("Benefisyè sa a deja asosye ak pwojè sa a.");
        });

        // Etap 2: Jwenn Pwojè a ak Benefisyè a
        Projet projet = projetDao.findById(requestDto.getIdProjet())
                .orElseThrow(() -> new RuntimeException("Pwojè pa jwenn ak ID: " + requestDto.getIdProjet()));

        Beneficiaire beneficiaire = beneficiaireDao.findById(requestDto.getIdBeneficiaire())
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + requestDto.getIdBeneficiaire()));

        // Etap 3: Kreye epi sove nouvo relasyon an
        ProjetBeneficiaire nouvelleRelation = new ProjetBeneficiaire();
        nouvelleRelation.setProjet(projet);
        nouvelleRelation.setBeneficiaire(beneficiaire);

        ProjetBeneficiaire savedRelation = projetBeneficiaireDao.save(nouvelleRelation);

        return convertToDto(savedRelation);
    }

    @Override
    public List<ProjetBeneficiaireDto> findByProjetId(String projetId) {
        return projetBeneficiaireDao.findByProjetIdProjet(projetId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<FormationDto> findFormationsByProjetId(String projetId) {
        List<Formation> formations = projetBeneficiaireFormationDao.findFormationsByProjetId(projetId);
        return formations.stream()
                .map(this::convertToFormationDto)
                .collect(Collectors.toList());
    }
    
    private FormationDto convertToFormationDto(Formation formation) {
        if (formation == null) {
            return null;
        }
        return FormationDto.builder()
                .idFormation(formation.getIdFormation())
                .titre(formation.getTitre())
                .description(formation.getDescription())
                .dateDebut(formation.getDateDebut())
                .dateFin(formation.getDateFin())
                .typeFormation(formation.getTypeFormation())
                .nomFormateur(formation.getNomFormateur())
                .createdBy(formation.getCreatedBy())
                .modifyBy(formation.getModifyBy())
                .createdAt(formation.getCreatedAt())
                .modifiedAt(formation.getModifiedAt())
                .build();
    }
    
    @Override
    @Transactional
    public ProjetBeneficiaireFormationDto addFormationToProjet(AddFormationToProjetRequestDto requestDto) {
        // 1. Jwenn tout ProjetBeneficiaire ki genyen nan pwojè a
        List<ProjetBeneficiaire> projetBeneficiaires = projetBeneficiaireDao.findByProjetIdProjet(requestDto.getProjetId());
        
        if (projetBeneficiaires.isEmpty()) {
            throw new RuntimeException("Pa gen okenn benefisyè nan pwojè sa a.");
        }
        
        // 2. Jwenn fòmasyon an
        Formation formation = formationDao.findById(requestDto.getFormationId())
                .orElseThrow(() -> new RuntimeException("Fòmasyon pa jwenn ak ID: " + requestDto.getFormationId()));
        
        // 3. Kreye relasyon ant chak benefisyè ak fòmasyon an
        ProjetBeneficiaireFormation savedRelation = null;
        
        for (ProjetBeneficiaire pb : projetBeneficiaires) {
            // Verifye si relasyon an deja egziste
            if (projetBeneficiaireFormationDao.existsByProjetBeneficiaireAndFormation(pb, formation)) {
                continue; // Sote si relasyon an deja egziste
            }
            
            // Kreye nouvo relasyon
            ProjetBeneficiaireFormation relation = new ProjetBeneficiaireFormation();
            relation.setProjetBeneficiaire(pb);
            relation.setFormation(formation);
            
            // Sove relasyon an
            savedRelation = projetBeneficiaireFormationDao.save(relation);
        }
        
        if (savedRelation == null) {
            throw new RuntimeException("Fòmasyon an te deja asosye ak tout benefisyè nan pwojè a.");
        }
        
        // Retounen dto a
        return convertToProjetBeneficiaireFormationDto(savedRelation);
    }
    
    private ProjetBeneficiaireFormationDto convertToProjetBeneficiaireFormationDto(ProjetBeneficiaireFormation pbf) {
        if (pbf == null) {
            return null;
        }
        
        ProjetBeneficiaireFormationDto dto = new ProjetBeneficiaireFormationDto();
        dto.setId(pbf.getId());
        
        // Konvèti ProjetBeneficiaire an DTO
        ProjetBeneficiaireDto pbDto = new ProjetBeneficiaireDto();
        pbDto.setIdProjetBeneficiaire(pbf.getProjetBeneficiaire().getIdProjetBeneficiaire());
        pbDto.setProjetId(pbf.getProjetBeneficiaire().getProjet().getIdProjet());
        pbDto.setBeneficiaireId(pbf.getProjetBeneficiaire().getBeneficiaire().getIdBeneficiaire());
        dto.setProjetBeneficiaire(pbDto);
        
        // Konvèti Formation an DTO
        dto.setFormation(convertToFormationDto(pbf.getFormation()));
        
        return dto;
    }

    private ProjetBeneficiaireDto convertToDto(ProjetBeneficiaire pb) {
        ProjetBeneficiaireDto dto = new ProjetBeneficiaireDto();
        dto.setIdProjetBeneficiaire(pb.getIdProjetBeneficiaire());
        dto.setProjetId(pb.getProjet().getIdProjet());
        dto.setBeneficiaireId(pb.getBeneficiaire().getIdBeneficiaire());
        return dto;
    }

    private ProjetBeneficiaire convertToEntity(ProjetBeneficiaireDto dto) {
        Projet projet = projetDao.findById(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Pwojè pa jwenn ak ID: " + dto.getProjetId()));

        Beneficiaire beneficiaire = beneficiaireDao.findById(dto.getBeneficiaireId())
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + dto.getBeneficiaireId()));

        ProjetBeneficiaire entity = new ProjetBeneficiaire();
        entity.setIdProjetBeneficiaire(dto.getIdProjetBeneficiaire());
        entity.setProjet(projet);
        entity.setBeneficiaire(beneficiaire);

        return entity;
    }
}
