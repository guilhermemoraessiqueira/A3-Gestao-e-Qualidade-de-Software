package model;

/**
 * Entidade que representa um Doador.
 * Responsável apenas por armazenar dados consistentes.
 */
public class Doador {
    private String cpfDoador;
    private int idade;
    private String sexo;
    private double peso;
    private String nome;

    public Doador(String cpfDoador, int idade, String sexo, double peso, String nome) {
        this.cpfDoador = cpfDoador;
        this.idade = idade;
        this.sexo = sexo;
        this.peso = peso;
        this.nome = nome;
    }

    // Getters e Setters
    public String getCpfDoador() { return cpfDoador; }
    public void setCpfDoador(String cpfDoador) { this.cpfDoador = cpfDoador; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
