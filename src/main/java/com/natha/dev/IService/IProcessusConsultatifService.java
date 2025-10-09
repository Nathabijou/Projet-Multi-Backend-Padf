package com.natha.dev.IService;

import com.natha.dev.Dto.ProcessusConsultatifDTO;
import com.natha.dev.Model.ProcessusConsultatif;

import java.util.List;

public interface IProcessusConsultatifService {
    List<ProcessusConsultatif> getAllProcessusConsultatifs();
    ProcessusConsultatif getProcessusConsultatifById(Long id);
    List<ProcessusConsultatif> getProcessusConsultatifsByCommune(Long communeId);
    ProcessusConsultatif createProcessusConsultatif(ProcessusConsultatifDTO processusConsultatifDTO);
    ProcessusConsultatif updateProcessusConsultatif(Long id, ProcessusConsultatifDTO processusConsultatifDTO);
    void deleteProcessusConsultatif(Long id);
}
