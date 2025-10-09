package com.natha.dev.Dao;

import com.natha.dev.Model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersonneDao extends JpaRepository<Personne, Long> {
    
    @Query("SELECT DISTINCT p FROM Personne p JOIN p.rencontres r WHERE r.id = :rencontreId")
    List<Personne> findByRencontres_Id(@Param("rencontreId") Long rencontreId);
}
