package br.edu.utfpr.api_filme.model;

import jakarta.persistence.*;

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

    public Filme(Long id, String titulo, String autor, String genero, String dataLancamento, String sinopse) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.dataLancamento = dataLancamento;
        this.sinopse = sinopse;
    }

    public Filme() {

    }

    //Getters




    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    // Setters


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

}
