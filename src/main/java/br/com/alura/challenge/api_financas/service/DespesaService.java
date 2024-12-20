package br.com.alura.challenge.api_financas.service;

import br.com.alura.challenge.api_financas.dto.despesa.DadosAtualizaDespesaDTO;
import br.com.alura.challenge.api_financas.dto.despesa.DadosCadastroDespesaDTO;
import br.com.alura.challenge.api_financas.dto.despesa.DadosDetalhesDespesaDTO;
import br.com.alura.challenge.api_financas.exceptions.DescricaoExisteMesException;
import br.com.alura.challenge.api_financas.exceptions.MesIncorretoException;
import br.com.alura.challenge.api_financas.model.Categoria;
import br.com.alura.challenge.api_financas.model.Despesa;
import br.com.alura.challenge.api_financas.repository.DespesaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DespesaService {

    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository) {
        this.repository = repository;
    }

    //CASO OS FILTROS AUMENTEM VERIFICAR A IMPLEMENTAÇÃO DO SPECIFICATION
    public Page<DadosDetalhesDespesaDTO> buscaTodasDespesas(String descricao, Pageable paginacao) {
        if(descricao != null && !descricao.isEmpty()) {
            return repository.findByDescricaoContainingIgnoreCase(descricao, paginacao).map(DadosDetalhesDespesaDTO::new);
        }

        return repository.findAll(paginacao).map(DadosDetalhesDespesaDTO::new);
    }

    public DadosDetalhesDespesaDTO obterPorId(@NotNull Long id) {
        Despesa despesa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o ID: " + id));

        return new DadosDetalhesDespesaDTO(despesa);
    }

    public Page<DadosDetalhesDespesaDTO> obterPorAnoMes(@NotNull int ano, @NotNull int mes, Pageable paginacao) {
        if (mes < 1 || mes > 12) {
            throw new MesIncorretoException("O mês deve estar entre 1 e 12.");
        }

        LocalDateTime start = LocalDateTime.of(ano, mes, 1, 0, 0, 0); // Primeiro dia do mês à meia-noite
        LocalDateTime end = start.plusMonths(1).minusSeconds(1); // Último segundo do mês

        return repository.findByDataBetween(start, end, paginacao).map(DadosDetalhesDespesaDTO::new);
    }

    public DadosDetalhesDespesaDTO novaDespesa(DadosCadastroDespesaDTO dto) {
        validaDespesaDescricaoExisteMesmoMes(dto.descricao(), dto.data().getMonthValue(), null);

        Despesa despesa = new Despesa(dto);

        repository.save(despesa);

        return new DadosDetalhesDespesaDTO(despesa);
    }

    public DadosDetalhesDespesaDTO atualizaDespesa(Long id, DadosAtualizaDespesaDTO dto) {
        validaDespesaDescricaoExisteMesmoMes(dto.descricao(), dto.data().getMonthValue(), id);
        Despesa despesa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o ID: " + id));

        despesa.atualizarDados(dto);

        return new DadosDetalhesDespesaDTO(despesa);
    }

    public void excluirDespesa(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Despesa não encontrada para o ID: " + id);
        }

        repository.deleteById(id);
    }

    //MOVER PARA UMA CLASSE DE VALIDAÇÃO
    public void validaDespesaDescricaoExisteMesmoMes(String descricao, int mes, Long id) {
        if(repository.existsByDescricaoEMesmoMes(descricao, mes, id)) {
            throw new DescricaoExisteMesException("Já existe essa descrição de despesa para o mês escolhido.");
        }
    }
}
