package br.com.ifsudestemg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.ifsudestemg.model.Receita;

public class ReceitaDAO {

    private Connection conexao;

    public ReceitaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Salva a receita. Se ID == 0 insere, caso contrário atualiza.
     */
    public void salvar(Receita receita) throws SQLException {
        if (receita.getId() == 0) {
            inserir(receita);
        } else {
            atualizar(receita);
        }
    }

    private void inserir(Receita receita) throws SQLException {
        String sql = "INSERT INTO receita (id_orcamento, mes, tipo, valor) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, receita.getIdOrcamento());
            ps.setString(2, receita.getMes());
            ps.setString(3, receita.getTipo());
            ps.setDouble(4, receita.getValor());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    receita.setId(rs.getInt(1));
                }
            }
        }
    }

    private void atualizar(Receita receita) throws SQLException {
        String sql = "UPDATE receita SET mes = ?, tipo = ?, valor = ? WHERE id = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, receita.getMes());
            ps.setString(2, receita.getTipo());
            ps.setDouble(3, receita.getValor());
            ps.setInt(4, receita.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM receita WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Receita> listarPorMes(int idOrcamento, String mes) throws SQLException {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT * FROM receita WHERE id_orcamento = ? AND mes = ? ORDER BY tipo";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idOrcamento);
            ps.setString(2, mes);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Método auxiliar para evitar redundância na extração de dados.
     */
    private Receita mapear(ResultSet rs) throws SQLException {
        Receita r = new Receita();
        r.setId(rs.getInt("id"));
        r.setIdOrcamento(rs.getInt("id_orcamento"));
        r.setMes(rs.getString("mes"));
        r.setTipo(rs.getString("tipo"));
        r.setValor(rs.getDouble("valor"));
        return r;
    }
}