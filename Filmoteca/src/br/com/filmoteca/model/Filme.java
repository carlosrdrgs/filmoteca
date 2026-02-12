package br.com.filmoteca.model;

import java.time.LocalDate;
import java.util.List;

public class Filme {

	private String titulo;
	private int ano;
	private LocalDate dataLancamento;
	private String classificacaoIndicativa;
	private String duracao;
	private double avaliacaoIMDB;

	private String sinopse;
	private String diretor;

	private List<String> generos;
	private List<String> autores;
	private List<String> elenco;

	private TipoMidia midia;
	private int avaliacaoPessoal;

	private String caminhoImagem;

	public Filme() {
		this("Sem Título",0,LocalDate.now(),"Livre","0h 0m",0.0,"Sem Sinopse","Sem Diretor",
				List.of("Sem Gênero"),List.of("Sem Autores"),List.of("Sem Elenco"),TipoMidia.INDEFINIDO,0,"");
	}

	public Filme(String titulo, int ano, LocalDate dataLancamento, String classificacaoIndicativa, String duracao,
			double avaliacaoIMDB, String sinopse, String diretor, List<String> generos, List<String> autores,
			List<String> elenco, TipoMidia midia, int avaliacaoPessoal, String caminhoImagem) {
		this.titulo = titulo;
		this.ano = ano;
		this.dataLancamento = dataLancamento;
		this.classificacaoIndicativa = classificacaoIndicativa;
		this.duracao = duracao;
		this.avaliacaoIMDB = avaliacaoIMDB;
		this.sinopse = sinopse;
		this.diretor = diretor;
		this.generos = generos;
		this.autores = autores;
		this.elenco = elenco;
		this.midia = midia;
		this.avaliacaoPessoal = avaliacaoPessoal;
		this.caminhoImagem = caminhoImagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getClassificacaoIndicativa() {
		return classificacaoIndicativa;
	}

	public void setClassificacaoIndicativa(String classificacaoIndicativa) {
		this.classificacaoIndicativa = classificacaoIndicativa;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public double getAvaliacaoIMDB() {
		return avaliacaoIMDB;
	}

	public void setAvaliacaoIMDB(double avaliacaoIMDB) {
		this.avaliacaoIMDB = avaliacaoIMDB;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	public List<String> getAutores() {
		return autores;
	}

	public void setAutores(List<String> autores) {
		this.autores = autores;
	}

	public List<String> getElenco() {
		return elenco;
	}

	public void setElenco(List<String> elenco) {
		this.elenco = elenco;
	}

	public String getMidia() {
		return String.valueOf(midia); // Acho que isto esta encapsulado
	}

	public void setMidia(TipoMidia midia) {
		this.midia = midia;
	}

	public int getAvaliacaoPessoal() {
		return avaliacaoPessoal;
	}

	public void setAvaliacaoPessoal(int avaliacaoPessoal) {
		this.avaliacaoPessoal = avaliacaoPessoal;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}
}
