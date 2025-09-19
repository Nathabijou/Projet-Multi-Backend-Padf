package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PhotoDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.PhotoDTO;
import com.natha.dev.IService.PhotoIService;
import com.natha.dev.Model.Photo;
import com.natha.dev.Model.Photo.PhotoType;
import com.natha.dev.Model.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoImpl implements PhotoIService {

    @Value("${app.upload.dir:${user.home}/.cantique/uploads}")
    private String uploadDir;

    private final PhotoDao photoDao;
    private final ProjetDao projetDao;

    @Autowired
    public PhotoImpl(PhotoDao photoDao, ProjetDao projetDao) {
        this.photoDao = photoDao;
        this.projetDao = projetDao;
    }

    @Override
    public PhotoDTO uploadPhoto(String projetId, MultipartFile file, PhotoType type, String description) throws IOException {
        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Fichye a vid. Tanpri chwazi yon fichye ki valab.");
        }

        // Find project
        Projet projet = projetDao.findById(projetId)
                .orElseThrow(() -> new IllegalArgumentException("Pwojè pa jwenn ak ID: " + projetId));

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFilename = UUID.randomUUID().toString() + fileExtension;
        Path targetLocation = uploadPath.resolve(newFilename);

        // Save file to filesystem
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Save photo info to database
        Photo photo = new Photo();
        photo.setFileName(newFilename);
        photo.setUrl(targetLocation.toUri().toString());
        photo.setType(type);
        photo.setContentType(file.getContentType());
        photo.setFileSize(file.getSize());
        photo.setProjet(projet);
        
        Photo savedPhoto = photoDao.save(photo);
        
        return convertToDTO(savedPhoto);
    }

    @Override
    public List<PhotoDTO> getPhotosByProjetAndType(String projetId, PhotoType type) {
        return photoDao.findByProjetIdAndType(projetId, type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PhotoDTO getPhotoById(Long id) {
        return photoDao.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<PhotoDTO> getPhotosByProjet(String projetId) {
        // Get all photos for the project using the optimized query
        List<Photo> photos = photoDao.findByProjetId(projetId);
        
        if (photos.isEmpty()) {
            // Optional: Verify project exists if no photos found
            projetDao.findById(projetId)
                   .orElseThrow(() -> new IllegalArgumentException("Pwojè pa jwenn ak ID: " + projetId));
        }
        
        // Convert to DTOs
        return photos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PhotoDTO updatePhotoDescription(Long id, String description) {
        return null;
    }

    @Override
    public boolean deletePhoto(Long id) {
        return false;
    }

//    @Override
//    public boolean deletePhoto(Long id) {
//        try {
//            // Jwenn foto a anvan pou n ka efase fichye fizik la
//            Photo photo = photoDao.findById(id)
//                    .orElseThrow(() -> new IllegalArgumentException("Foto pa jwenn ak ID: " + id));
//
//            // Efase fichye fizik la
//            Path filePath = Paths.get(photo.getFilePath());
//            if (Files.exists(filePath)) {
//                Files.delete(filePath);
//            }
//
//            // Efose entre nan baz done a
//            photoDao.delete(photo);
//            return true;
//        } catch (Exception e) {
//            // Log erreur yo pou debogaj
//            System.err.println("Erè pandan efasman foto ID: " + id + ", " + e.getMessage());
//            return false;
//        }
//    }

    @Override
    public void deletePhotosByProjet(Projet projet) {

    }

    private PhotoDTO convertToDTO(Photo photo) {
        if (photo == null) {
            return null;
        }
        
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setUrl(photo.getUrl());
        dto.setFileName(photo.getFileName());
        dto.setType(photo.getType());
        dto.setContentType(photo.getContentType());
        dto.setFileSize(photo.getFileSize());
        dto.setUploadDate(photo.getUploadDate());
        dto.setProjetId(photo.getProjet() != null ? photo.getProjet().getIdProjet() : null);
        
        return dto;
    }
}
