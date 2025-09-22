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
        SousEtatAvancement sousEtatAvancement = modelMapper.map(sousEtatAvancementDto, SousEtatAvancement.class);
        
        // Set the EtatAvancement from the DTO
        if (sousEtatAvancementDto.getEtatAvancementId() != null) {
            EtatAvancement etatAvancement = etatAvancementDao.findById(sousEtatAvancementDto.getEtatAvancementId())
                .orElseThrow(() -> new RuntimeException("État d'avancement non trouvé avec l'ID: " + sousEtatAvancementDto.getEtatAvancementId()));
            sousEtatAvancement.setEtatAvancement(etatAvancement);
        }
        
        // Generate ID if not provided
        if (sousEtatAvancement.getId() == null) {
            sousEtatAvancement.setId(UUID.randomUUID().toString());
        }
        
        // Save the SousEtatAvancement
        SousEtatAvancement savedSousEtat = sousEtatAvancementDao.save(sousEtatAvancement);
        
        // Save Photos if any
        if (sousEtatAvancementDto.getPhotos() != null && !sousEtatAvancementDto.getPhotos().isEmpty()) {
            for (PhotoSousEtatDto photoDto : sousEtatAvancementDto.getPhotos()) {
                photoSousEtatService.createPhotoSousEtat(photoDto, savedSousEtat.getId());
            }
        }
        
        return modelMapper.map(savedSousEtat, SousEtatAvancementDto.class);
    }

    @Override
    public SousEtatAvancementDto updateSousEtatAvancement(String id, SousEtatAvancementDto sousEtatAvancementDto) {
        SousEtatAvancement existingSousEtat = sousEtatAvancementDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Sous-état d'avancement non trouvé avec l'ID: " + id));
        
        // Update fields
        existingSousEtat.setNom(sousEtatAvancementDto.getLibelle());
        existingSousEtat.setPourcentageRealise(sousEtatAvancementDto.getPourcentageRealise());
        existingSousEtat.setPoids(sousEtatAvancementDto.getPoids());
        existingSousEtat.setDescription(sousEtatAvancementDto.getDescription());
        
        // Update EtatAvancement if changed
        if (sousEtatAvancementDto.getEtatAvancementId() != null && 
            (existingSousEtat.getEtatAvancement() == null || 
             !existingSousEtat.getEtatAvancement().getId().equals(sousEtatAvancementDto.getEtatAvancementId()))) {
            EtatAvancement etatAvancement = etatAvancementDao.findById(sousEtatAvancementDto.getEtatAvancementId())
                .orElseThrow(() -> new RuntimeException("État d'avancement non trouvé avec l'ID: " + sousEtatAvancementDto.getEtatAvancementId()));
            existingSousEtat.setEtatAvancement(etatAvancement);
        }
        
        SousEtatAvancement updatedSousEtat = sousEtatAvancementDao.save(existingSousEtat);
        
        // Update photos if needed (this is a simplified version, you might want to handle individual photo updates)
        
        return modelMapper.map(updatedSousEtat, SousEtatAvancementDto.class);
    }

    @Override
    public void deleteSousEtatAvancement(String id) {
        // Delete all photos first
        photoSousEtatService.deletePhotosBySousEtatAvancementId(id);
        
        // Then delete the sous-etat
        sousEtatAvancementDao.deleteById(id);
    }

    @Override
    public SousEtatAvancementDto getSousEtatAvancementById(String id) {
        SousEtatAvancement sousEtatAvancement = sousEtatAvancementDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Sous-état d'avancement non trouvé avec l'ID: " + id));
            
        SousEtatAvancementDto dto = modelMapper.map(sousEtatAvancement, SousEtatAvancementDto.class);
        
        // Set the etatAvancementId
        if (sousEtatAvancement.getEtatAvancement() != null) {
            dto.setEtatAvancementId(sousEtatAvancement.getEtatAvancement().getId());
        }
        
        // Get and set photos
        List<PhotoSousEtatDto> photos = photoSousEtatService.getPhotosBySousEtatAvancementId(id);
        dto.setPhotos(photos);
        
        return dto;
    }

    @Override
    public List<SousEtatAvancementDto> getSousEtatsAvancementByEtatAvancementId(String etatAvancementId) {
        return sousEtatAvancementDao.findByEtatAvancementId(etatAvancementId).stream()
            .map(sousEtat -> getSousEtatAvancementById(sousEtat.getId()))
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
