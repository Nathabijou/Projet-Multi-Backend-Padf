package com.natha.dev.IService;

import com.natha.dev.Dto.EntrepriseDto;
import com.natha.dev.Model.Entreprise;

import java.util.List;

public interface IEntrepriseService {
    List<Entreprise> findAll();
    Entreprise findById(Long id);
    Entreprise create(EntrepriseDto entrepriseDto);
    Entreprise update(Long id, EntrepriseDto entrepriseDto);
    void delete(Long id);
}
