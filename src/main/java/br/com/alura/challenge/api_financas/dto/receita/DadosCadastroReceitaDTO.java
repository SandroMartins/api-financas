package br.com.alura.challenge.api_financas.dto.receita;

import br.com.alura.challenge.api_financas.model.Receita;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosCadastroReceitaDTO(
                    @NotBlank
                    String descricao,

                    @NotNull
                    @DecimalMin(value = "0.01", message = "O valor mínimo de receita deve ser 0.01.")
                    BigDecimal valor,

                    @NotNull
                    LocalDateTime data
                ) {

    public DadosCadastroReceitaDTO(Receita receita) {
        this(receita.getDescricao(), receita.getValor(), receita.getData());
    }
}
