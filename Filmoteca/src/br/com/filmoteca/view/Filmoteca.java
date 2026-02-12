package br.com.filmoteca.view;

import br.com.filmoteca.control.FilmotecaController;

public class Filmoteca {
    public static void main(String[] args) {
    	IgFilmoteca igFilmoteca = new IgFilmoteca();
        new FilmotecaController(igFilmoteca);
        igFilmoteca.setVisible(true); // deixei no final de tudo
    }
}