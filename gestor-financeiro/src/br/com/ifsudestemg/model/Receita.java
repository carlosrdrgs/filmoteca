package br.com.ifsudestemg.model;

import java.math.BigDecimal;

public class Receita {
	private Long id;
	private Long idOcamento;
	private String mes;
	private String ano;
	private BigDecimal valor;
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
