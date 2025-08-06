package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.EntrepriseDao;
import com.natha.dev.Dto.EntrepriseDto;
import com.natha.dev.IService.IEntrepriseService;
import com.natha.dev.Model.Entreprise;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EntrepriseServiceImpl implements IEntrepriseService {

    private final EntrepriseDao entrepriseDao;

    @Autowired
    public EntrepriseServiceImpl(EntrepriseDao entrepriseDao) {
        this.entrepriseDao = entrepriseDao;
    }

    @Override
    public List<Entreprise> findAll() {
        return entrepriseDao.findAll();
    }

    @Override
    public Entreprise findById(Long id) {
        return entrepriseDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrise not found with ID: " + id));
    }

    @Override
    public Entreprise create(EntrepriseDto entrepriseDto) {
        if (entrepriseDao.existsByNom(entrepriseDto.getNom())) {
            throw new IllegalStateException("Enterprise already exists");
        }

        Entreprise entreprise = new Entreprise();
        BeanUtils.copyProperties(entrepriseDto, entreprise);
        entreprise.setCreateDate(LocalDateTime.now());
        
        return entrepriseDao.save(entreprise);
    }

    @Override
    public Entreprise update(Long id, EntrepriseDto entrepriseDto) {
        Entreprise existingEntreprise = findById(id);
        
        // Pa pèmèt chanje non an si li deja egziste pou yon lòt antrepriz
        if (!existingEntreprise.getNom().equals(entrepriseDto.getNom()) && 
            entrepriseDao.existsByNom(entrepriseDto.getNom())) {
            throw new IllegalStateException("Entreprise already exists with name: " + entrepriseDto.getNom());
        }
        
        existingEntreprise.setNom(entrepriseDto.getNom());
        existingEntreprise.setDescription(entrepriseDto.getDescription());
        existingEntreprise.setUpdateDate(LocalDateTime.now());
        
        return entrepriseDao.save(existingEntreprise);
    }

    @Override
    public void delete(Long id) {
        if (!entrepriseDao.existsById(id)) {
            throw new EntityNotFoundException("Entrise not found with ID: " + id);
        }
        entrepriseDao.deleteById(id);
    }
}
