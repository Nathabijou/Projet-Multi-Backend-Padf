package com.natha.dev.IService;

import com.natha.dev.Dto.PhotoDTO;
import com.natha.dev.Model.Photo.PhotoType;
import com.natha.dev.Model.Projet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoIService {

    /**
     * Telechaje yon nouvo foto pou yon pwojè
     *
     * @param projetId ID pwojè a
     * @param file Fichye foto a pou telechaje
     * @param type Kalite foto a (AVANT, PENDANT, APRES)
     * @param description Deskripsyon opsyonèl pou foto a
     * @return DTO foto ki fèk kreye a
     * @throws IOException Si gen yon erè pandan telechajman an
     */
    PhotoDTO uploadPhoto(String projetId, MultipartFile file, PhotoType type, String description) throws IOException;

    /**
     * Jwenn tout foto yon pwojè espesifik
     *
     * @param projetId ID pwojè a
     * @return Yon lis tout foto pwojè a
     */
    List<PhotoDTO> getPhotosByProjet(String projetId);

    /**
     * Jwenn tout foto yon pwojè espesifik selon kalite foto a
     *
     * @param projetId ID pwojè a
     * @param type Kalite foto a (AVANT, PENDANT, APRES)
     * @return Yon lis foto ki matche ak kritè yo
     */
    List<PhotoDTO> getPhotosByProjetAndType(String projetId, PhotoType type);

    /**
     * Jwenn yon foto pa ID li
     *
     * @param id ID foto a
     * @return DTO foto a si li egziste, otreman null
     */
    PhotoDTO getPhotoById(Long id);

    /**
     * Mete ajou deskripsyon yon foto
     *
     * @param id ID foto a pou mete ajou
     * @param description Nouvo deskripsyon an
     * @return DTO foto a ak deskripsyon an mete ajou
     */
    PhotoDTO updatePhotoDescription(Long id, String description);

    /**
     * Efase yon foto
     *
     * @param id ID foto a pou efase
     * @return true si foto a te efase avèk siksè, otreman false
     */
    boolean deletePhoto(Long id);

    /**
     * Jwenn tout foto ki asosye ak yon pwojè epi efase yo
     *
     * @param projet Pwojè a pou efase foto yo
     */
    void deletePhotosByProjet(Projet projet);
}
