package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ProgressionDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.ProgressionDTO;
import com.natha.dev.IService.ProgressionIService;
import com.natha.dev.Model.Progression;
import com.natha.dev.Model.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgressionServiceImpl implements ProgressionIService {

    @Autowired
    private ProgressionDao progressionDao;

    @Autowired
    private ProjetDao projetDao;

    @Override
    public ProgressionDTO save(ProgressionDTO dto) {
        Projet projet = projetDao.findById(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet non trouvé: " + dto.getProjetId()));
        
        Progression progression = convertToEntity(dto);
        progression.setProjet(projet);
        Progression saved = progressionDao.save(progression);
        return convertToDto(saved);
    }

    @Override
    public List<ProgressionDTO> findAll() {
        return progressionDao.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProgressionDTO> findById(String idProgression) {
        return progressionDao.findById(idProgression)
                .map(this::convertToDto);
    }

    @Override
    public ProgressionDTO update(String idProgression, ProgressionDTO dto) {
        Progression progression = progressionDao.findById(idProgression)
                .orElseThrow(() -> new RuntimeException("Progression non trouvée: " + idProgression));
        
        if (dto.getProjetId() != null) {
            Projet projet = projetDao.findById(dto.getProjetId())
                    .orElseThrow(() -> new RuntimeException("Projet non trouvé: " + dto.getProjetId()));
            progression.setProjet(projet);
        }
        
        if (dto.getDateDebut() != null) {
            progression.setDateDebut(dto.getDateDebut());
        }
        if (dto.getDateFin() != null) {
            progression.setDateFin(dto.getDateFin());
        }
        if (dto.getPourcentageSouhaite() != null) {
            progression.setPourcentageSouhaite(dto.getPourcentageSouhaite());
        }
        if (dto.getPourcentageDuJour() != null) {
            progression.setPourcentageDuJour(dto.getPourcentageDuJour());
        }
        if (dto.getMotif() != null) {
            progression.setMotif(dto.getMotif());
        }
        if (dto.getModifyBy() != null) {
            progression.setModifyBy(dto.getModifyBy());
        }
        if (dto.getActive() != null) {
            progression.setActive(dto.getActive());
        }
        
        Progression saved = progressionDao.save(progression);
        return convertToDto(saved);
    }

    @Override
    public void deleteById(String idProgression) {
        progressionDao.deleteById(idProgression);
    }

    @Override
    public List<ProgressionDTO> findByProjetId(String projetId) {
        return progressionDao.findByProjetIdProjet(projetId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Progression convertToEntity(ProgressionDTO dto) {
        Progression progression = new Progression();
        progression.setIdProgression(dto.getIdProgression());
        progression.setDateDebut(dto.getDateDebut());
        progression.setDateFin(dto.getDateFin());
        progression.setPourcentageSouhaite(dto.getPourcentageSouhaite());
        progression.setPourcentageDuJour(dto.getPourcentageDuJour());
        progression.setMotif(dto.getMotif());
        progression.setCreatedBy(dto.getCreatedBy());
        progression.setModifyBy(dto.getModifyBy());
        progression.setActive(dto.getActive());
        return progression;
    }

    private ProgressionDTO convertToDto(Progression progression) {
        ProgressionDTO dto = new ProgressionDTO();
        dto.setIdProgression(progression.getIdProgression());
        dto.setDateDebut(progression.getDateDebut());
        dto.setDateFin(progression.getDateFin());
        dto.setPourcentageSouhaite(progression.getPourcentageSouhaite());
        dto.setPourcentageDuJour(progression.getPourcentageDuJour());
        dto.setMotif(progression.getMotif());
        dto.setCreatedBy(progression.getCreatedBy());
        dto.setModifyBy(progression.getModifyBy());
        dto.setActive(progression.getActive());
        if (progression.getProjet() != null) {
            dto.setProjetId(progression.getProjet().getIdProjet());
        }
        return dto;
    }
}
