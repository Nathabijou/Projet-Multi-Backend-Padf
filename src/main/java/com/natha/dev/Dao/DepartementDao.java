package com.natha.dev.Dao;

import com.natha.dev.Model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartementDao extends JpaRepository<Departement, Long> {


    @Query("SELECT zd.departement FROM ZoneDepartement zd WHERE zd.zone.id = :zoneId")
    List<Departement> findByZoneId(@Param("zoneId") Long zoneId);

}
