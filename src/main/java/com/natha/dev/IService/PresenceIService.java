package com.natha.dev.IService;

import com.natha.dev.Dto.PresenceCountDto;
import com.natha.dev.Dto.PresenceDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PresenceIService {

    PresenceDto ajouterPresence(String projetId, String beneficiaireId, PresenceDto dto);

    List<PresenceDto> getPresencesByProjetBeneficiaire(String projetId, String beneficiaireId);

    PresenceDto modifierPresence(String idPresence, PresenceDto dto);

    void supprimerPresence(String idPresence);

    PresenceDto ajouterPresenceFormation(String projetId, String beneficiaireId, String formationId, PresenceDto dto);

    PresenceCountDto getPresenceCountByPeriode(String projetId, String beneficiaireId, LocalDate debutPeriode, LocalDate finPeriode);

    PresenceDto checkOut(String idPresence, LocalTime heureSorti);

    PresenceDto resetPresenceTime(String idPresence, String timeType);

    PresenceDto updateCheckIn(String idPresence, LocalTime heurEntrer);

    PresenceDto updateCheckOut(String idPresence, LocalTime heureSorti);

    void deleteCheckIn(String idPresence);

    void deleteCheckOut(String idPresence);

}
