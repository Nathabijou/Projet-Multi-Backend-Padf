package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.FormationDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.ProjetBeneficiaireFormationDao;
import com.natha.dev.Dto.*;
import com.natha.dev.IService.FormationIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Formation;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireFormation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormationImpl implements FormationIService {

    @Autowired
    private FormationDao dao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;
    
    @Autowired
    private ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao;

    @Override
    public FormationDto save(FormationDto dto) {
        Formation formation = convertToEntity(dto);
        formation.setIdFormation(UUID.randomUUID().toString());
        Formation savedFormation = dao.save(formation);
        return convertToDto(savedFormation);
    }

    @Override
    public FormationDto update(String idFormation, FormationDto dto) {
        dao.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));
        Formation formationToUpdate = convertToEntity(dto);
        formationToUpdate.setIdFormation(idFormation);
        Formation updatedFormation = dao.save(formationToUpdate);
        return convertToDto(updatedFormation);
    }

    @Override
    public void deleteById(String idFormation) {
        dao.deleteById(idFormation);
    }

    @Override
    public FormationDto getById(String idFormation) {
        return dao.findById(idFormation)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));
    }

    @Override
    public List<FormationDto> getAll() {
        return dao.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjetBeneficiaireFormationDto addBeneficiaireToFormation(AddBeneficiaireToFormationRequestDto requestDto) {
        // 1. Verify if the beneficiaire is in the project
        ProjetBeneficiaire projetBeneficiaire = projetBeneficiaireDao.findByProjetAndBeneficiaire(
                requestDto.getIdProjet(),
                requestDto.getIdBeneficiaire()
        ).orElseThrow(() -> new RuntimeException("Benefisyè sa a pa jwenn nan pwojè sa a."));

        // 2. Verify if the formation exists
        Formation formation = dao.findById(requestDto.getIdFormation())
                .orElseThrow(() -> new RuntimeException("Fòmasyon sa a pa jwenn."));

        ProjetBeneficiaireFormation pbf = new ProjetBeneficiaireFormation();
        pbf.setProjetBeneficiaire(projetBeneficiaire);
        pbf.setFormation(formation);

        ProjetBeneficiaireFormation savedPbf = projetBeneficiaireFormationDao.save(pbf);

        return convertToDto(savedPbf);
    }

    // --- Metòd Konvèsyon --- //

    private FormationDto convertToDto(Formation f) {
        return new FormationDto(
                f.getIdFormation(),
                f.getTitre(),
                f.getDescription(),
                f.getDateDebut(),
                f.getDateFin(),
                f.getTypeFormation()
        );
    }

    private ProjetBeneficiaireDto convertToDto(ProjetBeneficiaire pb) {
        return new ProjetBeneficiaireDto(
                pb.getIdProjetBeneficiaire(),
                pb.getProjet().getIdProjet(),
                pb.getBeneficiaire().getIdBeneficiaire()
        );
    }

    private ProjetBeneficiaireFormationDto convertToDto(ProjetBeneficiaireFormation pbf) {
        return new ProjetBeneficiaireFormationDto(
                pbf.getId(),
                convertToDto(pbf.getProjetBeneficiaire()),
                convertToDto(pbf.getFormation())
        );
    }

    private Formation convertToEntity(FormationDto d) {
        Formation f = new Formation();
        f.setIdFormation(d.getIdFormation());
        f.setTitre(d.getTitre());
        f.setDescription(d.getDescription());
        f.setDateDebut(d.getDateDebut());
        f.setDateFin(d.getDateFin());
        f.setTypeFormation(d.getTypeFormation());
        return f;
    }
    
    @Override
    public List<BeneficiaireDto> getBeneficiairesByFormationId(String idFormation) {
        Formation formation = dao.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));
        return formation.getProjetBeneficiaireFormations().stream()
                .map(pbf -> pbf.getProjetBeneficiaire().getBeneficiaire())
                .map(this::convertToBeneficiaireDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<FormationDto> getFormationsByProjetId(String projetId) {
        List<Formation> formations = projetBeneficiaireFormationDao.findFormationsByProjetId(projetId);
        return formations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private BeneficiaireDto convertToBeneficiaireDto(Beneficiaire beneficiaire) {
        if (beneficiaire == null) {
            return null;
        }
        
        BeneficiaireDto dto = new BeneficiaireDto();
        dto.setIdBeneficiaire(beneficiaire.getIdBeneficiaire());
        dto.setNom(beneficiaire.getNom());
        dto.setPrenom(beneficiaire.getPrenom());
        dto.setSexe(beneficiaire.getSexe());
        dto.setDateNaissance(beneficiaire.getDateNaissance());
        dto.setDomaineDeFormation(beneficiaire.getDomaineDeFormation());
        dto.setTypeIdentification(beneficiaire.getTypeIdentification());
        dto.setIdentification(beneficiaire.getIdentification());
        dto.setQualification(beneficiaire.getQualification());
        dto.setTelephoneContact(beneficiaire.getTelephoneContact());
        dto.setTelephonePaiement(beneficiaire.getTelephonePaiement());
        dto.setOperateurPaiement(beneficiaire.getOperateurPaiement());
        
        return dto;
    }
}
