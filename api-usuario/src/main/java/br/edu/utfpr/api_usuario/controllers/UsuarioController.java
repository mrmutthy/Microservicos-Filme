package br.edu.utfpr.api_usuario.controllers;

import br.edu.utfpr.api_usuario.model.Usuario;
import br.edu.utfpr.api_usuario.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable(name = "id") Long id) {
		Usuario usuarioEncontrado = this.repository.findById(id).orElse(null);

		if (usuarioEncontrado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.ok().body(usuarioEncontrado);
		}
    }

    @Transactional
    @PostMapping
    public ResponseEntity<String> addOne(@RequestBody Usuario usuario) {
//        if (filme.getTitulo() == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filme invalido");
//        } else {
//
//            this.repository.save(filme);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body("Filme adicionado com sucesso!");
//        }

        return ResponseEntity.ok().body("");
   }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOne(@PathVariable(name = "id") Long idTitulo, @RequestBody Usuario usuario) {
//        Filme filmeDB = this.repository.findById(idTitulo).orElse(null);
//
//        if (filmeDB != null){
//            filmeDB.setTitulo(filme.getTitulo());
//            filmeDB.setAutor(filme.getAutor());
//            filmeDB.setGenero(filme.getGenero());
//            filmeDB.setDataLancamento(filme.getDataLancamento());
//            filmeDB.setSinopse(filme.getSinopse());
//
//            this.repository.save(filmeDB);
//            return ResponseEntity.status(HttpStatus.OK).body("Filme alterado com sucesso!");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme nao encontrado");
//        }

        return ResponseEntity.ok().body("");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable(name = "id") Long idTitulo) {
//        Filme filmeDB = this.repository.findById(idTitulo).orElse(null);
//
//        if (filmeDB != null){
//            this.repository.delete(filmeDB);
//            return ResponseEntity.status(HttpStatus.OK).body("Filme deletado com sucesso!");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme nao encontrado");
//        }

        return ResponseEntity.ok().body("");
    }

}
