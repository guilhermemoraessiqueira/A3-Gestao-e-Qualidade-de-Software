package model;
/**
 *
 * @author João Vitor
 */
public class Doacao {
    private String nomeEnfermeiro;
    private String cpfDoador;
    private String tipoSanguineo;
    private String dataDoacao;
    private String horaDoacao;
    private String alimentado;
    private String ultimaDoacao;
    private String horasSono;

    public Doacao(String nomeEnfermeiro, String cpfDoador, String tipoSanguineo, String dataDoacao, String horaDoacao, String alimentado, String ultimaDoacao, String horasSono) {
        this.nomeEnfermeiro = nomeEnfermeiro;
        this.cpfDoador = cpfDoador;
        this.tipoSanguineo = tipoSanguineo;
        this.dataDoacao = dataDoacao;
        this.horaDoacao = horaDoacao;
        this.alimentado = alimentado;
        this.ultimaDoacao = ultimaDoacao;
        this.horasSono = horasSono;
    }

    public String getNomeEnfermeiro() {
        return nomeEnfermeiro;
    }

    public void setNomeEnfermeiro(String nomeEnfermeiro) {
        this.nomeEnfermeiro = nomeEnfermeiro;
    }

    public String getCpfDoador() {
        return cpfDoador;
    }

    public void setCpfDoador(String cpfDoador) {
        this.cpfDoador = cpfDoador;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getDataDoacao() {
        return dataDoacao;
    }

    public void setDataDoacao(String dataDoacao) {
        this.dataDoacao = dataDoacao;
    }

    public String getHoraDoacao() {
        return horaDoacao;
    }

    public void setHoraDoacao(String horaDoacao) {
        this.horaDoacao = horaDoacao;
    }

    public String getAlimentado() {
        return alimentado;
    }

    public void setAlimentado(String alimentado) {
        this.alimentado = alimentado;
    }

    public String getUltimaDoacao() {
        return ultimaDoacao;
    }

    public void setUltimaDoacao(String ultimaDoacao) {
        this.ultimaDoacao = ultimaDoacao;
    }

    public String getHorasSono() {
        return horasSono;
    }

    public void setHorasSono(String horasSono) {
        this.horasSono = horasSono;
    }
}

