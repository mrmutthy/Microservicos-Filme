package br.edu.utfpr.api_filme.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter


public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "livro-seq")
    @SequenceGenerator(name = "livro-seq", sequenceName = "livro_seq", allocationSize = 1)

    private Long id;
    private String Titulo;
    private String Autor;
    private String genero;
    private String DataLancamento;
    private String Sinopse;

    public Filme(Long id, String Titulo, String Autor, String genero, String DataLancamento, String Sinopse) {
        this.id = id;
        this.Titulo = Titulo;
        this.Autor = Autor;
        this.genero = genero;
        this.DataLancamento = DataLancamento;
        this.Sinopse = Sinopse;
    }

    public Filme() {

    }

    //Getters


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public String getgenero() {
        return genero;
    }

    public String getDataLancamento() {
        return DataLancamento;
    }

    public String getSinopse() {
        return Sinopse;
    }

    // Setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String Titulo) {
        Titulo = Titulo;
    }

    public void setAutor(String Autor) {
        Autor = Autor;
    }

    public void setgenero(String genero) {
        genero = genero;
    }

    public void setDataLancamento(String DataLancamento) {
        DataLancamento = DataLancamento;
    }

    public void setSinopse(String Sinopse) {
        Sinopse = Sinopse;
    }

}
