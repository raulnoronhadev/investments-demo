package br.com.investments.backend.shared.exception;

public class InvalidInvestmentException extends RuntimeException {
    public InvalidInvestmentException(String message) {
        super(message);
    }
}