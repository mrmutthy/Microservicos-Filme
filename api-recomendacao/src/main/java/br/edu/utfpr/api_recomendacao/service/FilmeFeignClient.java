package br.edu.utfpr.api_recomendacao.service;

import br.edu.utfpr.api_recomendacao.dto.FilmeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "api-filme", url = "http://localhost:8080")
public interface FilmeFeignClient {

    @GetMapping("/filmes")
    List<FilmeDTO> getAllFilmes();

    @GetMapping("/filmes/{id}")
    FilmeDTO getFilmeById(@PathVariable("id") Long id);
}
