package br.com.investments.backend.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(min = 2, max = 2) @Pattern(regexp = "^[A-Z]{2}$",
                message = "country must be an ISO 3166-1 alpha-2 code (for example: BR, US)")
        String country
) {}