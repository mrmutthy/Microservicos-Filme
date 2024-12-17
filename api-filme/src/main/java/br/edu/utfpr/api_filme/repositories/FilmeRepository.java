package br.edu.utfpr.api_filme.repositories;

import br.edu.utfpr.api_filme.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

	List<Filme> findByGenero(String genero);

}
