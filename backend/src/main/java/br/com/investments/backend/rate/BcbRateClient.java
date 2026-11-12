package br.com.investments.backend.rate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcbRateClient {

    private static final DateTimeFormatter BCB_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final RestClient bcbRestClient;
    private final BcbProperties properties;

    public SelicRate fetchCurrentSelic() {
        SeriesPoint latest = fetchLatest(properties.selic());
        return new SelicRate(
                new BigDecimal(latest.valor()),
                LocalDate.parse(latest.data(), BCB_DATE));
    }

    public List<MonthlyRatePoint> fetchMonthlySeries(int seriesCode, int months) {
        return fetchLast(seriesCode, months).stream()
                .map(point -> new MonthlyRatePoint(
                        YearMonth.from(LocalDate.parse(point.data(), BCB_DATE)),
                        new BigDecimal(point.valor())))
                .sorted(Comparator.comparing(MonthlyRatePoint::month))
                .toList();
    }

    private SeriesPoint fetchLatest(int seriesCode) {
        List<SeriesPoint> points = fetchLast(seriesCode, 1);
        return points.getFirst();
    }

    private List<SeriesPoint> fetchLast(int seriesCode, int count) {
        List<SeriesPoint> points;
        try {
            points = bcbRestClient.get()
                    .uri("/dados/serie/bcdata.sgs.{code}/dados/ultimos/{count}?formato=json", seriesCode, count)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (RestClientException ex) {
            throw new RateUnavailableException("Failed to reach Banco Central SGS API", ex);
        }

        if (points == null || points.isEmpty()) {
            throw new RateUnavailableException("Banco Central SGS API returned no data", null);
        }
        return points;
    }

    private record SeriesPoint(String data, String valor) {

    }
}
