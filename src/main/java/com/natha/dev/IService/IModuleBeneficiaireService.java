package com.natha.dev.IService;

import com.natha.dev.Dto.ModuleBeneficiaireDto;

import java.util.List;

public interface IModuleBeneficiaireService {
    ModuleBeneficiaireDto addBeneficiaireToModule(String moduleId, String beneficiaireId);
    void removeBeneficiaireFromModule(String moduleId, String beneficiaireId);
    List<ModuleBeneficiaireDto> getBeneficiairesByModule(String moduleId);
    List<ModuleBeneficiaireDto> getModulesByBeneficiaire(String beneficiaireId);
    boolean isBeneficiaireInModule(String moduleId, String beneficiaireId);
    ModuleBeneficiaireDto markModuleAsCompleted(String moduleId, String beneficiaireId, boolean isCompleted);
}
