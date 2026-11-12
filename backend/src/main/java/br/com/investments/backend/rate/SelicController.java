package br.com.investments.backend.rate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rates/selic")
@RequiredArgsConstructor
public class SelicController {

    private final SelicService service;

    @GetMapping
    public SelicRate current() {
        return service.getCurrentRate();
    }
}
