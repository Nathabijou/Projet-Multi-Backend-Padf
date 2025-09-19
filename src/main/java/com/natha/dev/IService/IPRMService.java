package com.natha.dev.IService;

import com.natha.dev.Dto.PRMAfaireDTO;
import com.natha.dev.Model.PRMRequis;
import com.natha.dev.Model.PRMRealise;

import java.util.List;
import java.util.Optional;

public interface IPRMService {
    // Operasyon sou PRMRequis
    PRMRequis saveRequis(PRMRequis prmRequis);
    List<PRMRequis> getAllRequis();

    void deleteRequisById(Long id);

    PRMRequis getRequisById(Long id);

    // Operasyon sou PRMRealise
    PRMRealise saveRealise(PRMRealise prmRealise);
    List<PRMRealise> getAllRealise();

    void deleteRealiseById(Long id);

    PRMRealise getRealiseById(Long id);

    // Operasyon kalkil PRMAfaire
    Optional<PRMAfaireDTO> getAFaire(String projetId, Long quartierId);

    // Jwenn lis PRMRequis pa ID pwojè ak ID katye
    List<PRMRequis> getRequisByProjetIdAndQuartierId(String projetId, Long quartierId);

    // Jwenn lis PRMRealise pa ID pwojè ak ID katye
    List<PRMRealise> getRealiseByProjetIdAndQuartierId(String projetId, Long quartierId);
}
