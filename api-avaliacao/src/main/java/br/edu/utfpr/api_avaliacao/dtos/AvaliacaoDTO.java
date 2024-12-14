package br.edu.utfpr.api_avaliacao.dtos;

public record AvaliacaoDTO(Long id, String titulo, String comentario, Integer nota, Long filme, Long usuario) {
}
