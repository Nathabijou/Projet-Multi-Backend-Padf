package com.natha.dev.IService;

import com.natha.dev.Dto.ChapitreStatsDto;
import com.natha.dev.Dto.KpiResponse;
import com.natha.dev.Dto.DashboardFormationDetailsDto;
import com.natha.dev.Dto.ModuleStatsDto;
import com.natha.dev.Model.DashboardFilter;

import java.util.List;

public interface DashboardIService {
    KpiResponse getKpiData(DashboardFilter filter, String username);

    /**
     * Konte kantite pwojè yo selon filtre yo
     * @param filter filtre yo pou konte pwojè yo
     * @return kantite pwojè ki matche ak filtre yo
     */
    long countProjets(DashboardFilter filter);

    /**
     * Jwenn estatistik fòmasyon yo pou dashboard la
     * @return Objè KpiResponse ki gen tout estatistik fòmasyon yo
     */
    KpiResponse getFormationStatistics();

    /**
     * Konte kantite fòmasyon ki asosye ak pwojè ki matche ak filtre yo
     */
    long countFormationsByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    );

    /**
     * Jwenn detay fòmasyon yo pou dashboard la
     * @param username non itilizatè k ap chèche a
     * @param composanteId ID kompozant si genyen
     * @param zoneId ID zòn si genyen
     * @param departementId ID depatman si genyen
     * @param arrondissementId ID awondisman si genyen
     * @param communeId ID komin si genyen
     * @param sectionId ID seksyon si genyen
     * @param quartierId ID katye si genyen
     * @param projetId ID pwojè si genyen
     * @return Detay fòmasyon yo nan yon objè DashboardFormationDetailsDto
     */
    DashboardFormationDetailsDto getFormationDetails(
            String username, Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    );

    /**
     * Konte kantite modil ki asosye ak fòmasyon ki matche ak filtre yo
     */
    long countModulesByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    );

    /**
     * Konte kantite chapit ki asosye ak fòmasyon ki matche ak filtre yo
     */
    long countChapitresByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    );

    /**
     * Konte kantite pwojè ki gen fòmasyon ki matche ak filtre yo
     */
    long countProjetsAvecFormationByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    );

    /**
     * Jwenn estatistik chapit yo ak kantite benefisyè pa sèks
     */
    List<ChapitreStatsDto> getChapitresStats(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    );

    /**
     * Jwenn estatistik chapit yo ak kantite benefisyè pa sèks
     */
    List<ChapitreStatsDto> getChapitreStats(
            String username, Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    );

    /**
     * Jwenn estatistik modil yo ak kantite fi ak gason ki kalifye pou chak modil
     * @return Lis estatistik modil yo
     */
    List<ModuleStatsDto> getModuleStats();

    /**
     * Egzekite yon rekèt SQL sou tab projet_beneficiaire_formation pou tcheke done yo
     * @param query rekèt SQL a egzekite
     * @return rezilta rekèt la kòm yon lis objè
     */
    List<Object[]> executeFormationQuery(String query);
}
