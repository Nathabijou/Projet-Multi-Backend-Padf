package com.natha.dev.Dao;

import com.natha.dev.Model.Chapitre;
import com.natha.dev.Model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapitreDao extends JpaRepository<Chapitre, String> {
    
    /**
     * Jwenn lis tout chapit ki nan yon fòmasyon
     * @param formation Fòmasyon pou chèche chapit yo
     * @return Lis chapit yo nan lòd yo
     */
    List<Chapitre> findByFormationOrderByOrdre(Formation formation);
    
    /**
     * Jwenn lis tout chapit ki nan yon fòmasyon (pa ID fòmasyon an)
     * @param formationId ID fòmasyon an
     * @return Lis chapit yo nan lòd yo
     */
    List<Chapitre> findByFormationIdFormationOrderByOrdre(String formationId);
    
    /**
     * Tcheke si yon chapit ekziste deja nan yon fòmasyon ak menm tit la
     * @param formation Fòmasyon an
     * @param tit Tit chapit la
     * @return boolean ki endike si chapit la ekziste deja
     */
    boolean existsByFormationAndTitre(Formation formation, String tit);
}
