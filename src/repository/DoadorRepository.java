package repository;

import model.Doador;
import java.util.List;

public interface DoadorRepository {
    void adicionar(Doador doador);
    List<Doador> listarTodos();
    Doador buscarPorCpf(String cpf);
    void atualizar(Doador doador);
    void deletar(String cpf);
}

