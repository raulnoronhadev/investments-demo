package br.com.investments.backend.rate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "selic_rate")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelicRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annual_rate", nullable = false, precision = 9, scale = 4)
    private BigDecimal annualRate;

    @Column(name = "reference_date", nullable = false, unique = true)
    private LocalDate referenceDate;

    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt;

    public static SelicRateEntity from(SelicRate rate) {
        SelicRateEntity entity = new SelicRateEntity();
        entity.annualRate = rate.annualRate();
        entity.referenceDate = rate.referenceDate();
        entity.fetchedAt = LocalDateTime.now();
        return entity;
    }

    public SelicRate toRate() {
        return new SelicRate(annualRate, referenceDate);
    }
}
