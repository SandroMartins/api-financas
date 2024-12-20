package br.com.alura.challenge.api_financas.repository;

import br.com.alura.challenge.api_financas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query("""
            select count(d.id) > 0
            from Despesa d
            where d.descricao = :descricao
            and month(d.data) = :mes
            and (:id is null or d.id != :id)
            """)
    boolean existsByDescricaoEMesmoMes(String descricao, int mes, Long id);
}
