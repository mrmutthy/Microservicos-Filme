package br.edu.utfpr.api_avaliacao.service;

import br.edu.utfpr.api_avaliacao.dtos.FilmeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="api-filme", url="localhost:8082")
public interface FilmeFeignClient {

	@GetMapping("/filmes/{id}")
	FilmeDTO getFilmeById(@PathVariable Long id);

}
