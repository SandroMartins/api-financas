package br.com.alura.challenge.api_financas.service;

import br.com.alura.challenge.api_financas.dto.receita.DadosAtualizaReceitaDTO;
import br.com.alura.challenge.api_financas.dto.receita.DadosCadastroReceitaDTO;
import br.com.alura.challenge.api_financas.dto.receita.DadosDetalhesReceitaDTO;
import br.com.alura.challenge.api_financas.exceptions.DescricaoExisteMesException;
import br.com.alura.challenge.api_financas.exceptions.MesIncorretoException;
import br.com.alura.challenge.api_financas.model.Receita;
import br.com.alura.challenge.api_financas.repository.ReceitaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReceitaService {

    private final ReceitaRepository repository;

    public ReceitaService(ReceitaRepository repository) {
        this.repository = repository;
    }

    public Page<DadosDetalhesReceitaDTO> buscaTodasReceitas(String descricao, Pageable paginacao) {
        if(descricao != null && !descricao.isEmpty()) {
            return repository.findByDescricaoContainingIgnoreCase(descricao, paginacao).map(DadosDetalhesReceitaDTO::new);
        }
        return repository.findAll(paginacao).map(DadosDetalhesReceitaDTO::new);
    }

    public DadosDetalhesReceitaDTO obterPorId(@NotNull Long id) {
        Receita receita = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada para o ID: " + id));

        return new DadosDetalhesReceitaDTO(receita);
    }

    public Page<DadosDetalhesReceitaDTO> obterPorAnoMes(@NotNull int ano, @NotNull int mes, Pageable paginacao) {
        if (mes < 1 || mes > 12) {
            throw new MesIncorretoException("O mês deve estar entre 1 e 12.");
        }

        LocalDateTime start = LocalDateTime.of(ano, mes, 1, 0, 0, 0); // Primeiro dia do mês à meia-noite
        LocalDateTime end = start.plusMonths(1).minusSeconds(1); // Último segundo do mês

        return repository.findByDataBetween(start, end, paginacao).map(DadosDetalhesReceitaDTO::new);
    }

    public DadosDetalhesReceitaDTO novaReceita(DadosCadastroReceitaDTO dto) {
        validaReceitaDescricaoExisteMesmoMes(dto.descricao(), dto.data().getMonthValue(), null);

        Receita receita = new Receita(dto);
        repository.save(receita);

        return new DadosDetalhesReceitaDTO(receita);
    }

    public DadosDetalhesReceitaDTO atualizaReceita(Long id, DadosAtualizaReceitaDTO dto) {
        validaReceitaDescricaoExisteMesmoMes(dto.descricao(), dto.data().getMonthValue(), id);
        Receita receita = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada para o ID: " + id));

        receita.atualizar(dto);

        return new DadosDetalhesReceitaDTO(receita);
    }

    public void excluirReceita(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Receita não encontrada para o ID: " + id);
        }

        repository.deleteById(id);
    }

    //MOVER PARA UMA CLASSE DE VALIDAÇÃO
    public void validaReceitaDescricaoExisteMesmoMes(String descricao, int mes, Long id) {
        if(repository.existsByDescricaoEMesmoMes(descricao, mes, id)) {
            throw new DescricaoExisteMesException("Já existe essa descrição de receita para o mês escolhido.");
        }
    }
}
