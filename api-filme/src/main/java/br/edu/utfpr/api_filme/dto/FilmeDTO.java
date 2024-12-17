package br.edu.utfpr.api_filme.dto;

import java.util.Date;

public record FilmeDTO(Long id, String titulo, String autor, String genero, Date lancamento, String sinopse) {
}
