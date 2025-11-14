package config;

import config.DataBaseManager;
import java.sql.Connection;

public class TestaBanco {
    public static void main(String[] args) {
        Connection conexao = DataBaseManager.obtemConexao();
        if (conexao != null) {
            System.out.println("✅ Conexão realizada com sucesso!");
        } else {
            System.out.println("❌ Falha na conexão!");
        }
    }
}
