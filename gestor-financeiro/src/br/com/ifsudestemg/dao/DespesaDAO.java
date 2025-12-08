package br.com.ifsudestemg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.ifsudestemg.model.Despesa;

public class DespesaDAO {

    private Connection conexao;

    public DespesaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(Despesa despesa) throws SQLException {
        if (despesa.getId() == 0) {
            inserir(despesa);
        } else {
            atualizar(despesa);
        }
    }

    private void inserir(Despesa despesa) throws SQLException {
        String sql = "INSERT INTO despesa (id_orcamento, mes_referencia, data_despesa, descricao, valor, situacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, despesa.getIdOrcamento());
            ps.setString(2, despesa.getMesReferencia());
            ps.setDate(3, despesa.getData());
            ps.setString(4, despesa.getDescricao());
            ps.setDouble(5, despesa.getValor());
            ps.setString(6, despesa.getSituacao());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    despesa.setId(rs.getInt(1));
                }
            }
        }
    }

    private void atualizar(Despesa despesa) throws SQLException {
        String sql = "UPDATE despesa SET data_despesa = ?, descricao = ?, valor = ?, situacao = ? WHERE id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setDate(1, despesa.getData());
            ps.setString(2, despesa.getDescricao());
            ps.setDouble(3, despesa.getValor());
            ps.setString(4, despesa.getSituacao());
            ps.setInt(5, despesa.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM despesa WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Despesa> listarPorMes(int idOrcamento, String mesReferencia) throws SQLException {
        List<Despesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM despesa WHERE id_orcamento = ? AND mes_referencia = ? ORDER BY data_despesa";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idOrcamento);
            ps.setString(2, mesReferencia);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Calcula o balanço mensal direto no Banco de Dados.
     * Retorna um array onde:
     * [0] = Total Mensal (Soma de tudo)
     * [1] = Total Pago (Soma onde situação não é vazia)
     * [2] = Total a Pagar (Total - Pago)
     * [3] = Saldo
     */
    public double[] buscarTotais(int idOrcamento, String mesReferencia) throws SQLException {
        String sql = "SELECT "
                   + "SUM(valor) as total_geral, "
                   + "SUM(CASE WHEN situacao IS NOT NULL AND situacao != '' THEN valor ELSE 0 END) as total_pago "
                   + "FROM despesa "
                   + "WHERE id_orcamento = ? AND mes_referencia = ?";

        double[] totais = new double[3];

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idOrcamento);
            ps.setString(2, mesReferencia);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totais[0] = rs.getDouble("total_geral");
                    totais[1] = rs.getDouble("total_pago");
                    totais[2] = totais[0] - totais[1];
                }
            }
        }
        return totais;
    }

    private Despesa mapear(ResultSet rs) throws SQLException {
        Despesa d = new Despesa();
        d.setId(rs.getInt("id"));
        d.setIdOrcamento(rs.getInt("id_orcamento"));
        d.setMesReferencia(rs.getString("mes_referencia"));
        d.setData(rs.getDate("data_despesa")); 
        d.setDescricao(rs.getString("descricao"));
        d.setValor(rs.getDouble("valor"));
        d.setSituacao(rs.getString("situacao"));
        return d;
    }
}