package com.natha.dev.Dao;

import com.natha.dev.Model.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MouvementStockDao extends JpaRepository<MouvementStock, Long> {

    /**
     * Jwenn mouvman pa stock item id
     */
    List<MouvementStock> findByStockItemId(Long stockItemId);

    /**
     * Jwenn mouvman pa stock item id ak tenant
     */
    List<MouvementStock> findByStockItemIdAndTenantId(Long stockItemId, String tenantId);

    /**
     * Jwenn mouvman pa type
     *
     */
    List<MouvementStock> findByTypeMouvement(String typeMouvement);

    /**
     * Jwenn mouvman pa type ak tenant
     */
    List<MouvementStock> findByTypeMouvementAndTenantId(String typeMouvement, String tenantId);

    /**
     * Jwenn mouvman ant de dat
     */
    List<MouvementStock> findByDateMouvementBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Jwenn mouvman ant de dat ak tenant
     */
    List<MouvementStock> findByDateMouvementBetweenAndTenantId(LocalDateTime startDate, LocalDateTime endDate, String tenantId);

    /**
     * Jwenn tout mouvman pou yon tenant
     */
    List<MouvementStock> findByTenantId(String tenantId);
}
