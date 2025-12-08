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

    private Connection conexao;

    public OrcamentoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Se o ID for 0, realiza INSERT. Se for diferente de 0, realiza UPDATE.
     */
    public void salvar(Orcamento orcamento) throws SQLException {
        if (orcamento.getId() == 0) {
            inserir(orcamento);
        } else {
            atualizar(orcamento);
        }
    }

    private void inserir(Orcamento orcamento) throws SQLException {
        String sql = "INSERT INTO orcamento (nome) VALUES (?)";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, orcamento.getNome());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    orcamento.setId(rs.getInt(1));
                }
            }
        }
    }

    private void atualizar(Orcamento orcamento) throws SQLException {
        String sql = "UPDATE orcamento SET nome = ? WHERE id = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, orcamento.getNome());
            ps.setInt(2, orcamento.getId());
            ps.executeUpdate();
        }
    }

    public List<Orcamento> listarTodos() throws SQLException {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM orcamento ORDER BY nome";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapearOrcamento(rs));
            }
        }
        return lista;
    }

    public Orcamento buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM orcamento WHERE nome = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearOrcamento(rs);
                }
            }
        }
        return null;
    }

    /**
     * Converte uma linha do ResultSet em um objeto Orcamento.
     */
    private Orcamento mapearOrcamento(ResultSet rs) throws SQLException {
        Orcamento orcamento = new Orcamento();
        orcamento.setId(rs.getInt("id"));
        orcamento.setNome(rs.getString("nome"));
        return orcamento;
    }
}