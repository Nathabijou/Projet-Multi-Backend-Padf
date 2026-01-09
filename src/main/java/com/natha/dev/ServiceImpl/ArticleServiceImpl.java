package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ArticleDao;
import com.natha.dev.Dto.ArticleDTO;
import com.natha.dev.IService.IArticleService;
import com.natha.dev.Model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setCode(articleDTO.getCode());
        article.setNom(articleDTO.getNom());
        article.setMarque(articleDTO.getMarque());
        article.setModele(articleDTO.getModele());
        article.setDescription(articleDTO.getDescription());
        article.setCategorie(articleDTO.getCategorie());
        article.setUnite(articleDTO.getUnite());
        article.setPoidsVolume(articleDTO.getPoidsVolume());
        article.setPrixUnitaire(articleDTO.getPrixUnitaire());
        article.setPrixMoyen(articleDTO.getPrixMoyen());
        article.setDevise(articleDTO.getDevise());
        article.setImageUrl(articleDTO.getImageUrl());
        article.setActif(articleDTO.getActif() != null ? articleDTO.getActif() : true);
        article.setCreatedBy(articleDTO.getCreatedBy());
        article.setUpdatedBy(articleDTO.getUpdatedBy());
        article.setTenantId(getTenantId());
        article.setCreateDate(LocalDateTime.now());
        article.setUpdateDate(LocalDateTime.now());

        Article savedArticle = articleDao.save(article);
        return convertToDTO(savedArticle);
    }

    @Override
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
        Optional<Article> articleOptional = articleDao.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            article.setNom(articleDTO.getNom());
            article.setMarque(articleDTO.getMarque());
            article.setModele(articleDTO.getModele());
            article.setDescription(articleDTO.getDescription());
            article.setCategorie(articleDTO.getCategorie());
            article.setUnite(articleDTO.getUnite());
            article.setPoidsVolume(articleDTO.getPoidsVolume());
            article.setPrixUnitaire(articleDTO.getPrixUnitaire());
            article.setPrixMoyen(articleDTO.getPrixMoyen());
            article.setDevise(articleDTO.getDevise());
            article.setImageUrl(articleDTO.getImageUrl());
            article.setActif(articleDTO.getActif());
            article.setUpdatedBy(articleDTO.getUpdatedBy());
            article.setUpdateDate(LocalDateTime.now());

            Article updatedArticle = articleDao.save(article);
            return convertToDTO(updatedArticle);
        }
        return null;
    }

    @Override
    public Optional<ArticleDTO> getArticleById(Long id) {
        return articleDao.findById(id).map(this::convertToDTO);
    }

    @Override
    public Optional<ArticleDTO> getArticleByCode(String code) {
        return articleDao.findByCodeAndTenantId(code, getTenantId()).map(this::convertToDTO);
    }

    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleDao.findByTenantId(getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByNom(String nom) {
        return articleDao.findByNomAndTenantId(nom, getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByCategorie(String categorie) {
        return articleDao.findByCategorieAndTenantId(categorie, getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getActiveArticles() {
        return articleDao.findByActifAndTenantId(true, getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByMarque(String marque) {
        return articleDao.findByMarqueAndTenantId(marque, getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArticle(Long id) {
        articleDao.deleteById(id);
    }

    @Override
    public ArticleDTO toggleArticleStatus(Long id) {
        Optional<Article> articleOptional = articleDao.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            article.setActif(!article.getActif());
            article.setUpdateDate(LocalDateTime.now());
            Article updatedArticle = articleDao.save(article);
            return convertToDTO(updatedArticle);
        }
        return null;
    }

    /**
     * Konveti Article a ArticleDTO
     */
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setCode(article.getCode());
        dto.setNom(article.getNom());
        dto.setMarque(article.getMarque());
        dto.setModele(article.getModele());
        dto.setDescription(article.getDescription());
        dto.setCategorie(article.getCategorie());
        dto.setUnite(article.getUnite());
        dto.setPoidsVolume(article.getPoidsVolume());
        dto.setPrixUnitaire(article.getPrixUnitaire());
        dto.setPrixMoyen(article.getPrixMoyen());
        dto.setDevise(article.getDevise());
        dto.setImageUrl(article.getImageUrl());
        dto.setActif(article.getActif());
        dto.setCreateDate(article.getCreateDate());
        dto.setUpdateDate(article.getUpdateDate());
        dto.setCreatedBy(article.getCreatedBy());
        dto.setUpdatedBy(article.getUpdatedBy());
        return dto;
    }

    /**
     * Jwenn tenant ID soti nan security context
     */
    private String getTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return username != null ? username : "default-tenant";
        }
        return "default-tenant";
    }
}
