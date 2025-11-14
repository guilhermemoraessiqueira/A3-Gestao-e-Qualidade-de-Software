package service;

import model.Doador;
import repository.DoadorRepository;
import java.util.List;

public class DoadorService {

    private final DoadorRepository repository;

    public DoadorService(DoadorRepository repository) {
        this.repository = repository;
    }

    public void cadastrarDoador(Doador doador) throws Exception {
        validarDoador(doador);
        repository.adicionar(doador);
    }

    public List<Doador> listarDoadores() {
        return repository.listarTodos();
    }

    private void validarDoador(Doador doador) {
        if (doador.getIdade() < 16)
            throw new IllegalArgumentException("O voluntário não pode doar se for menor que 16 anos.");
        if (doador.getIdade() > 69)
            throw new IllegalArgumentException("O voluntário não pode doar se for maior que 69 anos.");
        if (doador.getPeso() < 50)
            throw new IllegalArgumentException("O voluntário não pode doar se pesar menos que 50Kg.");
        if (doador.getCpfDoador().length() != 11)
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos.");
    }
}
