package br.com.alura.challenge.api_financas.controller;

import br.com.alura.challenge.api_financas.dto.receita.DadosAtualizaReceitaDTO;
import br.com.alura.challenge.api_financas.dto.receita.DadosCadastroReceitaDTO;
import br.com.alura.challenge.api_financas.dto.receita.DadosDetalhesReceitaDTO;
import br.com.alura.challenge.api_financas.service.ReceitaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService service;

    public ReceitaController(ReceitaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<DadosDetalhesReceitaDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return service.buscaTodasReceitas(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesReceitaDTO> detalhar(@PathVariable @NotNull Long id) {
        DadosDetalhesReceitaDTO dto = service.obterPorId(id);
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping
    public ResponseEntity<DadosDetalhesReceitaDTO> cadastrar(@Valid @RequestBody DadosCadastroReceitaDTO dto) {
        DadosDetalhesReceitaDTO receita = service.novaReceita(dto);
        return ResponseEntity.ok().body(receita);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhesReceitaDTO> atualizar(@Valid @PathVariable @NotNull Long id,
                                                             @RequestBody DadosAtualizaReceitaDTO dto) {
        DadosDetalhesReceitaDTO receita = service.atualizaReceita(id, dto);
        return ResponseEntity.ok().body(receita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable @NotNull Long id) {
        service.excluirReceita(id);
        return ResponseEntity.noContent().build();
    }
}
