package br.com.investments.backend.rate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface SelicRateRepository extends JpaRepository<SelicRateEntity, Long> {

    Optional<SelicRateEntity> findTopByOrderByReferenceDateDesc();

    boolean existsByReferenceDate(LocalDate referenceDate);
}
