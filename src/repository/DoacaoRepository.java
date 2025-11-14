package repository;

import config.DataBaseManager;
import model.Doacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoacaoRepository {

    private final Connection connection;

    public DoacaoRepository() {
        this.connection = DataBaseManager.obtemConexao();
    }

    // Inserir nova doação
    public void save(Doacao doacao) throws SQLException {
        String sql = """
            INSERT INTO doacoes
            (cpfDoador, nome_enfermeiro, tipo_sanguineo, alimentado, ultima_doacao,
             horas_sono, data_doacao, horario_doacao)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, doacao.getCpfDoador());
            stmt.setString(2, doacao.getNomeEnfermeiro());
            stmt.setString(3, doacao.getTipoSanguineo());
            stmt.setString(4, doacao.getAlimentado());
            stmt.setString(5, doacao.getUltimaDoacao());
            stmt.setString(6, doacao.getHorasSono());
            stmt.setString(7, doacao.getDataDoacao());
            stmt.setString(8, doacao.getHoraDoacao());
            stmt.executeUpdate();
        }
    }

    // Buscar doações por CPF
    public List<Doacao> findByCpf(String cpfDoador) throws SQLException {
        String sql = "SELECT * FROM doacoes WHERE cpfDoador = ?";
        List<Doacao> doacoes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpfDoador);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doacoes.add(mapResultSetToDoacao(rs));
                }
            }
        }

        return doacoes;
    }

    // Buscar todas as doações
    public List<Doacao> findAll() throws SQLException {
        String sql = "SELECT * FROM doacoes";
        List<Doacao> doacoes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                doacoes.add(mapResultSetToDoacao(rs));
            }
        }

        return doacoes;
    }

    // Atualizar doação pelo ID
    public void update(Doacao doacao, int idDoacao) throws SQLException {
        String sql = """
            UPDATE doacoes SET
            nome_enfermeiro = ?, cpfDoador = ?, tipo_sanguineo = ?, alimentado = ?, ultima_doacao = ?, 
            horas_sono = ?, data_doacao = ?, horario_doacao = ?
            WHERE id_doacao = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, doacao.getNomeEnfermeiro());
            stmt.setString(2, doacao.getCpfDoador());
            stmt.setString(3, doacao.getTipoSanguineo());
            stmt.setString(4, doacao.getAlimentado());
            stmt.setString(5, doacao.getUltimaDoacao());
            stmt.setString(6, doacao.getHorasSono());
            stmt.setString(7, doacao.getDataDoacao());
            stmt.setString(8, doacao.getHoraDoacao());
            stmt.setInt(9, idDoacao);
            stmt.executeUpdate();
        }
    }

    // Deletar doação pelo ID
    public void delete(int idDoacao) throws SQLException {
        String sql = "DELETE FROM doacoes WHERE id_doacao = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDoacao);
            stmt.executeUpdate();
        }
    }

    // Retorna a contagem de doações por tipo sanguíneo
    public Map<String, Integer> contarDoacoesPorTipoSanguineo() throws SQLException {
        String sql = "SELECT tipo_sanguineo, COUNT(*) AS quantidade FROM doacoes GROUP BY tipo_sanguineo";
        Map<String, Integer> resultado = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getString("tipo_sanguineo"), rs.getInt("quantidade"));
            }
        }

        return resultado;
    }

    // Mapeia ResultSet para objeto Doacao
    private Doacao mapResultSetToDoacao(ResultSet rs) throws SQLException {
        return new Doacao(
                rs.getString("nome_enfermeiro"),
                rs.getString("cpfDoador"),
                rs.getString("tipo_sanguineo"),
                rs.getString("data_doacao"),
                rs.getString("horario_doacao"),
                rs.getString("alimentado"),
                rs.getString("ultima_doacao"),
                rs.getString("horas_sono")
        );
    }
}

