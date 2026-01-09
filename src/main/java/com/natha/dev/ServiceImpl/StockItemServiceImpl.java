package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.StockItemDao;
import com.natha.dev.Dao.MouvementStockDao;
import com.natha.dev.Dao.StockDao;
import com.natha.dev.Dao.ArticleDao;
import com.natha.dev.Dto.StockItemDTO;
import com.natha.dev.Dto.MouvementStockDTO;
import com.natha.dev.IService.IStockItemService;
import com.natha.dev.Model.StockItem;
import com.natha.dev.Model.MouvementStock;
import com.natha.dev.Model.Stock;
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
public class StockItemServiceImpl implements IStockItemService {

    @Autowired
    private StockItemDao stockItemDao;

    @Autowired
    private MouvementStockDao mouvementStockDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public StockItemDTO createStockItem(StockItemDTO stockItemDTO) {
        Optional<Stock> stock = stockDao.findById(stockItemDTO.getStockId());
        Optional<Article> article = articleDao.findById(stockItemDTO.getArticleId());

        if (stock.isPresent() && article.isPresent()) {
            StockItem stockItem = new StockItem();
            stockItem.setStock(stock.get());
            stockItem.setArticle(article.get());
            stockItem.setQuantiteEntree(stockItemDTO.getQuantiteEntree());
            stockItem.setQuantiteSortie(stockItemDTO.getQuantiteSortie());
            stockItem.setQuantiteActuelle(stockItemDTO.getQuantiteEntree() - stockItemDTO.getQuantiteSortie());
            stockItem.setSeuilMinimum(stockItemDTO.getSeuilMinimum());
            stockItem.setEtat(stockItemDTO.getEtat());
            stockItem.setPrixMoyen(article.get().getPrixMoyen());
            stockItem.setValeurTotale(stockItem.getQuantiteActuelle() * stockItem.getPrixMoyen());
            stockItem.setCommentaire(stockItemDTO.getCommentaire());
            stockItem.setTenantId(getTenantId());
            stockItem.setDateCreation(LocalDateTime.now());
            stockItem.setDerniereMiseAJour(LocalDateTime.now());

            StockItem savedStockItem = stockItemDao.save(stockItem);
            return convertToDTO(savedStockItem);
        }
        return null;
    }

    @Override
    public StockItemDTO updateStockItem(Long id, StockItemDTO stockItemDTO) {
        Optional<StockItem> stockItemOptional = stockItemDao.findById(id);
        if (stockItemOptional.isPresent()) {
            StockItem stockItem = stockItemOptional.get();
            stockItem.setQuantiteEntree(stockItemDTO.getQuantiteEntree());
            stockItem.setQuantiteSortie(stockItemDTO.getQuantiteSortie());
            stockItem.setSeuilMinimum(stockItemDTO.getSeuilMinimum());
            stockItem.setEtat(stockItemDTO.getEtat());
            stockItem.setCommentaire(stockItemDTO.getCommentaire());
            stockItem.setDerniereMiseAJour(LocalDateTime.now());

            StockItem updatedStockItem = stockItemDao.save(stockItem);
            return convertToDTO(updatedStockItem);
        }
        return null;
    }

    @Override
    public Optional<StockItemDTO> getStockItemById(Long id) {
        return stockItemDao.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<StockItemDTO> getStockItemsByStockId(Long stockId) {
        return stockItemDao.findByStockIdAndTenantId(stockId, getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockItemDTO> getStockItemsByArticleId(Long articleId) {
        return stockItemDao.findByArticleIdAndTenantId(articleId, getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StockItemDTO> getStockItemByStockAndArticle(Long stockId, Long articleId) {
        return stockItemDao.findByStockIdAndArticleIdAndTenantId(stockId, articleId, getTenantId())
                .map(this::convertToDTO);
    }

    @Override
    public List<StockItemDTO> getStockItemsByEtat(String etat) {
        return stockItemDao.findByEtatAndTenantId(etat, getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockItemDTO> getAllStockItems() {
        return stockItemDao.findByTenantId(getTenantId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockItemDTO> getLowStockItems() {
        List<StockItem> lowStockItems = stockItemDao.findByTenantId(getTenantId()).stream()
                .filter(item -> item.getQuantiteActuelle() < item.getSeuilMinimum())
                .collect(Collectors.toList());
        return lowStockItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StockItemDTO addEntree(Long stockItemId, Double quantite, Double prixUnitaire, String description, Long createdBy) {
        Optional<StockItem> stockItemOptional = stockItemDao.findById(stockItemId);
        if (stockItemOptional.isPresent()) {
            StockItem stockItem = stockItemOptional.get();
            stockItem.setQuantiteEntree(stockItem.getQuantiteEntree() + quantite);
            stockItem.setQuantiteActuelle(stockItem.getQuantiteEntree() - stockItem.getQuantiteSortie());
            stockItem.setValeurTotale(stockItem.getQuantiteActuelle() * stockItem.getPrixMoyen());
            stockItem.setDerniereMiseAJour(LocalDateTime.now());

            // Kreye mouvman
            MouvementStock mouvement = new MouvementStock();
            mouvement.setStockItem(stockItem);
            mouvement.setTypeMouvement("ENTREE");
            mouvement.setQuantite(quantite);
            mouvement.setPrixUnitaire(prixUnitaire);
            mouvement.setDescription(description);
            mouvement.setCreatedBy(createdBy);
            mouvement.setTenantId(getTenantId());
            mouvement.setDateMouvement(LocalDateTime.now());
            mouvementStockDao.save(mouvement);

            StockItem updatedStockItem = stockItemDao.save(stockItem);
            return convertToDTO(updatedStockItem);
        }
        return null;
    }

    @Override
    public StockItemDTO addSortie(Long stockItemId, Double quantite, String description, Long createdBy) {
        Optional<StockItem> stockItemOptional = stockItemDao.findById(stockItemId);
        if (stockItemOptional.isPresent()) {
            StockItem stockItem = stockItemOptional.get();
            stockItem.setQuantiteSortie(stockItem.getQuantiteSortie() + quantite);
            stockItem.setQuantiteActuelle(stockItem.getQuantiteEntree() - stockItem.getQuantiteSortie());
            stockItem.setValeurTotale(stockItem.getQuantiteActuelle() * stockItem.getPrixMoyen());
            stockItem.setDerniereMiseAJour(LocalDateTime.now());

            // Kreye mouvman
            MouvementStock mouvement = new MouvementStock();
            mouvement.setStockItem(stockItem);
            mouvement.setTypeMouvement("SORTIE");
            mouvement.setQuantite(quantite);
            mouvement.setDescription(description);
            mouvement.setCreatedBy(createdBy);
            mouvement.setTenantId(getTenantId());
            mouvement.setDateMouvement(LocalDateTime.now());
            mouvementStockDao.save(mouvement);

            StockItem updatedStockItem = stockItemDao.save(stockItem);
            return convertToDTO(updatedStockItem);
        }
        return null;
    }

    @Override
    public StockItemDTO addAjustement(Long stockItemId, Double quantite, String description, Long createdBy) {
        Optional<StockItem> stockItemOptional = stockItemDao.findById(stockItemId);
        if (stockItemOptional.isPresent()) {
            StockItem stockItem = stockItemOptional.get();
            stockItem.setQuantiteActuelle(quantite);
            stockItem.setValeurTotale(stockItem.getQuantiteActuelle() * stockItem.getPrixMoyen());
            stockItem.setDerniereMiseAJour(LocalDateTime.now());

            // Kreye mouvman
            MouvementStock mouvement = new MouvementStock();
            mouvement.setStockItem(stockItem);
            mouvement.setTypeMouvement("AJUSTEMENT");
            mouvement.setQuantite(quantite);
            mouvement.setDescription(description);
            mouvement.setCreatedBy(createdBy);
            mouvement.setTenantId(getTenantId());
            mouvement.setDateMouvement(LocalDateTime.now());
            mouvementStockDao.save(mouvement);

            StockItem updatedStockItem = stockItemDao.save(stockItem);
            return convertToDTO(updatedStockItem);
        }
        return null;
    }

    @Override
    public List<MouvementStockDTO> getMouvementsByStockItem(Long stockItemId) {
        return mouvementStockDao.findByStockItemIdAndTenantId(stockItemId, getTenantId()).stream()
                .map(this::convertMouvementToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStockItem(Long id) {
        stockItemDao.deleteById(id);
    }

    /**
     * Konveti StockItem a StockItemDTO
     */
    private StockItemDTO convertToDTO(StockItem stockItem) {
        StockItemDTO dto = new StockItemDTO();
        dto.setId(stockItem.getId());
        dto.setStockId(stockItem.getStock().getId());
        dto.setArticleId(stockItem.getArticle().getId());
        dto.setArticleCode(stockItem.getArticle().getCode());
        dto.setArticleNom(stockItem.getArticle().getNom());
        dto.setQuantiteEntree(stockItem.getQuantiteEntree());
        dto.setQuantiteSortie(stockItem.getQuantiteSortie());
        dto.setQuantiteActuelle(stockItem.getQuantiteActuelle());
        dto.setSeuilMinimum(stockItem.getSeuilMinimum());
        dto.setValeurTotale(stockItem.getValeurTotale());
        dto.setPrixMoyen(stockItem.getPrixMoyen());
        dto.setDerniereMiseAJour(stockItem.getDerniereMiseAJour());
        dto.setDateCreation(stockItem.getDateCreation());
        dto.setEtat(stockItem.getEtat());
        dto.setCommentaire(stockItem.getCommentaire());
        return dto;
    }

    /**
     * Konveti MouvementStock a MouvementStockDTO
     */
    private MouvementStockDTO convertMouvementToDTO(MouvementStock mouvement) {
        MouvementStockDTO dto = new MouvementStockDTO();
        dto.setId(mouvement.getId());
        dto.setStockItemId(mouvement.getStockItem().getId());
        dto.setTypeMouvement(mouvement.getTypeMouvement());
        dto.setQuantite(mouvement.getQuantite());
        dto.setPrixUnitaire(mouvement.getPrixUnitaire());
        dto.setDescription(mouvement.getDescription());
        dto.setDateMouvement(mouvement.getDateMouvement());
        dto.setCreatedBy(mouvement.getCreatedBy());
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
