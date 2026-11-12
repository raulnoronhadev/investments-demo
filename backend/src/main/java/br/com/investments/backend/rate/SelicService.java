package br.com.investments.backend.rate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SelicService {

    private final BcbRateClient client;
    private final SelicRateRepository repository;

    @Cacheable("selicRate")
    @Transactional
    public SelicRate getCurrentRate() {
        try {
            SelicRate rate = client.fetchCurrentSelic();
            persist(rate);
            return rate;
        } catch (RateUnavailableException ex) {
            return repository.findTopByOrderByReferenceDateDesc()
                    .map(SelicRateEntity::toRate)
                    .orElseThrow(() -> ex);
        }
    }

    private void persist(SelicRate rate) {
        if (!repository.existsByReferenceDate(rate.referenceDate())) {
            repository.save(SelicRateEntity.from(rate));
        }
    }
}
