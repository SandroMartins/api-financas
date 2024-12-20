package br.com.alura.challenge.api_financas.model;

import br.com.alura.challenge.api_financas.dto.receita.DadosAtualizaReceitaDTO;
import br.com.alura.challenge.api_financas.dto.receita.DadosCadastroReceitaDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

    public Receita() {
    }

    public Receita(Long id, String descricao, BigDecimal valor, LocalDateTime data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Receita(DadosCadastroReceitaDTO dadosCadastroReceitaDTO) {
        BeanUtils.copyProperties(dadosCadastroReceitaDTO, this);
    }

    public void atualizar(DadosAtualizaReceitaDTO dto) {
        this.descricao = dto.descricao();
        this.valor = dto.valor();
        this.data = dto.data();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Receita receita = (Receita) o;
        return id.equals(receita.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
