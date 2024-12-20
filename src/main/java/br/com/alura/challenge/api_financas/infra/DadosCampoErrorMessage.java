package br.com.alura.challenge.api_financas.infra;

import org.springframework.validation.FieldError;

public record DadosCampoErrorMessage(String field, String message) {

    public DadosCampoErrorMessage(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
