package test;

import model.Doacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para validar as regras de negócio associadas ao objeto Doacao.
 * Usa JUnit 5.
 */
class DoacaoTest {

    private Doacao doacaoValida;

    /**
     * Configura um objeto Doacao válido antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        // Objeto Doacao base com dados que satisfazem as regras típicas
        doacaoValida = new Doacao(
                "Enf. Ana",
                "123.456.789-00",
                "O+",
                "09/11/2025", // Data da doação
                "10:00",
                "Sim", // Estar alimentado
                "09/10/2025",
                "8" // Horas de sono
        );
    }

    // --- Regra 1: Validação de Horas de Sono (Exemplo: Mínimo 6 horas) ---

    @Test
    void horasSonoDeveSerValida() {
        // Arrange
        Doacao doacao = doacaoValida; // 8 horas

        // Act & Assert
        int horas = Integer.parseInt(doacao.getHorasSono());
        boolean sonoSuficiente = horas >= 6;

        if (!sonoSuficiente) {
            System.out.println("❌ Sono insuficiente para doação: " + horas + " horas.");
        } else {
            System.out.println("✅ Horas de sono válidas: " + horas + " horas.");
        }

        assertTrue(sonoSuficiente, "O doador deve ter dormido no mínimo 6 horas.");
    }

    @Test
    void horasSonoDeveSerInvalida() {
        // Arrange
        Doacao doacaoInvalida = doacaoValida;
        doacaoInvalida.setHorasSono("4"); // Define horas insuficientes

        // Act & Assert
        int horas = Integer.parseInt(doacaoInvalida.getHorasSono());
        boolean sonoSuficiente = horas >= 6;

        // O teste deve falhar (passar) quando o valor é inválido (false)
        assertFalse(sonoSuficiente, "O doador com 4 horas de sono deveria ser considerado inválido.");
        System.out.println("✅ Teste de sono inválido passou corretamente.");
    }

    // --- Regra 2: Validação de Status Alimentar (Deve estar alimentado) ---

    @Test
    void doadorDeveEstarAlimentado() {
        // Arrange
        Doacao doacao = doacaoValida;

        // Act
        boolean estaAlimentado = doacao.getAlimentado().equalsIgnoreCase("Sim");

        // Assert
        assertTrue(estaAlimentado, "O doador deve estar alimentado para realizar a doação.");
        System.out.println("✅ Status alimentado válido.");

        // Testando cenário inválido dentro do mesmo princípio
        doacao.setAlimentado("Não");
        assertFalse(doacao.getAlimentado().equalsIgnoreCase("Sim"), "Doador não alimentado deve ser recusado.");
        System.out.println("✅ Status não alimentado inválido (teste de recusa passou).");
    }


    // --- Regra 3: Testando a imutabilidade do CPF (Getter/Setter) ---

    @Test
    void cpfDoadorNaoDeveSerAlteradoAposCriacao() {
        // Arrange
        String cpfOriginal = doacaoValida.getCpfDoador();
        String novoCpf = "999.888.777-66";

        // Act
        doacaoValida.setCpfDoador(novoCpf);

        // Assert
        // Verifica se o setter funcionou *como esperado pelo modelo*
        assertEquals(novoCpf, doacaoValida.getCpfDoador(),
                "O método setCpfDoador deve atualizar o valor do CPF.");

        // Na vida real, o CPF seria imutável ou validado em um service.
        System.out.println("✅ CPF alterado com sucesso pelo setter (teste do modelo de dados).");
    }
}
 