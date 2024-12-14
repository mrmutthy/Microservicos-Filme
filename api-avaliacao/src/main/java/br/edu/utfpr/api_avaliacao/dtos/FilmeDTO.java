package utfpr.edu.br.api_avaliacao.dtos;

import java.util.Date;

public record FilmeDTO (Long id, String titulo, String autor, String genero, Date lancamento, String sinopse) {}
