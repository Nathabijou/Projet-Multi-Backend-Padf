package com.natha.dev.ServiceImpl;

import com.natha.dev.Dto.PRMAfaireDTO;
import com.natha.dev.IService.IPRMService;
import com.natha.dev.Model.PRMRealise;
import com.natha.dev.Model.PRMRequis;
import com.natha.dev.Dao.PRMRealiseDao;
import com.natha.dev.Dao.PRMRequisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PRMServiceImpl implements IPRMService {

    private final PRMRequisDao prmRequisDao;
    private final PRMRealiseDao prmRealiseDao;

    @Autowired
    public PRMServiceImpl(PRMRequisDao prmRequisDao, PRMRealiseDao prmRealiseDao) {
        this.prmRequisDao = prmRequisDao;
        this.prmRealiseDao = prmRealiseDao;
    }

    @Override
    public PRMRequis saveRequis(PRMRequis prmRequis) {
        return prmRequisDao.save(prmRequis);
    }

    @Override
    public List<PRMRequis> getAllRequis() {
        return prmRequisDao.findAll();
    }

    @Override
    public void deleteRequisById(Long id) {
        prmRequisDao.deleteById(id);
    }

    @Override
    public PRMRequis getRequisById(Long id) {
        return prmRequisDao.findById(id)
                .orElseThrow(() -> new RuntimeException("PRMRequis pa jwenn ak ID: " + id));
    }

    @Override
    public PRMRealise saveRealise(PRMRealise prmRealise) {
        return prmRealiseDao.save(prmRealise);
    }

    @Override
    public List<PRMRealise> getAllRealise() {
        return prmRealiseDao.findAll();
    }

    @Override
    public void deleteRealiseById(Long id) {
        prmRealiseDao.deleteById(id);
    }

    @Override
    public PRMRealise getRealiseById(Long id) {
        return prmRealiseDao.findById(id)
                .orElseThrow(() -> new RuntimeException("PRMRealise pa jwenn ak ID: " + id));
    }

    @Override
    public List<PRMRequis> getRequisByProjetIdAndQuartierId(String projetId, Long quartierId) {
        return prmRequisDao.findByProjetIdAndQuartierId(projetId, quartierId);
    }

    @Override
    public List<PRMRealise> getRealiseByProjetIdAndQuartierId(String projetId, Long quartierId) {
        return prmRealiseDao.findByProjetIdAndQuartierId(projetId, quartierId);
    }

    @Override
    public Optional<PRMAfaireDTO> getAFaire(String projetId, Long quartierId) {
        // Jwenn PRMRequis ki koresponn ak pwojè ak katye a
        PRMRequis requis = prmRequisDao.findByProjetIdAndQuartierId(projetId, quartierId)
                .stream()
                .findFirst()
                .orElse(null);

        if (requis == null) {
            return Optional.empty();
        }

        // Jwenn PRMRealise ki koresponn ak pwojè ak katye a
        PRMRealise realise = prmRealiseDao.findByProjetIdAndQuartierId(projetId, quartierId)
                .stream()
                .findFirst()
                .orElse(null);

        // Kreye PRMAfaireDTO a
        PRMAfaireDTO dto = PRMAfaireDTO.of(
                projetId,
                quartierId,
                calculateAFaire(requis.getBeneficiairePlan(), realise != null ? realise.getBeneficiaireReal() : 0),
                calculateAFaire(requis.getQualifierPlan(), realise != null ? realise.getQualifierReal() : 0),
                calculateAFaire(requis.getNqPlan(), realise != null ? realise.getNqReal() : 0),
                calculateAFaire(requis.getFormationSocioPlan(), realise != null ? realise.getFormationSocioReal() : 0),
                calculateAFaire(requis.getFormationTechPlan(), realise != null ? realise.getFormationTechReal() : 0)
        );

        return Optional.of(dto);
    }

    private Integer calculateAFaire(Integer plan, Integer realise) {
        if (plan == null) return 0;
        if (realise == null) realise = 0;
        return Math.max(0, plan - realise);
    }
}
