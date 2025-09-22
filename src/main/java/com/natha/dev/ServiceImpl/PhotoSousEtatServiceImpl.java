package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PhotoSousEtatDao;
import com.natha.dev.Dao.SousEtatAvancementDao;
import com.natha.dev.Dto.PhotoSousEtatDto;
import com.natha.dev.IService.IPhotoSousEtatService;
import com.natha.dev.Model.PhotoSousEtat;
import com.natha.dev.Model.SousEtatAvancement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoSousEtatServiceImpl implements IPhotoSousEtatService {

    @Autowired
    private PhotoSousEtatDao photoSousEtatDao;
    
    @Autowired
    private SousEtatAvancementDao sousEtatAvancementDao;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Value("${app.upload.dir:${user.home}/.projet-uploads}")
    private String uploadDir;

    @Override
    public PhotoSousEtatDto createPhotoSousEtat(PhotoSousEtatDto photoSousEtatDto, String sousEtatAvancementId) {
        PhotoSousEtat photoSousEtat = modelMapper.map(photoSousEtatDto, PhotoSousEtat.class);
        
        // Set the SousEtatAvancement
        SousEtatAvancement sousEtatAvancement = sousEtatAvancementDao.findById(sousEtatAvancementId)
            .orElseThrow(() -> new RuntimeException("Sous-état d'avancement non trouvé avec l'ID: " + sousEtatAvancementId));
        
        photoSousEtat.setSousEtatAvancement(sousEtatAvancement);
        
        // Generate ID if not provided
        if (photoSousEtat.getId() == null) {
            photoSousEtat.setId(UUID.randomUUID().toString());
        }
        
        // Set current date if not provided
        if (photoSousEtat.getDateAjout() == null) {
            photoSousEtat.setDateAjout(LocalDateTime.now());
        }
        
        // If this is set as main photo, unset any existing main photo
        if (photoSousEtat.isEstPhotoPrincipale()) {
            unsetExistingMainPhoto(sousEtatAvancementId);
        }
        
        PhotoSousEtat savedPhoto = photoSousEtatDao.save(photoSousEtat);
        return modelMapper.map(savedPhoto, PhotoSousEtatDto.class);
    }

    @Override
    public PhotoSousEtatDto uploadAndCreatePhotoSousEtat(MultipartFile file, String description, 
                                                        boolean estPhotoPrincipale, String sousEtatAvancementId) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Generate a unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + fileExtension;
            
            // Save the file
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);
            
            // Create and save the photo entity
            PhotoSousEtatDto photoDto = new PhotoSousEtatDto();
            photoDto.setNomFichier(originalFilename);
            photoDto.setCheminFichier(filePath.toString());
            photoDto.setDescription(description);
            photoDto.setEstPhotoPrincipale(estPhotoPrincipale);
            
            return createPhotoSousEtat(photoDto, sousEtatAvancementId);
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier: " + e.getMessage(), e);
        }
    }

    @Override
    public PhotoSousEtatDto updatePhotoSousEtat(String id, PhotoSousEtatDto photoSousEtatDto) {
        PhotoSousEtat existingPhoto = photoSousEtatDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Photo non trouvée avec l'ID: " + id));
        
        // Update fields
        existingPhoto.setDescription(photoSousEtatDto.getDescription());
        existingPhoto.setEstPhotoPrincipale(photoSousEtatDto.isEstPhotoPrincipale());
        
        // If this is set as main photo, unset any existing main photo
        if (existingPhoto.isEstPhotoPrincipale()) {
            unsetExistingMainPhoto(existingPhoto.getSousEtatAvancement().getId());
        }
        
        PhotoSousEtat updatedPhoto = photoSousEtatDao.save(existingPhoto);
        return modelMapper.map(updatedPhoto, PhotoSousEtatDto.class);
    }

    @Override
    public void deletePhotoSousEtat(String id) {
        PhotoSousEtat photo = photoSousEtatDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Photo non trouvée avec l'ID: " + id));
        
        // Delete the file from the filesystem
        try {
            Files.deleteIfExists(Paths.get(photo.getCheminFichier()));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression du fichier: " + e.getMessage(), e);
        }
        
        // Delete the entity
        photoSousEtatDao.delete(photo);
    }

    @Override
    public PhotoSousEtatDto getPhotoSousEtatById(String id) {
        PhotoSousEtat photo = photoSousEtatDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Photo non trouvée avec l'ID: " + id));
        return modelMapper.map(photo, PhotoSousEtatDto.class);
    }

    @Override
    public List<PhotoSousEtatDto> getPhotosBySousEtatAvancementId(String sousEtatAvancementId) {
        return photoSousEtatDao.findBySousEtatAvancementId(sousEtatAvancementId).stream()
            .map(photo -> modelMapper.map(photo, PhotoSousEtatDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public void deletePhotosBySousEtatAvancementId(String sousEtatAvancementId) {
        List<PhotoSousEtat> photos = photoSousEtatDao.findBySousEtatAvancementId(sousEtatAvancementId);
        
        // Delete all files first
        for (PhotoSousEtat photo : photos) {
            try {
                Files.deleteIfExists(Paths.get(photo.getCheminFichier()));
            } catch (IOException e) {
                // Log the error but continue with other files
                System.err.println("Erreur lors de la suppression du fichier: " + photo.getCheminFichier() + ": " + e.getMessage());
            }
        }
        
        // Then delete all entities
        photoSousEtatDao.deleteBySousEtatAvancementId(sousEtatAvancementId);
    }

    @Override
    public void setAsMainPhoto(String photoId, String sousEtatAvancementId) {
        // Unset any existing main photo
        unsetExistingMainPhoto(sousEtatAvancementId);
        
        // Set the specified photo as main
        PhotoSousEtat photo = photoSousEtatDao.findById(photoId)
            .orElseThrow(() -> new RuntimeException("Photo non trouvée avec l'ID: " + photoId));
        
        if (!photo.getSousEtatAvancement().getId().equals(sousEtatAvancementId)) {
            throw new RuntimeException("La photo n'appartient pas au sous-état d'avancement spécifié");
        }
        
        photo.setEstPhotoPrincipale(true);
        photoSousEtatDao.save(photo);
    }
    
    private void unsetExistingMainPhoto(String sousEtatAvancementId) {
        List<PhotoSousEtat> mainPhotos = photoSousEtatDao.findBySousEtatAvancementIdAndEstPhotoPrincipaleTrue(sousEtatAvancementId);
        for (PhotoSousEtat mainPhoto : mainPhotos) {
            mainPhoto.setEstPhotoPrincipale(false);
            photoSousEtatDao.save(mainPhoto);
        }
    }
}
