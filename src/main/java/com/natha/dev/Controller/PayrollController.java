package com.natha.dev.Controller;

import com.natha.dev.Dto.PayrollDto;
import com.natha.dev.Dto.PeriodeMethodePaiementDTO;
import com.natha.dev.IService.PayrollIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payrolls")
public class PayrollController {

    @Autowired
    private PayrollIService payrollIService;

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PostMapping("/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<PayrollDto> createPayroll(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @RequestBody PayrollDto dto) {
        PayrollDto result = payrollIService.createPayroll(projetId, beneficiaireId, dto);
        return ResponseEntity.ok(result);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/projets/{projetId}/beneficiaire/{beneficiaireId}")
    public ResponseEntity<List<PayrollDto>> getAllPayrolls(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        return ResponseEntity.ok(
                payrollIService.getPayrollsByProjetBeneficiaire(projetId, beneficiaireId)
        );
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<PayrollDto>> getPayrollsByProjet(@PathVariable String projetId) {
        return ResponseEntity.ok(payrollIService.getPayrollsByProjet(projetId));
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/methode-paiement-par-periode/{projetId}")
    public ResponseEntity<List<PeriodeMethodePaiementDTO>> getMethodePaiementParPeriode(@PathVariable String projetId) {
        return ResponseEntity.ok(payrollIService.getMethodePaiementParPeriode(projetId));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/payrolls/{payrollId}")
    public ResponseEntity<PayrollDto> updatePayroll(
            @PathVariable String payrollId,
            @RequestBody PayrollDto dto) {

        PayrollDto updated = payrollIService.updatePayroll(payrollId, dto);
        return ResponseEntity.ok(updated);
    }

}
