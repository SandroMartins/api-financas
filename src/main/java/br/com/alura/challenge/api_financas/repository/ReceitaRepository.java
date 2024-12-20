package br.com.alura.challenge.api_financas.repository;

import br.com.alura.challenge.api_financas.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query("""
            select count(r.id) > 0
            from Receita r
            where r.descricao = :descricao
            and month(r.data) = :mes
            and (:id is null or r.id != :id)
            """)
    boolean existsByDescricaoEMesmoMes(String descricao, int mes, Long id);

    Page<Receita> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

    @Query("SELECT r FROM Receita r WHERE r.data BETWEEN :start AND :end")
    Page<Receita> findByDataBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT SUM(r.valor) FROM Receita r WHERE r.data BETWEEN :start AND :end")
    BigDecimal findValorByDataBetween(LocalDateTime start, LocalDateTime end);
}
