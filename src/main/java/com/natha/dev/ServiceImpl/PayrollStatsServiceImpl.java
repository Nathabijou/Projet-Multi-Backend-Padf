package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.*;
import com.natha.dev.Dto.PayrollStatsDto;
import com.natha.dev.IService.PayrollStatsIService;
import com.natha.dev.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollStatsServiceImpl implements PayrollStatsIService {

    @Autowired
    private PayrollDao payrollDao;

    @Autowired
    private ComposanteDao composanteDao;

    @Autowired
    private ZoneDao zoneDao;

    @Autowired
    private DepartementDao departementDao;

    @Autowired
    private ArrondissementDao arrondissementDao;

    @Autowired
    private CommuneDao communeDao;

    @Autowired
    private SectionCommunaleDao sectionCommunaleDao;

    @Autowired
    private QuartierDao quartierDao;

    @Autowired
    private ProjetDao projetDao;

    @Override
    public PayrollStatsDto getPayrollStatsByComposante(Long composanteId) {
        Composante composante = composanteDao.findById(composanteId)
                .orElseThrow(() -> new RuntimeException("Composante not found"));

        // Jwenn tout projek nan composante sa a
        List<Projet> projets = projetDao.findByComposanteId(composanteId);

        long totalPayrollPeriodes = 0;
        long totalBeneficiaire = 0;

        // Kalkile total peryòd payroll ak beneficiaire pou tout projek nan composante sa a
        for (Projet projet : projets) {
            long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(composanteId, null, null, null, null, null, null, projet.getIdProjet());
            long beneficiaireCount = payrollDao.countBeneficiaireByLocation(composanteId, null, null, null, null, null, null, projet.getIdProjet());
            totalPayrollPeriodes += payrollPeriodesCount;
            totalBeneficiaire += beneficiaireCount;
        }

        long hommeMois = totalBeneficiaire * totalPayrollPeriodes;

        return new PayrollStatsDto("COMPOSANTE", composanteId, composante.getNom(), totalPayrollPeriodes, totalBeneficiaire, hommeMois);
    }

    @Override
    public List<PayrollStatsDto> getPayrollStatsByZone(Long zoneId) {
        Zone zone = zoneDao.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        // Jwenn tout projek nan zone sa a
        List<Projet> projets = projetDao.findByZoneId(zoneId);

        long totalPayrollPeriodes = 0;
        long totalBeneficiaire = 0;

        // Kalkile total peryòd payroll ak beneficiaire pou tout projek nan zone sa a
        for (Projet projet : projets) {
            long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(null, zoneId, null, null, null, null, null, projet.getIdProjet());
            long beneficiaireCount = payrollDao.countBeneficiaireByLocation(null, zoneId, null, null, null, null, null, projet.getIdProjet());
            totalPayrollPeriodes += payrollPeriodesCount;
            totalBeneficiaire += beneficiaireCount;
        }

        long hommeMois = totalBeneficiaire * totalPayrollPeriodes;

        List<PayrollStatsDto> stats = new ArrayList<>();
        stats.add(new PayrollStatsDto("ZONE", zoneId, zone.getNom(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getPayrollStatsByDepartement(Long departementId, Long composanteId, Long zoneId) {
        Departement departement = departementDao.findById(departementId)
                .orElseThrow(() -> new RuntimeException("Departement not found"));

        // Jwenn tout projek nan departement sa a
        List<Projet> projets = projetDao.findByDepartementId(departementId);
        System.out.println("DEBUG: Departement ID: " + departementId + ", Projets trouvés: " + projets.size());

        // Filtre projek si composanteId oswa zoneId bayen
        List<Projet> filteredProjets = new ArrayList<>();
        for (Projet projet : projets) {
            boolean matches = true;
            if (composanteId != null) {
                boolean hasComposante = projet.getComposantes().stream()
                        .anyMatch(c -> c.getId().equals(composanteId));
                if (!hasComposante) {
                    matches = false;
                }
            }
            if (zoneId != null && projet.getQuartier() != null &&
                projet.getQuartier().getSectionCommunale() != null &&
                projet.getQuartier().getSectionCommunale().getCommune() != null &&
                projet.getQuartier().getSectionCommunale().getCommune().getArrondissement() != null &&
                projet.getQuartier().getSectionCommunale().getCommune().getArrondissement().getDepartement() != null &&
                projet.getQuartier().getSectionCommunale().getCommune().getArrondissement().getDepartement().getZoneDepartements() != null) {
                boolean hasZone = projet.getQuartier().getSectionCommunale().getCommune().getArrondissement().getDepartement().getZoneDepartements().stream()
                        .anyMatch(zd -> zd.getZone() != null && zd.getZone().getId().equals(zoneId));
                if (!hasZone) {
                    matches = false;
                }
            } else if (zoneId != null) {
                matches = false;
            }
            if (matches) {
                filteredProjets.add(projet);
            }
        }

        long totalPayrollPeriodes = 0;
        long totalBeneficiaire = 0;

        // Kalkile total peryòd payroll ak beneficiaire pou tout projek nan departement sa a
        // Ak filtre composante ak zone opsyonèl
        System.out.println("DEBUG: Projets filtrés: " + filteredProjets.size());
        for (Projet projet : filteredProjets) {
            long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            long beneficiaireCount = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            System.out.println("DEBUG: Projet ID: " + projet.getIdProjet() + ", PayrollPeriodes: " + payrollPeriodesCount + ", Beneficiaires: " + beneficiaireCount);
            totalPayrollPeriodes += payrollPeriodesCount;
            totalBeneficiaire += beneficiaireCount;
        }

        long hommeMois = totalBeneficiaire * totalPayrollPeriodes;

        List<PayrollStatsDto> stats = new ArrayList<>();
        stats.add(new PayrollStatsDto("DEPARTEMENT", departementId, departement.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getPayrollStatsByArrondissement(Long arrondissementId) {
        Arrondissement arrondissement = arrondissementDao.findById(arrondissementId)
                .orElseThrow(() -> new RuntimeException("Arrondissement not found"));

        // Jwenn tout projek nan arrondissement sa a
        List<Projet> projets = projetDao.findByArrondissementId(arrondissementId);

        long totalPayrollPeriodes = 0;
        long totalBeneficiaire = 0;

        // Kalkile total peryòd payroll ak beneficiaire pou tout projek nan arrondissement sa a
        for (Projet projet : projets) {
            long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            long beneficiaireCount = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            totalPayrollPeriodes += payrollPeriodesCount;
            totalBeneficiaire += beneficiaireCount;
        }

        long hommeMois = totalBeneficiaire * totalPayrollPeriodes;

        List<PayrollStatsDto> stats = new ArrayList<>();
        stats.add(new PayrollStatsDto("ARRONDISSEMENT", arrondissementId, arrondissement.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getPayrollStatsByCommune(Long communeId) {
        Commune commune = communeDao.findById(communeId)
                .orElseThrow(() -> new RuntimeException("Commune not found"));

        // Jwenn tout projek nan commune sa a
        List<Projet> projets = projetDao.findByCommuneId(communeId);

        long totalPayrollPeriodes = 0;
        long totalBeneficiaire = 0;

        // Kalkile total peryòd payroll ak beneficiaire pou tout projek nan commune sa a
        for (Projet projet : projets) {
            long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            long beneficiaireCount = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            totalPayrollPeriodes += payrollPeriodesCount;
            totalBeneficiaire += beneficiaireCount;
        }

        long hommeMois = totalBeneficiaire * totalPayrollPeriodes;

        List<PayrollStatsDto> stats = new ArrayList<>();
        stats.add(new PayrollStatsDto("COMMUNE", communeId, commune.getNom(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getPayrollStatsBySectionCommunale(Long sectionId) {
        SectionCommunale section = sectionCommunaleDao.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section Communale not found"));

        // Jwenn tout projek nan section communale sa a
        List<Projet> projets = projetDao.findBySectionCommunaleId(sectionId);

        long totalPayrollPeriodes = 0;
        long totalBeneficiaire = 0;

        // Kalkile total peryòd payroll ak beneficiaire pou tout projek nan section communale sa a
        for (Projet projet : projets) {
            long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            long beneficiaireCount = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            totalPayrollPeriodes += payrollPeriodesCount;
            totalBeneficiaire += beneficiaireCount;
        }

        long hommeMois = totalBeneficiaire * totalPayrollPeriodes;

        List<PayrollStatsDto> stats = new ArrayList<>();
        stats.add(new PayrollStatsDto("SECTION_COMMUNALE", sectionId, section.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getPayrollStatsByQuartier(Long quartierId) {
        Quartier quartier = quartierDao.findById(quartierId)
                .orElseThrow(() -> new RuntimeException("Quartier not found"));

        List<Projet> projets = quartier.getProjets();

        long totalPayrollPeriodes = 0;
        long totalBeneficiaire = 0;

        // Kalkile total peryòd payroll ak beneficiaire pou tout projek nan quartier sa a
        for (Projet projet : projets) {
            long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, quartierId, projet.getIdProjet());
            long beneficiaireCount = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, quartierId, projet.getIdProjet());
            totalPayrollPeriodes += payrollPeriodesCount;
            totalBeneficiaire += beneficiaireCount;
        }

        long hommeMois = totalBeneficiaire * totalPayrollPeriodes;

        List<PayrollStatsDto> stats = new ArrayList<>();
        stats.add(new PayrollStatsDto("QUARTIER", quartierId, quartier.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));

        return stats;
    }

    @Override
    public PayrollStatsDto getPayrollStatsByProjet(String projetId) {
        Projet projet = projetDao.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet not found"));

        long nombrePayrollPeriodes = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projetId);
        long nombreBeneficiaire = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projetId);
        long hommeMois = nombreBeneficiaire * nombrePayrollPeriodes;

        return new PayrollStatsDto("PROJET", null, projet.getName(), nombrePayrollPeriodes, nombreBeneficiaire, hommeMois);
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStats() {
        List<PayrollStatsDto> allStats = new ArrayList<>();

        // Global stats (all locations null)
        long nombrePayrollPeriodes = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, null);
        long nombreBeneficiaire = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, null);
        long hommeMois = nombreBeneficiaire * nombrePayrollPeriodes;

        allStats.add(new PayrollStatsDto("GLOBAL", null, "Tout Lokasyon", nombrePayrollPeriodes, nombreBeneficiaire, hommeMois));

        return allStats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsByComposantes() {
        List<Composante> composantes = composanteDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (Composante composante : composantes) {
            List<Projet> projets = projetDao.findByComposanteId(composante.getId());
            
            long totalPayrollPeriodes = 0;
            long totalBeneficiaire = 0;

            for (Projet projet : projets) {
                long payrollPeriodesCount = payrollDao.countPayrollPeriodesByLocation(composante.getId(), null, null, null, null, null, null, projet.getIdProjet());
                long beneficiaireCount = payrollDao.countBeneficiaireByLocation(composante.getId(), null, null, null, null, null, null, projet.getIdProjet());
                totalPayrollPeriodes += payrollPeriodesCount;
                totalBeneficiaire += beneficiaireCount;
            }

            long hommeMois = totalBeneficiaire * totalPayrollPeriodes;
            stats.add(new PayrollStatsDto("COMPOSANTE", composante.getId(), composante.getNom(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));
        }

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsByZones() {
        List<Zone> zones = zoneDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (Zone zone : zones) {
            List<Projet> projets = projetDao.findByZoneId(zone.getId());
            long totalPayrollPeriodes = 0;
            long totalBeneficiaire = 0;

            for (Projet projet : projets) {
                totalPayrollPeriodes += payrollDao.countPayrollPeriodesByLocation(null, zone.getId(), null, null, null, null, null, projet.getIdProjet());
                totalBeneficiaire += payrollDao.countBeneficiaireByLocation(null, zone.getId(), null, null, null, null, null, projet.getIdProjet());
            }

            long hommeMois = totalBeneficiaire * totalPayrollPeriodes;
            stats.add(new PayrollStatsDto("ZONE", zone.getId(), zone.getNom(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));
        }

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsByDepartements() {
        List<Departement> departements = departementDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (Departement departement : departements) {
            List<Projet> projets = projetDao.findByDepartementId(departement.getId());
            long totalPayrollPeriodes = 0;
            long totalBeneficiaire = 0;

            for (Projet projet : projets) {
                totalPayrollPeriodes += payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
                totalBeneficiaire += payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            }

            long hommeMois = totalBeneficiaire * totalPayrollPeriodes;
            stats.add(new PayrollStatsDto("DEPARTEMENT", departement.getId(), departement.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));
        }

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsByArrondissements() {
        List<Arrondissement> arrondissements = arrondissementDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (Arrondissement arrondissement : arrondissements) {
            List<Projet> projets = projetDao.findByArrondissementId(arrondissement.getId());
            long totalPayrollPeriodes = 0;
            long totalBeneficiaire = 0;

            for (Projet projet : projets) {
                totalPayrollPeriodes += payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
                totalBeneficiaire += payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            }

            long hommeMois = totalBeneficiaire * totalPayrollPeriodes;
            stats.add(new PayrollStatsDto("ARRONDISSEMENT", arrondissement.getId(), arrondissement.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));
        }

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsByCommunes() {
        List<Commune> communes = communeDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (Commune commune : communes) {
            List<Projet> projets = projetDao.findByCommuneId(commune.getId());
            long totalPayrollPeriodes = 0;
            long totalBeneficiaire = 0;

            for (Projet projet : projets) {
                totalPayrollPeriodes += payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
                totalBeneficiaire += payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            }

            long hommeMois = totalBeneficiaire * totalPayrollPeriodes;
            stats.add(new PayrollStatsDto("COMMUNE", commune.getId(), commune.getNom(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));
        }

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsBySectionsCommunales() {
        List<SectionCommunale> sections = sectionCommunaleDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (SectionCommunale section : sections) {
            List<Projet> projets = projetDao.findBySectionCommunaleId(section.getId());
            long totalPayrollPeriodes = 0;
            long totalBeneficiaire = 0;

            for (Projet projet : projets) {
                totalPayrollPeriodes += payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
                totalBeneficiaire += payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            }

            long hommeMois = totalBeneficiaire * totalPayrollPeriodes;
            stats.add(new PayrollStatsDto("SECTION_COMMUNALE", section.getId(), section.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));
        }

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsByQuartiers() {
        List<Quartier> quartiers = quartierDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (Quartier quartier : quartiers) {
            List<Projet> projets = quartier.getProjets();

            long totalPayrollPeriodes = 0;
            long totalBeneficiaire = 0;

            for (Projet projet : projets) {
                totalPayrollPeriodes += payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, quartier.getId(), projet.getIdProjet());
                totalBeneficiaire += payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, quartier.getId(), projet.getIdProjet());
            }

            long hommeMois = totalBeneficiaire * totalPayrollPeriodes;
            stats.add(new PayrollStatsDto("QUARTIER", quartier.getId(), quartier.getName(), totalPayrollPeriodes, totalBeneficiaire, hommeMois));
        }

        return stats;
    }

    @Override
    public List<PayrollStatsDto> getAllPayrollStatsByProjets() {
        List<Projet> projets = projetDao.findAll();
        List<PayrollStatsDto> stats = new ArrayList<>();

        for (Projet projet : projets) {
            long nombrePayrollPeriodes = payrollDao.countPayrollPeriodesByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            long nombreBeneficiaire = payrollDao.countBeneficiaireByLocation(null, null, null, null, null, null, null, projet.getIdProjet());
            long hommeMois = nombreBeneficiaire * nombrePayrollPeriodes;

            stats.add(new PayrollStatsDto("PROJET", null, projet.getName(), nombrePayrollPeriodes, nombreBeneficiaire, hommeMois));
        }

        return stats;
    }
}
