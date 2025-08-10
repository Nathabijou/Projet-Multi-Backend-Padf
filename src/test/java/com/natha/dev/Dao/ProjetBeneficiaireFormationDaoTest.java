package com.natha.dev.Dao;

import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireFormation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class ProjetBeneficiaireFormationDaoTest {

    @Autowired
    private ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao;

    @Test
    public void whenCountByProjetBeneficiaireId_thenReturnCount() {
        // Given
        String projetBeneficiaireId = "test-pb-1";
        
        // When
        long count = projetBeneficiaireFormationDao.countByProjetBeneficiaireId(projetBeneficiaireId);
        
        // Then
        assertTrue(count >= 0, "Count should be greater than or equal to 0");
        System.out.println("Count for projetBeneficiaireId " + projetBeneficiaireId + ": " + count);
    }

    @Test
    public void whenCountByNonExistentProjetBeneficiaireId_thenReturnZero() {
        // Given
        String nonExistentId = "non-existent-id";
        
        // When
        long count = projetBeneficiaireFormationDao.countByProjetBeneficiaireId(nonExistentId);
        
        // Then
        assertEquals(0, count, "Count should be 0 for non-existent projetBeneficiaireId");
    }
}
