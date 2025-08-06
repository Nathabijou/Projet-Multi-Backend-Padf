package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ChapitreDao;
import com.natha.dev.Dao.FormationDao;
import com.natha.dev.Dto.ChapitreCreationDto;
import com.natha.dev.Dto.ChapitreDto;
import com.natha.dev.IService.IChapitreService;
import com.natha.dev.Mapper.ChapitreMapper;
import com.natha.dev.Model.Chapitre;
import com.natha.dev.Model.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChapitreServiceImpl implements IChapitreService {

    private final ChapitreDao chapitreDao;
    private final FormationDao formationDao;
    private final ChapitreMapper chapitreMapper;

    @Autowired
    public ChapitreServiceImpl(ChapitreDao chapitreDao, 
                             FormationDao formationDao,
                             ChapitreMapper chapitreMapper) {
        this.chapitreDao = chapitreDao;
        this.formationDao = formationDao;
        this.chapitreMapper = chapitreMapper;
    }

    @Override
    public List<ChapitreDto> getChapitresByFormationId(String formationId) {
        // Jwenn tout chapit yo nan lòd yo
        List<Chapitre> chapitres = chapitreDao.findByFormationIdFormationOrderByOrdre(formationId);
        
        // Konvèti chak chapit an ChapitreDto epi kolekte yo nan yon lis
        return chapitres.stream()
                .map(chapitreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChapitreDto createChapitre(String formationId, ChapitreCreationDto chapitreDto) {
        // Jwenn fòmasyon an
        Formation formation = formationDao.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Fòmasyon pa jwenn ak ID: " + formationId));
        
        // Kreye nouvo chapit la
        Chapitre chapitre = new Chapitre();
        chapitre.setTitre(chapitreDto.getTitre());
        chapitre.setDescription(chapitreDto.getDescription());
        chapitre.setOrdre(chapitreDto.getOrdre());
        chapitre.setCreatedBy(chapitreDto.getCreatedBy());
        chapitre.setFormation(formation);
        
        // Sove chapit la
        Chapitre savedChapitre = chapitreDao.save(chapitre);
        
        // Retounen chapit la kòm DTO
        return chapitreMapper.toDto(savedChapitre);
    }

    @Override
    public List<ChapitreDto> createChapitres(String formationId, List<ChapitreCreationDto> chapitresDto) {
        // Jwenn fòmasyon an
        Formation formation = formationDao.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Fòmasyon pa jwenn ak ID: " + formationId));
        
        // Kreye chak chapit epi kolekte yo nan yon lis
        List<Chapitre> chapitres = chapitresDto.stream()
                .map(dto -> {
                    Chapitre chapitre = new Chapitre();
                    chapitre.setTitre(dto.getTitre());
                    chapitre.setDescription(dto.getDescription());
                    chapitre.setOrdre(dto.getOrdre());
                    chapitre.setCreatedBy(dto.getCreatedBy());
                    chapitre.setFormation(formation);
                    return chapitre;
                })
                .collect(Collectors.toList());
        
        // Sove tout chapit yo
        List<Chapitre> savedChapitres = chapitreDao.saveAll(chapitres);
        
        // Retounen lis chapit yo kòm DTO
        return savedChapitres.stream()
                .map(chapitreMapper::toDto)
                .collect(Collectors.toList());
    }
}
