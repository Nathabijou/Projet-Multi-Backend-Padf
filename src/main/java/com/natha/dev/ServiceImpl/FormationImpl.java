package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.*;

import java.util.Map;
import java.util.Objects;
import com.natha.dev.Dto.*;
import com.natha.dev.Dto.Request.AddFormationToProjetBeneficiaireRequestDto;
import com.natha.dev.IService.FormationIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Formation;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireFormation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FormationImpl implements FormationIService {

    @Autowired
    private FormationDao dao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;

    @Override
    public void deleteById(String idFormation) {
        if (idFormation == null || idFormation.isEmpty()) {
            throw new IllegalArgumentException("ID formation la pa ka vid");
        }
        if (!dao.existsById(idFormation)) {
            throw new RuntimeException("Formasyon ak ID " + idFormation + " pa egziste");
        }
        dao.deleteById(idFormation);
    }

    @Autowired
    private ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao;

    @Autowired
    private ChapitreDao chapitreDao;

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public Map<String, Object> debugFormationData() {
        return Map.of();
    }

    @Override
    public FormationDto save(FormationDto dto) {
        Formation formation = convertToEntity(dto);
        formation.setIdFormation(UUID.randomUUID().toString());
        formation.setCreatedAt(LocalDateTime.now());
        formation.setModifiedAt(LocalDateTime.now());
        // Ou ka mete createdBy ak modifyBy la si w gen itilizatè kounye a k ap kreye
        // formation.setCreatedBy(currentUser);
        // formation.setModifyBy(currentUser);
        Formation savedFormation = dao.save(formation);
        return convertToDto(savedFormation);
    }

    @Override
    public FormationDto update(String idFormation, FormationDto dto) {
        Formation existingFormation = dao.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));

        // Konsève done ki pa ta dwe chanje
        dto.setCreatedAt(existingFormation.getCreatedAt());
        dto.setCreatedBy(existingFormation.getCreatedBy());
        dto.setIdFormation(idFormation);

        // Mete ajou dat modifikasyon an
        dto.setModifiedAt(LocalDateTime.now());

        // Konvèti DTO a an entite epi sove l
        Formation updatedFormation = convertToEntity(dto);
        Formation savedFormation = dao.save(updatedFormation);

        // Retounen DTO a ak enfòmasyon mete ajou yo
        return convertToDto(savedFormation);
    }


    @Override
    public FormationDto getById(String idFormation) {
        return dao.findById(idFormation)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));
    }

    @Override
    public List<FormationDto> getAll() {
        return dao.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjetBeneficiaireFormationDto addBeneficiaireToFormation(AddBeneficiaireToFormationRequestDto requestDto) {
        // 1. Verify if the beneficiaire is in the project
        ProjetBeneficiaire projetBeneficiaire = projetBeneficiaireDao.findByProjetAndBeneficiaire(
                requestDto.getIdProjet(),
                requestDto.getIdBeneficiaire()
        ).orElseThrow(() -> new RuntimeException("Benefisyè sa a pa jwenn nan pwojè sa a."));

        // 2. Verify if the formation exists
        Formation formation = dao.findById(requestDto.getIdFormation())
                .orElseThrow(() -> new RuntimeException("Fòmasyon sa a pa jwenn."));

        ProjetBeneficiaireFormation pbf = new ProjetBeneficiaireFormation();
        pbf.setProjetBeneficiaire(projetBeneficiaire);
        pbf.setFormation(formation);

        ProjetBeneficiaireFormation savedPbf = projetBeneficiaireFormationDao.save(pbf);

        return convertToDto(savedPbf);
    }

    // --- Metòd Konvèsyon --- //

    private FormationDto convertToDto(Formation f) {
        return FormationDto.builder()
                .idFormation(f.getIdFormation())
                .titre(f.getTitre())
                .description(f.getDescription())
                .dateDebut(f.getDateDebut())
                .dateFin(f.getDateFin())
                .typeFormation(f.getTypeFormation())
                .nomFormateur(f.getNomFormateur())
                .createdBy(f.getCreatedBy())
                .modifyBy(f.getModifyBy())
                .createdAt(f.getCreatedAt())
                .modifiedAt(f.getModifiedAt())
                .build();
    }

    private ProjetBeneficiaireDto convertToDto(ProjetBeneficiaire pb) {
        return new ProjetBeneficiaireDto(
                pb.getIdProjetBeneficiaire(),
                pb.getProjet().getIdProjet(),
                pb.getBeneficiaire().getIdBeneficiaire()
        );
    }

    private ProjetBeneficiaireFormationDto convertToDto(ProjetBeneficiaireFormation pbf) {
        return new ProjetBeneficiaireFormationDto(
                pbf.getId(),
                convertToDto(pbf.getProjetBeneficiaire()),
                convertToDto(pbf.getFormation())
        );
    }

    @Override
    public long countAllFormationRecords() {
        return dao.countAllFormationRecords();
    }

    @Override
    public FormationDashboardDto getFormationStatistics() {
        FormationDashboardDto stats = new FormationDashboardDto();

        try {
            // Total projets avec formations
            stats.setTotalProjetsAvecFormation(dao.countProjectsWithFormations());

            // Total formations
            stats.setTotalFormations(dao.countDistinctFormations());

            // Total modules
            stats.setTotalModules(moduleDao.count());

            // Total chapitres
            stats.setTotalChapitres(chapitreDao.count());

            // Total bénéficiaires avec formations
            stats.setTotalBeneficiairesFormation(dao.countBeneficiariesWithFormations());

            // Total femmes avec formations
            stats.setTotalFemmesFormation(dao.countFemaleBeneficiariesWithFormations());

            // Total hommes avec formations
            stats.setTotalHommesFormation(dao.countMaleBeneficiariesWithFormations());

            // Femmes qualifiées et non qualifiées
            stats.setTotalFemmesQualifieesFormation(dao.countQualifiedFemaleBeneficiaries());
            stats.setTotalFemmesNonQualifieesFormation(dao.countNonQualifiedFemaleBeneficiaries());

            // Hommes qualifiés et non qualifiés
            stats.setTotalHommesQualifiesFormation(dao.countQualifiedMaleBeneficiaries());
            stats.setTotalHommesNonQualifiesFormation(dao.countNonQualifiedMaleBeneficiaries());

            return stats;
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des statistiques de formation: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la récupération des statistiques de formation", e);
        }
    }

    private Formation convertToEntity(FormationDto d) {
        Formation f = new Formation();
        f.setIdFormation(d.getIdFormation());
        f.setTitre(d.getTitre());
        f.setDescription(d.getDescription());
        f.setDateDebut(d.getDateDebut());
        f.setDateFin(d.getDateFin());
        f.setTypeFormation(d.getTypeFormation());
        f.setNomFormateur(d.getNomFormateur());
        f.setCreatedBy(d.getCreatedBy());
        f.setModifyBy(d.getModifyBy());
        f.setCreatedAt(d.getCreatedAt());
        f.setModifiedAt(d.getModifiedAt());
        return f;
    }

    @Override
    public List<BeneficiaireDto> getBeneficiairesByFormationId(String idFormation, String projetId) {
        log.info("Début de la recherche des bénéficiaires pour la formation: {} et le projet: {}", idFormation, projetId);

        try {
            // Jwenn benefisyè yo dirèkteman ak yon sèl rekèt efikas
            log.debug("Appel à findDistinctBeneficiairesByFormationAndProjet avec formationId={}, projetId={}", idFormation, projetId);
            List<Beneficiaire> beneficiaires = projetBeneficiaireFormationDao
                    .findDistinctBeneficiairesByFormationAndProjet(idFormation, projetId);

            log.info("Nombre de bénéficiaires trouvés dans la base de données: {}", beneficiaires.size());

            if (beneficiaires.isEmpty()) {
                log.warn("Aucun bénéficiaire trouvé pour la formation {} et le projet {}", idFormation, projetId);
            } else {
                log.debug("Premier bénéficiaire trouvé: ID={}, Nom={} {}",
                        beneficiaires.get(0).getIdBeneficiaire(),
                        beneficiaires.get(0).getNom(),
                        beneficiaires.get(0).getPrenom());
            }

            List<BeneficiaireDto> result = beneficiaires.stream()
                    .map(beneficiaire -> {
                        try {
                            return convertToBeneficiaireDto(beneficiaire);
                        } catch (Exception e) {
                            log.error("Erreur lors de la conversion du bénéficiaire ID: " +
                                    (beneficiaire != null ? beneficiaire.getIdBeneficiaire() : "null"), e);
                            return null;
                        }
                    })
                    .filter(obj -> obj != null)
                    .collect(Collectors.toList());

            log.info("Nombre de bénéficiaires convertis avec succès: {}", result.size());
            return result;

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des bénéficiaires pour la formation " + idFormation +
                    " et le projet " + projetId, e);
            throw e;
        }
    }

    @Override
    public List<FormationDto> getFormationsByProjetId(String projetId) {
        List<Formation> formations = projetBeneficiaireFormationDao.findFormationsByProjetId(projetId);
        return formations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Metòd getFormationStatistics() ki te repete a te retire - li te deja egziste pi wo a

    @Override
    public void removeBeneficiaireFromFormation(String beneficiaireId, String formationId) {
        // 1. Jwenn ProjetBeneficiaire ki koresponn ak benefisyè a
        ProjetBeneficiaire projetBeneficiaire = projetBeneficiaireDao.findByBeneficiaireId(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Benefisyè sa a pa jwenn nan okenn pwojè."));

        // 2. Jwenn fòmasyon an
        Formation formation = dao.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Fòmasyon sa a pa jwenn."));

        // 3. Chèche relasyon an
        ProjetBeneficiaireFormation relation = projetBeneficiaireFormationDao
                .findByProjetBeneficiaireAndFormation(projetBeneficiaire, formation)
                .orElseThrow(() -> new RuntimeException("Benefisyè sa a pa nan fòmasyon sa a."));

        // 4. Efase relasyon an
        projetBeneficiaireFormationDao.delete(relation);
    }

    @Override
    public ProjetBeneficiaireFormationDto addFormationToProjetBeneficiaire(AddFormationToProjetBeneficiaireRequestDto requestDto) {
        // 1. Jwenn ProjetBeneficiaire a
        ProjetBeneficiaire projetBeneficiaire;

        if (requestDto.getIdBeneficiaire() != null) {
            // Si gen yon benefisyè espesifik
            projetBeneficiaire = projetBeneficiaireDao
                    .findByProjetAndBeneficiaire(requestDto.getIdProjet(), requestDto.getIdBeneficiaire())
                    .orElseThrow(() -> new RuntimeException("Pwojè benefisyè a pa jwenn. Asire w ke benefisyè a asosye ak pwojè a."));
        } else {
            // Si pa gen benefisyè espesifik, chèche premye pwojè benefisyè a
            List<ProjetBeneficiaire> projets = projetBeneficiaireDao.findByProjetIdProjet(requestDto.getIdProjet());
            if (projets.isEmpty()) {
                throw new RuntimeException("Pa gen okenn benefisyè ki asosye ak pwojè sa a.");
            }
            projetBeneficiaire = projets.get(0); // Pran premye benefisyè a
        }

        // 2. Jwenn fòmasyon an
        Formation formation = dao.findById(requestDto.getIdFormation())
                .orElseThrow(() -> new RuntimeException("Fòmasyon sa a pa egziste."));

        // 3. Tcheke si asosyasyon an deja egziste
        if (projetBeneficiaireFormationDao.existsByProjetBeneficiaireAndFormation(projetBeneficiaire, formation)) {
            throw new RuntimeException("Fòmasyon sa a deja asosye ak benefisyè sa a nan pwojè sa a.");
        }

        // 4. Kreye nouvo asosyasyon
        ProjetBeneficiaireFormation pbf = new ProjetBeneficiaireFormation();
        pbf.setProjetBeneficiaire(projetBeneficiaire);
        pbf.setFormation(formation);

        // 5. Sove asosyasyon an
        ProjetBeneficiaireFormation saved = projetBeneficiaireFormationDao.save(pbf);

        // 6. Retounen DTO a
        return convertToDto(saved);
    }

    private BeneficiaireDto convertToBeneficiaireDto(Beneficiaire beneficiaire) {
        if (beneficiaire == null) {
            return null;
        }

        BeneficiaireDto dto = new BeneficiaireDto();
        dto.setIdBeneficiaire(beneficiaire.getIdBeneficiaire());
        dto.setNom(beneficiaire.getNom());
        dto.setPrenom(beneficiaire.getPrenom());
        dto.setSexe(beneficiaire.getSexe());
        dto.setDateNaissance(beneficiaire.getDateNaissance());
        dto.setDomaineDeFormation(beneficiaire.getDomaineDeFormation());
        dto.setTypeIdentification(beneficiaire.getTypeIdentification());
        dto.setIdentification(beneficiaire.getIdentification());
        dto.setQualification(beneficiaire.getQualification());
        dto.setTelephoneContact(beneficiaire.getTelephoneContact());
        dto.setTelephonePaiement(beneficiaire.getTelephonePaiement());
        dto.setOperateurPaiement(beneficiaire.getOperateurPaiement());

        return dto;
    }
}
