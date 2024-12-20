package br.com.alura.challenge.api_financas.dto.resumo;

import br.com.alura.challenge.api_financas.model.Categoria;

import java.math.BigDecimal;

public record DadosDetalhesResumoCategoriaMesDTO(Categoria categoria, BigDecimal valorTotal) {
}
