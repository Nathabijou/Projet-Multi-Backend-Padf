package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ComposanteDao;
import com.natha.dev.Dao.LivrableDao;
import com.natha.dev.Dto.LivrableDto;
import com.natha.dev.IService.ILivrableService;
import com.natha.dev.Model.Composante;
import com.natha.dev.Model.Livrable;
import com.natha.dev.Model.StatutLivrable;
import jakarta.persistence.EntityNotFoundException;
import com.natha.dev.Mapper.LivrableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LivrableServiceImpl implements ILivrableService {

    @Autowired
    private LivrableDao livrableDao;

    @Autowired
    private ComposanteDao composanteRepository;

    @Autowired
    private LivrableMapper livrableMapper;

    /**
     * Met à jour le statut d'un livrable en fonction de son avancement
     * @param livrable Le livrable à mettre à jour
     */
    private void updateStatut(Livrable livrable) {
        if (livrable == null) return;

        // Vérifier si tous les champs requis sont complets
        boolean isComplete = livrable.getProjetsRealises() >= livrable.getProjetRequis() &&
                livrable.getFormationTechniqueRealises() >= livrable.getFormationTechniqueRequis() &&
                livrable.getFormationSocioRealises() >= livrable.getFormationSocioRequis();

        // Mete ajou statu a
        if (isComplete) {
            livrable.setStatut(StatutLivrable.TERMINE);
        } else if (livrable.getProjetsRealises() > 0 ||
                livrable.getFormationTechniqueRealises() > 0 ||
                livrable.getFormationSocioRealises() > 0) {
            livrable.setStatut(StatutLivrable.EN_COURS);
        } else {
            livrable.setStatut(StatutLivrable.EN_ATTENTE);
        }
    }

    @Override
    public LivrableDto createLivrable(LivrableDto livrableDto, String username) {
        // Kreye livrab la
        Livrable livrable = new Livrable();
        livrable.setNom(livrableDto.getNom());
        livrable.setDate(livrableDto.getDate());

        // Mete ajou pwojè yo
        livrable.setProjetRequis(livrableDto.getProjetRequis());
        livrable.setProjetsRealises(livrableDto.getProjetsRealises());
        livrable.setProjetAFaire(Math.max(0, livrableDto.getProjetRequis() - livrableDto.getProjetsRealises()));

        // Mete ajou fòmasyon teknik yo
        livrable.setFormationTechniqueRequis(livrableDto.getFormationTechniqueRequis());
        livrable.setFormationTechniqueRealises(livrableDto.getFormationTechniqueRealises());
        livrable.setFormationTechniqueAFaire(Math.max(0, livrableDto.getFormationTechniqueRequis() - livrableDto.getFormationTechniqueRealises()));

        // Mete ajou fòmasyon sosyo yo
        livrable.setFormationSocioRequis(livrableDto.getFormationSocioRequis());
        livrable.setFormationSocioRealises(livrableDto.getFormationSocioRealises());
        livrable.setFormationSocioAFaire(Math.max(0, livrableDto.getFormationSocioRequis() - livrableDto.getFormationSocioRealises()));

        // Set composante
        Composante composante = composanteRepository.findById(livrableDto.getComposanteId())
                .orElseThrow(() -> new EntityNotFoundException("Composante not found with id: " + livrableDto.getComposanteId()));
        livrable.setComposante(composante);

        // Mete ajou statu a
        updateStatut(livrable);

        // Set champs audit
        livrable.setCreatedBy(username);

        Livrable savedLivrable = livrableDao.save(livrable);
        return livrableMapper.toDto(savedLivrable);
    }

    @Override
    public LivrableDto getLivrableById(Long id) {
        Livrable livrable = livrableDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livrable not found with id: " + id));
        return livrableMapper.toDto(livrable);
    }

    @Override
    public List<LivrableDto> getLivrablesByComposante(Long composanteId) {
        List<Livrable> livrables = livrableDao.findByComposanteId(composanteId);
        return livrables.stream()
                .map(livrableMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LivrableDto updateLivrable(Long id, LivrableDto livrableDto, String username) {
        Livrable existingLivrable = livrableDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livrable not found with id: " + id));

        // Mete ajou champs ki soti nan DTO a
        existingLivrable.setNom(livrableDto.getNom());
        existingLivrable.setDate(livrableDto.getDate());

        // Mete ajou champs pwojè yo
        existingLivrable.setProjetRequis(livrableDto.getProjetRequis());
        existingLivrable.setProjetsRealises(livrableDto.getProjetsRealises());
        existingLivrable.setProjetAFaire(Math.max(0, livrableDto.getProjetRequis() - livrableDto.getProjetsRealises()));

        // Mete ajou fòmasyon teknik yo
        existingLivrable.setFormationTechniqueRequis(livrableDto.getFormationTechniqueRequis());
        existingLivrable.setFormationTechniqueRealises(livrableDto.getFormationTechniqueRealises());
        existingLivrable.setFormationTechniqueAFaire(Math.max(0, livrableDto.getFormationTechniqueRequis() - livrableDto.getFormationTechniqueRealises()));

        // Mete ajou fòmasyon sosyo yo
        existingLivrable.setFormationSocioRequis(livrableDto.getFormationSocioRequis());
        existingLivrable.setFormationSocioRealises(livrableDto.getFormationSocioRealises());
        existingLivrable.setFormationSocioAFaire(Math.max(0, livrableDto.getFormationSocioRequis() - livrableDto.getFormationSocioRealises()));

        // Mete ajou composante si li chanje
        if (livrableDto.getComposanteId() != null &&
                (existingLivrable.getComposante() == null ||
                        !existingLivrable.getComposante().getId().equals(livrableDto.getComposanteId()))) {
            Composante composante = composanteRepository.findById(livrableDto.getComposanteId())
                    .orElseThrow(() -> new EntityNotFoundException("Composante not found with id: " + livrableDto.getComposanteId()));
            existingLivrable.setComposante(composante);
        }

        // Mete ajou statu a
        updateStatut(existingLivrable);

        // Mete ajou champs d'audit
        existingLivrable.setUpdatedBy(username);

        Livrable updatedLivrable = livrableDao.save(existingLivrable);
        return livrableMapper.toDto(updatedLivrable);
    }

    @Override
    public void deleteLivrable(Long id) {
        Livrable livrable = livrableDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livrable not found with id: " + id));
        livrableDao.delete(livrable);
    }
}
