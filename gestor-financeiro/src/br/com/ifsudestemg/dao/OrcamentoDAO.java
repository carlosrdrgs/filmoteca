package br.com.ifsudestemg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.ifsudestemg.model.Orcamento;

public class OrcamentoDAO {
	Connection connection;

	public OrcamentoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void salvar(Orcamento orcamento) {
		String sql = "INSERT INTO orcamento (nome_orcamento) VALUES (?)";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, orcamento.getNome());
			
			preparedStatement.executeUpdate();
			
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                orcamento.setId(generatedKeys.getLong(1));
	            }
	        }
			
		} catch (Exception e) {
		}
	}
	
	public List<Orcamento> getOrcamentos() throws SQLException {
		String sql = "SELECT * FROM orcamento ORDER BY nome_orcamento";
		List<Orcamento> orcamentos = new ArrayList<Orcamento>();
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Orcamento orcamento = new Orcamento();
				orcamento.setId(resultSet.getLong("id"));
				orcamento.setNome(resultSet.getString("nome"));
				
				orcamentos.add(orcamento);
			}
		} catch (SQLException e) {
			throw new SQLException("Erro na busca de todos os Orcamentos no método \\\"getOrcamentos\\\"");
		}
		return orcamentos;
	}
	
	public Orcamento buscarPorNome(String nome) throws SQLException {
		String sql = "SELECT * FROM orcamento WHERE nome_orcamento = ?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setString(1, nome);
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				
				if (resultSet.next()) {
					Orcamento orcamento = new Orcamento();
					
					orcamento.setId(resultSet.getLong("id"));
					orcamento.setNome(resultSet.getString("nome_orcamento"));
					
					return orcamento;
				}
			}
			
		} catch (SQLException e) {
			throw new SQLException("Erro na busca de Orcamento no método \\\"buscarPorNome\\\"");
		}
		return null;
	}
	
	
}
