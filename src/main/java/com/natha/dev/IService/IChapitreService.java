package com.natha.dev.IService;

import com.natha.dev.Dto.ChapitreCreationDto;
import com.natha.dev.Dto.ChapitreDto;

import java.util.List;

public interface IChapitreService {
    
    /**
     * Jwenn lis tout chapit ki nan yon fòmasyon
     * @param formationId ID fòmasyon an
     * @return Lis chapit yo nan lòd yo
     */
    List<ChapitreDto> getChapitresByFormationId(String formationId);
    
    /**
     * Kreye yon nouvo chapit nan yon fòmasyon
     * @param formationId ID fòmasyon an
     * @param chapitreDto Done pou kreye chapit la
     * @return Chapit ki fèk kreye a
     */
    ChapitreDto createChapitre(String formationId, ChapitreCreationDto chapitreDto);
    
    /**
     * Kreye plizyè chapit nan yon fòmasyon
     * @param formationId ID fòmasyon an
     * @param chapitresDto Lis done pou kreye chapit yo
     * @return Lis chapit ki fèk kreye yo
     */
    List<ChapitreDto> createChapitres(String formationId, List<ChapitreCreationDto> chapitresDto);
}
