package com.natha.dev.IService;

import com.natha.dev.Dto.StockItemDTO;
import com.natha.dev.Dto.MouvementStockDTO;
import java.util.List;
import java.util.Optional;

public interface IStockItemService {

    /**
     * Kreye yon stock item
     */
    StockItemDTO createStockItem(StockItemDTO stockItemDTO);

    /**
     * Mete a jou yon stock item
     */
    StockItemDTO updateStockItem(Long id, StockItemDTO stockItemDTO);

    /**
     * Jwenn stock item pa id
     */
    Optional<StockItemDTO> getStockItemById(Long id);

    /**
     * Jwenn stock item pa stock id
     */
    List<StockItemDTO> getStockItemsByStockId(Long stockId);

    /**
     * Jwenn stock item pa article id
     */
    List<StockItemDTO> getStockItemsByArticleId(Long articleId);

    /**
     * Jwenn stock item pa stock ak article
     */
    Optional<StockItemDTO> getStockItemByStockAndArticle(Long stockId, Long articleId);

    /**
     * Jwenn stock item pa etat
     */
    List<StockItemDTO> getStockItemsByEtat(String etat);

    /**
     * Jwenn tout stock item
     */
    List<StockItemDTO> getAllStockItems();

    /**
     * Jwenn stock item kote quantite < seuil minimum
     */
    List<StockItemDTO> getLowStockItems();

    /**
     * Ajoute mouvman entree
     */
    StockItemDTO addEntree(Long stockItemId, Double quantite, Double prixUnitaire, String description, Long createdBy);

    /**
     * Ajoute mouvman sortie
     */
    StockItemDTO addSortie(Long stockItemId, Double quantite, String description, Long createdBy);

    /**
     * Ajoute mouvman ajusteman
     */
    StockItemDTO addAjustement(Long stockItemId, Double quantite, String description, Long createdBy);

    /**
     * Jwenn mouvman pou yon stock item
     */
    List<MouvementStockDTO> getMouvementsByStockItem(Long stockItemId);

    /**
     * Efase yon stock item
     */
    void deleteStockItem(Long id);
}
