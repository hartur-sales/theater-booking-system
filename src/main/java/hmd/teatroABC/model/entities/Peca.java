package hmd.teatroABC.model.entities;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static hmd.teatroABC.util.FXMLLoaderUtil.BUNDLE;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 19/11/2024
 * @brief Class Peca
 */

public class Peca {
    private Sessao sessao;
    private String nome;
    private String descricao;
    private ArrayList<String> assentos = new ArrayList<>();
    private int ingressosVendidos;

    private final File poster;
    private Image posterImg;

    public Peca(File poster, Sessao sessao, String nome, String descricao) {
        this.poster = poster;
        this.sessao = sessao;
        this.nome = nome;
        this.posterImg = new Image(poster.toURI().toString());
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public File getPoster() {
        return poster;
    }

    public Image getPosterImg() {
        return posterImg;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarAssento(String assento) {
        assentos.add(assento);
    }

    public void adicionarAssentos(List<String> assentos) {
        this.assentos.addAll(assentos);
    }

    public List<String> getAssentos() {
        return Collections.unmodifiableList(assentos);
    }

    public int getIngressosVendidos() {
        return ingressosVendidos;
    }

    public void setIngressosVendidos(int ingressosVendidos) {
        this.ingressosVendidos = ingressosVendidos;
    }

    public void aumentarIngressosVendidos() {
        this.ingressosVendidos++;
    }

    @Override
    public String toString() {
        return "Peca{" +
                "sessao=" + sessao +
                ", nome='" + nome + '\'' +
                ", ingressosVendidos=" + ingressosVendidos +
                ", assentos=" + assentos +
                ", poster=" + poster +
                ", posterImg=" + posterImg +
                '}';
    }

    public static String traduzirNome(String nomeOriginal) {
        return BUNDLE.containsKey(nomeOriginal) ? BUNDLE.getString(nomeOriginal) : nomeOriginal;
    }
}
