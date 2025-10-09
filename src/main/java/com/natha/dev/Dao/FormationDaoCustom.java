package com.natha.dev.Dao;

import com.natha.dev.Dto.ChapitreStatsDto;

import java.util.List;

public interface FormationDaoCustom {
    List<ChapitreStatsDto> getChapitreStatsWithFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId);

    long countBeneficiariesBySexeQualificationAndFormation(
            String sexe, boolean isQualified, Long composanteId, Long zoneId,
            Long departementId, Long arrondissementId, Long communeId,
            Long sectionId, Long quartierId, String projetId);
}
