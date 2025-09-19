package com.natha.dev.Dto;

import com.natha.dev.Model.Photo.PhotoType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Objè transfè done pou foto yo.
 * Sèvi kòm yon modèl pou kominikasyon ant kliyan an ak sèvè a.
 */
@Data
public class PhotoDTO {
    private Long id;
    private String url;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private PhotoType type;
    private LocalDateTime uploadDate;
    private String projetId;  // ID pwojè ki gen foto a

    // Konstriktè vid pou deserializasyon JSON
    public PhotoDTO() {}

    // Konstriktè ki pran yon objè Photo epi kreye yon PhotoDTO
    public static PhotoDTO fromEntity(com.natha.dev.Model.Photo photo) {
        if (photo == null) {
            return null;
        }

        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setUrl(photo.getUrl());
        dto.setFileName(photo.getFileName());
        dto.setContentType(photo.getContentType());
        dto.setFileSize(photo.getFileSize());
        dto.setType(photo.getType());
        dto.setUploadDate(photo.getUploadDate());

        if (photo.getProjet() != null) {
            dto.setProjetId(photo.getProjet().getIdProjet());
        }

        return dto;
    }

    // Konvèti yon PhotoDTO an yon entite Photo
    public com.natha.dev.Model.Photo toEntity() {
        com.natha.dev.Model.Photo photo = new com.natha.dev.Model.Photo();
        photo.setId(this.id);
        photo.setUrl(this.url);
        photo.setFileName(this.fileName);
        photo.setContentType(this.contentType);
        photo.setFileSize(this.fileSize);
        photo.setType(this.type);
        // Nou p'ap mete projet la isit la, li dwe mete nan kou a
        return photo;
    }
}
