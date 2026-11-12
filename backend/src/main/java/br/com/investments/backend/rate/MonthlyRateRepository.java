package br.com.investments.backend.rate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface MonthlyRateRepository extends JpaRepository<MonthlyRateEntity, Long> {

    List<MonthlyRateEntity> findTop12BySeriesOrderByReferenceMonthDesc(RateSeries series);

    boolean existsBySeriesAndReferenceMonth(RateSeries series, LocalDate referenceMonth);
}
