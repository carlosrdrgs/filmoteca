package br.com.ifsudestemg.dao;

import java.sql.Connection;

import br.com.ifsudestemg.model.Receita;

public class ReceitaDAO {
	Connection connection;

	public ReceitaDAO() {
		this.connection = new ConnectionFactory().getConnection();;
	}
	
	public void salvarEAtualizar(Receita receita) {
		
	}
}
