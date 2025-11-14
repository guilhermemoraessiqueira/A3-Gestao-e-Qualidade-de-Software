package daoImpl;

import model.Doacao;
import repository.DoacaoRepository;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DoacaoDAO {

    private final DoacaoRepository repository;

    public DoacaoDAO() {
        this.repository = new DoacaoRepository();
    }

    // Inserir doação
    public void inserirDoacaoNoBanco(Doacao doacao) throws SQLException {
        repository.save(doacao);
    }

    // Verifica se o CPF do doador existe (pode criar um método no Repository futuramente)
    public boolean verificarCPFDoador(String cpfDoador) {
        // Aqui você pode criar um método repository.verificarCPFDoador(cpf) se quiser validar tabela de doadores
        // Por enquanto retornamos true como placeholder
        return true;
    }

    // Buscar todas as doações
    public List<Doacao> listarDoacoes() throws SQLException {
        return repository.findAll();
    }

    // Atualizar doação
    public void atualizarDoacao(Doacao doacao, int idDoacao) throws SQLException {
        repository.update(doacao, idDoacao);
    }

    // Deletar doação
    public void removerDoacao(int idDoacao) throws SQLException {
        repository.delete(idDoacao);
    }

    // Atualiza as labels de tipo sanguíneo
    public void atualizarLabelsDoacoes(
            JLabel txtApos, JLabel txtAneg, JLabel txtBpos, JLabel txtBneg,
            JLabel txtABpos, JLabel txtABneg, JLabel txtOpos, JLabel txtOneg
    ) {
        try {
            Map<String, Integer> doacoesPorTipo = repository.contarDoacoesPorTipoSanguineo();

            SwingUtilities.invokeLater(() -> {
                txtApos.setText(String.valueOf(doacoesPorTipo.getOrDefault("A+", 0)));
                txtAneg.setText(String.valueOf(doacoesPorTipo.getOrDefault("A-", 0)));
                txtBpos.setText(String.valueOf(doacoesPorTipo.getOrDefault("B+", 0)));
                txtBneg.setText(String.valueOf(doacoesPorTipo.getOrDefault("B-", 0)));
                txtABpos.setText(String.valueOf(doacoesPorTipo.getOrDefault("AB+", 0)));
                txtABneg.setText(String.valueOf(doacoesPorTipo.getOrDefault("AB-", 0)));
                txtOpos.setText(String.valueOf(doacoesPorTipo.getOrDefault("O+", 0)));
                txtOneg.setText(String.valueOf(doacoesPorTipo.getOrDefault("O-", 0)));
            });
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar labels de doações: " + e.getMessage());
        }
    }
}
