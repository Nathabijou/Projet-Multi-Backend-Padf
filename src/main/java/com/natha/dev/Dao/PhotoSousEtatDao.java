package com.natha.dev.Dao;

import com.natha.dev.Model.PhotoSousEtat;
import com.natha.dev.Model.SousEtatAvancement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoSousEtatDao extends JpaRepository<PhotoSousEtat, String> {
    List<PhotoSousEtat> findBySousEtatAvancementId(String sousEtatAvancementId);
    List<PhotoSousEtat> findBySousEtatAvancementIdAndEstPhotoPrincipaleTrue(String sousEtatAvancementId);
    void deleteBySousEtatAvancementId(String sousEtatAvancementId);
}
