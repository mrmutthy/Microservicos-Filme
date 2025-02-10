package br.edu.utfpr.api_filme.controllers;

import br.edu.utfpr.api_filme.dto.FilmeDTO;
import br.edu.utfpr.api_filme.model.Filme;
import br.edu.utfpr.api_filme.repositories.FilmeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Endpoints para gerenciamento de filmes")
public class FilmeController {

    private FilmeRepository repository;

    // Construtor com injeção do FilmeRepository
    public FilmeController(FilmeRepository repository) {
        this.repository = repository;
    }

    // Endpoint para pegar todos os filmes
    @GetMapping
    @Operation(
        summary = "Listar todos os filmes",
        description = "Retorna todos os filmes cadastrados."
    )
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Filme.class)))
    public ResponseEntity<List<Filme>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    // Endpoint para pegar um filme por ID
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar filme por ID",
        description = "Retorna os detalhes do filme para o ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Filme encontrado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmeDTO.class)))
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    public ResponseEntity<FilmeDTO> getById(
            @Parameter(description = "ID do filme") @PathVariable(name = "id") Long id) {
        Filme filme = this.repository.findById(id).orElse(null);
        if (filme == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            FilmeDTO filmeDTO = new FilmeDTO(
                filme.getId(),
                filme.getTitulo(),
                filme.getAutor(),
                filme.getGenero(),
                filme.getDataLancamento(),
                filme.getSinopse());
            return ResponseEntity.status(HttpStatus.OK).body(filmeDTO);
        }
    }

    // Endpoint para adicionar um filme
    @PostMapping
    @Operation(
        summary = "Adicionar filme",
        description = "Adiciona um novo filme com os dados fornecidos."
    )
    @ApiResponse(responseCode = "201", description = "Filme adicionado com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Filme inválido")
    public ResponseEntity<String> addOne(@RequestBody FilmeDTO filmeDTO) {
        if (filmeDTO.titulo() == null || filmeDTO.titulo().isEmpty()
            || filmeDTO.genero() == null || filmeDTO.genero().isEmpty()
            || filmeDTO.autor() == null || filmeDTO.autor().isEmpty()
            || filmeDTO.sinopse() == null || filmeDTO.sinopse().isEmpty()
            || filmeDTO.lancamento() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filme inválido");
        }

        Filme filme = new Filme();
        filme.setTitulo(filmeDTO.titulo());
        filme.setSinopse(filmeDTO.sinopse());
        filme.setGenero(filmeDTO.genero());
        filme.setAutor(filmeDTO.autor());
        filme.setDataLancamento(filmeDTO.lancamento());

        this.repository.save(filme);

        return ResponseEntity.status(HttpStatus.CREATED).body("Filme adicionado com sucesso!");
    }

    // Endpoint para atualizar um filme existente
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar filme",
        description = "Atualiza os dados de um filme existente pelo ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Filme alterado com sucesso")
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    public ResponseEntity<String> updateOne(
            @Parameter(description = "ID do filme a ser atualizado") @PathVariable(name = "id") Long id,
            @RequestBody FilmeDTO filmeDTO) {
        Filme filmeDB = this.repository.findById(id).orElse(null);
        if (filmeDB != null) {
            filmeDB.setTitulo(filmeDTO.titulo());
            filmeDB.setAutor(filmeDTO.autor());
            filmeDB.setGenero(filmeDTO.genero());
            filmeDB.setDataLancamento(filmeDTO.lancamento());
            filmeDB.setSinopse(filmeDTO.sinopse());

            this.repository.save(filmeDB);
            return ResponseEntity.status(HttpStatus.OK).body("Filme alterado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
        }
    }

    // Endpoint para deletar um filme pelo ID
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar filme",
        description = "Exclui um filme pelo ID informado."
    )
    @ApiResponse(responseCode = "200", description = "Filme deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    public ResponseEntity<String> deleteOne(
            @Parameter(description = "ID do filme a ser deletado") @PathVariable(name = "id") Long id) {
        Filme filme = this.repository.findById(id).orElse(null);
        if (filme != null) {
            this.repository.delete(filme);
            return ResponseEntity.status(HttpStatus.OK).body("Filme deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
        }
    }

    // Endpoint para buscar filmes por gênero
    @GetMapping("/genero/{genero}")
    @Operation(
        summary = "Buscar filmes por gênero",
        description = "Retorna uma lista de filmes que correspondem ao gênero informado."
    )
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmeDTO.class)))
    @ApiResponse(responseCode = "204", description = "Nenhum filme encontrado para o gênero informado")
    public ResponseEntity<List<FilmeDTO>> getByGenero(
            @Parameter(description = "Gênero do filme") @PathVariable("genero") String genero) {
        List<Filme> lista = this.repository.findByGenero(genero);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<FilmeDTO> listaDTO = lista.stream()
                .map(filme -> new FilmeDTO(
                        filme.getId(),
                        filme.getTitulo(),
                        filme.getAutor(),
                        filme.getGenero(),
                        filme.getDataLancamento(),
                        filme.getSinopse()))
                .toList();
        return ResponseEntity.ok(listaDTO);
    }
}
