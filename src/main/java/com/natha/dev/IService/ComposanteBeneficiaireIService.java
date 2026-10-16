package com.natha.dev.IService;

import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.Dto.ComposanteBeneficiaireDto;

import java.util.List;

public interface ComposanteBeneficiaireIService {

    ComposanteBeneficiaireDto save(Long composanteId, String beneficiaireId);

    List<BeneficiaireDto> getBeneficiairesByComposante(Long composanteId);

    List<BeneficiaireDto> getCandidatsByComposante(Long composanteId);

    void delete(Long composanteId, String beneficiaireId);
}
