package com.natha.dev.Dao;

import com.natha.dev.Model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationDao extends JpaRepository<Evaluation, String> {
    Optional<Evaluation> findById(String id);
}
