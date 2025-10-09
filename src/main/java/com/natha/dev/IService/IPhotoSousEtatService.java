package com.natha.dev.IService;

import com.natha.dev.Dto.PhotoSousEtatDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPhotoSousEtatService {
    PhotoSousEtatDto createPhotoSousEtat(PhotoSousEtatDto photoSousEtatDto, String sousEtatAvancementId);
    PhotoSousEtatDto uploadAndCreatePhotoSousEtat(MultipartFile file, String description, boolean estPhotoPrincipale, String sousEtatAvancementId);
    PhotoSousEtatDto updatePhotoSousEtat(String id, PhotoSousEtatDto photoSousEtatDto);
    void deletePhotoSousEtat(String id);
    PhotoSousEtatDto getPhotoSousEtatById(String id);
    List<PhotoSousEtatDto> getPhotosBySousEtatAvancementId(String sousEtatAvancementId);
    void deletePhotosBySousEtatAvancementId(String sousEtatAvancementId);
    void setAsMainPhoto(String photoId, String sousEtatAvancementId);
}
