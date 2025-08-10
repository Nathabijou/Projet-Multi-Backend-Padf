package com.natha.dev.Service;

import com.natha.dev.Dto.EvaluationDto;
import com.natha.dev.Dto.NoteEvaluationDto;
import com.natha.dev.Model.Evaluation;
import com.natha.dev.Model.ModuleBeneficiaire;
import com.natha.dev.Repository.EvaluationRepository;
import com.natha.dev.Repository.ModuleBeneficiaireRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    
    private final EvaluationRepository evaluationRepository;
    private final ModuleBeneficiaireRepository moduleBeneficiaireRepository;
    
    @Transactional
    public EvaluationDto mettreAJourNotes(String moduleBeneficiaireId, NoteEvaluationDto noteDto) {
        // Jwenn ModuleBeneficiaire a
        ModuleBeneficiaire mb = moduleBeneficiaireRepository.findById(moduleBeneficiaireId)
                .orElseThrow(() -> new RuntimeException("Modil benefisyè a pa jwenn"));
        
        // Verify if evaluation already exists
        Evaluation evaluation = mb.getEvaluation();
        if (evaluation == null) {
            evaluation = new Evaluation();
            evaluation.setModuleBeneficiaire(mb);
            mb.setEvaluation(evaluation);
        }
        
        // Mete nòt yo ak validasyon
        if (noteDto.getNoteTheorique() != null) {
            evaluation.setNoteTheorique(noteDto.getNoteTheorique());
        }

        if (noteDto.getNotePratique() != null) {
            evaluation.setNotePratique(noteDto.getNotePratique());
        }
        
        // Mete kòmantè a si li egziste
        if (noteDto.getCommentaire() != null && !noteDto.getCommentaire().trim().isEmpty()) {
            evaluation.setCommentaire(noteDto.getCommentaire());
        }
        
        // Sove epi retounen evaluation a
        Evaluation saved = evaluationRepository.save(evaluation);
        return convertToDto(saved);
    }
    
    public EvaluationDto getEvaluationByModuleBeneficiaireId(String moduleBeneficiaireId) {
        Evaluation evaluation = evaluationRepository.findByModuleBeneficiaireId(moduleBeneficiaireId)
                .orElseThrow(() -> new RuntimeException("Pa gen evaluasyon pou modil benefisyè sa a"));
        return convertToDto(evaluation);
    }
    
    private EvaluationDto convertToDto(Evaluation evaluation) {
        EvaluationDto dto = new EvaluationDto();
        dto.setId(evaluation.getId());
        dto.setNoteMaconnerie(evaluation.getNoteMaconnerie());
        dto.setNoteSalle(evaluation.getNoteSalle());
        dto.setNotePratique(evaluation.getNotePratique());
        dto.setNoteTheorique(evaluation.getNoteTheorique());
        dto.setMoyenne(evaluation.getMoyenne());
        dto.setIsPass(evaluation.getIsPass());
        dto.setMention(evaluation.getMention());
        dto.setDateCreation(evaluation.getDateCreation());
        dto.setDateModification(evaluation.getDateModification());
        return dto;
    }
}
