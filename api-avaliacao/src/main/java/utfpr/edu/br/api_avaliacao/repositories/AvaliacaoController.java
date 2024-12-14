package utfpr.edu.br.api_avaliacao.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utfpr.edu.br.api_avaliacao.dtos.AvaliacaoDTO;
import utfpr.edu.br.api_avaliacao.dtos.MediaDTO;
import utfpr.edu.br.api_avaliacao.model.Avaliacao;
import utfpr.edu.br.api_avaliacao.repository.AvaliacaoRepository;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

	private AvaliacaoRepository repository;

	public AvaliacaoController(AvaliacaoRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public ResponseEntity<List<AvaliacaoDTO>> findAll() {
		List<AvaliacaoDTO> lista = this.repository.findAll().stream().map(avaliacao -> new AvaliacaoDTO(avaliacao.getId(), avaliacao.getTitulo(), avaliacao.getComentario(), avaliacao.getNota())).toList();

		return ResponseEntity.ok().body(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AvaliacaoDTO> findById(@RequestParam Long id) {
		Avaliacao avaliacao = repository.findById(id).orElse(null);

		if (avaliacao == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new AvaliacaoDTO(avaliacao.getId(), avaliacao.getTitulo(), avaliacao.getComentario(), avaliacao.getNota()));
		}
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody AvaliacaoDTO avaliacaoDTO) {
		/*

        if (avaliacao.getTitulo() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do filme é obrigatorio");
        } else if (avaliacao.getNota() > 5 || avaliacao.getNota() < 0 ) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A Nota deve ser entre 0 e 5");
        }

		*/

		Avaliacao avaliacao = new Avaliacao(avaliacaoDTO.id(), avaliacaoDTO.titulo(), avaliacaoDTO.comentario(), avaliacaoDTO.nota());

		this.repository.save(avaliacao);

		return ResponseEntity.status(HttpStatus.CREATED).body("Filme avaliado com sucesso!");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody AvaliacaoDTO avaliacaoDTO) {
		Avaliacao avaliacao = this.repository.findById(id).orElse(null);

		if (avaliacao != null) {
			avaliacao.setTitulo(avaliacaoDTO.titulo());
			avaliacao.setComentario(avaliacaoDTO.comentario());
			avaliacao.setNota(avaliacao.getNota());

			this.repository.save(avaliacao);

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
	public ResponseEntity<List<AvaliacaoDTO>> getByTitulo(@PathVariable("titulo") String titulo) {
		List<Avaliacao> lista = this.repository.findByTitulo(titulo);

		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		return ResponseEntity.ok(lista.stream().map(avaliacao -> new AvaliacaoDTO(avaliacao.getId(), avaliacao.getTitulo(), avaliacao.getComentario(), avaliacao.getNota())).toList());
	}

	@GetMapping("/media/{titulo}")
	public ResponseEntity<MediaDTO> getAverageNotaByTitulo(@PathVariable("titulo") String titulo) {
		Double media = repository.findAverageNotaByTitulo(titulo);

		if (media == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		MediaDTO mediaDTO = new MediaDTO(titulo, media);

		return ResponseEntity.ok(mediaDTO);
	}

}
