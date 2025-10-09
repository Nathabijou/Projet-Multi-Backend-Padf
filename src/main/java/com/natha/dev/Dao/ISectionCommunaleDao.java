package com.natha.dev.Dao;

import com.natha.dev.Model.SectionCommunale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISectionCommunaleDao extends JpaRepository<SectionCommunale, Long> {
    List<SectionCommunale> findAllById(Iterable<Long> ids);
}
