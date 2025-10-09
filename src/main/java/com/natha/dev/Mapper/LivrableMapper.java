package com.natha.dev.Mapper;

import com.natha.dev.Dto.LivrableDto;
import com.natha.dev.Model.Livrable;
import com.natha.dev.Model.StatutLivrable;
import org.springframework.stereotype.Component;

@Component
public class LivrableMapper {

    /**
     * Kalkile valè 'à faire' la kòm (requis - réalisés)
     * @param requis Valè total ki nesesè
     * @param realises Valè ki deja reyalize
     * @return Valè ki rete pou fè (pa janm negatif)
     */
    private int calculerAFaire(int requis, int realises) {
        return Math.max(0, requis - realises);
    }

    public LivrableDto toDto(Livrable livrable) {
        if (livrable == null) {
            return null;
        }

        LivrableDto dto = new LivrableDto();
        dto.setIdLivrable(livrable.getIdLivrable());
        dto.setNom(livrable.getNom());
        dto.setDescription(livrable.getDescription());
        dto.setDate(livrable.getDate());
        dto.setProjetRequis(livrable.getProjetRequis());
        dto.setProjetsRealises(livrable.getProjetsRealises());
        dto.setProjetAFaire(livrable.getProjetAFaire());
        dto.setFormationTechniqueRequis(livrable.getFormationTechniqueRequis());
        dto.setFormationTechniqueRealises(livrable.getFormationTechniqueRealises());
        dto.setFormationTechniqueAFaire(livrable.getFormationTechniqueAFaire());
        dto.setFormationSocioRequis(livrable.getFormationSocioRequis());
        dto.setFormationSocioRealises(livrable.getFormationSocioRealises());
        dto.setFormationSocioAFaire(livrable.getFormationSocioAFaire());
        dto.setComposanteId(livrable.getComposante() != null ? livrable.getComposante().getId() : null);
        dto.setCreatedBy(livrable.getCreatedBy());
        dto.setCreatedAt(livrable.getCreatedAt());
        dto.setUpdatedBy(livrable.getUpdatedBy());
        dto.setUpdatedAt(livrable.getUpdatedAt());
        dto.setStatut(livrable.getStatut() != null ? livrable.getStatut().name() : null);

        return dto;
    }

    public Livrable toEntity(LivrableDto dto) {
        if (dto == null) {
            return null;
        }

        Livrable livrable = new Livrable();
        livrable.setIdLivrable(dto.getIdLivrable());
        livrable.setNom(dto.getNom());
        livrable.setDescription(dto.getDescription());
        livrable.setDate(dto.getDate());
        // Pwojè yo
        livrable.setProjetRequis(dto.getProjetRequis());
        livrable.setProjetsRealises(dto.getProjetsRealises());
        livrable.setProjetAFaire(calculerAFaire(dto.getProjetRequis(), dto.getProjetsRealises()));

        // Fòmasyon teknik
        livrable.setFormationTechniqueRequis(dto.getFormationTechniqueRequis());
        livrable.setFormationTechniqueRealises(dto.getFormationTechniqueRealises());
        livrable.setFormationTechniqueAFaire(calculerAFaire(dto.getFormationTechniqueRequis(), dto.getFormationTechniqueRealises()));

        // Fòmasyon sosyo
        livrable.setFormationSocioRequis(dto.getFormationSocioRequis());
        livrable.setFormationSocioRealises(dto.getFormationSocioRealises());
        livrable.setFormationSocioAFaire(calculerAFaire(dto.getFormationSocioRequis(), dto.getFormationSocioRealises()));

        // Set statut si li egziste
        if (dto.getStatut() != null) {
            try {
                livrable.setStatut(StatutLivrable.valueOf(dto.getStatut()));
            } catch (IllegalArgumentException e) {
                livrable.setStatut(StatutLivrable.EN_COURS); // Valeur par défaut si invalide
            }
        } else {
            livrable.setStatut(StatutLivrable.EN_COURS); // Valeur par défaut
        }

        // Note: composante, createdBy, updatedBy, etc. yo dwe mete nan service layer

        return livrable;
    }
}
