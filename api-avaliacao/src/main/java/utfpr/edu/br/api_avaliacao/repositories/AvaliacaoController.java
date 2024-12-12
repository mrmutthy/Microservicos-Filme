package utfpr.edu.br.api_avaliacao.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utfpr.edu.br.api_avaliacao.dtos.AvaliacaoDTO;
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
        if (avaliacao.getTitulo() == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do filme é obrigatorio");

        } else if (avaliacao.getNota() > 5 || avaliacao.getNota() < 0 ) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A Nota deve ser entre 0 e 5");

        } else{

            this.repository.save(avaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body("Filme avaliado com sucesso!");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        Avaliacao avaliacaoUpdate = repository.findById(id).orElse(null);
        if (avaliacaoUpdate != null) {
            avaliacaoUpdate.setTitulo(avaliacao.getTitulo());
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

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Avaliacao>> getByTitulo(@PathVariable("titulo") String titulo){
        List<Avaliacao> lista = this.repository.findByTitulo(titulo);
        if (lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/media/{titulo}")
    public ResponseEntity<AvaliacaoDTO> getAverageNotaByTitulo(@PathVariable("titulo") String titulo) {
        Double media = repository.findAverageNotaByTitulo(titulo);
        if (media == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        AvaliacaoDTO dto = new AvaliacaoDTO(titulo, media);
        return ResponseEntity.ok(dto);
    }

}
