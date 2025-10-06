package service;

import dao.DoadorDAO;
import model.Doador;
import repository.DoadorRepository;
import java.util.List;

public class DoadorService {
    private final DoadorRepository repository;

    public DoadorService() {
        this.repository = new DoadorDAO(); // Pode ser substituído facilmente
    }

    public void cadastrarDoador(Doador doador) {
        repository.adicionar(doador);
    }

    public List<Doador> listarDoadores() {
        return repository.listarTodos();
    }
}

