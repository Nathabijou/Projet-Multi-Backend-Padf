package com.natha.dev.Dao;

import com.natha.dev.Model.ZoneDepartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneDepartementDao extends JpaRepository<ZoneDepartement, Long> {
    // Ou ka ajoute metòd koutim isit la alavni si w bezwen
}
