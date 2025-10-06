package dao;

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
        return null;
    }

    @Override
    public void atualizar(Doador doador) {

    }

    @Override
    public void deletar(String cpf) {

    }

    // Métodos buscarPorCpf, atualizar e deletar podem ser implementados depois
}

