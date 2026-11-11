package br.com.investments.backend.investment;
import br.com.investments.backend.investment.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService service;

    @GetMapping
    public Page<InvestmentResponse> list(
            @RequestParam(required = false) Long accountId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return accountId == null ? service.findAll(pageable) : service.findByAccount(accountId, pageable);
    }

    @GetMapping("/{id}")
    public InvestmentResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/api/accounts/{accountId}/investments")
    public Page<InvestmentResponse> listByAccount(
            @PathVariable Long accountId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.findByAccount(accountId, pageable);
    }

    @PostMapping
    public ResponseEntity<InvestmentResponse> create(
            @Valid @RequestBody CreateInvestmentRequest request,
            UriComponentsBuilder uriBuilder) {
        InvestmentResponse created = service.create(request);
        URI location = uriBuilder.path("/api/investments/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public InvestmentResponse update(@PathVariable Long id, @Valid @RequestBody UpdateInvestmentRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}