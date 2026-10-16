package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.ComposanteBeneficiaireDao;
import com.natha.dev.Dao.ComposanteDao;
import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.Dto.ComposanteBeneficiaireDto;
import com.natha.dev.IService.ComposanteBeneficiaireIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Composante;
import com.natha.dev.Model.ComposanteBeneficiaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComposanteBeneficiaireServiceImpl implements ComposanteBeneficiaireIService {

    @Autowired
    private ComposanteBeneficiaireDao composanteBeneficiaireDao;

    @Autowired
    private ComposanteDao composanteDao;

    @Autowired
    private BeneficiaireDao beneficiaireDao;

    @Override
    public ComposanteBeneficiaireDto save(Long composanteId, String beneficiaireId) {
        Composante composante = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante not found with id: " + composanteId));

        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Beneficiaire not found with id: " + beneficiaireId));

        ComposanteBeneficiaire composanteBeneficiaire = new ComposanteBeneficiaire();
        composanteBeneficiaire.setComposante(composante);
        composanteBeneficiaire.setBeneficiaire(beneficiaire);

        ComposanteBeneficiaire saved = composanteBeneficiaireDao.save(composanteBeneficiaire);
        return ComposanteBeneficiaireDto.fromEntity(saved);
    }

    @Override
    public List<BeneficiaireDto> getBeneficiairesByComposante(Long composanteId) {
        List<ComposanteBeneficiaire> relations = composanteBeneficiaireDao.findByComposanteId(composanteId);
        return relations.stream()
                .map(ComposanteBeneficiaire::getBeneficiaire)
                .map(beneficiaire -> new BeneficiaireDto(
                        beneficiaire.getIdBeneficiaire(),
                        beneficiaire.getNom(),
                        beneficiaire.getPrenom(),
                        beneficiaire.getSexe(),
                        beneficiaire.getCommuneResidence(),
                        beneficiaire.getIsGraduate(),
                        beneficiaire.getFiliere(),
                        beneficiaire.getDateNaissance(),
                        beneficiaire.getDomaineDeFormation(),
                        beneficiaire.getTypeIdentification(),
                        beneficiaire.getIdentification(),
                        beneficiaire.getQualification(),
                        beneficiaire.getLieuNaissance(),
                        beneficiaire.getPhoto(),
                        beneficiaire.getZoneFormation(),
                        beneficiaire.getDepartementFormation(),
                        beneficiaire.getQuartierResidence(),
                        beneficiaire.getRue(),
                        beneficiaire.getNumeroAdresse(),
                        beneficiaire.getLocalite(),
                        beneficiaire.getEtatMatrimonial(),
                        beneficiaire.getRelationChefMenage(),
                        beneficiaire.getMilieuResidence(),
                        beneficiaire.getSecuriteAlimentaire(),
                        beneficiaire.getSanteExemptionMedecin(),
                        beneficiaire.getSanteProblemeSante(),
                        beneficiaire.getEnfantsNesVivants(),
                        beneficiaire.getEnfantsNesDecedes(),
                        beneficiaire.getScolariteRecente(),
                        beneficiaire.getActivitePrincipale(),
                        beneficiaire.getActivitePrincipaleAutre(),
                        beneficiaire.getTelephoneContact(),
                        beneficiaire.getTelephonePaiement(),
                        beneficiaire.getOperateurPaiement(),
                        beneficiaire.getLienNaissance(),
                        beneficiaire.getTypeBeneficiaire()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<BeneficiaireDto> getCandidatsByComposante(Long composanteId) {
        List<ComposanteBeneficiaire> relations = composanteBeneficiaireDao.findByComposanteId(composanteId);
        return relations.stream()
                .map(ComposanteBeneficiaire::getBeneficiaire)
                .filter(beneficiaire -> "CANDIDAT".equals(beneficiaire.getTypeBeneficiaire()))
                .map(beneficiaire -> new BeneficiaireDto(
                        beneficiaire.getIdBeneficiaire(),
                        beneficiaire.getNom(),
                        beneficiaire.getPrenom(),
                        beneficiaire.getSexe(),
                        beneficiaire.getCommuneResidence(),
                        beneficiaire.getIsGraduate(),
                        beneficiaire.getFiliere(),
                        beneficiaire.getDateNaissance(),
                        beneficiaire.getDomaineDeFormation(),
                        beneficiaire.getTypeIdentification(),
                        beneficiaire.getIdentification(),
                        beneficiaire.getQualification(),
                        beneficiaire.getLieuNaissance(),
                        beneficiaire.getPhoto(),
                        beneficiaire.getZoneFormation(),
                        beneficiaire.getDepartementFormation(),
                        beneficiaire.getQuartierResidence(),
                        beneficiaire.getRue(),
                        beneficiaire.getNumeroAdresse(),
                        beneficiaire.getLocalite(),
                        beneficiaire.getEtatMatrimonial(),
                        beneficiaire.getRelationChefMenage(),
                        beneficiaire.getMilieuResidence(),
                        beneficiaire.getSecuriteAlimentaire(),
                        beneficiaire.getSanteExemptionMedecin(),
                        beneficiaire.getSanteProblemeSante(),
                        beneficiaire.getEnfantsNesVivants(),
                        beneficiaire.getEnfantsNesDecedes(),
                        beneficiaire.getScolariteRecente(),
                        beneficiaire.getActivitePrincipale(),
                        beneficiaire.getActivitePrincipaleAutre(),
                        beneficiaire.getTelephoneContact(),
                        beneficiaire.getTelephonePaiement(),
                        beneficiaire.getOperateurPaiement(),
                        beneficiaire.getLienNaissance(),
                        beneficiaire.getTypeBeneficiaire()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long composanteId, String beneficiaireId) {
        composanteBeneficiaireDao.deleteByComposanteIdAndBeneficiaireIdBeneficiaire(composanteId, beneficiaireId);
    }
}
