package service;

import model.Doacao;
import repository.DoacaoRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class DoacaoService {

    private final DoacaoRepository repository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DoacaoService() {
        this.repository = new DoacaoRepository();
    }

    // Registrar nova doação com validação de regras de negócio
    public void registrarDoacao(Doacao doacao) throws SQLException {
        validarDoacao(doacao);
        validarIntervaloUltimaDoacao(doacao);

        repository.save(doacao);
    }

    // Buscar todas as doações
    public List<Doacao> listarDoacoes() throws SQLException {
        return repository.findAll();
    }

    // Atualizar doação
    public void atualizarDoacao(Doacao doacao, int idDoacao) throws SQLException {
        validarDoacao(doacao);
        repository.update(doacao, idDoacao);
    }

    // Deletar doação
    public void removerDoacao(int idDoacao) throws SQLException {
        repository.delete(idDoacao);
    }

    // Contagem de doações por tipo sanguíneo
    public Map<String, Integer> contarDoacoesPorTipoSanguineo() throws SQLException {
        return repository.contarDoacoesPorTipoSanguineo();
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
        if (doacao.getUltimaDoacao() != null && !doacao.getUltimaDoacao().isEmpty()) {
            LocalDate ultimaDoacao = LocalDate.parse(doacao.getUltimaDoacao(), formatter);
            LocalDate hoje = LocalDate.now();

            long dias = ChronoUnit.DAYS.between(ultimaDoacao, hoje);
            if (dias < 60) { // menos de 2 meses
                throw new IllegalArgumentException("Voluntário fez doação nos últimos 2 meses!");
            }
        }
    }
}
