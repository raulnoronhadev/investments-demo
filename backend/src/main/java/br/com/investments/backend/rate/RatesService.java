package br.com.investments.backend.rate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatesService {

    private static final int MONTHS = 12;

    private final SelicService selicService;
    private final BcbRateClient client;
    private final BcbProperties properties;
    private final MonthlyRateRepository repository;

    @Cacheable("ratesSummary")
    @Transactional
    public RatesSummary getSummary() {
        return new RatesSummary(
                selicService.getCurrentRate(),
                getMonthlySeries(RateSeries.IPCA, properties.ipca()),
                getMonthlySeries(RateSeries.CDI, properties.cdi()));
    }

    private List<MonthlyRatePoint> getMonthlySeries(RateSeries series, int seriesCode) {
        try {
            List<MonthlyRatePoint> points = client.fetchMonthlySeries(seriesCode, MONTHS);
            persist(series, points);
            return points;
        } catch (RateUnavailableException ex) {
            List<MonthlyRatePoint> stored = repository
                    .findTop12BySeriesOrderByReferenceMonthDesc(series)
                    .stream()
                    .map(MonthlyRateEntity::toPoint)
                    .sorted(Comparator.comparing(MonthlyRatePoint::month))
                    .toList();
            if (stored.isEmpty()) {
                throw ex;
            }
            return stored;
        }
    }

    private void persist(RateSeries series, List<MonthlyRatePoint> points) {
        for (MonthlyRatePoint point : points) {
            if (!repository.existsBySeriesAndReferenceMonth(series, point.month().atDay(1))) {
                repository.save(MonthlyRateEntity.from(series, point));
            }
        }
    }
}
