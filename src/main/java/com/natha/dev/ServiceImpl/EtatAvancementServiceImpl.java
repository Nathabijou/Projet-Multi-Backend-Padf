package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.EtatAvancementDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.EtatAvancementDto;
import com.natha.dev.Dto.SousEtatAvancementDto;
import com.natha.dev.IService.IEtatAvancementService;
import com.natha.dev.IService.ISousEtatAvancementService;
import com.natha.dev.Model.EtatAvancement;
import com.natha.dev.Model.Projet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class EtatAvancementServiceImpl implements IEtatAvancementService {

    @Autowired
    private EtatAvancementDao etatAvancementDao;
    
    @Autowired
    private ProjetDao projetDao;
    
    @Autowired
    private ISousEtatAvancementService sousEtatAvancementService;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EtatAvancementDto createEtatAvancement(EtatAvancementDto etatAvancementDto) {
        EtatAvancement etatAvancement = modelMapper.map(etatAvancementDto, EtatAvancement.class);
        
        // Set the Projet from the DTO
        if (etatAvancementDto.getProjetId() != null) {
            Projet projet = projetDao.findById(etatAvancementDto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID: " + etatAvancementDto.getProjetId()));
            etatAvancement.setProjet(projet);
        }
        
        // Generate ID if not provided
        if (etatAvancement.getId() == null) {
            etatAvancement.setId(UUID.randomUUID().toString());
        }
        
        // Save the EtatAvancement
        EtatAvancement savedEtat = etatAvancementDao.save(etatAvancement);
        
        // Save SousEtats if any
        if (etatAvancementDto.getSousEtats() != null && !etatAvancementDto.getSousEtats().isEmpty()) {
            for (SousEtatAvancementDto sousEtatDto : etatAvancementDto.getSousEtats()) {
                sousEtatDto.setEtatAvancementId(savedEtat.getId());
                sousEtatAvancementService.createSousEtatAvancement(sousEtatDto);
            }
        }
        
        return modelMapper.map(savedEtat, EtatAvancementDto.class);
    }

    @Override
    public EtatAvancementDto updateEtatAvancement(String id, EtatAvancementDto etatAvancementDto) {
        EtatAvancement existingEtat = etatAvancementDao.findById(id)
            .orElseThrow(() -> new RuntimeException("État d'avancement non trouvé avec l'ID: " + id));
        
        // Update fields
        existingEtat.setNom(etatAvancementDto.getNom());
        existingEtat.setPourcentageTotal(etatAvancementDto.getPourcentageTotal());
        existingEtat.setDescription(etatAvancementDto.getDescription());
        
        // Update Projet if changed
        if (etatAvancementDto.getProjetId() != null && 
            (existingEtat.getProjet() == null || !existingEtat.getProjet().getIdProjet().equals(etatAvancementDto.getProjetId()))) {
            Projet projet = projetDao.findById(etatAvancementDto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID: " + etatAvancementDto.getProjetId()));
            existingEtat.setProjet(projet);
        }
        
        EtatAvancement updatedEtat = etatAvancementDao.save(existingEtat);
        return modelMapper.map(updatedEtat, EtatAvancementDto.class);
    }

    @Override
    public void deleteEtatAvancement(String id) {
        // Delete all sous-etats first
        List<SousEtatAvancementDto> sousEtats = sousEtatAvancementService.getSousEtatsAvancementByEtatAvancementId(id);
        for (SousEtatAvancementDto sousEtat : sousEtats) {
            sousEtatAvancementService.deleteSousEtatAvancement(sousEtat.getId().toString());
        }
        
        // Then delete the etat
        etatAvancementDao.deleteById(id);
    }

    @Override
    public EtatAvancementDto getEtatAvancementById(String id) {
        EtatAvancement etatAvancement = etatAvancementDao.findById(id)
            .orElseThrow(() -> new RuntimeException("État d'avancement non trouvé avec l'ID: " + id));
            
        EtatAvancementDto dto = modelMapper.map(etatAvancement, EtatAvancementDto.class);
        
        // Set the projet ID
        if (etatAvancement.getProjet() != null) {
            dto.setProjetId(etatAvancement.getProjet().getIdProjet());
        }
        
        // Get and set sous-etats
        List<SousEtatAvancementDto> sousEtats = sousEtatAvancementService
            .getSousEtatsAvancementByEtatAvancementId(id);
        dto.setSousEtats(sousEtats);
        
        // Calculate and set pourcentage réalisé
        double pourcentageRealise = sousEtats.stream()
            .filter(se -> se.getPourcentageRealise() != null && se.getPoids() != null)
            .mapToDouble(se -> (se.getPourcentageRealise() * se.getPoids()) / 100.0)
            .sum();
            
        dto.setPourcentageRealise(pourcentageRealise);
        
        return dto;
    }

    @Override
    public List<EtatAvancementDto> getAllEtatsAvancement() {
        return etatAvancementDao.findAll().stream()
            .map(etat -> getEtatAvancementById(etat.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<EtatAvancementDto> getEtatsAvancementByProjetId(String projetId) {
        return etatAvancementDao.findByProjetId(projetId).stream()
            .map(etat -> {
                // Création manuelle du DTO
                EtatAvancementDto dto = new EtatAvancementDto();
                dto.setId(etat.getId());
                dto.setNom(etat.getNom());
                dto.setDescription(etat.getDescription());
                dto.setPourcentageTotal(etat.getPourcentageTotal());
                
                // Définir l'ID du projet s'il existe
                if (etat.getProjet() != null) {
                    dto.setProjetId(etat.getProjet().getIdProjet());
                }
                
                // Récupérer les sous-états d'avancement
                List<SousEtatAvancementDto> sousEtats = sousEtatAvancementService
                    .getSousEtatsAvancementByEtatAvancementId(etat.getId());
                dto.setSousEtats(sousEtats);
                
                // Calculer le pourcentage réalisé
                double pourcentageRealise = sousEtats.stream()
                    .filter(se -> se.getPourcentageRealise() != null && se.getPoids() != null)
                    .mapToDouble(se -> (se.getPourcentageRealise() * se.getPoids()) / 100.0)
                    .sum();
                dto.setPourcentageRealise(pourcentageRealise);
                
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public double calculerPourcentageTotalProjet(String projetId) {
        List<EtatAvancementDto> etats = getEtatsAvancementByProjetId(projetId);
        
        return etats.stream()
            .filter(e -> e.getPourcentageTotal() != null && e.getPourcentageRealise() != null)
            .mapToDouble(e -> (e.getPourcentageTotal() * e.getPourcentageRealise()) / 100.0)
            .sum();
    }
}
