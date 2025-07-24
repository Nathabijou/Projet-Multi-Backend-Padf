package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PayrollDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dto.PayrollDto;
import com.natha.dev.Dto.PeriodeMethodePaiementDTO;
import com.natha.dev.IService.PayrollIService;
import com.natha.dev.Model.Payroll;
import com.natha.dev.Model.ProjetBeneficiaire;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollImpl implements PayrollIService {

    private final PayrollDao payrollDao;
    private final ProjetBeneficiaireDao projetBeneficiaireDao;

    @Override
    public PayrollDto createPayroll(String projetId, String beneficiaireId, PayrollDto dto) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetAndBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon Beneficiaire-Projet pa jwenn"));

        Payroll payroll = new Payroll();
        payroll.setIdTransaction(dto.getIdTransaction());
        payroll.setMethodePaiement(dto.getMethodePaiement());
        payroll.setDebutPeriode(dto.getDebutPeriode());
        payroll.setFinPeriode(dto.getFinPeriode());
        payroll.setNbreJourTravail(dto.getNbrejourTravail());
        payroll.setStatut(dto.getStatut());
        payroll.setDatePaiement(dto.getDatePaiement());
        payroll.setProjetBeneficiaire(pb);

        // Kalkile montantPayer otomatik
        double montantParJour = dto.getMontantParJour() != null ? dto.getMontantParJour() : 0.0;
        payroll.setMontantPayer(payroll.getNbreJourTravail() * montantParJour);

        Payroll saved = payrollDao.save(payroll);

        return convertToDto(saved, montantParJour);
    }

    @Override
    public List<PayrollDto> getPayrollsByProjetBeneficiaire(String projetId, String beneficiaireId) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetAndBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon Beneficiaire-Projet pa jwenn"));

        return payrollDao.findByProjetBeneficiaire(pb)
                .stream()
                .map(p -> convertToDto(p, p.getMontantPayer() / (p.getNbreJourTravail() == 0 ? 1 : p.getNbreJourTravail())))
                .collect(Collectors.toList());
    }

    @Override
    public List<PayrollDto> getPayrollsByProjet(String projetId) {
        return payrollDao.findByProjetId(projetId)
                .stream()
                .map(p -> convertToDto(p, p.getMontantPayer() / (p.getNbreJourTravail() == 0 ? 1 : p.getNbreJourTravail())))
                .collect(Collectors.toList());
    }

    @Override
    public PayrollDto updatePayroll(String payrollId, PayrollDto dto) {
        Payroll payroll = payrollDao.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll pa jwenn"));

        payroll.setIdTransaction(dto.getIdTransaction());
        payroll.setMethodePaiement(dto.getMethodePaiement());
        payroll.setDebutPeriode(dto.getDebutPeriode());
        payroll.setFinPeriode(dto.getFinPeriode());
        payroll.setNbreJourTravail(dto.getNbrejourTravail());
        payroll.setStatut(dto.getStatut());
        payroll.setDatePaiement(dto.getDatePaiement());

        double montantParJour = dto.getMontantParJour() != null ? dto.getMontantParJour() : 0.0;
        payroll.setMontantPayer(payroll.getNbreJourTravail() * montantParJour);

        Payroll saved = payrollDao.save(payroll);

        return convertToDto(saved, montantParJour);
    }

    @Override
    public List<PeriodeMethodePaiementDTO> getMethodePaiementParPeriode(String projetId) {
        return payrollDao.findPeriodeAndMethodeByProjet(projetId);
    }

    private PayrollDto convertToDto(Payroll p, double montantParJour) {
        return new PayrollDto(
                p.getIdPayroll(),
                p.getIdTransaction(),
                p.getMethodePaiement(),
                p.getDebutPeriode(),
                p.getFinPeriode(),
                p.getMontantPayer(),
                p.getStatut(),
                p.getDatePaiement(),
                p.getNbreJourTravail(),
                montantParJour
        );
    }
}
