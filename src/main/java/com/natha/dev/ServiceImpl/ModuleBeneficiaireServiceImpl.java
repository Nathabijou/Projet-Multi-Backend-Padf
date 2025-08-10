package com.natha.dev.ServiceImpl;

import com.natha.dev.Dto.ModuleBeneficiaireDto;
import com.natha.dev.IService.IModuleBeneficiaireService;
import com.natha.dev.Model.*;
import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.ModuleDao;
import com.natha.dev.Model.Module;
import com.natha.dev.Repository.ModuleBeneficiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleBeneficiaireServiceImpl implements IModuleBeneficiaireService {

    private final ModuleBeneficiaireRepository moduleBeneficiaireRepository;
    private final ModuleDao moduleDao;
    private final BeneficiaireDao beneficiaireDao;

    @Autowired
    public ModuleBeneficiaireServiceImpl(
            ModuleBeneficiaireRepository moduleBeneficiaireRepository,
            ModuleDao moduleDao,
            BeneficiaireDao beneficiaireDao) {
        this.moduleBeneficiaireRepository = moduleBeneficiaireRepository;
        this.moduleDao = moduleDao;
        this.beneficiaireDao = beneficiaireDao;
    }

    @Override
    @Transactional
    public ModuleBeneficiaireDto addBeneficiaireToModule(String moduleId, String beneficiaireId) {
        Module module = moduleDao.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module pa jwenn ak ID: " + moduleId));
        
        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + beneficiaireId));
        
        // Verify if the relationship already exists
        if (moduleBeneficiaireRepository.existsByModuleAndBeneficiaire(module, beneficiaire)) {
            throw new RuntimeException("Benefisyè a deja enskri nan modil sa a");
        }
        
        // Kreye moduleBeneficiaire a
        ModuleBeneficiaire moduleBeneficiaire = new ModuleBeneficiaire();
        moduleBeneficiaire.setModule(module);
        moduleBeneficiaire.setBeneficiaire(beneficiaire);
        moduleBeneficiaire.setCompleted(false);
        
        // Kreye yon evaluasyon pou moduleBeneficiaire a
        Evaluation evaluation = new Evaluation();
        evaluation.setModuleBeneficiaire(moduleBeneficiaire);
        moduleBeneficiaire.setEvaluation(evaluation);
        
        // Sove moduleBeneficiaire a ak evaluasyon an ansanm
        ModuleBeneficiaire saved = moduleBeneficiaireRepository.save(moduleBeneficiaire);
        return convertToDto(saved);
    }

    @Override
    @Transactional
    public void removeBeneficiaireFromModule(String moduleId, String beneficiaireId) {
        Module module = moduleDao.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module pa jwenn ak ID: " + moduleId));
        
        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + beneficiaireId));
        
        ModuleBeneficiaire moduleBeneficiaire = moduleBeneficiaireRepository.findByModuleAndBeneficiaire(module, beneficiaire)
                .orElseThrow(() -> new RuntimeException("Relasyon modil-benefisyè a pa egziste"));

        moduleBeneficiaireRepository.delete(moduleBeneficiaire);
    }

    @Override
    public List<ModuleBeneficiaireDto> getBeneficiairesByModule(String moduleId) {
        Module module = moduleDao.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module pa jwenn ak ID: " + moduleId));
        
        return moduleBeneficiaireRepository.findByModule(module).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModuleBeneficiaireDto> getModulesByBeneficiaire(String beneficiaireId) {
        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + beneficiaireId));
        
        return moduleBeneficiaireRepository.findByBeneficiaire(beneficiaire).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isBeneficiaireInModule(String moduleId, String beneficiaireId) {
        Module module = moduleDao.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module pa jwenn ak ID: " + moduleId));
        
        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + beneficiaireId));
        
        return moduleBeneficiaireRepository.existsByModuleAndBeneficiaire(module, beneficiaire);
    }

    @Override
    @Transactional
    public ModuleBeneficiaireDto markModuleAsCompleted(String moduleId, String beneficiaireId, boolean isCompleted) {
        Module module = moduleDao.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module pa jwenn ak ID: " + moduleId));
        
        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Benefisyè pa jwenn ak ID: " + beneficiaireId));
        
        ModuleBeneficiaire moduleBeneficiaire = moduleBeneficiaireRepository
                .findByModuleAndBeneficiaire(module, beneficiaire)
                .orElseThrow(() -> new RuntimeException("Benefisyè a pa enskri nan modil sa a"));
        
        moduleBeneficiaire.setCompleted(isCompleted);
        if (isCompleted) {
            moduleBeneficiaire.setDateCompletion(LocalDateTime.now());
        } else {
            moduleBeneficiaire.setDateCompletion(null);
        }
        
        ModuleBeneficiaire updated = moduleBeneficiaireRepository.save(moduleBeneficiaire);
        return convertToDto(updated);
    }
    
    private ModuleBeneficiaireDto convertToDto(ModuleBeneficiaire moduleBeneficiaire) {
        ModuleBeneficiaireDto dto = new ModuleBeneficiaireDto();
        dto.setId(moduleBeneficiaire.getId());
        
        Module module = moduleBeneficiaire.getModule();
        dto.setModuleId(module.getIdModule());
        dto.setModuleTitre(module.getTitre());
        
        Beneficiaire beneficiaire = moduleBeneficiaire.getBeneficiaire();
        dto.setBeneficiaireId(beneficiaire.getIdBeneficiaire());
        dto.setBeneficiaireNom(beneficiaire.getNom());
        dto.setBeneficiairePrenom(beneficiaire.getPrenom());
        
        dto.setCompleted(moduleBeneficiaire.isCompleted());
        dto.setDateInscription(moduleBeneficiaire.getDateInscription());
        dto.setDateCompletion(moduleBeneficiaire.getDateCompletion());
        
        return dto;
    }
}
