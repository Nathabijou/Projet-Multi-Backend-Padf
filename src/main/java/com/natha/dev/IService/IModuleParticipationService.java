package com.natha.dev.IService;

import com.natha.dev.Dto.BeneficiaireModuleDto;

import java.util.List;

public interface IModuleParticipationService {
    
    /**
     * Jwenn lis tout benefisyè ki patisipe nan yon modil
     * @param moduleId ID modil la
     * @return Lis benefisyè yo ak si yo te prezan oswa ou pa
     */
    List<BeneficiaireModuleDto> getBeneficiairesByModuleId(String moduleId);
}
