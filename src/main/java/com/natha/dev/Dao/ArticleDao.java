package com.natha.dev.Dao;

import com.natha.dev.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleDao extends JpaRepository<Article, Long> {

    /**
     * Jwenn atik pa code
     */
    Optional<Article> findByCode(String code);

    /**
     * Jwenn atik pa code ak tenant
     */
    Optional<Article> findByCodeAndTenantId(String code, String tenantId);

    /**
     * Jwenn atik pa nom
     */
    List<Article> findByNom(String nom);

    /**
     * Jwenn atik pa nom ak tenant
     */
    List<Article> findByNomAndTenantId(String nom, String tenantId);

    /**
     * Jwenn atik pa kategori
     */
    List<Article> findByCategorie(String categorie);

    /**
     * Jwenn atik pa kategori ak tenant
     */
    List<Article> findByCategorieAndTenantId(String categorie, String tenantId);

    /**
     * Jwenn atik aktif
     */
    List<Article> findByActif(Boolean actif);

    /**
     * Jwenn atik aktif ak tenant
     */
    List<Article> findByActifAndTenantId(Boolean actif, String tenantId);

    /**
     * Jwenn tout atik pou yon tenant
     */
    List<Article> findByTenantId(String tenantId);

    /**
     * Jwenn atik pa marque
     */
    List<Article> findByMarque(String marque);

    /**
     * Jwenn atik pa marque ak tenant
     */
    List<Article> findByMarqueAndTenantId(String marque, String tenantId);
}
