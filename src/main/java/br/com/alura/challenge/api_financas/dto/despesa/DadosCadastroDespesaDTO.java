package br.com.alura.challenge.api_financas.dto.despesa;

import br.com.alura.challenge.api_financas.model.Categoria;
import br.com.alura.challenge.api_financas.model.Despesa;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosCadastroDespesaDTO(
                    @NotBlank
                    String descricao,

                    @NotNull
                    @DecimalMin(value = "0.01", message = "O valor mínimo de despesa deve ser 0.01.")
                    BigDecimal valor,

                    @NotNull
                    LocalDateTime data,

                    Categoria categoria
                ) {

    public DadosCadastroDespesaDTO(Despesa despesa) {
        this(despesa.getDescricao(), despesa.getValor(), despesa.getData(), despesa.getCategoria());
    }
}
