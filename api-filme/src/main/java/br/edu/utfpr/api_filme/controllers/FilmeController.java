package br.edu.utfpr.api_filme.controllers;

import br.edu.utfpr.api_filme.dto.FilmeDTO;
import br.edu.utfpr.api_filme.model.Filme;
import br.edu.utfpr.api_filme.repositories.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private FilmeRepository repository;

    // Adicionar construtor com parâmetro FilmeRepository
    // Spring injetará uma instância repository
    public FilmeController(FilmeRepository repository) {
        this.repository = repository;
    }

    // Endpoinst

    //pegar todos
    @GetMapping
    public ResponseEntity<List<Filme>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    //pegar um
    @GetMapping("/{id}")
    public ResponseEntity<Filme> getById(@PathVariable(name = "id") Long idTitulo) {
        Filme filmeEncontrado = this.repository.findById(idTitulo).orElse(null);

        if (filmeEncontrado == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(filmeEncontrado);
    }

    //adicionar filme
    @PostMapping
    public ResponseEntity<String> addOne(@RequestBody FilmeDTO filmeDTO) {
		if (filmeDTO.titulo() == null || filmeDTO.titulo().isEmpty()
			|| filmeDTO.genero() == null || filmeDTO.genero().isEmpty()
			|| filmeDTO.autor() == null || filmeDTO.autor().isEmpty()
			|| filmeDTO.sinopse() == null || filmeDTO.sinopse().isEmpty()
			|| filmeDTO.lancamento() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filme invalido");
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

    //Alterar um filme
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOne(@PathVariable(name = "id") Long idTitulo, @RequestBody Filme filme) {
        Filme filmeDB = this.repository.findById(idTitulo).orElse(null);

        if (filmeDB != null){
            filmeDB.setTitulo(filme.getTitulo());
            filmeDB.setAutor(filme.getAutor());
            filmeDB.setGenero(filme.getGenero());
            filmeDB.setDataLancamento(filme.getDataLancamento());
            filmeDB.setSinopse(filme.getSinopse());

            this.repository.save(filmeDB);
            return ResponseEntity.status(HttpStatus.OK).body("Filme alterado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme nao encontrado");
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable(name = "id") Long idTitulo) {
        Filme filmeDB = this.repository.findById(idTitulo).orElse(null);

        if (filmeDB != null){
            this.repository.delete(filmeDB);
            return ResponseEntity.status(HttpStatus.OK).body("Filme deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme nao encontrado");
        }
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Filme>> getByGenero(@PathVariable("genero") String genero){
        List<Filme> lista = this.repository.findByGenero(genero);
        if (lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

}
