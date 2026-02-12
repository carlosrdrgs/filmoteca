package br.com.filmoteca.model;

public enum TipoMidia {
    BLURAY("Blu-ray"),
    DVD("DVD"),
    INDEFINIDO("Indefinido");

    private final String descricao;

    TipoMidia(){
    	this("<Sem Descrição>");
    }
    
    TipoMidia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}