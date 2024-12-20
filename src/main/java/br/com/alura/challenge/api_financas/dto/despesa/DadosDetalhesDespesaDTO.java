package br.com.alura.challenge.api_financas.dto.despesa;

import br.com.alura.challenge.api_financas.model.Despesa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhesDespesaDTO(Long id,
                                      String descricao,
                                      BigDecimal valor,
                                      LocalDateTime data
) {

    public DadosDetalhesDespesaDTO(Despesa despesa) {
        this(despesa.getId(), despesa.getDescricao(), despesa.getValor(), despesa.getData());
    }
}
