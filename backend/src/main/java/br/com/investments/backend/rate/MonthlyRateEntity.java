package br.com.investments.backend.rate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Entity
@Table(name = "monthly_rate")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "series", nullable = false, length = 16)
    private RateSeries series;

    @Column(name = "reference_month", nullable = false)
    private LocalDate referenceMonth;

    @Column(name = "value", nullable = false, precision = 9, scale = 4)
    private BigDecimal value;

    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt;

    public static MonthlyRateEntity from(RateSeries series, MonthlyRatePoint point) {
        MonthlyRateEntity entity = new MonthlyRateEntity();
        entity.series = series;
        entity.referenceMonth = point.month().atDay(1);
        entity.value = point.value();
        entity.fetchedAt = LocalDateTime.now();
        return entity;
    }

    public MonthlyRatePoint toPoint() {
        return new MonthlyRatePoint(YearMonth.from(referenceMonth), value);
    }
}
