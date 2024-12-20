package br.com.alura.challenge.api_financas.service;

import br.com.alura.challenge.api_financas.dto.despesa.DadosAtualizaDespesaDTO;
import br.com.alura.challenge.api_financas.dto.despesa.DadosCadastroDespesaDTO;
import br.com.alura.challenge.api_financas.dto.despesa.DadosDetalhesDespesaDTO;
import br.com.alura.challenge.api_financas.exceptions.DescricaoExisteMesException;
import br.com.alura.challenge.api_financas.model.Despesa;
import br.com.alura.challenge.api_financas.repository.DespesaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DespesaService {

    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository) {
        this.repository = repository;
    }

    public Page<DadosDetalhesDespesaDTO> buscaTodasDespesas(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosDetalhesDespesaDTO::new);
    }

    public DadosDetalhesDespesaDTO obterPorId(@NotNull Long id) {
        Despesa despesa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada para o ID: " + id));

        return new DadosDetalhesDespesaDTO(despesa);
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

        despesa.atualizar(dto);

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
