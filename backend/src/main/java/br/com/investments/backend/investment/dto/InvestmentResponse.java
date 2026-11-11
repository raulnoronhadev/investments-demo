package br.com.investments.backend.investment.dto;
import br.com.investments.backend.investment.Investment;
import br.com.investments.backend.investment.InvestmentType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestmentResponse(
        Long id,
        Long accountId,
        BigDecimal amount,
        InvestmentType type,
        LocalDate maturityDate,
        BigDecimal rate
) {
    public static InvestmentResponse from(Investment investment) {
        return new InvestmentResponse(
                investment.getId(),
                investment.getAccountId(),
                investment.getAmount(),
                investment.getType(),
                investment.getMaturityDate(),
                investment.getRate()
        );
    }
}