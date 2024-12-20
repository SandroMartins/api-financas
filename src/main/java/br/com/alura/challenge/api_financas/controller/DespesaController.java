package br.com.alura.challenge.api_financas.controller;

import br.com.alura.challenge.api_financas.dto.despesa.DadosAtualizaDespesaDTO;
import br.com.alura.challenge.api_financas.dto.despesa.DadosCadastroDespesaDTO;
import br.com.alura.challenge.api_financas.dto.despesa.DadosDetalhesDespesaDTO;
import br.com.alura.challenge.api_financas.service.DespesaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService service;

    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<DadosDetalhesDespesaDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return service.buscaTodasDespesas(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesDespesaDTO> detalhar(@PathVariable @NotNull Long id) {
        DadosDetalhesDespesaDTO dto = service.obterPorId(id);
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping
    public ResponseEntity<DadosDetalhesDespesaDTO> cadastrar(@Valid @RequestBody DadosCadastroDespesaDTO dto) {
        DadosDetalhesDespesaDTO despesa = service.novaDespesa(dto);
        return ResponseEntity.ok().body(despesa);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhesDespesaDTO> atualizar(@Valid @PathVariable @NotNull Long id,
                                                             @RequestBody DadosAtualizaDespesaDTO dto) {
        DadosDetalhesDespesaDTO despesa = service.atualizaDespesa(id, dto);
        return ResponseEntity.ok().body(despesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable @NotNull Long id) {
        service.excluirDespesa(id);
        return ResponseEntity.noContent().build();
    }
}
