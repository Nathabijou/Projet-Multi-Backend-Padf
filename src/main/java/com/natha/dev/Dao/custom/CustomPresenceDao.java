package com.natha.dev.Dao.custom;

import com.natha.dev.Dto.BeneficiaireModuleDto;
import java.util.List;

public interface CustomPresenceDao {
    List<BeneficiaireModuleDto> findBeneficiairesByModuleId(String moduleId);
}
