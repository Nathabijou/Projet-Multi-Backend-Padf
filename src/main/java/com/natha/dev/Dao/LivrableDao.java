package com.natha.dev.Dao;

import com.natha.dev.Model.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivrableDao extends JpaRepository<Livrable, Long> {
    List<Livrable> findByComposanteId(Long composanteId);
}
