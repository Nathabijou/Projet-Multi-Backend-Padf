package com.natha.dev.IService;

import com.natha.dev.Dto.AddBeneficiaireToFormationRequestDto;
import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.Dto.FormationDto;
import com.natha.dev.Dto.ProjetBeneficiaireFormationDto;

import java.util.List;

public interface FormationIService {

    FormationDto save(FormationDto dto);
    FormationDto update(String idFormation, FormationDto dto);
    void deleteById(String idFormation);
    FormationDto getById(String idFormation);
    List<FormationDto> getAll();

    ProjetBeneficiaireFormationDto addBeneficiaireToFormation(AddBeneficiaireToFormationRequestDto requestDto);
    
    List<BeneficiaireDto> getBeneficiairesByFormationId(String idFormation);
    
    List<FormationDto> getFormationsByProjetId(String projetId);
}
