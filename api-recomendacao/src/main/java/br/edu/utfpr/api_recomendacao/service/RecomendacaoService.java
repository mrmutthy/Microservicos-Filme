package br.edu.utfpr.api_recomendacao.service;

import br.edu.utfpr.api_recomendacao.dto.FilmeDTO;
import br.edu.utfpr.api_recomendacao.dto.MediaDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    private final FilmeFeignClient filmeFeignClient;
    private final AvaliacaoFeignClient avaliacaoFeignClient;

    public RecomendacaoService(FilmeFeignClient filmeFeignClient, AvaliacaoFeignClient avaliacaoFeignClient) {
        this.filmeFeignClient = filmeFeignClient;
        this.avaliacaoFeignClient = avaliacaoFeignClient;
    }

    public List<FilmeDTO> getFilmesRecomendados(Double notaMinima) {
        List<FilmeDTO> todosFilmes = filmeFeignClient.getAllFilmes();

        return todosFilmes.stream().filter(filme -> {
            MediaDTO media = avaliacaoFeignClient.getMediaByTitulo(filme.titulo());
            return media != null && media.media() != null && media.media() >= notaMinima;
        }).collect(Collectors.toList());
    }
}
