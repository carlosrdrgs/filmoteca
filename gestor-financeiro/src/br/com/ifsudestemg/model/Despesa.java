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
	
	public String getMesReferencia() {
		return mesReferencia;
	}
	public LocalDate getData() {
		return data;
	}
}
