package br.com.investments.backend.investment;
import br.com.investments.backend.investment.dto.*;
import br.com.investments.backend.shared.exception.InvalidInvestmentException;
import br.com.investments.backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import br.com.investments.backend.account.AccountRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvestmentService {

    private final InvestmentRepository repository;
    private final AccountRepository accountRepository;

    public Page<InvestmentResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(InvestmentResponse::from);
    }

    public Page<InvestmentResponse> findByAccount(Long accountId, Pageable pageable) {
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account", accountId);
        }
        return repository.findByAccountId(accountId, pageable).map(InvestmentResponse::from);
    }

    public InvestmentResponse findById(Long id) {
        return InvestmentResponse.from(findEntityById(id));
    }

    @Transactional
    public InvestmentResponse create(CreateInvestmentRequest request) {
        if (!accountRepository.existsById(request.accountId())) {
            throw new ResourceNotFoundException("Account", request.accountId());
        }
        validateTreasuryFields(request.type(), request.maturityDate(), request.rate());
        Investment investment = new Investment();
        investment.setAccountId(request.accountId());
        investment.setAmount(request.amount());
        investment.setType(request.type());
        investment.setMaturityDate(request.maturityDate());
        investment.setRate(request.rate());
        return InvestmentResponse.from(repository.save(investment));
    }

    @Transactional
    public InvestmentResponse update(Long id, UpdateInvestmentRequest request) {
        validateTreasuryFields(request.type(), request.maturityDate(), request.rate());
        Investment investment = findEntityById(id);
        investment.setAmount(request.amount());
        investment.setType(request.type());
        investment.setMaturityDate(request.maturityDate());
        investment.setRate(request.rate());
        return InvestmentResponse.from(investment);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private Investment findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment", id));
    }

    private void validateTreasuryFields(InvestmentType type, LocalDate maturityDate, BigDecimal rate) {
        boolean hasFields = maturityDate != null && rate != null;

        if (type.requiresMaturityAndRate() && !hasFields) {
            throw new InvalidInvestmentException(
                    "Direct Treasury requires a maturity date and a rate");
        }
        if (!type.requiresMaturityAndRate() && (maturityDate != null || rate != null)) {
            throw new InvalidInvestmentException(
                    "Maturity date and fee only apply to Direct Treasury");
        }
    }
}