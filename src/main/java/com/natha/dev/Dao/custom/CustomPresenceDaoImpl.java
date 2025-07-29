package com.natha.dev.Dao.custom;

import com.natha.dev.Dto.BeneficiaireModuleDto;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomPresenceDaoImpl implements CustomPresenceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BeneficiaireModuleDto> findBeneficiairesByModuleId(String moduleId) {
        String query = """
            SELECT NEW com.natha.dev.Dto.BeneficiaireModuleDto(
                b.idBeneficiaire,
                b.nom,
                b.prenom,
                b.telephoneContact,
                CASE WHEN p.idPresence IS NOT NULL THEN true ELSE false END
            )
            FROM Module m
            JOIN m.chapitre c
            JOIN c.formation f
            JOIN ProjetBeneficiaireFormation pbf ON pbf.formation.idFormation = f.idFormation
            JOIN pbf.projetBeneficiaire pb
            JOIN pb.beneficiaire b
            LEFT JOIN Presence p ON p.projetBeneficiaireFormation.id = pbf.id 
                AND p.projetBeneficiaire.idProjetBeneficiaire = pb.idProjetBeneficiaire
                AND p.datePresence = CURRENT_DATE
            WHERE m.idModule = :moduleId
            ORDER BY b.nom, b.prenom
            """;
            
        return entityManager.createQuery(query, BeneficiaireModuleDto.class)
                          .setParameter("moduleId", moduleId)
                          .getResultList();
    }
}
