package br.com.investments.backend.investment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "investment")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InvestmentType type;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(precision = 9, scale = 4)
    private BigDecimal rate;
}