package hmd.teatroABC.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 21/11/2024
 * @brief class Pessoa
 */

public class Pessoa {
    private String cpf;
    private boolean ehFidelidade;
    private ArrayList<Ingresso> ingressos = new ArrayList<>();
    private String nome;
    private String telefone;
    private String endereco;
    private LocalDate dataNascimento;

    public Pessoa(String cpf, boolean ehFidelidade) {
        this.cpf = cpf;
        this.ehFidelidade = ehFidelidade;
    }

    public Pessoa(String cpf, boolean ehFidelidade, String nome, String telefone, String endereco, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.ehFidelidade = ehFidelidade;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isEhFidelidade() {
        return ehFidelidade;
    }

    public void setEhFidelidade(boolean ehFidelidade) {
        this.ehFidelidade = ehFidelidade;
    }

    public ArrayList<Ingresso> getIngressos() {
        return new ArrayList<>(ingressos);
    }

    public void setIngressos(ArrayList<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        ingressos.add(ingresso);
    }
}
