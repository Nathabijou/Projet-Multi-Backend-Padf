package com.natha.dev.IService;

import com.natha.dev.Dto.RencontreDto;
import com.natha.dev.Model.Rencontre;

import java.util.List;

public interface IRencontreService {
    List<Rencontre> findAll();
    Rencontre findById(Long id);
    List<Rencontre> findByProcessusConsultatifId(Long processusConsultatifId);
    Rencontre save(RencontreDto rencontreDto);
    Rencontre update(Long id, RencontreDto rencontreDto);
    void delete(Long id);
}
