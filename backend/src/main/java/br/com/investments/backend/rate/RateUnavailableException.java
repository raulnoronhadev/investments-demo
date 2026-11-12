package br.com.investments.backend.rate;

public class RateUnavailableException extends RuntimeException {

    public RateUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
