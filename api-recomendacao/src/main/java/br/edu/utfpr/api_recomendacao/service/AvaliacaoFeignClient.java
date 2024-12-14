package br.edu.utfpr.api_recomendacao.service;

import br.edu.utfpr.api_recomendacao.dto.MediaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-avaliacao", url = "http://localhost:8080")
public interface AvaliacaoFeignClient {

    @GetMapping("/avaliacoes/media/{titulo}")
    MediaDTO getMediaByTitulo(@PathVariable("titulo") String titulo);
}
