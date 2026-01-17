package br.com.carlos.api.exception.duplicate;

public class DuplicateFiedException extends RuntimeException {
    public DuplicateFiedException(String message) {
        super(message);
    }
}
