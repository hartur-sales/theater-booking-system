package hmd.teatroABC.model.entities;

/**
 * Project: theater-booking-system2
 * Package: hmd.teatroABC.model.entities
 * <p>
 * User: MegaD
 * Email: davylopes866@gmail.com
 * Date: 12/06/2025
 * Time: 22:40
 * <p>
 */
public class Venda {
    private String nome;
    private Sessao sessao;
    private int ingressosVendidos;

    public Venda(String nome, Sessao sessao, int ingressosVendidos) {
        this.nome = nome;
        this.sessao = sessao;
        this.ingressosVendidos = ingressosVendidos;
    }

    public String getNome() {
        return nome;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public int getIngressosVendidos() {
        return ingressosVendidos;
    }
}
