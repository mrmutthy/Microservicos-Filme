package br.edu.utfpr.api_recomendacao.controller;

import br.edu.utfpr.api_recomendacao.dto.FilmeDTO;
import br.edu.utfpr.api_recomendacao.service.RecomendacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoController(RecomendacaoService recomendacaoService) {
        this.recomendacaoService = recomendacaoService;
    }

    @GetMapping
    public ResponseEntity<List<FilmeDTO>> getFilmesRecomendados(
            @RequestParam(defaultValue = "8.0") Double notaMinima) {
        List<FilmeDTO> recomendados = recomendacaoService.getFilmesRecomendados(notaMinima);
        if (recomendados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recomendados);
    }
}
