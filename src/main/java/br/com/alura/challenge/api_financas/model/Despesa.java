package br.com.alura.challenge.api_financas.model;

import br.com.alura.challenge.api_financas.dto.despesa.DadosAtualizaDespesaDTO;
import br.com.alura.challenge.api_financas.dto.despesa.DadosCadastroDespesaDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "despesas")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Despesa() {
    }

    public Despesa(Long id, String descricao, BigDecimal valor, LocalDateTime data, Categoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public Despesa(DadosCadastroDespesaDTO dto) {
        this.descricao = dto.descricao();
        this.valor = dto.valor();
        this.data = dto.data();
        this.categoria = isCategoriaValida(dto.categoria()) ? dto.categoria() : Categoria.OUTRAS;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Despesa despesa = (Despesa) o;
        return id.equals(despesa.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void atualizarDados(DadosAtualizaDespesaDTO dto) {
        this.descricao = dto.descricao();
        this.valor = dto.valor();
        this.data = dto.data();
        this.categoria = isCategoriaValida(dto.categoria()) ? dto.categoria() : Categoria.OUTRAS;
    }

    public boolean isCategoriaValida(Categoria categoria) {
        return categoria != null && Arrays.stream(Categoria.values())
                .anyMatch(c -> c.equals(categoria));
    }
}
