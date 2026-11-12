package br.com.investments.backend.rate;
import java.math.BigDecimal;
import java.time.YearMonth;

public record MonthlyRatePoint(YearMonth month, BigDecimal value) {
}
