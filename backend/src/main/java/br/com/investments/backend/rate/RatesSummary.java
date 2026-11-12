package br.com.investments.backend.rate;
import java.util.List;

public record RatesSummary(
        SelicRate selic,
        List<MonthlyRatePoint> ipca,
        List<MonthlyRatePoint> cdi) {
}
