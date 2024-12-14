package br.edu.utfpr.api_avaliacao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avaliacao_seq")
	@SequenceGenerator(name = "avaliacao_seq", sequenceName = "avaliacao_seq", allocationSize = 1)
	Long id;

	String titulo;
	String comentario;
	Integer nota;

	Long filme;
	Long usuario;

}
