package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.IProcessusConsultatifDao;
import com.natha.dev.Dao.IRencontreDao;
import com.natha.dev.Dao.ISectionCommunaleDao;
import com.natha.dev.Dto.RencontreDto;
import com.natha.dev.IService.IRencontreService;
import com.natha.dev.Model.ProcessusConsultatif;
import com.natha.dev.Model.Rencontre;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.natha.dev.Model.SectionCommunale;

@Service
@Transactional
public class RencontreServiceImpl implements IRencontreService {

    @Autowired
    private IRencontreDao rencontreDao;

    @Autowired
    private IProcessusConsultatifDao processusConsultatifDao;
    
    @Autowired
    private ISectionCommunaleDao sectionCommunaleDao;

    @Override
    public List<Rencontre> findAll() {
        return rencontreDao.findAll();
    }

    @Override
    public Rencontre findById(Long id) {
        return rencontreDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rencontre not found with id: " + id));
    }

    @Override
    public List<Rencontre> findByProcessusConsultatifId(Long processusConsultatifId) {
        return rencontreDao.findByProcessusConsultatifId(processusConsultatifId);
    }

    @Override
    public Rencontre save(RencontreDto rencontreDto) {
        ProcessusConsultatif processusConsultatif = processusConsultatifDao.findById(rencontreDto.getProcessusConsultatifId())
                .orElseThrow(() -> new EntityNotFoundException("ProcessusConsultatif not found with id: " + rencontreDto.getProcessusConsultatifId()));

        Rencontre rencontre = new Rencontre();
        mapDtoToEntity(rencontreDto, rencontre);
        
        // Gestion des sections communales
        if (rencontreDto.getSectionCommunaleIds() != null && !rencontreDto.getSectionCommunaleIds().isEmpty()) {
            Set<SectionCommunale> sectionsCommunales = new HashSet<>(
                sectionCommunaleDao.findAllById(rencontreDto.getSectionCommunaleIds())
            );
            rencontre.setSectionsCommunales(sectionsCommunales);
        }
        
        // Set tracking fields
        Date now = new Date();
        rencontre.setCreateDate(now);
        rencontre.setLastModifyDate(now);
        // You might want to get the current user from Spring Security here
        // String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        // rencontre.setCreateBy(currentUser);
        // rencontre.setLastModifyBy(currentUser);
        
        rencontre.setProcessusConsultatif(processusConsultatif);
        
        return rencontreDao.save(rencontre);
    }

    @Override
    public Rencontre update(Long id, RencontreDto rencontreDto) {
        Rencontre rencontre = findById(id);
        mapDtoToEntity(rencontreDto, rencontre);
        
        // Mise à jour des sections communales
        if (rencontreDto.getSectionCommunaleIds() != null) {
            Set<SectionCommunale> sectionsCommunales = new HashSet<>(
                sectionCommunaleDao.findAllById(rencontreDto.getSectionCommunaleIds())
            );
            rencontre.setSectionsCommunales(sectionsCommunales);
        }
        
        // Update tracking fields
        Date now = new Date();
        rencontre.setLastModifyDate(now);
        // You might want to get the current user from Spring Security here
        // String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        // rencontre.setLastModifyBy(currentUser);
        
        if (!rencontre.getProcessusConsultatif().getId().equals(rencontreDto.getProcessusConsultatifId())) {
            ProcessusConsultatif newProcessus = processusConsultatifDao.findById(rencontreDto.getProcessusConsultatifId())
                    .orElseThrow(() -> new EntityNotFoundException("ProcessusConsultatif not found with id: " + rencontreDto.getProcessusConsultatifId()));
            rencontre.setProcessusConsultatif(newProcessus);
        }
        
        return rencontreDao.save(rencontre);
    }

    @Override
    public void delete(Long id) {
        Rencontre rencontre = findById(id);
        rencontreDao.delete(rencontre);
    }

    private void mapDtoToEntity(RencontreDto dto, Rencontre entity) {
        entity.setNom(dto.getNom());
        entity.setDate(dto.getDate());
        entity.setPointDiscussion(dto.getPointDiscussion());
        entity.setObjectif(dto.getObjectif());
        entity.setDescription(dto.getDescription());
    }
}
