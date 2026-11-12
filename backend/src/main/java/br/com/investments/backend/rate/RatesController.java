package br.com.investments.backend.rate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RatesController {

    private final RatesService service;

    @GetMapping
    public RatesSummary summary() {
        return service.getSummary();
    }
}
