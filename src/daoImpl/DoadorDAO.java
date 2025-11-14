package daoImpl;

import config.DataBaseManager;
import model.Doador;
import repository.DoadorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoadorDAO implements DoadorRepository {

    @Override
    public void adicionar(Doador doador) {
        String sql = "INSERT INTO doadores (cpfDoador, idade, sexo, peso, nome) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseManager.obtemConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, doador.getCpfDoador());
            stmt.setInt(2, doador.getIdade());
            stmt.setString(3, doador.getSexo());
            stmt.setDouble(4, doador.getPeso());
            stmt.setString(5, doador.getNome());
            stmt.executeUpdate();

            System.out.println("✅ Doador cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar doador: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Doador> listarTodos() {
        List<Doador> doadores = new ArrayList<>();
        String sql = "SELECT * FROM doadores";

        try (Connection connection = DataBaseManager.obtemConexao();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doador doador = new Doador(
                        rs.getString("cpfDoador"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getDouble("peso"),
                        rs.getString("nome")
                );
                doadores.add(doador);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar doadores: " + e.getMessage(), e);
        }

        return doadores;
    }

    @Override
    public Doador buscarPorCpf(String cpf) {
        // 💡 Ajuste a instrução SQL para selecionar um único registro com base no CPF
        String sql = "SELECT * FROM doadores WHERE cpfDoador = ?";

        try (Connection connection = DataBaseManager.obtemConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Mapeia o resultado para um objeto Doador
                    Doador doador = new Doador(
                            rs.getString("cpfDoador"),
                            rs.getInt("idade"),
                            rs.getString("sexo"),
                            rs.getDouble("peso"),
                            rs.getString("nome")
                    );
                    return doador;
                }
            }
            // Se rs.next() não encontrar nada, o método retorna null (como o teste espera)
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar doador por CPF: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Doador doador) {
        String sql = "UPDATE doadores SET idade=?, sexo=?, peso=?, nome=? WHERE cpfDoador=?";
        try (Connection connection = DataBaseManager.obtemConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doador.getIdade());
            stmt.setString(2, doador.getSexo());
            stmt.setDouble(3, doador.getPeso());
            stmt.setString(4, doador.getNome());
            stmt.setString(5, doador.getCpfDoador());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar doador: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(String cpf) {
        String sql = "DELETE FROM doadores WHERE cpfDoador = ?";
        try (Connection connection = DataBaseManager.obtemConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar doador: " + e.getMessage(), e);
        }
    }


    // Métodos buscarPorCpf, atualizar e deletar podem ser implementados depois
}

