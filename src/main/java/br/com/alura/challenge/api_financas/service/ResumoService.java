package br.com.alura.challenge.api_financas.service;

import br.com.alura.challenge.api_financas.dto.resumo.DadosDetalhesResumoCategoriaMesDTO;
import br.com.alura.challenge.api_financas.dto.resumo.DadosDetalhesResumoDTO;
import br.com.alura.challenge.api_financas.exceptions.MesIncorretoException;
import br.com.alura.challenge.api_financas.repository.DespesaRepository;
import br.com.alura.challenge.api_financas.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumoService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public ResumoService(ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    public DadosDetalhesResumoDTO resumoMes(int ano, int mes) {
        if (mes < 1 || mes > 12) {
            throw new MesIncorretoException("O mês deve estar entre 1 e 12.");
        }

        LocalDateTime start = LocalDateTime.of(ano, mes, 1, 0, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusSeconds(1);

        BigDecimal valorReceita = getValor(receitaRepository.findValorByDataBetween(start, end));
        BigDecimal valorDespesa = getValor(despesaRepository.findValorByDataBetween(start, end));

        BigDecimal valorFinalMes = calcularSaldo(valorReceita, valorDespesa);

        List<DadosDetalhesResumoCategoriaMesDTO> resumoCategoriaMes = obterResumoCategorias(start, end);

        return new DadosDetalhesResumoDTO(valorReceita, valorDespesa, valorFinalMes, resumoCategoriaMes);
    }

    private BigDecimal getValor(BigDecimal valor) {
        return valor != null ? valor : BigDecimal.ZERO;
    }

    private BigDecimal calcularSaldo(BigDecimal receita, BigDecimal despesa) {
        return receita.subtract(despesa);
    }

    private List<DadosDetalhesResumoCategoriaMesDTO> obterResumoCategorias(LocalDateTime start, LocalDateTime end) {
        return despesaRepository.findValorTotalByCategoria(start, end);
    }
}
