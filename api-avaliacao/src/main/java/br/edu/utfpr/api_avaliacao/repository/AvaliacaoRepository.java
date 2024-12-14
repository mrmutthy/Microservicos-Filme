package br.edu.utfpr.api_avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.edu.utfpr.api_avaliacao.model.Avaliacao;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByTitulo(String titulo);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.titulo = :titulo")
    Double findAverageNotaByTitulo(@Param("titulo") String titulo);

}
