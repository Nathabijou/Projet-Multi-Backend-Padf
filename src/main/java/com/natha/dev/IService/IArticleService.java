package com.natha.dev.IService;

import com.natha.dev.Dto.ArticleDTO;
import java.util.List;
import java.util.Optional;

public interface IArticleService {

    /**
     * Kreye yon atik
     */
    ArticleDTO createArticle(ArticleDTO articleDTO);

    /**
     * Mete a jou yon atik
     */
    ArticleDTO updateArticle(Long id, ArticleDTO articleDTO);

    /**
     * Jwenn atik pa id
     */
    Optional<ArticleDTO> getArticleById(Long id);

    /**
     * Jwenn atik pa code
     */
    Optional<ArticleDTO> getArticleByCode(String code);

    /**
     * Jwenn tout atik
     */
    List<ArticleDTO> getAllArticles();

    /**
     * Jwenn atik pa nom
     */
    List<ArticleDTO> getArticlesByNom(String nom);

    /**
     * Jwenn atik pa kategori
     */
    List<ArticleDTO> getArticlesByCategorie(String categorie);

    /**
     * Jwenn atik aktif
     */
    List<ArticleDTO> getActiveArticles();

    /**
     * Jwenn atik pa marque
     */
    List<ArticleDTO> getArticlesByMarque(String marque);

    /**
     * Efase yon atik
     */
    void deleteArticle(Long id);

    /**
     * Chanje status atik (aktif/inaktif)
     */
    ArticleDTO toggleArticleStatus(Long id);
}
