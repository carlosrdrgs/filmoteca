package br.com.ifsudestemg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements AutoCloseable {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/orcamentos";
    private static final String USUARIO = "admin";
    private static final String SENHA = "pf@orcamento";

    private Connection conexao;

    public Database() throws SQLException {
        try {
            Class.forName(DRIVER);
            this.conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            this.conexao.setAutoCommit(true);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL não foi encontrado na biblioteca.", e);
        }
    }

    public Connection getConexao() {
        return conexao;
    }

    @Override
    public void close() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com o banco: " + e.getMessage());
        }
    }
}