package com.natha.dev.IService;

import com.natha.dev.Dto.PersonneDto;
import com.natha.dev.Model.Personne;

import java.util.List;

public interface IPersonneService {
    List<Personne> findAll();
    Personne findById(Long id);
    Personne save(PersonneDto personneDto);
    Personne update(Long id, PersonneDto personneDto);
    void delete(Long id);
    List<Personne> findByRencontreId(Long rencontreId);
}
