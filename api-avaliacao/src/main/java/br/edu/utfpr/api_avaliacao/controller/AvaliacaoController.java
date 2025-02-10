package br.edu.utfpr.api_avaliacao.controller;

import br.edu.utfpr.api_avaliacao.dtos.AvaliacaoDTO;
import br.edu.utfpr.api_avaliacao.dtos.MediaDTO;
import br.edu.utfpr.api_avaliacao.model.Avaliacao;
import br.edu.utfpr.api_avaliacao.repository.AvaliacaoRepository;
import br.edu.utfpr.api_avaliacao.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de avaliações")
public class AvaliacaoController {

    private AvaliacaoRepository repository;
    private AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoRepository repository, AvaliacaoService avaliacaoService) {
        this.repository = repository;
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    @Operation(
        summary = "Listar avaliações",
        description = "Retorna todas as avaliações cadastradas."
    )
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvaliacaoDTO.class)))
    public ResponseEntity<List<AvaliacaoDTO>> findAll() {
        List<AvaliacaoDTO> lista = this.repository.findAll()
            .stream()
            .map(avaliacao -> new AvaliacaoDTO(
                    avaliacao.getId(),
                    avaliacao.getTitulo(),
                    avaliacao.getComentario(),
                    avaliacao.getNota(),
                    avaliacao.getFilme(),
                    avaliacao.getUsuario()))
            .toList();

        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar avaliação por ID",
        description = "Retorna uma avaliação pelo ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Avaliação encontrada",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvaliacaoDTO.class)))
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    public ResponseEntity<AvaliacaoDTO> findById(
            @Parameter(description = "ID da avaliação") @PathVariable Long id) {
        Avaliacao avaliacao = repository.findById(id).orElse(null);

        if (avaliacao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                .body(new AvaliacaoDTO(
                    avaliacao.getId(),
                    avaliacao.getTitulo(),
                    avaliacao.getComentario(),
                    avaliacao.getNota(),
                    avaliacao.getFilme(),
                    avaliacao.getUsuario()));
        }
    }

    @PostMapping
    @Operation(
        summary = "Criar avaliação",
        description = "Cria uma nova avaliação com os dados fornecidos."
    )
    @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<String> create(@Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        if (avaliacaoDTO.nota() < 0 || avaliacaoDTO.nota() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A Nota deve ser entre 0 e 10");
        }

        Avaliacao avaliacao = new Avaliacao(
            avaliacaoDTO.id(),
            avaliacaoDTO.titulo(),
            avaliacaoDTO.comentario(),
            avaliacaoDTO.nota(),
            avaliacaoDTO.filme(),
            avaliacaoDTO.usuario());

        this.repository.save(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body("Filme avaliado com sucesso!");
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar avaliação",
        description = "Atualiza os dados de uma avaliação existente pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    public ResponseEntity<String> update(
            @Parameter(description = "ID da avaliação a ser atualizada") @PathVariable Long id,
            @Valid @RequestBody AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = this.repository.findById(id).orElse(null);

        if (avaliacao != null) {
            avaliacao.setTitulo(avaliacaoDTO.titulo());
            avaliacao.setComentario(avaliacaoDTO.comentario());
            avaliacao.setNota(avaliacaoDTO.nota());

            this.repository.save(avaliacao);

            return ResponseEntity.status(HttpStatus.OK).body("Avaliação alterada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrada");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar avaliação",
        description = "Remove uma avaliação com o ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Avaliação deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID da avaliação a ser deletada") @PathVariable Long id) {
        Avaliacao avaliacao = repository.findById(id).orElse(null);

        if (avaliacao != null) {
            this.repository.delete(avaliacao);
            return ResponseEntity.status(HttpStatus.OK).body("Avaliação deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrada");
        }
    }

    @GetMapping("/titulo/{titulo}")
    @Operation(
        summary = "Buscar avaliações por título",
        description = "Retorna as avaliações que correspondem ao título informado."
    )
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvaliacaoDTO.class)))
    @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada")
    public ResponseEntity<List<AvaliacaoDTO>> getByTitulo(
            @Parameter(description = "Título da avaliação a ser buscada") @PathVariable("titulo") String titulo) {
        List<Avaliacao> lista = this.repository.findByTitulo(titulo);

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<AvaliacaoDTO> dtos = lista.stream()
            .map(avaliacao -> new AvaliacaoDTO(
                avaliacao.getId(),
                avaliacao.getTitulo(),
                avaliacao.getComentario(),
                avaliacao.getNota(),
                avaliacao.getFilme(),
                avaliacao.getUsuario()))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/media/{titulo}")
    @Operation(
        summary = "Obter média de notas por título",
        description = "Retorna a média das notas das avaliações para o título informado."
    )
    @ApiResponse(responseCode = "200", description = "Média calculada com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = MediaDTO.class)))
    @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada para o título")
    public ResponseEntity<MediaDTO> getAverageNotaByTitulo(
            @Parameter(description = "Título do filme para cálculo da média de notas") @PathVariable("titulo") String titulo) {
        Double media = repository.findAverageNotaByTitulo(titulo);

        if (media == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        MediaDTO mediaDTO = new MediaDTO(titulo, media);
        return ResponseEntity.ok(mediaDTO);
    }
}
