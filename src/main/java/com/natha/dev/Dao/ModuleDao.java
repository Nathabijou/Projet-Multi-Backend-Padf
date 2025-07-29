package com.natha.dev.Dao;

import com.natha.dev.Model.Chapitre;
import com.natha.dev.Model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleDao extends JpaRepository<Module, String> {
    
    /**
     * Jwenn lis tout modil ki nan yon chapit
     * @param chapit Chapit pou chèche modil yo
     * @return Lis modil yo nan lòd yo
     */
    List<Module> findByChapitreOrderByOrdre(Chapitre chapit);
    
    /**
     * Jwenn lis tout modil ki nan yon chapit (pa ID chapit la)
     * @param chapitreId ID chapit la
     * @return Lis modil yo nan lòd yo
     */
    List<Module> findByChapitreIdChapitreOrderByOrdre(String chapitreId);
    
    /**
     * Tcheke si yon modil ekziste deja nan yon chapit ak menm tit la
     * @param chapit Chapit la
     * @param tit Tit modil la
     * @return boolean ki endike si modil la ekziste deja
     */
    boolean existsByChapitreAndTitre(Chapitre chapit, String tit);
    
    /**
     * Konte konbyen modil ki nan yon chapit
     * @param chapitId ID chapit la
     * @return Kantite modil nan chapit la
     */
    long countByChapitreIdChapitre(String chapitId);
}
