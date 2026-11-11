package br.com.investments.backend.account;
import br.com.investments.backend.account.dto.*;
import br.com.investments.backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository repository;

    public Page<AccountResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(AccountResponse::from);
    }

    public Page<AccountResponse> findByCountry(String country, Pageable pageable) {
        return repository.findByCountry(country, pageable).map(AccountResponse::from);
    }

    public AccountResponse findById(Long id) {
        return AccountResponse.from(findEntityById(id));
    }

    @Transactional
    public AccountResponse create(CreateAccountRequest request) {
        Account account = new Account();
        account.setName(request.name());
        account.setCountry(request.country());

        return AccountResponse.from(repository.save(account));
    }

    @Transactional
    public AccountResponse update(Long id, UpdateAccountRequest request) {
        Account account = findEntityById(id);
        account.setName(request.name());
        account.setCountry(request.country());

        return AccountResponse.from(account);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    private Account findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", id));
    }
}