package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.EtatAvancementDao;
import com.natha.dev.Dao.PhotoSousEtatDao;
import com.natha.dev.Dao.SousEtatAvancementDao;
import com.natha.dev.Dto.PhotoSousEtatDto;
import com.natha.dev.Dto.SousEtatAvancementDto;
import com.natha.dev.IService.IPhotoSousEtatService;
import com.natha.dev.IService.ISousEtatAvancementService;
import com.natha.dev.Model.EtatAvancement;
import com.natha.dev.Model.PhotoSousEtat;
import com.natha.dev.Model.SousEtatAvancement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SousEtatAvancementServiceImpl implements ISousEtatAvancementService {

    @Autowired
    private SousEtatAvancementDao sousEtatAvancementDao;
    
    @Autowired
    private EtatAvancementDao etatAvancementDao;
    
    @Autowired
    private IPhotoSousEtatService photoSousEtatService;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SousEtatAvancementDto createSousEtatAvancement(SousEtatAvancementDto sousEtatAvancementDto) {
        // Création manuelle de l'entité à partir du DTO
        SousEtatAvancement sousEtatAvancement = new SousEtatAvancement();
        
        // Mappage des champs du DTO vers l'entité
        sousEtatAvancement.setId(UUID.randomUUID().toString());
        sousEtatAvancement.setNom(sousEtatAvancementDto.getLibelle());
        sousEtatAvancement.setDescription(sousEtatAvancementDto.getDescription());
        sousEtatAvancement.setPourcentageRealise(sousEtatAvancementDto.getPourcentageRealise());
        sousEtatAvancement.setPoids(sousEtatAvancementDto.getPoids());
        
        // Gestion de la relation avec EtatAvancement
        if (sousEtatAvancementDto.getEtatAvancementId() != null) {
            EtatAvancement etatAvancement = etatAvancementDao.findById(sousEtatAvancementDto.getEtatAvancementId())
                .orElseThrow(() -> new RuntimeException("État d'avancement non trouvé avec l'ID: " + sousEtatAvancementDto.getEtatAvancementId()));
            sousEtatAvancement.setEtatAvancement(etatAvancement);
        }
        
        // Sauvegarde du SousEtatAvancement
        SousEtatAvancement savedSousEtat = sousEtatAvancementDao.save(sousEtatAvancement);
        
        // Sauvegarde des photos si elles existent
        if (sousEtatAvancementDto.getPhotos() != null && !sousEtatAvancementDto.getPhotos().isEmpty()) {
            for (PhotoSousEtatDto photoDto : sousEtatAvancementDto.getPhotos()) {
                photoSousEtatService.createPhotoSousEtat(photoDto, savedSousEtat.getId());
            }
        }
        
        // Création manuelle du DTO de réponse
        SousEtatAvancementDto responseDto = new SousEtatAvancementDto();
        responseDto.setId(savedSousEtat.getId());
        responseDto.setLibelle(savedSousEtat.getNom());
        responseDto.setDescription(savedSousEtat.getDescription());
        responseDto.setPourcentageRealise(savedSousEtat.getPourcentageRealise());
        responseDto.setPoids(savedSousEtat.getPoids());
        if (savedSousEtat.getEtatAvancement() != null) {
            responseDto.setEtatAvancementId(savedSousEtat.getEtatAvancement().getId());
        }
        
        return responseDto;
    }

    @Override
    public SousEtatAvancementDto updateSousEtatAvancement(String id, SousEtatAvancementDto sousEtatAvancementDto) {
        // Récupération de l'entité existante
        SousEtatAvancement existingSousEtat = sousEtatAvancementDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Sous-état d'avancement non trouvé avec l'ID: " + id));
        
        // Mise à jour des champs
        existingSousEtat.setNom(sousEtatAvancementDto.getLibelle());
        existingSousEtat.setDescription(sousEtatAvancementDto.getDescription());
        existingSousEtat.setPourcentageRealise(sousEtatAvancementDto.getPourcentageRealise());
        existingSousEtat.setPoids(sousEtatAvancementDto.getPoids());
        
        // Mise à jour de la relation avec EtatAvancement si nécessaire
        if (sousEtatAvancementDto.getEtatAvancementId() != null && 
            (existingSousEtat.getEtatAvancement() == null || 
             !existingSousEtat.getEtatAvancement().getId().equals(sousEtatAvancementDto.getEtatAvancementId()))) {
            EtatAvancement etatAvancement = etatAvancementDao.findById(sousEtatAvancementDto.getEtatAvancementId())
                .orElseThrow(() -> new RuntimeException("État d'avancement non trouvé avec l'ID: " + sousEtatAvancementDto.getEtatAvancementId()));
            existingSousEtat.setEtatAvancement(etatAvancement);
        }
        
        // Sauvegarde des modifications
        SousEtatAvancement updatedSousEtat = sousEtatAvancementDao.save(existingSousEtat);
        
        // Création manuelle du DTO de réponse
        SousEtatAvancementDto responseDto = new SousEtatAvancementDto();
        responseDto.setId(updatedSousEtat.getId());
        responseDto.setLibelle(updatedSousEtat.getNom());
        responseDto.setDescription(updatedSousEtat.getDescription());
        responseDto.setPourcentageRealise(updatedSousEtat.getPourcentageRealise());
        responseDto.setPoids(updatedSousEtat.getPoids());
        if (updatedSousEtat.getEtatAvancement() != null) {
            responseDto.setEtatAvancementId(updatedSousEtat.getEtatAvancement().getId());
        }
        
        return responseDto;
    }

    @Override
    public void deleteSousEtatAvancement(String id) {
        // Delete all photos first
        photoSousEtatService.deletePhotosBySousEtatAvancementId(id);
        
        // Then delete the sous-etat
        sousEtatAvancementDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SousEtatAvancementDto getSousEtatAvancementById(String id) {
        // Fetch the entity with required associations
        SousEtatAvancement sousEtatAvancement = sousEtatAvancementDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Sous-état d'avancement non trouvé avec l'ID: " + id));
        
        // Force initialization of lazy collections within the transaction
        if (sousEtatAvancement.getPhotos() != null) {
            sousEtatAvancement.getPhotos().size(); // This forces Hibernate to load the collection
        }
            
        // Map to DTO using ModelMapper with explicit mapping
        SousEtatAvancementDto dto = modelMapper.map(sousEtatAvancement, SousEtatAvancementDto.class);
        
        // Set etatAvancementId if available
        if (sousEtatAvancement.getEtatAvancement() != null) {
            dto.setEtatAvancementId(sousEtatAvancement.getEtatAvancement().getId());
        }
        
        // Map photos if available
        if (sousEtatAvancement.getPhotos() != null && !sousEtatAvancement.getPhotos().isEmpty()) {
            List<PhotoSousEtatDto> photoDtos = sousEtatAvancement.getPhotos().stream()
                .map(photo -> modelMapper.map(photo, PhotoSousEtatDto.class))
                .collect(Collectors.toList());
            dto.setPhotos(photoDtos);
        }
        
        return dto;
    }

    @Override
    public List<SousEtatAvancementDto> getSousEtatsAvancementByEtatAvancementId(String etatAvancementId) {
        return sousEtatAvancementDao.findByEtatAvancementId(etatAvancementId).stream()
            .map(sousEtat -> {
                // Création manuelle du DTO
                SousEtatAvancementDto dto = new SousEtatAvancementDto();
                dto.setId(sousEtat.getId());
                dto.setLibelle(sousEtat.getNom());
                dto.setDescription(sousEtat.getDescription());
                dto.setPourcentageRealise(sousEtat.getPourcentageRealise());
                dto.setPoids(sousEtat.getPoids());
                
                // Définir l'ID de l'état d'avancement
                if (sousEtat.getEtatAvancement() != null) {
                    dto.setEtatAvancementId(sousEtat.getEtatAvancement().getId());
                }
                
                // Récupérer les photos
                List<PhotoSousEtatDto> photos = photoSousEtatService.getPhotosBySousEtatAvancementId(sousEtat.getId());
                dto.setPhotos(photos);
                
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public double calculerPourcentageRealise(String etatAvancementId) {
        List<SousEtatAvancementDto> sousEtats = getSousEtatsAvancementByEtatAvancementId(etatAvancementId);
        
        double totalPoids = sousEtats.stream()
            .filter(se -> se.getPoids() != null)
            .mapToDouble(SousEtatAvancementDto::getPoids)
            .sum();
            
        if (totalPoids == 0) {
            return 0.0;
        }
        
        return sousEtats.stream()
            .filter(se -> se.getPourcentageRealise() != null && se.getPoids() != null)
            .mapToDouble(se -> (se.getPourcentageRealise() * se.getPoids()) / totalPoids)
            .sum();
    }
}
