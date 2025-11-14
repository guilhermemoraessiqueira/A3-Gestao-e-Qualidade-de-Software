
import model.Doacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.DoacaoRepository;
import service.DoacaoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários da classe DoacaoService.
 * Segue o padrão AAA (Arrange, Act, Assert)
 * e utiliza um repositório fake em memória para isolar dependências.
 */
class DoacaoServiceTest {

    private DoacaoService service;
    private FakeDoacaoRepository fakeRepo;
    private Doacao doacaoValida;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        // Arrange comum: repositório fake e serviço com injeção de dependência
        fakeRepo = new FakeDoacaoRepository();
        service = new DoacaoService(fakeRepo);

        // Cria uma doação válida (última doação há mais de 60 dias)
        doacaoValida = new Doacao(
                "Enf. Teste",
                "12345678900",
                "O+",
                LocalDate.now().format(formatter),
                "10:00",
                "Sim",
                LocalDate.now().minusDays(61).format(formatter),
                "Sim"
        );
    }

    // ===========================================================
    // ✅ CENÁRIOS POSITIVOS
    // ===========================================================

    @Test
    @DisplayName("Quando a doação for válida, deve ser registrada com sucesso")
    void quandoDoacaoForValida_entaoDeveSerRegistradaComSucesso() throws SQLException {
        // Act
        service.registrarDoacao(doacaoValida);

        // Assert
        assertEquals(1, fakeRepo.findAll().size());
        assertEquals("12345678900", fakeRepo.findAll().get(0).getCpfDoador());
        assertTrue(fakeRepo.metodoSaveChamado, "O método save() deve ser chamado.");
    }

    // ===========================================================
    // ❌ CENÁRIOS NEGATIVOS - Validações básicas
    // ===========================================================

    @Test
    @DisplayName("Deve lançar exceção se o tipo sanguíneo for inválido")
    void quandoTipoSanguineoForInvalido_entaoLancarExcecao() {
        doacaoValida.setTipoSanguineo("XPTO");

        assertThrows(IllegalArgumentException.class,
                () -> service.registrarDoacao(doacaoValida));
    }

    @Test
    @DisplayName("Deve lançar exceção se o CPF for inválido")
    void quandoCpfForInvalido_entaoLancarExcecao() {
        doacaoValida.setCpfDoador("123"); // Menos de 11 dígitos

        assertThrows(IllegalArgumentException.class,
                () -> service.registrarDoacao(doacaoValida));
    }

    @Test
    @DisplayName("Deve lançar exceção se o voluntário não estiver alimentado")
    void quandoVoluntarioNaoEstiverAlimentado_entaoLancarExcecao() {
        doacaoValida.setAlimentado("Não");

        assertThrows(IllegalArgumentException.class,
                () -> service.registrarDoacao(doacaoValida));
    }

    @Test
    @DisplayName("Deve lançar exceção se o voluntário não dormiu o suficiente")
    void quandoVoluntarioNaoDormiuOBastante_entaoLancarExcecao() {
        doacaoValida.setHorasSono("Não");

        assertThrows(IllegalArgumentException.class,
                () -> service.registrarDoacao(doacaoValida));
    }

    // ===========================================================
    // ❌ CENÁRIOS NEGATIVOS - Validação de data
    // ===========================================================

    @Test
    @DisplayName("Deve lançar exceção se a última doação for há menos de 60 dias")
    void quandoUltimaDoacaoForRecente_entaoLancarExcecao() {
        doacaoValida.setUltimaDoacao(LocalDate.now().minusDays(30).format(formatter));

        assertThrows(IllegalArgumentException.class,
                () -> service.registrarDoacao(doacaoValida));
    }

    @Test
    @DisplayName("Deve lançar exceção se a data da última doação tiver formato inválido")
    void quandoDataUltimaDoacaoForInvalida_entaoLancarExcecao() {
        doacaoValida.setUltimaDoacao("31-12-2024"); // formato errado

        assertThrows(IllegalArgumentException.class,
                () -> service.registrarDoacao(doacaoValida));
    }

    // ===========================================================
    // ❌ CENÁRIOS DE EXCEÇÃO DO REPOSITÓRIO
    // ===========================================================

    @Test
    @DisplayName("Deve propagar SQLException se o repositório falhar")
    void quandoRepositorioLancarSQLException_entaoPropagarErro() {
        DoacaoRepository repoQueLancaErro = new DoacaoRepository() {
            @Override
            public void save(Doacao d) throws SQLException {
                throw new SQLException("Erro simulado no banco");
            }
        };

        DoacaoService serviceComErro = new DoacaoService(repoQueLancaErro);

        assertThrows(SQLException.class,
                () -> serviceComErro.registrarDoacao(doacaoValida));
    }

    // ===========================================================
    // 🔧 REPOSITÓRIO FAKE (Simula o banco de dados)
    // ===========================================================

    static class FakeDoacaoRepository extends DoacaoRepository {
        private final List<Doacao> bancoFake = new ArrayList<>();
        boolean metodoSaveChamado = false;

        @Override
        public void save(Doacao doacao) {
            metodoSaveChamado = true;
            bancoFake.add(doacao);
        }

        @Override
        public List<Doacao> findAll() {
            return bancoFake;
        }

        @Override
        public void update(Doacao doacao, int idDoacao) {
            // Não necessário para estes testes, mas pode ser adicionado futuramente
        }

        @Override
        public void delete(int idDoacao) {
            // Simulação de remoção
            bancoFake.removeIf(d -> bancoFake.indexOf(d) == idDoacao);
        }
    }
}
 