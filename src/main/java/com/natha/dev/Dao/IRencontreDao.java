package com.natha.dev.Dao;

import com.natha.dev.Model.Rencontre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRencontreDao extends JpaRepository<Rencontre, Long> {
    List<Rencontre> findByProcessusConsultatifId(Long processusConsultatifId);
}
