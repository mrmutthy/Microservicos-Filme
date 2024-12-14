package br.edu.utfpr.api_filme.model;

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
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filme_seq")
    @SequenceGenerator(name = "filme_seq", sequenceName = "filme_seq", allocationSize = 1)
    private Long id;

    private String titulo;
    private String autor;
    private String genero;
    private String dataLancamento;
    private String sinopse;

}
