
import config.DataBaseManager;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

class DataBaseManagerTest {

    @Test
    void deveConectarAoBancoComSucesso() {
        Connection conn = DataBaseManager.obtemConexao();
        assertNotNull(conn);
        try {
            assertFalse(conn.isClosed());
        } catch (Exception e) {
            fail("Erro ao verificar estado da conexão: " + e.getMessage());
        }
    }
}
