package br.com.ifsudestemg.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesa {
	private Long id;
	private Long idOrcamento;
	private String mesReferencia;
	private LocalDate data;
	private String descricao;
	private BigDecimal valor;
	private String situacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdOrcamento() {
		return idOrcamento;
	}
	public void setIdOrcamento(Long idOrcamento) {
		this.idOrcamento = idOrcamento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getMesReferencia() {
		return mesReferencia;
	}
	public LocalDate getData() {
		return data;
	}
}
