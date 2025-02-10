package br.edu.utfpr.api_recomendacao.controller;

import br.edu.utfpr.api_recomendacao.dto.FilmeDTO;
import br.edu.utfpr.api_recomendacao.service.RecomendacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recomendacoes")
@Tag(name = "Recomendações", description = "Endpoints para obter recomendações de filmes")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoController(RecomendacaoService recomendacaoService) {
        this.recomendacaoService = recomendacaoService;
    }

    @GetMapping
    @Operation(
        summary = "Obter recomendações de filmes",
        description = "Retorna uma lista de filmes recomendados com base na nota mínima informada. Se não houver recomendações, retorna um status 204 (No Content)."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Recomendações encontradas",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmeDTO.class))
    )
    @ApiResponse(responseCode = "204", description = "Nenhuma recomendação encontrada")
    public ResponseEntity<List<FilmeDTO>> getFilmesRecomendados(
            @Parameter(description = "Nota mínima para filtrar as recomendações. Valor padrão é 8.0")
            @RequestParam(defaultValue = "8.0") Double notaMinima) {
        List<FilmeDTO> recomendados = recomendacaoService.getFilmesRecomendados(notaMinima);
        if (recomendados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recomendados);
    }
}
