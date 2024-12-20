package br.com.alura.challenge.api_financas.exceptions;

public class MesIncorretoException extends RuntimeException {

    public MesIncorretoException(String message) {
        super(message);
    }
}
