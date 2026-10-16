package com.natha.dev.IService;

import com.natha.dev.Dto.ProgressionDTO;

import java.util.List;
import java.util.Optional;

public interface ProgressionIService {
    ProgressionDTO save(ProgressionDTO dto);
    List<ProgressionDTO> findAll();
    Optional<ProgressionDTO> findById(String idProgression);
    ProgressionDTO update(String idProgression, ProgressionDTO dto);
    void deleteById(String idProgression);
    List<ProgressionDTO> findByProjetId(String projetId);
}
