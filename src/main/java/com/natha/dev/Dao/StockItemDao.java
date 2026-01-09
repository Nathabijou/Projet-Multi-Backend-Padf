package com.natha.dev.Dao;

import com.natha.dev.Model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockItemDao extends JpaRepository<StockItem, Long> {

    /**
     * Jwenn stock item pa stock id
     */
    List<StockItem> findByStockId(Long stockId);

    /**
     * Jwenn stock item pa stock id ak tenant
     */
    List<StockItem> findByStockIdAndTenantId(Long stockId, String tenantId);

    /**
     * Jwenn stock item pa article id
     */
    List<StockItem> findByArticleId(Long articleId);

    /**
     * Jwenn stock item pa article id ak tenant
     */
    List<StockItem> findByArticleIdAndTenantId(Long articleId, String tenantId);

    /**
     * Jwenn stock item pa stock ak article
     */
    Optional<StockItem> findByStockIdAndArticleId(Long stockId, Long articleId);

    /**
     * Jwenn stock item pa stock, article ak tenant
     */
    Optional<StockItem> findByStockIdAndArticleIdAndTenantId(Long stockId, Long articleId, String tenantId);

    /**
     * Jwenn stock item pa etat
     */
    List<StockItem> findByEtat(String etat);

    /**
     * Jwenn stock item pa etat ak tenant
     */
    List<StockItem> findByEtatAndTenantId(String etat, String tenantId);

    /**
     * Jwenn tout stock item pou yon tenant
     */
    List<StockItem> findByTenantId(String tenantId);

    /**
     * Jwenn stock item kote quantiteActuelle < seuilMinimum
     */
    List<StockItem> findByQuantiteActuelleLessThanAndTenantId(Double quantiteActuelle, String tenantId);
}
