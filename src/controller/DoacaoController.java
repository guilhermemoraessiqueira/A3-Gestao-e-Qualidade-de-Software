package controller;

import model.Doacao;
import repository.DoacaoRepository;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class DoacaoController {

    private final DoacaoRepository doacaoRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DoacaoController() {
        this.doacaoRepository = new DoacaoRepository();
    }

    // Registrar nova doação
    public void registrarDoacao(Doacao doacao) throws SQLException {
        validarDoacao(doacao);
        validarIntervaloUltimaDoacao(doacao);

        doacaoRepository.save(doacao);
    }

    // Buscar todas as doações
    public List<Doacao> listarDoacoes() throws SQLException {
        return doacaoRepository.findAll();
    }

    // Atualizar doação
    public void atualizarDoacao(Doacao doacao, int idDoacao) throws SQLException {
        validarDoacao(doacao);
        validarIntervaloUltimaDoacao(doacao);

        doacaoRepository.update(doacao, idDoacao);
    }

    // Deletar doação
    public void removerDoacao(int idDoacao) throws SQLException {
        doacaoRepository.delete(idDoacao);
    }

    // Atualiza as labels de tipo sanguíneo usando o Repository
    public void atualizarLabelsDoacoes(
            JLabel txtApos, JLabel txtAneg, JLabel txtBpos, JLabel txtBneg,
            JLabel txtABpos, JLabel txtABneg, JLabel txtOpos, JLabel txtOneg
    ) {
        try {
            Map<String, Integer> doacoesPorTipo = doacaoRepository.contarDoacoesPorTipoSanguineo();

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

    // -------------------- VALIDAÇÕES --------------------

    private void validarDoacao(Doacao doacao) {
        // CPF
        if (doacao.getCpfDoador() == null || doacao.getCpfDoador().length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos!");
        }

        // Alimentado
        if (!doacao.getAlimentado().equalsIgnoreCase("S")) {
            throw new IllegalArgumentException("Voluntário não está alimentado!");
        }

        // Tipo sanguíneo
        String[] tiposValidos = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        boolean valido = false;
        for (String tipo : tiposValidos) {
            if (doacao.getTipoSanguineo().equalsIgnoreCase(tipo)) {
                valido = true;
                break;
            }
        }
        if (!valido) {
            throw new IllegalArgumentException("Tipo sanguíneo inválido!");
        }

        // Horas de sono
        if (!doacao.getHorasSono().equalsIgnoreCase("S")) {
            throw new IllegalArgumentException("Voluntário não teve 6h mínimas de sono!");
        }
    }

    private void validarIntervaloUltimaDoacao(Doacao doacao) {
        if (doacao.getUltimaDoacao().equalsIgnoreCase("S")) {
            throw new IllegalArgumentException("Voluntário já doou sangue nos últimos 2 meses!");
        }
    }
}

