package br.com.investments.backend.rate;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SelicRate(BigDecimal annualRate, LocalDate referenceDate) {
}
