package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.CommuneDao;
import com.natha.dev.Dao.IProcessusConsultatifDao;
import com.natha.dev.Dto.ProcessusConsultatifDTO;
import com.natha.dev.IService.IProcessusConsultatifService;
import com.natha.dev.Model.Commune;
import com.natha.dev.Model.ProcessusConsultatif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProcessusConsultatifServiceImpl implements IProcessusConsultatifService {

    @Autowired
    private IProcessusConsultatifDao processusConsultatifDao;

    @Autowired
    private CommuneDao communeDao;

    @Override
    public List<ProcessusConsultatif> getAllProcessusConsultatifs() {
        return processusConsultatifDao.findAll();
    }

    @Override
    public ProcessusConsultatif getProcessusConsultatifById(Long id) {
        return processusConsultatifDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Processus Consultatif not found with id: " + id));
    }

    @Override
    public List<ProcessusConsultatif> getProcessusConsultatifsByCommune(Long communeId) {
        return processusConsultatifDao.findByCommuneId(communeId);
    }

    @Override
    public ProcessusConsultatif createProcessusConsultatif(ProcessusConsultatifDTO processusConsultatifDTO) {
        ProcessusConsultatif processus = new ProcessusConsultatif();
        mapDtoToEntity(processusConsultatifDTO, processus);
        return processusConsultatifDao.save(processus);
    }

    @Override
    public ProcessusConsultatif updateProcessusConsultatif(Long id, ProcessusConsultatifDTO processusConsultatifDTO) {
        ProcessusConsultatif processus = getProcessusConsultatifById(id);
        mapDtoToEntity(processusConsultatifDTO, processus);
        return processusConsultatifDao.save(processus);
    }

    @Override
    public void deleteProcessusConsultatif(Long id) {
        ProcessusConsultatif processus = getProcessusConsultatifById(id);
        processusConsultatifDao.delete(processus);
    }

    private void mapDtoToEntity(ProcessusConsultatifDTO dto, ProcessusConsultatif processus) {
        processus.setNom(dto.getNom());
        processus.setDate(dto.getDate());
        processus.setCreateBy(dto.getCreateBy());
        
        if (dto.getCommuneId() != null) {
            Commune commune = communeDao.findById(dto.getCommuneId())
                    .orElseThrow(() -> new RuntimeException("Commune not found with id: " + dto.getCommuneId()));
            processus.setCommune(commune);
        }
    }
}
