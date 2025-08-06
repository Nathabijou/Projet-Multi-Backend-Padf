package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.AddBeneficiaireToProjetRequestDto;
import com.natha.dev.Dto.ProjetBeneficiaireDto;
import com.natha.dev.IService.ProjetBeneficiaireIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Projet;
import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetBeneficiaireImpl implements ProjetBeneficiaireIService {

    private final ProjetDao projetDao;
    private final BeneficiaireDao beneficiaireDao;
    private final ProjetBeneficiaireDao projetBeneficiaireDao;

    @Autowired
    public ProjetBeneficiaireImpl(ProjetDao projetDao, BeneficiaireDao beneficiaireDao, ProjetBeneficiaireDao projetBeneficiaireDao) {
        this.projetDao = projetDao;
        this.beneficiaireDao = beneficiaireDao;
        this.projetBeneficiaireDao = projetBeneficiaireDao;
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
