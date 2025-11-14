import model.Doador;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suíte de testes de unidade para a Entidade Doador.
 * Objetivo: 100% de cobertura das regras de validação (construtor e setters).
 */
class DoadorTest {

    // Dados base válidos para o objeto Doador
    private static final String CPF_VALIDO = "12345678900";
    private static final int IDADE_VALIDA = 32;
    private static final String SEXO_VALIDO = "M";
    private static final double PESO_VALIDO = 75.5;
    private static final String NOME_VALIDO = "João Silva";

    // Método auxiliar para criar um Doador válido para testes de Setter
    private Doador criarDoadorValido() {
        return assertDoesNotThrow(() -> new Doador(CPF_VALIDO, IDADE_VALIDA, SEXO_VALIDO, PESO_VALIDO, NOME_VALIDO));
    }

    // ----------------------------------------------------------------------
    // 1. Testes de Sucesso (GREEN)
    // ----------------------------------------------------------------------

    @Test
    void deveCriarDoadorComDadosValidos_E_ValidarGetters() {
        // Testa a criação e verifica se os valores foram atribuídos corretamente
        Doador doador = assertDoesNotThrow(() -> new Doador(
                CPF_VALIDO, IDADE_VALIDA, SEXO_VALIDO, PESO_VALIDO, NOME_VALIDO
        ));

        // Verifica os getters para 100% de cobertura
        assertEquals(CPF_VALIDO, doador.getCpfDoador());
        assertEquals(IDADE_VALIDA, doador.getIdade());
        assertEquals(SEXO_VALIDO, doador.getSexo());
        assertEquals(PESO_VALIDO, doador.getPeso());
        assertEquals(NOME_VALIDO, doador.getNome());
    }

    // ----------------------------------------------------------------------
    // 2. Testes de Falha do Construtor (RED)
    // ----------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(ints = {17, 101}) // Testa abaixo de 18 e acima de 100
    void deveFalharAoCriarDoadorComIdadeInvalida(int idadeInvalida) {
        // Valida a regra: idade < 18 ou idade > 100
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Doador(CPF_VALIDO, idadeInvalida, SEXO_VALIDO, PESO_VALIDO, NOME_VALIDO);
        });
        assertEquals("Idade inválida para doador.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {49.9, 0.0}) // Testa valores abaixo de 50.0
    void deveFalharAoCriarDoadorComPesoInvalido(double pesoInvalido) {
        // Valida a regra: peso < 50.0
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Doador(CPF_VALIDO, IDADE_VALIDA, SEXO_VALIDO, pesoInvalido, NOME_VALIDO);
        });
        assertEquals("Peso mínimo para doador é 50kg.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "a", "outro"}) // Testa valores diferentes de 'M' ou 'F'
    void deveFalharAoCriarDoadorComSexoInvalido(String sexoInvalido) {
        // Valida a regra: sexo deve ser M ou F
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Doador(CPF_VALIDO, IDADE_VALIDA, sexoInvalido, PESO_VALIDO, NOME_VALIDO);
        });
        assertEquals("Sexo deve ser M ou F.", e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource // Testa para null, "" e " "
    void deveFalharAoCriarDoadorComCPFVazioOuNulo(String cpfInvalido) {
        // Valida a regra: CPF é obrigatório.
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Doador(cpfInvalido, IDADE_VALIDA, SEXO_VALIDO, PESO_VALIDO, NOME_VALIDO);
        });
        assertEquals("CPF é obrigatório.", e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource // Testa para null, "" e " "
    void deveFalharAoCriarDoadorComNomeVazioOuNulo(String nomeInvalido) {
        // Valida a regra: Nome é obrigatório.
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Doador(CPF_VALIDO, IDADE_VALIDA, SEXO_VALIDO, PESO_VALIDO, nomeInvalido);
        });
        assertEquals("Nome é obrigatório.", e.getMessage());
    }

    // ----------------------------------------------------------------------
    // 3. Testes de Falha dos Setters (RED)
    // ----------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(ints = {17, 101}) // Testa setIdade
    void deveFalharAoSetarIdadeInvalida(int idadeInvalida) {
        Doador doador = criarDoadorValido();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            doador.setIdade(idadeInvalida);
        });
        assertEquals("Idade inválida para doador.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {49.9, 0.0}) // Testa setPeso
    void deveFalharAoSetarPesoInvalido(double pesoInvalido) {
        Doador doador = criarDoadorValido();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            doador.setPeso(pesoInvalido);
        });
        assertEquals("Peso mínimo para doador é 50kg.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "a", "outro"}) // Testa setSexo
    void deveFalharAoSetarSexoInvalido(String sexoInvalido) {
        Doador doador = criarDoadorValido();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            doador.setSexo(sexoInvalido);
        });
        assertEquals("Sexo deve ser M ou F.", e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource // Testa setCpfDoador para null, "" e " "
    void deveFalharAoSetarCPFVazioOuNulo(String cpfInvalido) {
        Doador doador = criarDoadorValido();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            doador.setCpfDoador(cpfInvalido);
        });
        assertEquals("CPF não pode ser vazio.", e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource // Testa setNome para null, "" e " "
    void deveFalharAoSetarNomeVazioOuNulo(String nomeInvalido) {
        Doador doador = criarDoadorValido();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            doador.setNome(nomeInvalido);
        });
        assertEquals("Nome não pode ser vazio.", e.getMessage());
    }
}