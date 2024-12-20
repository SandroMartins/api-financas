package br.com.alura.challenge.api_financas.controller;

import br.com.alura.challenge.api_financas.dto.resumo.DadosDetalhesResumoDTO;
import br.com.alura.challenge.api_financas.service.ResumoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

    private final ResumoService service;

    public ResumoController(ResumoService service) {
        this.service = service;
    }

    @GetMapping("/{ano}/{mes}")
    public DadosDetalhesResumoDTO detalharPorAnoMes(@PathVariable @NotNull int ano,
                                                    @PathVariable @NotNull int mes) {
        return service.resumoMes(ano, mes);
    }
}
