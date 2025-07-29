package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PresenceDao;
import com.natha.dev.Dto.BeneficiaireModuleDto;
import com.natha.dev.IService.IModuleParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModuleParticipationServiceImpl implements IModuleParticipationService {

    private final PresenceDao presenceDao;

    @Autowired
    public ModuleParticipationServiceImpl(PresenceDao presenceDao) {
        this.presenceDao = presenceDao;
    }

    @Override
    public List<BeneficiaireModuleDto> getBeneficiairesByModuleId(String moduleId) {
        return presenceDao.findBeneficiairesByModuleId(moduleId);
    }
}
