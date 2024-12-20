package br.com.alura.challenge.api_financas.infra;

import br.com.alura.challenge.api_financas.exceptions.DescricaoExisteMesException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosCampoErrorMessage>> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        List<DadosCampoErrorMessage> dadosErro = erros.stream().map(DadosCampoErrorMessage::new).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dadosErro);
    }

    @ExceptionHandler(DescricaoExisteMesException.class)
    private ResponseEntity<RestErrorMessage> descricaoExisteMesHandler(DescricaoExisteMesException ex) {
        RestErrorMessage errorResponse = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<RestErrorMessage> receitaNotFoundHandler(EntityNotFoundException ex) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
