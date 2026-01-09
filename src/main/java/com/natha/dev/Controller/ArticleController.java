package com.natha.dev.Controller;

import com.natha.dev.Dto.ArticleDTO;
import com.natha.dev.IService.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    /**
     * Kreye yon atik
     */
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    /**
     * Mete a jou yon atik
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDTO);
        if (updatedArticle != null) {
            return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Jwenn atik pa id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Optional<ArticleDTO> article = articleService.getArticleById(id);
        if (article.isPresent()) {
            return new ResponseEntity<>(article.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Jwenn atik pa code
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<ArticleDTO> getArticleByCode(@PathVariable String code) {
        Optional<ArticleDTO> article = articleService.getArticleByCode(code);
        if (article.isPresent()) {
            return new ResponseEntity<>(article.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Jwenn tout atik
     */
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * Jwenn atik pa nom
     */
    @GetMapping("/search/nom")
    public ResponseEntity<List<ArticleDTO>> getArticlesByNom(@RequestParam String nom) {
        List<ArticleDTO> articles = articleService.getArticlesByNom(nom);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * Jwenn atik pa kategori
     */
    @GetMapping("/search/categorie")
    public ResponseEntity<List<ArticleDTO>> getArticlesByCategorie(@RequestParam String categorie) {
        List<ArticleDTO> articles = articleService.getArticlesByCategorie(categorie);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * Jwenn atik aktif
     */
    @GetMapping("/search/actif")
    public ResponseEntity<List<ArticleDTO>> getActiveArticles() {
        List<ArticleDTO> articles = articleService.getActiveArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * Jwenn atik pa marque
     */
    @GetMapping("/search/marque")
    public ResponseEntity<List<ArticleDTO>> getArticlesByMarque(@RequestParam String marque) {
        List<ArticleDTO> articles = articleService.getArticlesByMarque(marque);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    /**
     * Efase yon atik
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Chanje status atik (aktif/inaktif)
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ArticleDTO> toggleArticleStatus(@PathVariable Long id) {
        ArticleDTO updatedArticle = articleService.toggleArticleStatus(id);
        if (updatedArticle != null) {
            return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
