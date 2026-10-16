package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PresenceDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.ProjetBeneficiaireFormationDao;
import com.natha.dev.Dto.PresenceCountDto;
import com.natha.dev.Dto.PresenceDto;
import com.natha.dev.IService.PresenceIService;
import com.natha.dev.Model.Presence;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireFormation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PresenceImpl implements PresenceIService {

    @Autowired
    private PresenceDao presenceDao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;
    @Autowired
    private ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao;

    @Override
    public PresenceDto ajouterPresence(String projetId, String beneficiaireId, PresenceDto dto) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetAndBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relation Projet-Bénéficiaire pa jwenn"));

        Presence presence = new Presence();
        presence.setIdPresence(UUID.randomUUID().toString());
        presence.setDatePresence(dto.getDatePresence());
        presence.setHeurEntrer(dto.getHeurEntrer());
        presence.setHeureSorti(dto.getHeureSorti());
        presence.setCreateBy(dto.getCreateBy());
        presence.setProjetBeneficiaire(pb);

        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public List<PresenceDto> getPresencesByProjetBeneficiaire(String projetId, String beneficiaireId) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetAndBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon pa egziste"));

        return presenceDao.findByProjetBeneficiaireIdProjetBeneficiaire(pb.getIdProjetBeneficiaire())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // Nouvo metòd pou Presence Formation
    public PresenceDto ajouterPresenceFormation(String projetId, String beneficiaireId, String idFormation, PresenceDto dto) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetAndBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relation Projet-Bénéficiaire pa jwenn"));

        ProjetBeneficiaireFormation pbf = (ProjetBeneficiaireFormation) projetBeneficiaireFormationDao
                .findByProjetBeneficiaireAndFormationIdFormation(pb, idFormation)
                .orElseThrow(() -> new RuntimeException("Relation ProjetBeneficiaire-Formation pa jwenn"));

        Presence presence = new Presence();
        presence.setIdPresence(UUID.randomUUID().toString());
        presence.setDatePresence(dto.getDatePresence());
        presence.setHeurEntrer(dto.getHeurEntrer());
        presence.setHeureSorti(dto.getHeureSorti());
        presence.setCreateBy(dto.getCreateBy());
        presence.setProjetBeneficiaireFormation(pbf);

        return convertToDto(presenceDao.save(presence));
    }






    @Override
    public PresenceDto modifierPresence(String idPresence, PresenceDto dto) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));

        presence.setDatePresence(dto.getDatePresence());
        presence.setHeurEntrer(dto.getHeurEntrer());
        presence.setHeureSorti(dto.getHeureSorti());
        presence.setCreateBy(dto.getCreateBy());

        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public void supprimerPresence(String idPresence) {
        presenceDao.deleteById(idPresence);
    }

    private PresenceDto convertToDto(Presence p) {
        return new PresenceDto(
                p.getIdPresence(), p.getDatePresence(),
                p.getHeurEntrer(), p.getHeureSorti(),
                p.getCreateBy()
        );
    }

    @Override
    public PresenceCountDto getPresenceCountByPeriode(String projetId, String beneficiaireId, LocalDate debutPeriode, LocalDate finPeriode) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetAndBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon Projet-Bénéficiaire pa jwenn"));

        List<Presence> presences = presenceDao.findByProjetBeneficiaireAndPeriode(
                pb.getIdProjetBeneficiaire(),
                debutPeriode,
                finPeriode
        );

        List<LocalDate> dates = presences.stream()
                .map(Presence::getDatePresence)
                .collect(Collectors.toList());

        return new PresenceCountDto(presences.size(), dates);
    }

    @Override
    public PresenceDto checkOut(String idPresence, LocalTime heureSorti) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));

        if (presence.getHeurEntrer() == null) {
            throw new RuntimeException("Check-in pa fèt ankò, ou pa ka check-out");
        }

        presence.setHeureSorti(heureSorti);

        long heuresTravail = java.time.Duration.between(presence.getHeurEntrer(), heureSorti).toHours();

        if (heuresTravail < 5) {
            throw new RuntimeException("Presence la pa konte - ou dwe gen omwen 5 èdtan travay");
        }

        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public PresenceDto resetPresenceTime(String idPresence, String timeType) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));

        if ("checkin".equalsIgnoreCase(timeType)) {
            presence.setHeurEntrer(null);
        } else if ("checkout".equalsIgnoreCase(timeType)) {
            presence.setHeureSorti(null);
        } else {
            throw new RuntimeException("TimeType dwe 'checkin' oswa 'checkout'");
        }

        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public PresenceDto updateCheckIn(String idPresence, LocalTime heurEntrer) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));
        presence.setHeurEntrer(heurEntrer);
        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public PresenceDto updateCheckOut(String idPresence, LocalTime heureSorti) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));
        presence.setHeureSorti(heureSorti);
        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public void deleteCheckIn(String idPresence) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));
        presence.setHeurEntrer(null);
        presenceDao.save(presence);
    }

    @Override
    public void deleteCheckOut(String idPresence) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));
        presence.setHeureSorti(null);
        presenceDao.save(presence);
    }
}
