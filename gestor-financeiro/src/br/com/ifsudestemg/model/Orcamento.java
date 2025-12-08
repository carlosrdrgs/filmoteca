package br.com.ifsudestemg.model;

public class Orcamento implements Comparable<Orcamento>{
	private int id;
	private String nome;
	
	public Orcamento() {
		this(0,"");
	}

	public Orcamento(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "ID Orcamento = " + id + "| nome = " + nome;
	}

	@Override
	public int compareTo(Orcamento outro) {
		return this.nome.compareToIgnoreCase(outro.nome);
	}
}
