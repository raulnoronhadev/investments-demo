package br.com.investments.backend.investment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    Page<Investment> findByAccountId(Long accountId, Pageable pageable);
    Page<Investment> findByType(InvestmentType type, Pageable pageable);
}