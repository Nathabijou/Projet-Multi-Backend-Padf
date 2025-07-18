package com.natha.dev.IService;

import com.natha.dev.Dto.PayrollDto;
import com.natha.dev.Dto.PeriodeMethodePaiementDTO;
import java.util.List;

public interface PayrollIService {

    PayrollDto createPayroll(String projetId, String beneficiaireId, PayrollDto dto);

    List<PayrollDto> getPayrollsByProjetBeneficiaire(String projetId, String beneficiaireId);

    PayrollDto updatePayroll(String payrollId, PayrollDto dto);

    List<PayrollDto> getPayrollsByProjet(String projetId);

    List<PeriodeMethodePaiementDTO> getMethodePaiementParPeriode(String projetId);

}
