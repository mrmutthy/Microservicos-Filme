package utfpr.edu.br.api_avaliacao.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utfpr.edu.br.api_avaliacao.model.Avaliacao;
import utfpr.edu.br.api_avaliacao.repository.AvaliacaoRepository;

import java.util.List;


@RestController
@RequestMapping("/avaliacao")

public class AvaliacaoController {
    private AvaliacaoRepository repository;

    public AvaliacaoController(AvaliacaoRepository repository) { this.repository = repository; }


    @GetMapping
    public ResponseEntity<List<Avaliacao>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> findById(@RequestParam Long id) {
        Avaliacao avaliacao = repository.findById(id).orElse(null);
        if (avaliacao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(avaliacao);
    }
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Avaliacao avaliacao) {
        if (avaliacao.getNota() == null || avaliacao.getComentario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deixe sua Avaliação");
        } else {

            this.repository.save(avaliacao);

            return ResponseEntity.status(HttpStatus.CREATED).body("Filme avaliado com sucesso!");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        Avaliacao avaliacaoUpdate = repository.findById(id).orElse(null);
        if (avaliacaoUpdate != null) {
            avaliacaoUpdate.setNota(avaliacao.getNota());
            avaliacaoUpdate.setComentario(avaliacao.getComentario());
            this.repository.save(avaliacaoUpdate);
            return ResponseEntity.status(HttpStatus.OK).body("Avaliação alterada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrada");
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Avaliacao avaliacao = repository.findById(id).orElse(null);
        if (avaliacao != null) {
            this.repository.delete(avaliacao);
            return ResponseEntity.status(HttpStatus.OK).body("Avaliação deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrada");
        }
    }

}
