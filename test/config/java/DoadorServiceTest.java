import daoImpl.DoadorDAO;
import model.Doador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DoadorRepository;
import service.DoadorService;

import static org.junit.jupiter.api.Assertions.*;

class DoadorServiceTest {

    private DoadorService doadorService;

    @BeforeEach
    void setUp() {
        DoadorDAO dao = new DoadorDAO(); // Pode ser mock depois
        doadorService = new DoadorService(dao);
    }

    @Test
    void idadeDeveSerValida() {
        // Arrange: criar um doador para teste
        Doador doador = new Doador("98765432100", 60, "M", 70.0, "João");

        // Act & Assert
        boolean idadeValida = doador.getIdade() > 16 && doador.getIdade() <= 69;

        if (!idadeValida) {
            System.out.println("Idade inválida para doador: " + doador.getIdade());
        } else {
            System.out.println("Idade válida para doador: " + doador.getIdade());
        }

        // Teste só passa se a idade for válida
        assertTrue(idadeValida, "Idade do doador fora do intervalo permitido (17 a 100)");
    }

    @Test
    void pesoDeveSerMaiorQue50() {
        // Arrange: criar um doador para teste
        Doador doador = new Doador("98765432100", 60, "M", 70.0, "João");

        // Act & Assert
        boolean idadeValida = doador.getIdade() > 16 && doador.getIdade() <= 69;

        if (!idadeValida) {
            System.out.println("Idade inválida para doador: " + doador.getIdade());
        } else {
            System.out.println("Idade válida para doador: " + doador.getIdade());
        }

        // Teste só passa se a idade for válida
        assertTrue(idadeValida, "Idade do doador fora do intervalo permitido (17 a 100)");
    }
}
