package br.com.alura.challenge.api_financas.repository;

import br.com.alura.challenge.api_financas.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query("""
            select count(r.id) > 0
            from Receita r
            where r.descricao = :descricao
            and month(r.data) = :mes
            and (:id is null or r.id != :id)
            """)
    boolean existsByDescricaoEMesmoMes(String descricao, int mes, Long id);
}
