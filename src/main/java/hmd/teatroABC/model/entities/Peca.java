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
    private final Object posterImg;
    private Sessao sessao;
    private String nome;
    private String descricao;
    private ArrayList<String> assentos = new ArrayList<>();
    private int ingressosVendidos;

    private final File poster;


    // Construtor sem imagem, para uso interno/estatísticas
    public Peca(Sessao sessao, String nome, String descricao) {
        this.poster = null;
        this.posterImg = null;
        this.sessao = sessao;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Para compatibilidade com o parser de texto
    public void setAssentos(List<String> assentos) {
        this.assentos = new ArrayList<>(assentos);
    }


    public Peca(File poster, Sessao sessao, String nome, String descricao) {
        this.poster = poster;
        this.sessao = sessao;
        this.nome = nome;
        this.posterImg = (poster != null) ? new Image(poster.toURI().toString()) : null;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public File getPoster() {
        return poster;
    }

    public Image getPosterImg() {
        return (Image) posterImg;
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
