package br.com.investments.backend.rate;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bcb")
public record BcbProperties(String baseUrl, int selic, int ipca, int cdi) {
}
