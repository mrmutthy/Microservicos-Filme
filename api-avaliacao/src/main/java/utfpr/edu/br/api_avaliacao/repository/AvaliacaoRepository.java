package utfpr.edu.br.api_avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.api_avaliacao.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
