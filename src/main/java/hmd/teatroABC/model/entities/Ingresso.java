package hmd.teatroABC.model.entities;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 21/11/2024
 * @brief Class Ingresso
 */

public class Ingresso {
    private Area area;
    private Peca peca;
    private String assento;
    private double preco;

    public Ingresso(Area area, Peca peca, String assento, double preco) {
        this.area = area;
        this.peca = peca;
        this.assento = assento;
        this.preco = preco;
    }

    public Area getArea() {
        return area;
    }

    public Peca getPeca() {
        return peca;
    }

    public String getAssento() {
        return assento;
    }

    public void setAssento(String assento) {
        this.assento = assento;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Ingresso{" +
                "area=" + area +
                ", peca=" + peca +
                ", assento='" + assento + '\'' +
                ", preco=" + preco +
                '}';
    }
}
