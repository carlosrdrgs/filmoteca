package br.com.ifsudestemg.model;

public class Receita implements Comparable<Receita>{
	private int id;
	private int idOrcamento;
	private String mes;
	private String tipo;
	private double valor;

	public Receita() {
		this(0,0,"","",0f);
	}

	public Receita(int id, int idOrcamento, String mes, String tipo, double valor) {
		this.id = id;
		this.idOrcamento = idOrcamento;
		this.mes = mes;
		this.tipo = tipo;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdOrcamento() {
		return idOrcamento;
	}

	public void setIdOrcamento(int idOrcamento) {
		this.idOrcamento = idOrcamento;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "ID Receita =" + id + " | idOrcamento= " + idOrcamento + " | mes =" + mes + " | tipo=" + tipo + " | valor="
				+ valor;
	}

	@Override
	public int compareTo(Receita outro) {
		return this.mes.compareToIgnoreCase(outro.mes);
	}

	
	
}
