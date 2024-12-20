package br.com.alura.challenge.api_financas.dto.receita;

import br.com.alura.challenge.api_financas.model.Receita;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhesReceitaDTO(Long id,
                                      String descricao,
                                      BigDecimal valor,
                                      LocalDateTime data
) {

    public DadosDetalhesReceitaDTO(Receita receita) {
        this(receita.getId(), receita.getDescricao(), receita.getValor(), receita.getData());
    }
}
