package controller;

import model.Doador;
import service.DoadorService;

public class DoadorController {
    private final DoadorService doadorService;

    public DoadorController(DoadorService doadorService) {
        this.doadorService = doadorService;
    }

    public void cadastrarDoador(String cpf, int idade, String sexo, double peso, String nome) throws Exception {
        Doador doador = new Doador(cpf, idade, sexo, peso, nome);
        doadorService.cadastrarDoador(doador);
    }
}
