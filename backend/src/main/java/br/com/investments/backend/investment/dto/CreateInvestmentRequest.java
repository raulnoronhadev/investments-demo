package br.com.investments.backend.investment.dto;
import br.com.investments.backend.investment.InvestmentType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateInvestmentRequest(
        @NotNull        Long accountId,
        @NotNull @DecimalMin(value = "0.01") @Digits(integer = 10, fraction = 2) BigDecimal amount,
        @NotNull        InvestmentType type,
        @FutureOrPresent LocalDate maturityDate,
        @PositiveOrZero @Digits(integer = 5, fraction = 4) BigDecimal rate
) {}