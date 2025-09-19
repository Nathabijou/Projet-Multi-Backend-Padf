package com.natha.dev.IService;

import com.natha.dev.Dto.DocumentaireDto;
import com.natha.dev.Model.Documentaire;
import com.natha.dev.Model.Documentaire.DocumentaireType;
import com.natha.dev.Model.Projet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentaireIService {

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
    DocumentaireDto uploadDocumentaire(String projetId, MultipartFile file, Documentaire.DocumentaireType type, String description) throws IOException;

    /**
     * Jwenn tout foto yon pwojè espesifik
     *
     * @param projetId ID pwojè a
     * @return Yon lis tout foto pwojè a
     */
    List<DocumentaireDto> getDocumentairesByProjet(String projetId);

    /**
     * Jwenn tout foto yon pwojè espesifik selon kalite foto a
     *
     * @param projetId ID pwojè a
     * @param type Kalite foto a (AVANT, PENDANT, APRES)
     * @return Yon lis foto ki matche ak kritè yo
     */
List<DocumentaireDto> getDocumentairesByProjetAndType(String projetId, Documentaire.DocumentaireType type);

    /**
     * Jwenn yon foto pa ID li
     *
     * @param id ID foto a
     * @return DTO foto a si li egziste, otreman null
     */
    DocumentaireDto getDocumentaireById(Long id);

    /**
     * Mete ajou deskripsyon yon foto
     *
     * @param id ID foto a pou mete ajou
     * @param description Nouvo deskripsyon an
     * @return DTO foto a ak deskripsyon an mete ajou
     */
    DocumentaireDto updateDocumentaireDescription(Long id, String description);

    /**
     * Efase yon foto
     *
     * @param id ID foto a pou efase
     * @return true si foto a te efase avèk siksè, otreman false
     */
    boolean deleteDocumentaire(Long id);

    /**
     * Jwenn tout foto ki asosye ak yon pwojè epi efase yo
     *
     * @param projet Pwojè a pou efase foto yo
     */
    void deleteDocumentaireByProjet(Projet projet);
}
