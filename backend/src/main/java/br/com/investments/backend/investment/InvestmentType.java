package br.com.investments.backend.investment;

public enum InvestmentType {
    STOCK,
    FUND,
    TREASURY_BOND;

    public boolean requiresMaturityAndRate() {
        return this == TREASURY_BOND;
    }
}