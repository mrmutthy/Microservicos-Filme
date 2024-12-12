package utfpr.edu.br.api_avaliacao.dtos;

public class AvaliacaoDTO {
    private String titulo;
    private Double media;

    public AvaliacaoDTO(String titulo, Double media) {
        this.titulo = titulo;
        this.media = media;
    }

    // Getters e setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }
}