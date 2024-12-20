package br.com.alura.challenge.api_financas.dto.resumo;

import java.math.BigDecimal;
import java.util.List;

public record DadosDetalhesResumoDTO(BigDecimal totalReceita, BigDecimal totalDespesa, BigDecimal saldoMes, List<DadosDetalhesResumoCategoriaMesDTO> valorGastoPorCategoria) {
}
