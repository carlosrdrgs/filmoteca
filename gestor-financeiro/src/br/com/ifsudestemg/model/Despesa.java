package br.com.ifsudestemg.model;

import java.sql.Date;

public class Despesa implements Comparable<Despesa>{
	private int id;
	private int idOrcamento;
	private String mesReferencia;
	private Date data;
	private String descricao;
	private double valor;
	private String situacao;

	public Despesa() {
		this(0,0,"", new Date(System.currentTimeMillis()),"",0f,"");
	}

	public Despesa(int id, int idOrcamento, String mesReferencia, Date data, String descricao, double valor,
			String situacao) {
		this.id = id;
		this.idOrcamento = idOrcamento;
		this.mesReferencia = mesReferencia;
		this.data = data;
		this.descricao = descricao;
		this.valor = valor;
		this.situacao = situacao;
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

	public String getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "ID Despesa = " + id + " | idOrcamento = " + idOrcamento + " | Mes Referencia = " + mesReferencia + " | Data = "
				+ data + " | Descricao = " + descricao + " | Valor = " + valor + " | Situacao = " + situacao;
	}

	@Override
	public int compareTo(Despesa o) {
		return this.data.compareTo(o.data);
	}


}
