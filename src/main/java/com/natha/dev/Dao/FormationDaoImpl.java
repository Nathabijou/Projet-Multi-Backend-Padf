package com.natha.dev.Dao;

import com.natha.dev.Dto.ChapitreStatsDto;
import com.natha.dev.Model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class FormationDaoImpl implements FormationDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ChapitreStatsDto> getChapitreStatsWithFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChapitreStatsDto> cq = cb.createQuery(ChapitreStatsDto.class);

        // Root entities
        Root<Chapitre> chapitre = cq.from(Chapitre.class);
        Join<Chapitre, Formation> formation = chapitre.join("formation");

        // Subquery for female count
        Subquery<Long> femaleCount = cq.subquery(Long.class);
        Root<ChapitreBeneficiaire> cbF = femaleCount.from(ChapitreBeneficiaire.class);
        Join<ChapitreBeneficiaire, Beneficiaire> bF = cbF.join("beneficiaire");
        Join<ChapitreBeneficiaire, Chapitre> chapF = cbF.join("chapitre");

        femaleCount.select(cb.countDistinct(cbF.get("id").get("beneficiaireId")))
                .where(
                        cb.equal(chapF.get("idChapitre"), chapitre.get("idChapitre")),
                        cb.equal(bF.get("sexe"), 'F'),
                        buildPredicatesForBeneficiaire(cb, cbF, composanteId, zoneId, departementId,
                                arrondissementId, communeId, sectionId, quartierId, projetId)
                );

        // Subquery for male count
        Subquery<Long> maleCount = cq.subquery(Long.class);
        Root<ChapitreBeneficiaire> cbM = maleCount.from(ChapitreBeneficiaire.class);
        Join<ChapitreBeneficiaire, Beneficiaire> bM = cbM.join("beneficiaire");
        Join<ChapitreBeneficiaire, Chapitre> chapM = cbM.join("chapitre");

        maleCount.select(cb.countDistinct(cbM.get("id").get("beneficiaireId")))
                .where(
                        cb.equal(chapM.get("idChapitre"), chapitre.get("idChapitre")),
                        cb.equal(bM.get("sexe"), 'M'),
                        buildPredicatesForBeneficiaire(cb, cbM, composanteId, zoneId, departementId,
                                arrondissementId, communeId, sectionId, quartierId, projetId)
                );

        // Subquery for completed count
        Subquery<Long> completedCount = cq.subquery(Long.class);
        Root<ChapitreBeneficiaire> cbC = completedCount.from(ChapitreBeneficiaire.class);
        Join<ChapitreBeneficiaire, Beneficiaire> bC = cbC.join("beneficiaire");
        Join<ChapitreBeneficiaire, Chapitre> chapC = cbC.join("chapitre");

        completedCount.select(cb.countDistinct(cbC.get("id").get("beneficiaireId")))
                .where(
                        cb.equal(chapC.get("idChapitre"), chapitre.get("idChapitre")),
                        cb.isTrue(cbC.get("isCompleted")),
                        buildPredicatesForBeneficiaire(cb, cbC, composanteId, zoneId, departementId,
                                arrondissementId, communeId, sectionId, quartierId, projetId)
                );

        // Main query
        cq.select(cb.construct(
                ChapitreStatsDto.class,
                chapitre.get("idChapitre"),
                chapitre.get("titre"),
                cb.coalesce(cb.nullif(femaleCount, 0L), 0L),
                cb.coalesce(cb.nullif(maleCount, 0L), 0L),
                cb.coalesce(cb.nullif(completedCount, 0L), 0L)
        ));

        // Add group by
        cq.groupBy(chapitre.get("idChapitre"), chapitre.get("titre"));

        TypedQuery<ChapitreStatsDto> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    private Predicate buildPredicatesForBeneficiaire(
            CriteriaBuilder cb,
            Root<ChapitreBeneficiaire> cbRoot,
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId) {

        List<Predicate> predicates = new ArrayList<>();

        // Join with ProjetBeneficiaireFormation
        Join<ChapitreBeneficiaire, Beneficiaire> b = cbRoot.join("beneficiaire");
        Join<Beneficiaire, ProjetBeneficiaire> pb = b.join("projetBeneficiaires");
        Join<ProjetBeneficiaire, Projet> p = pb.join("projet");

        // Add filters
        if (projetId != null) {
            predicates.add(cb.equal(p.get("idProjet"), projetId));
        }

        if (composanteId != null) {
            predicates.add(cb.equal(p.get("composante").get("id"), composanteId));
        }

        // Add other filters as needed...

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public long countBeneficiariesBySexeQualificationAndFormation(
            String sexe, boolean isQualified, Long composanteId, Long zoneId,
            Long departementId, Long arrondissementId, Long communeId,
            Long sectionId, Long quartierId, String projetId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        // Root entity
        Root<ProjetBeneficiaireFormation> pbf = cq.from(ProjetBeneficiaireFormation.class);

        // Joins
        Join<ProjetBeneficiaireFormation, ProjetBeneficiaire> pb = pbf.join("projetBeneficiaire");
        Join<ProjetBeneficiaire, Beneficiaire> b = pb.join("beneficiaire");
        Join<ProjetBeneficiaire, Projet> p = pb.join("projet");

        // Left joins for location filters
        Join<Projet, Quartier> q = p.join("quartier", JoinType.LEFT);
        Join<Quartier, SectionCommunale> sc = q.join("sectionCommunale", JoinType.LEFT);
        Join<SectionCommunale, Commune> comm = sc.join("commune", JoinType.LEFT);
        Join<Commune, Arrondissement> a = comm.join("arrondissement", JoinType.LEFT);
        Join<Arrondissement, Departement> d = a.join("departement", JoinType.LEFT);
        Join<Departement, ZoneDepartement> zd = d.join("zoneDepartements", JoinType.LEFT);
        Join<ZoneDepartement, Zone> z = zd.join("zone", JoinType.LEFT);

        // Build predicates
        List<Predicate> predicates = new ArrayList<>();

        // Base predicates
        predicates.add(cb.equal(b.get("sexe"), sexe));

        // Qualification predicate
        if (isQualified) {
            predicates.add(cb.isNotNull(pbf.get("noteFinale")));
            predicates.add(cb.greaterThanOrEqualTo(pbf.get("noteFinale"), 70.0));
        } else {
            predicates.add(cb.or(
                    cb.isNull(pbf.get("noteFinale")),
                    cb.lessThan(pbf.get("noteFinale"), 70.0)
            ));
        }

        // Location filters
        if (composanteId != null) {
            predicates.add(cb.equal(p.get("composante").get("id"), composanteId));
        }
        if (zoneId != null) {
            predicates.add(cb.equal(z.get("id"), zoneId));
        }
        if (departementId != null) {
            predicates.add(cb.equal(d.get("id"), departementId));
        }
        if (arrondissementId != null) {
            predicates.add(cb.equal(a.get("id"), arrondissementId));
        }
        if (communeId != null) {
            predicates.add(cb.equal(comm.get("id"), communeId));
        }
        if (sectionId != null) {
            predicates.add(cb.equal(sc.get("id"), sectionId));
        }
        if (quartierId != null) {
            predicates.add(cb.equal(q.get("id"), quartierId));
        }
        if (projetId != null) {
            predicates.add(cb.equal(p.get("idProjet"), projetId));
        }

        // Build query
        cq.select(cb.countDistinct(pbf.get("formation")));
        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getSingleResult();
    }
}
