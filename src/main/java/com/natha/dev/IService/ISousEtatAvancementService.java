package com.natha.dev.IService;

import com.natha.dev.Dto.SousEtatAvancementDto;

import java.util.List;

public interface ISousEtatAvancementService {
    SousEtatAvancementDto createSousEtatAvancement(SousEtatAvancementDto sousEtatAvancementDto);
    SousEtatAvancementDto updateSousEtatAvancement(String id, SousEtatAvancementDto sousEtatAvancementDto);
    void deleteSousEtatAvancement(String id);
    SousEtatAvancementDto getSousEtatAvancementById(String id);
    List<SousEtatAvancementDto> getSousEtatsAvancementByEtatAvancementId(String etatAvancementId);
    double calculerPourcentageRealise(String etatAvancementId);
}
