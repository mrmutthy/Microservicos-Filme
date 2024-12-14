package br.edu.utfpr.api_avaliacao.service;

import br.edu.utfpr.api_avaliacao.dtos.AvaliacaoDTO;
import br.edu.utfpr.api_avaliacao.dtos.FilmeDTO;
import br.edu.utfpr.api_avaliacao.dtos.UsuarioDTO;
import br.edu.utfpr.api_avaliacao.model.Avaliacao;
import br.edu.utfpr.api_avaliacao.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

	private AvaliacaoRepository repository;
	private FilmeFeignClient filmeFeignClient;
	private UsuarioFeignClient usuarioFeignClient;

	public AvaliacaoService(AvaliacaoRepository repository, FilmeFeignClient filmeFeignClient, UsuarioFeignClient usuarioFeignClient) {
		this.repository = repository;
		this.filmeFeignClient = filmeFeignClient;
		this.usuarioFeignClient = usuarioFeignClient;
	}

	public Avaliacao getAvaliacaoById(Long id) {
		Avaliacao avaliacao = this.repository.findById(id).orElse(null);

		FilmeDTO filmeDTO = filmeFeignClient.getFilmeById(avaliacao.getFilme());
		UsuarioDTO usuarioDTO = usuarioFeignClient.getUsuarioById(avaliacao.getUsuario());

		return avaliacao;
	}

	public List<Avaliacao> getAvaliacaoForFilme(Long id) {
		return this.repository.findAll().stream().filter(avaliacao -> avaliacao.getFilme().equals(id)).toList();
	}

	public List<Avaliacao> getAvalicaoForUsuario(Long id) {
		return this.repository.findAll().stream().filter(avaliacao -> avaliacao.getUsuario().equals(id)).toList();
	}

	public Avaliacao createAvaliacao(AvaliacaoDTO avaliacaoDTO) {
		Avaliacao avaliacao = new Avaliacao();

		FilmeDTO filmeDTO = filmeFeignClient.getFilmeById(avaliacaoDTO.filme());
		UsuarioDTO usuarioDTO = usuarioFeignClient.getUsuarioById(avaliacaoDTO.usuario());

		return this.repository.save(avaliacao);
	}

}
