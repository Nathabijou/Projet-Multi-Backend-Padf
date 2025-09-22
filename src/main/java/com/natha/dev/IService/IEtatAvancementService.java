package com.natha.dev.IService;

import com.natha.dev.Dto.EtatAvancementDto;

import java.util.List;

public interface IEtatAvancementService {
    EtatAvancementDto createEtatAvancement(EtatAvancementDto etatAvancementDto);
    EtatAvancementDto updateEtatAvancement(String id, EtatAvancementDto etatAvancementDto);
    void deleteEtatAvancement(String id);
    EtatAvancementDto getEtatAvancementById(String id);
    List<EtatAvancementDto> getAllEtatsAvancement();
    List<EtatAvancementDto> getEtatsAvancementByProjetId(String projetId);
    double calculerPourcentageTotalProjet(String projetId);
}
