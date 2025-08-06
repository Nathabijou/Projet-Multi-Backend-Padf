package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ChapitreDao;
import com.natha.dev.Dao.ModuleDao;
import com.natha.dev.Dto.ModuleCreationDto;
import com.natha.dev.Dto.ModuleDto;
import com.natha.dev.IService.IModuleService;
import com.natha.dev.Mapper.ModuleMapper;
import com.natha.dev.Model.Chapitre;
import com.natha.dev.Model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModuleServiceImpl implements IModuleService {

    private final ModuleDao moduleDao;
    private final ChapitreDao chapitreDao;
    private final ModuleMapper moduleMapper;

    @Autowired
    public ModuleServiceImpl(ModuleDao moduleDao, ChapitreDao chapitreDao, ModuleMapper moduleMapper) {
        this.moduleDao = moduleDao;
        this.chapitreDao = chapitreDao;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public List<ModuleDto> getModulesByChapitreId(String chapitreId) {
        // Jwenn tout modil yo nan lòd yo
        List<Module> modules = moduleDao.findByChapitreIdChapitreOrderByOrdre(chapitreId);
        
        // Konvèti chak modil an ModuleDto epi kolekte yo nan yon lis
        return modules.stream()
                .map(moduleMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public ModuleDto createModule(String chapitreId, ModuleCreationDto moduleDto) {
        // Jwenn chapit la
        Chapitre chapitre = chapitreDao.findById(chapitreId)
                .orElseThrow(() -> new RuntimeException("Chapit pa jwenn ak ID: " + chapitreId));
        
        // Konvèti DTO a an entite
        Module module = new Module();
        module.setTitre(moduleDto.getTitre());
        module.setDescription(moduleDto.getDescription());
        module.setContenu(moduleDto.getContenu());
        module.setDureeEnMinutes(moduleDto.getDureeEnMinutes());
        module.setOrdre(moduleDto.getOrdre());
        module.setTypeModule(moduleDto.getTypeModule());
        module.setUrlRessource(moduleDto.getUrlRessource());
        module.setChapitre(chapitre);
        
        // Sove modil la
        Module savedModule = moduleDao.save(module);
        
        // Retounen modil la konvèti an DTO
        return moduleMapper.toDto(savedModule);
    }
}
