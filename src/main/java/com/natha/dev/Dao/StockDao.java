package com.natha.dev.Dao;

import com.natha.dev.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockDao extends JpaRepository<Stock, Long> {

    /**
     * Jwenn stock pa non
     */
    List<Stock> findByName(String name);

    /**
     * Jwenn stock pa non ak tenant
     */
    List<Stock> findByNameAndTenantId(String name, String tenantId);

    /**
     * Jwenn stock pa tip
     */
    List<Stock> findByType(Stock.StockType type);

    /**
     * Jwenn stock pa tip ak tenant
     */
    List<Stock> findByTypeAndTenantId(Stock.StockType type, String tenantId);

    /**
     * Jwenn stock pa status
     */
    List<Stock> findByStatus(Stock.StockStatus status);

    /**
     * Jwenn stock pa status ak tenant
     */
    List<Stock> findByStatusAndTenantId(Stock.StockStatus status, String tenantId);

    /**
     * Jwenn stock pou yon composante
     */
    List<Stock> findByComposanteId(Long composanteId);

    /**
     * Jwenn stock pou yon composante ak tenant
     */
    List<Stock> findByComposanteIdAndTenantId(Long composanteId, String tenantId);

    /**
     * Jwenn stock pou yon projet
     */
    List<Stock> findByProjetId(String projetId);

    /**
     * Jwenn stock pou yon projet ak tenant
     */
    List<Stock> findByProjetIdAndTenantId(String projetId, String tenantId);

    /**
     * Jwenn tout stock pou yon tenant
     */
    List<Stock> findByTenantId(String tenantId);

    /**
     * Jwenn stock pa manager
     */
    List<Stock> findByManagerId(Long managerId);

    /**
     * Jwenn stock pa manager ak tenant
     */
    List<Stock> findByManagerIdAndTenantId(Long managerId, String tenantId);

    /**
     * Verifye si stock egziste pa non ak tenant
     */
    boolean existsByNameAndTenantId(String name, String tenantId);

    /**
     * Jwenn stock pa id ak tenant
     */
    Optional<Stock> findByIdAndTenantId(Long id, String tenantId);
}
