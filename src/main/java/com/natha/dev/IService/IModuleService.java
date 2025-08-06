package com.natha.dev.IService;

import com.natha.dev.Dto.ModuleCreationDto;
import com.natha.dev.Dto.ModuleDto;
import com.natha.dev.Model.Module;

import java.util.List;

public interface IModuleService {
    /**
     * Jwenn tout modil ki nan yon chapit
     * @param chapitreId ID chapit la
     * @return Lis modil yo nan lòd yo
     */
    List<ModuleDto> getModulesByChapitreId(String chapitreId);
    
    /**
     * Kreye yon nouvo modil nan yon chapit
     * @param chapitreId ID chapit kote wap ajoute modil la
     * @param moduleDto Done pou kreye modil la
     * @return Modil ki fèk kreye a
     */
    ModuleDto createModule(String chapitreId, ModuleCreationDto moduleDto);
}
