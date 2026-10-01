package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PayrollDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dto.PayrollBatchDto;
import com.natha.dev.Dto.PayrollDto;
import com.natha.dev.Dto.PeriodeMethodePaiementDTO;
import com.natha.dev.IService.PayrollIService;
import com.natha.dev.Model.Payroll;
import com.natha.dev.Model.ProjetBeneficiaire;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        payroll.setFraisTransport(dto.getFraisTransport());

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
        payroll.setFraisTransport(dto.getFraisTransport());

        double montantParJour = dto.getMontantParJour() != null ? dto.getMontantParJour() : 0.0;
        payroll.setMontantPayer(payroll.getNbreJourTravail() * montantParJour);

        Payroll saved = payrollDao.save(payroll);

        return convertToDto(saved, montantParJour);
    }

    @Override
    public List<PeriodeMethodePaiementDTO> getMethodePaiementParPeriode(String projetId) {
        return payrollDao.findPeriodeAndMethodeByProjet(projetId);
    }

    @Override
    public List<PayrollDto> importPayrollBatch(PayrollBatchDto batchDto) {
        List<PayrollDto> results = new ArrayList<>();

        for (PayrollDto dto : batchDto.getPayrolls()) {
            if (dto.getProjetBeneficiaireId() == null) {
                throw new RuntimeException("projetBeneficiaireId obligatwa pou chak payroll");
            }

            ProjetBeneficiaire pb = projetBeneficiaireDao.findById(dto.getProjetBeneficiaireId())
                    .orElseThrow(() -> new RuntimeException("ProjetBeneficiaire pa jwenn pou ID: " + dto.getProjetBeneficiaireId()));

            Payroll payroll = new Payroll();
            
            if (dto.getIdPayroll() != null && !dto.getIdPayroll().isEmpty()) {
                payroll.setIdPayroll(dto.getIdPayroll());
            }
            
            payroll.setIdTransaction(dto.getIdTransaction());
            payroll.setMethodePaiement(dto.getMethodePaiement());
            payroll.setDebutPeriode(dto.getDebutPeriode());
            payroll.setFinPeriode(dto.getFinPeriode());
            payroll.setNbreJourTravail(dto.getNbrejourTravail());
            payroll.setStatut(dto.getStatut());
            payroll.setDatePaiement(dto.getDatePaiement());
            payroll.setProjetBeneficiaire(pb);
            payroll.setFraisTransport(dto.getFraisTransport());

            if (dto.getMontantPayer() != null) {
                payroll.setMontantPayer(dto.getMontantPayer());
            } else {
                double montantParJour = dto.getMontantParJour() != null ? dto.getMontantParJour() : 0.0;
                payroll.setMontantPayer(payroll.getNbreJourTravail() * montantParJour);
            }

            Payroll saved = payrollDao.save(payroll);
            
            double montantParJour = dto.getMontantParJour() != null ? dto.getMontantParJour() : 
                (saved.getMontantPayer() / (saved.getNbreJourTravail() == 0 ? 1 : saved.getNbreJourTravail()));
            
            results.add(convertToDto(saved, montantParJour));
        }

        return results;
    }

    @Override
    public List<PayrollDto> importPayrollFromCsv(String csvContent) {
        List<PayrollDto> dtos = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        String[] lines = csvContent.split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty() || i == 0) {
                continue;
            }
            
            String[] columns = line.split("\t");
            
            PayrollDto dto = new PayrollDto();
            
            if (columns.length >= 11) {
                dto.setIdPayroll(columns[0].trim());
                dto.setDatePaiement(parseDate(columns[1].trim(), dateFormatter));
                dto.setDebutPeriode(parseDate(columns[2].trim(), dateFormatter));
                dto.setFinPeriode(parseDate(columns[3].trim(), dateFormatter));
                dto.setIdTransaction(parseNullable(columns[4].trim()));
                dto.setMethodePaiement(columns[5].trim());
                dto.setMontantPayer(parseDouble(columns[6].trim()));
                dto.setNbrejourTravail(parseInt(columns[7].trim()));
                dto.setStatut(columns[8].trim());
                dto.setProjetBeneficiaireId(columns[9].trim());
                dto.setFraisTransport(parseDouble(columns[10].trim()));
                
                dtos.add(dto);
            }
        }
        
        PayrollBatchDto batchDto = new PayrollBatchDto();
        batchDto.setPayrolls(dtos);
        
        return importPayrollBatch(batchDto);
    }

    private LocalDate parseDate(String dateStr, DateTimeFormatter formatter) {
        if (dateStr == null || dateStr.isEmpty() || dateStr.equals("NULL")) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    private String parseNullable(String value) {
        if (value == null || value.isEmpty() || value.equals("NULL")) {
            return null;
        }
        return value;
    }

    private Double parseDouble(String value) {
        if (value == null || value.isEmpty() || value.equals("NULL")) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int parseInt(String value) {
        if (value == null || value.isEmpty() || value.equals("NULL")) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
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
                p.getFraisTransport(),
                montantParJour,
                p.getProjetBeneficiaire() != null ? p.getProjetBeneficiaire().getIdProjetBeneficiaire() : null
        );
    }
}
