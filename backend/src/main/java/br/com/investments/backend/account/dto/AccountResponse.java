package br.com.investments.backend.account.dto;
import br.com.investments.backend.account.Account;

public record AccountResponse(
        Long id,
        String name,
        String country
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(account.getId(), account.getName(), account.getCountry());
    }
}