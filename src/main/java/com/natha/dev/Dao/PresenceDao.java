package com.natha.dev.Dao;

import com.natha.dev.Dao.custom.CustomPresenceDao;
import com.natha.dev.Model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresenceDao extends JpaRepository<Presence, String>, CustomPresenceDao {
    List<Presence> findByProjetBeneficiaireIdProjetBeneficiaire(String idProjetBeneficiaire);

    List<Presence> findByProjetBeneficiaireFormationId(String id);
}
