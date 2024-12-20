package br.com.alura.challenge.api_financas.repository;

import br.com.alura.challenge.api_financas.dto.resumo.DadosDetalhesResumoCategoriaMesDTO;
import br.com.alura.challenge.api_financas.model.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query("""
            select count(d.id) > 0
            from Despesa d
            where d.descricao = :descricao
            and month(d.data) = :mes
            and (:id is null or d.id != :id)
            """)
    boolean existsByDescricaoEMesmoMes(String descricao, int mes, Long id);

    Page<Despesa> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

    @Query("SELECT d FROM Despesa d WHERE d.data BETWEEN :start AND :end")
    Page<Despesa> findByDataBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.data BETWEEN :start AND :end")
    BigDecimal findValorByDataBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT new br.com.alura.challenge.api_financas.dto.resumo.DadosDetalhesResumoCategoriaMesDTO(d.categoria, SUM(d.valor))
            FROM Despesa d
            WHERE d.data BETWEEN :start AND :end
            GROUP BY d.categoria
            """)
    List<DadosDetalhesResumoCategoriaMesDTO> findValorTotalByCategoria(LocalDateTime start, LocalDateTime end);
}
