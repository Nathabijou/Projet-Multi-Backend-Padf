package com.natha.dev.Dao;

import com.natha.dev.Model.Beneficiaire;
import org.springframework.data.jpa.repository.EntityGraph;
import com.natha.dev.Model.ProjetBeneficiaireEntreprise;
import com.natha.dev.Model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetBeneficiaireEntrepriseDao extends JpaRepository<ProjetBeneficiaireEntreprise, Long> {
    
    @Query("SELECT pbe FROM ProjetBeneficiaireEntreprise pbe " +
           "JOIN FETCH pbe.entreprise e " +
           "WHERE pbe.projetBeneficiaire.idProjetBeneficiaire = :projetBeneficiaireId")
    List<ProjetBeneficiaireEntreprise> findByProjetBeneficiaireId(@Param("projetBeneficiaireId") String projetBeneficiaireId);
    
    @Query("SELECT pbe FROM ProjetBeneficiaireEntreprise pbe " +
           "JOIN FETCH pbe.projetBeneficiaire pb " +
           "WHERE pbe.entreprise.id = :entrepriseId")
    List<ProjetBeneficiaireEntreprise> findByEntrepriseId(@Param("entrepriseId") Long entrepriseId);
    
    @Query("SELECT pbe FROM ProjetBeneficiaireEntreprise pbe " +
           "JOIN FETCH pbe.projetBeneficiaire pb " +
           "JOIN FETCH pb.beneficiaire b " +
           "WHERE pbe.entreprise.id = :entrepriseId")
    List<ProjetBeneficiaireEntreprise> findByEntrepriseIdWithBeneficiaire(@Param("entrepriseId") Long entrepriseId);
    
    @Query("SELECT DISTINCT pbe.entreprise FROM ProjetBeneficiaireEntreprise pbe " +
           "JOIN pbe.projetBeneficiaire pb " +
           "WHERE pb.projet.idProjet = :projetId")
    List<Entreprise> findEntreprisesByProjetId(@Param("projetId") String projetId);
    
    /**
     * Tcheke si yon relasyon ant yon pwojè benefisyè ak yon antrepriz deja egziste
     * @param projetBeneficiaireId ID pwojè benefisyè a
     * @param entrepriseId ID antrepriz la
     * @return true si relasyon an egziste, otreman false
     */
    @Query("SELECT CASE WHEN COUNT(pbe) > 0 THEN true ELSE false END " +
           "FROM ProjetBeneficiaireEntreprise pbe " +
           "WHERE pbe.projetBeneficiaire.idProjetBeneficiaire = :projetBeneficiaireId " +
           "AND pbe.entreprise.id = :entrepriseId")
    boolean existsByProjetBeneficiaireIdAndEntrepriseId(
            @Param("projetBeneficiaireId") String projetBeneficiaireId,
            @Param("entrepriseId") Long entrepriseId);
            
    @Query("SELECT DISTINCT b FROM ProjetBeneficiaireEntreprise pbe " +
           "JOIN pbe.projetBeneficiaire pb " +
           "JOIN pb.beneficiaire b " +
           "JOIN pbe.entreprise e " +
           "WHERE e.id = :entrepriseId")
    @EntityGraph(attributePaths = {"projetBeneficiaire.beneficiaire"})
    List<Beneficiaire> findBeneficiairesByEntrepriseId(@Param("entrepriseId") Long entrepriseId);
}
