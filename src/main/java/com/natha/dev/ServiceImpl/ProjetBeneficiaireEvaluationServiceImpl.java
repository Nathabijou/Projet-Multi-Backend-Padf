package com.natha.dev.ServiceImpl;

import com.natha.dev.Dto.BeneficiaireEvaluationDto;
import com.natha.dev.Dto.EvaluationDto;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Evaluation;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireEvaluationId;
import com.natha.dev.Model.ProjetBeneficiaireEvaluation;
import com.natha.dev.Repository.ProjetBeneficiaireEvaluationRepository;

import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.EvaluationDao;
import com.natha.dev.Service.ProjetBeneficiaireEvaluationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetBeneficiaireEvaluationServiceImpl implements ProjetBeneficiaireEvaluationService {

    private final ProjetBeneficiaireDao projetBeneficiaireDao;
    private final ProjetBeneficiaireEvaluationRepository projetBeneficiaireEvaluationRepository;
    private final EvaluationDao evaluationDao;

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaireEvaluationDto> getBeneficiaireEvaluationsByProjetId(String projetId) {
        // Jwenn tout ProjetBeneficiaire pou pwojè a
        List<ProjetBeneficiaire> projetBeneficiaires = projetBeneficiaireDao.findByProjetIdProjet(projetId);
        
        return projetBeneficiaires.stream()
            .filter(pb -> pb.getBeneficiaire() != null) // Asire w ke benefisyè a egziste
            .flatMap(pb -> {
                Beneficiaire b = pb.getBeneficiaire();
                return pb.getProjetBeneficiaireEvaluations().stream()
                    .map(pbe -> {
                        Evaluation e = pbe.getEvaluation();
                        BeneficiaireEvaluationDto dto = new BeneficiaireEvaluationDto();
                        dto.setId(pb.getIdProjetBeneficiaire());
                        dto.setEvaluationId(e.getId());
                        dto.setNom(b.getNom());
                        dto.setPrenom(b.getPrenom());
                        dto.setSexe(b.getSexe());
                        dto.setCommuneResidence(b.getCommuneResidence());
                        dto.setNoteMaconnerie(e.getNoteMaconnerie());
                        dto.setNoteSalle(e.getNoteSalle());
                        dto.setMoyenne(e.getMoyenne());
                        dto.setMention(e.getMention());
                        dto.setIsPass(e.getIsPass());
                        dto.setDateCreation(e.getDateCreation());
                        dto.setDateModification(e.getDateModification());
                        dto.setCommentaire(pbe.getCommentaire());
                        dto.setNote(pbe.getNote() != null ? String.valueOf(pbe.getNote()) : null);
                        return dto;
                    });
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluationDto> getEvaluationsByProjetBeneficiaire(String projetBeneficiaireId) {
        // Jwenn ProjetBeneficiaire a
        ProjetBeneficiaire projetBeneficiaire = projetBeneficiaireDao.findById(projetBeneficiaireId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("ProjetBeneficiaire pa jwenn ak ID: %s", projetBeneficiaireId)
                ));

        // Jwenn tout evalyasyon yo
        List<ProjetBeneficiaireEvaluation> evaluations = 
                projetBeneficiaireEvaluationRepository.findByProjetBeneficiaire(projetBeneficiaire);

        // Konvèti nan DTO
        return evaluations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EvaluationDto createEvaluation(String projetBeneficiaireId, EvaluationDto evaluationDto) {
        // Jwenn ProjetBeneficiaire a
        ProjetBeneficiaire projetBeneficiaire = projetBeneficiaireDao.findById(projetBeneficiaireId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("ProjetBeneficiaire pa jwenn ak ID: %s", projetBeneficiaireId)
                ));

        // Sèvi ak mwayèn ki soti dirèkteman nan DTO a
        Double moyenne = evaluationDto.getMoyenne();
        String mention = evaluationDto.getMention();
        
        if (moyenne == null) {
            throw new IllegalArgumentException("Mwayèn an dwe prezan nan DTO a");
        }
        
        if (mention == null || mention.trim().isEmpty()) {
            mention = determineMention(moyenne);
        }
        
        // Kreye nouvo evalyasyon an
        Evaluation evaluation = new Evaluation();
        evaluation.setNoteMaconnerie(evaluationDto.getNoteMaconnerie());
        evaluation.setNoteSalle(evaluationDto.getNoteSalle());
        evaluation.setMoyenne(moyenne);
        evaluation.setMention(mention);
        evaluation.setIsPass(moyenne >= 50.0); // 50 se nòt pase
        evaluation.setDateCreation(LocalDateTime.now());
        evaluation.setDateModification(LocalDateTime.now());

        // Sove Evaluation an premye pou jwenn ID li a
        Evaluation savedEvaluation = evaluationDao.save(evaluation);

        // Kreye ID pou tab entèmedyè a
        ProjetBeneficiaireEvaluationId id = new ProjetBeneficiaireEvaluationId(
            projetBeneficiaireId,
            savedEvaluation.getId()
        );

        // Kreye relasyon an ant ProjetBeneficiaire ak Evaluation
        ProjetBeneficiaireEvaluation projetBeneficiaireEvaluation = new ProjetBeneficiaireEvaluation();
        projetBeneficiaireEvaluation.setId(id);
        projetBeneficiaireEvaluation.setProjetBeneficiaire(projetBeneficiaire);
        projetBeneficiaireEvaluation.setEvaluation(savedEvaluation);
        projetBeneficiaireEvaluation.setNote(evaluationDto.getNote());
        projetBeneficiaireEvaluation.setCommentaire(evaluationDto.getCommentaire());

        // Sove relasyon an
        ProjetBeneficiaireEvaluation savedProjetBeneficiaireEvaluation = 
            projetBeneficiaireEvaluationRepository.save(projetBeneficiaireEvaluation);
        
        // Retounen DTO a
        return convertToDto(savedProjetBeneficiaireEvaluation);
    }

    private Double calculateMoyenne(EvaluationDto dto) {
        // Kalkile mwayèn nan nòt maconnerie ak nòt sal la
        // Ou ka ajoute plis lojik si nesesè
        return (dto.getNoteMaconnerie() + dto.getNoteSalle()) / 2.0;
    }

    private String determineMention(Double moyenne) {
        if (moyenne >= 50.0) return "Pass";
        return "Échoué";
    }

    private EvaluationDto convertToDto(ProjetBeneficiaireEvaluation pbe) {
        if (pbe == null || pbe.getEvaluation() == null) {
            return null;
        }
        
        EvaluationDto dto = new EvaluationDto();
        // Sere ID evalyasyon an nan jaden evaluationId
        dto.setEvaluationId(pbe.getEvaluation().getId());
        dto.setNoteMaconnerie(pbe.getEvaluation().getNoteMaconnerie());
        dto.setNoteSalle(pbe.getEvaluation().getNoteSalle());
        dto.setMoyenne(pbe.getEvaluation().getMoyenne());
        dto.setMention(pbe.getEvaluation().getMention());
        dto.setIsPass(pbe.getEvaluation().getIsPass());
        dto.setDateCreation(pbe.getEvaluation().getDateCreation());
        dto.setDateModification(pbe.getEvaluation().getDateModification());
        dto.setCommentaire(pbe.getCommentaire());
        dto.setNote(pbe.getNote());
        return dto;
    }
}
