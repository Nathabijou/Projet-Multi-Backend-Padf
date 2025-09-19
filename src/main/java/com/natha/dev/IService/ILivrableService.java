package com.natha.dev.IService;

import com.natha.dev.Dto.LivrableDto;

import java.util.List;

public interface ILivrableService {
    LivrableDto createLivrable(LivrableDto livrableDto, String username);
    LivrableDto getLivrableById(Long id);
    List<LivrableDto> getLivrablesByComposante(Long composanteId);
    LivrableDto updateLivrable(Long id, LivrableDto livrableDto, String username);
    void deleteLivrable(Long id);
}
