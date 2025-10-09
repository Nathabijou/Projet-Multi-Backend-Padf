package com.natha.dev.IService;

import com.natha.dev.Dto.*;
import com.natha.dev.Dto.Request.AddFormationToProjetBeneficiaireRequestDto;

import java.util.List;
import java.util.Map;

public interface FormationIService {

    Map<String, Object> debugFormationData();

    FormationDto save(FormationDto dto);
    FormationDto update(String idFormation, FormationDto dto);
    void deleteById(String idFormation);
    FormationDto getById(String idFormation);
    List<FormationDto> getAll();

    ProjetBeneficiaireFormationDto addBeneficiaireToFormation(AddBeneficiaireToFormationRequestDto requestDto);

    List<BeneficiaireDto> getBeneficiairesByFormationId(String idFormation, String projetId);

    List<FormationDto> getFormationsByProjetId(String projetId);

    void removeBeneficiaireFromFormation(String beneficiaireId, String formationId);

    /**
     * Ajoute yon fòmasyon nan yon ProjetBeneficiaire
     * @param requestDto DTO ki genyen ID fòmasyon an, ID benefisyè a, ak ID pwojè a
     * @return Detay asosyasyon an kreye a
     */
    ProjetBeneficiaireFormationDto addFormationToProjetBeneficiaire(AddFormationToProjetBeneficiaireRequestDto requestDto);

    /**
     * Konte kantite total dosye fòmasyon ki egziste nan baz done a
     * @return kantite total dosye fòmasyon yo
     */
    long countAllFormationRecords();

    /**
     * Jwenn estatistik fòmasyon yo pou dashboard la
     * @return Yon objè FormationDashboardDto ki gen tout estatistik yo
     */
    FormationDashboardDto getFormationStatistics();
}
