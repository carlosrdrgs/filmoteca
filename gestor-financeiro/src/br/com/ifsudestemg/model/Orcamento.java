package br.com.ifsudestemg.model;

public class Orcamento {
	private Long id;
	private String nome;
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "Orcamento [id=" + id + ", nome=" + nome + "]";
	}
}
