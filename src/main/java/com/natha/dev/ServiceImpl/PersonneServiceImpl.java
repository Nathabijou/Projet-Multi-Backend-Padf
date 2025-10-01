package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.IPersonneDao;
import com.natha.dev.Dao.IRencontreDao;
import com.natha.dev.Dto.PersonneDto;
import com.natha.dev.IService.IPersonneService;
import com.natha.dev.Model.Personne;
import com.natha.dev.Model.Rencontre;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PersonneServiceImpl implements IPersonneService {

    @Autowired
    private IPersonneDao personneDao;
    
    @Autowired
    private IRencontreDao rencontreDao;

    @Override
    public List<Personne> findAll() {
        return personneDao.findAll();
    }

    @Override
    public Personne findById(Long id) {
        return personneDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personne not found with id: " + id));
    }

    @Override
    public Personne save(PersonneDto personneDto) {
        Personne personne = mapDtoToEntity(personneDto, new Personne());
        
        // Sauvegarder d'abord la personne sans les rencontres
        Personne savedPersonne = personneDao.save(personne);
        
        // Gestion des rencontres
        if (personneDto.getRencontreIds() != null && !personneDto.getRencontreIds().isEmpty()) {
            Set<Rencontre> rencontres = new HashSet<>();
            for (Long rencontreId : personneDto.getRencontreIds()) {
                Rencontre rencontre = rencontreDao.findById(rencontreId)
                    .orElseThrow(() -> new EntityNotFoundException("Rencontre not found with id: " + rencontreId));
                
                // Ajouter la personne à la rencontre (côté propriétaire de la relation)
                rencontre.getParticipants().add(savedPersonne);
                rencontreDao.save(rencontre);
                
                rencontres.add(rencontre);
            }
            savedPersonne.setRencontres(rencontres);
            savedPersonne = personneDao.save(savedPersonne);
        }
        
        return savedPersonne;
    }

    @Override
    public Personne update(Long id, PersonneDto personneDto) {
        Personne existingPersonne = findById(id);
        mapDtoToEntity(personneDto, existingPersonne);
        
        // Mise à jour des rencontres
        if (personneDto.getRencontreIds() != null) {
            // D'abord, retirer la personne de toutes ses anciennes rencontres
            for (Rencontre oldRencontre : existingPersonne.getRencontres()) {
                oldRencontre.getParticipants().remove(existingPersonne);
                rencontreDao.save(oldRencontre);
            }
            
            // Ensuite, ajouter la personne aux nouvelles rencontres
            Set<Rencontre> nouvellesRencontres = new HashSet<>();
            for (Long rencontreId : personneDto.getRencontreIds()) {
                Rencontre rencontre = rencontreDao.findById(rencontreId)
                    .orElseThrow(() -> new EntityNotFoundException("Rencontre not found with id: " + rencontreId));
                
                // Ajouter la personne à la rencontre (côté propriétaire de la relation)
                rencontre.getParticipants().add(existingPersonne);
                rencontreDao.save(rencontre);
                
                nouvellesRencontres.add(rencontre);
            }
            existingPersonne.setRencontres(nouvellesRencontres);
        }
        
        return personneDao.save(existingPersonne);
    }

    @Override
    public void delete(Long id) {
        Personne personne = findById(id);
        personneDao.delete(personne);
    }

    @Override
    public List<Personne> findByRencontreId(Long rencontreId) {
        return personneDao.findByRencontres_Id(rencontreId);
    }

    private Personne mapDtoToEntity(PersonneDto dto, Personne entity) {
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setSexe(dto.getSexe());
        entity.setAdresse(dto.getAdresse());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setInstitution(dto.getInstitution());
        entity.setTypePersonne(dto.getTypePersonne());
        entity.setIdentification(dto.getIdentification());

        // Handle rencontres if needed
        if (dto.getRencontreIds() != null && !dto.getRencontreIds().isEmpty()) {
            Set<Rencontre> rencontres = new HashSet<>(rencontreDao.findAllById(dto.getRencontreIds()));
            entity.setRencontres(rencontres);
        }

        return entity;
    }
}
