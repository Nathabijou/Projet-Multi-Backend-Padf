package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.DocumentaireDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.DocumentaireDto;
import com.natha.dev.IService.DocumentaireIService;
import com.natha.dev.Model.Documentaire;
import com.natha.dev.Model.Documentaire.DocumentaireType;
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
public class DocumentaireImpl implements DocumentaireIService {

    @Value("${app.upload.dir:${user.home}/.cantique/uploads}")
    private String uploadDir;

    private final DocumentaireDao documentaireDao;
    private final ProjetDao projetDao;

    @Autowired
    public DocumentaireImpl(DocumentaireDao documentaireDao, ProjetDao projetDao) {
        this.documentaireDao = documentaireDao;
        this.projetDao = projetDao;
    }

    @Override
    public DocumentaireDto uploadDocumentaire(String projetId, MultipartFile file, Documentaire.DocumentaireType type, String description) throws IOException {
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

        // Save document info to database
        Documentaire documentaire = new Documentaire();
        documentaire.setFileName(newFilename);
        documentaire.setUrl(targetLocation.toUri().toString());
        documentaire.setType(type);
        documentaire.setContentType(file.getContentType());
        documentaire.setFileSize(file.getSize());
        documentaire.setProjet(projet);

        Documentaire savedDocumentaire = documentaireDao.save(documentaire);

        return convertToDTO(savedDocumentaire);
    }

    @Override
    public DocumentaireDto getDocumentaireById(Long id) {
        return documentaireDao.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<DocumentaireDto> getDocumentairesByProjet(String projetId) {
        // Get all documents for the project using the optimized query
        List<Documentaire> documentaires = documentaireDao.findByProjetId(projetId);

        if (documentaires.isEmpty()) {
            // Verify project exists if no documents found
            projetDao.findById(projetId)
                    .orElseThrow(() -> new IllegalArgumentException("Pwojè pa jwenn ak ID: " + projetId));
        }

        // Convert to DTOs
        return documentaires.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<DocumentaireDto> getDocumentairesByProjetAndType(String projetId, Documentaire.DocumentaireType type) {
        // Get all documents for the project and type
        List<Documentaire> documentaires = documentaireDao.findByProjetIdAndType(projetId, type);

        if (documentaires.isEmpty()) {
            // Verify project exists if no documents found
            projetDao.findById(projetId)
                    .orElseThrow(() -> new IllegalArgumentException("Pwojè pa jwenn ak ID: " + projetId));
        }

        // Convert to DTOs
        return documentaires.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public DocumentaireDto updateDocumentaireDescription(Long id, String description) {
        Documentaire documentaire = documentaireDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dokimantè pa jwenn ak ID: " + id));
        
        // Update description if needed
        // documentaire.setDescription(description);
        
        Documentaire updatedDocumentaire = documentaireDao.save(documentaire);
        return convertToDTO(updatedDocumentaire);
    }

    @Override
    public boolean deleteDocumentaire(Long id) {
        try {
            // Jwenn dokimantè a anvan pou n ka efase fichye fizik la
            Documentaire documentaire = documentaireDao.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Dokimantè pa jwenn ak ID: " + id));

            // Efase fichye fizik la
            if (documentaire.getUrl() != null) {
                try {
                    Path filePath = Paths.get(documentaire.getUrl().replace("file:", ""));
                    if (Files.exists(filePath)) {
                        Files.delete(filePath);
                    }
                } catch (Exception e) {
                    System.err.println("Erè pandan efasman fichye a: " + e.getMessage());
                }
            }

            // Efase nan baz done a
            documentaireDao.delete(documentaire);
            return true;
        } catch (Exception e) {
            System.err.println("Erè pandan efasman dokimantè ID: " + id + ", " + e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteDocumentaireByProjet(Projet projet) {
        List<Documentaire> documentaires = documentaireDao.findByProjet(projet);
        for (Documentaire doc : documentaires) {
            deleteDocumentaire(doc.getId());
        }
    }

    private DocumentaireDto convertToDTO(Documentaire documentaire) {
        if (documentaire == null) {
            return null;
        }

        DocumentaireDto dto = new DocumentaireDto();
        dto.setId(documentaire.getId());
        dto.setUrl(documentaire.getUrl());
        dto.setFileName(documentaire.getFileName());
        dto.setContentType(documentaire.getContentType());
        dto.setFileSize(documentaire.getFileSize());
        dto.setUploadDate(documentaire.getUploadDate());
        dto.setType(documentaire.getType());

        return dto;
    }
}
