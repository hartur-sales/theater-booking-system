package hmd.teatroABC.model.objects;

import hmd.teatroABC.controller.TelaIngressoController;
import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Teatro;

import java.util.List;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 22/11/2024
 * @brief Class Estatistica
 */

public class Estatistica {
    private List<Peca> pecasEstatisticas;
    private int vendasWicked = 0;
    private int vendasReiLeao = 0;
    private int vendasAuto = 0;
    private double lucroWicked = 0;
    private double lucroReiLeao = 0;
    private double lucroAuto = 0;
    private double lucroMedioWicked = 0;
    private double lucroMedioReiLeao = 0;
    private double lucroMedioAuto = 0;
    private int vendasManha = 0;
    private int vendasTarde = 0;
    private int vendasNoite = 0;
    private int vendasManhaWicked = 0;
    private int vendasTardeWicked = 0;
    private int vendasNoiteWicked = 0;
    private int vendasManhaReiLeao = 0;
    private int vendasTardeReiLeao = 0;
    private int vendasNoiteReiLeao = 0;
    private int vendasManhaAuto = 0;
    private int vendasTardeAuto = 0;
    private int vendasNoiteAuto = 0;
    private String sessaoMaisLucrativaWicked = "";
    private String sessaoMenosLucrativaWicked = "";
    private String sessaoMaisLucrativaReiLeao = "";
    private String sessaoMenosLucrativaReiLeao = "";
    private String sessaoMaisLucrativaAuto = "";
    private String sessaoMenosLucrativaAuto = "";

    public void carregarEstatisticas() {
        this.pecasEstatisticas = Teatro.getPecas();
        calcularVendas();
        calcularLucro();
        lucroMedioWicked = calcularLucroMedioPeca(lucroWicked, vendasWicked);
        lucroMedioReiLeao = calcularLucroMedioPeca(lucroReiLeao, vendasReiLeao);
        lucroMedioAuto = calcularLucroMedioPeca(lucroAuto, vendasAuto);
        sessaoMaisLucrativaWicked = calcularSessaoMaisLucrativaWicked();
        sessaoMenosLucrativaWicked = calcularSessaoMenosLucrativaWicked();
        sessaoMaisLucrativaReiLeao = calcularSessaoMaisLucrativaReiLeao();
        sessaoMenosLucrativaReiLeao = calcularSessaoMenosLucrativaReiLeao();
        sessaoMaisLucrativaAuto = calcularSessaoMaisLucrativaAuto();
        sessaoMenosLucrativaAuto = calcularSessaoMenosLucrativaAuto();
    }

    private void calcularVendas() {
        for (Peca peca : pecasEstatisticas) {
            String nome = peca.getNome();
            String sessao = peca.getSessao().getNome();
            int ingressosVendidos = peca.getIngressosVendidos();
            if (nome.equals("Wicked")) {
                vendasWicked += ingressosVendidos;
                if (sessao.equals("Manha")) vendasManhaWicked += ingressosVendidos;
                if (sessao.equals("Tarde")) vendasTardeWicked += ingressosVendidos;
                if (sessao.equals("Noite")) vendasNoiteWicked += ingressosVendidos;
            }
            if (nome.equals("Rei Leao")) {
                vendasReiLeao += ingressosVendidos;
                if (sessao.equals("Manha")) vendasManhaReiLeao += ingressosVendidos;
                if (sessao.equals("Tarde")) vendasTardeReiLeao += ingressosVendidos;
                if (sessao.equals("Noite")) vendasNoiteReiLeao += ingressosVendidos;
            }
            if (nome.equals("Auto da Compadecida")) {
                vendasAuto += ingressosVendidos;
                if (sessao.equals("Manha")) vendasManhaAuto += ingressosVendidos;
                if (sessao.equals("Tarde")) vendasTardeAuto += ingressosVendidos;
                if (sessao.equals("Noite")) vendasNoiteAuto += ingressosVendidos;
            }

            if (sessao.equals("Manha")) {
                vendasManha += ingressosVendidos;
            }
            if (sessao.equals("Tarde")) {
                vendasTarde += ingressosVendidos;
            }
            if (sessao.equals("Noite")) {
                vendasNoite += ingressosVendidos;
            }
        }
    }

    private void calcularLucro() {
        double[] lucros = {0, 0, 0};
        for (Peca peca : pecasEstatisticas) {
            adicionarLucro(peca.getNome(), peca.getAssentos(), 0, lucros);
        }
        lucroWicked = lucros[0];
        lucroReiLeao = lucros[1];
        lucroAuto = lucros[2];
    }

    private void adicionarLucro(String nome, List<String> assentosVendidos, int indice, double[] lucros) {
        if (indice >= assentosVendidos.size()) {
            return;
        }
        String assento = assentosVendidos.get(indice);
        char identificador = assento.charAt(0);
        double preco = TelaIngressoController.getPrecoPorIdentificador(identificador);
        switch (nome) {
            case "Wicked":
                lucros[0] += preco;
                break;
            case "Rei Leao":
                lucros[1] += preco;
                break;
            case "Auto da Compadecida":
                lucros[2] += preco;
                break;
        }
        adicionarLucro(nome, assentosVendidos, indice + 1, lucros);
    }

    public int calcularTotalVendas() {
        return vendasWicked + vendasReiLeao + vendasAuto;
    }

    public String calcularPecaMaisVendida() {
        String pecaMaisVendida;
        if (vendasWicked >= vendasReiLeao && vendasWicked >= vendasAuto) {
            pecaMaisVendida = "Wicked";
        } else if (vendasReiLeao >= vendasWicked && vendasReiLeao >= vendasAuto) {
//            pecaMaisVendida = BUNDLE.getString("Rei Leão");
            pecaMaisVendida = "Rei Leao";
        } else {
            pecaMaisVendida = "Auto da Compadecida";
        }
        return pecaMaisVendida;
    }

    public String calcularPecaMenosVendida() {
        String pecaMenosVendida;
        if (vendasWicked <= vendasReiLeao && vendasWicked <= vendasAuto) {
            pecaMenosVendida = "Wicked";
        } else if (vendasReiLeao <= vendasWicked && vendasReiLeao <= vendasAuto) {
//            pecaMenosVendida = BUNDLE.getString("Rei Leao");
            pecaMenosVendida = "Rei Leao";
        } else {
            pecaMenosVendida = "Auto da Compadecida";
        }
        return pecaMenosVendida;
    }

    public String calcularSessaoMaisOcupada() {
        String sessaoMaisOcupada;
        if (vendasManha >= vendasTarde && vendasManha >= vendasNoite) {
//            sessaoMaisOcupada = BUNDLE.getString("sessao.manha");
            sessaoMaisOcupada = "Manha";
        } else if (vendasTarde >= vendasManha && vendasTarde >= vendasNoite) {
//            sessaoMaisOcupada = BUNDLE.getString("sessao.tarde");
            sessaoMaisOcupada = "Tarde";
        } else {
//            sessaoMaisOcupada = BUNDLE.getString("sessao.noite");
            sessaoMaisOcupada = "Noite";
        }
        return sessaoMaisOcupada;
    }

    public String calcularSessaoMenosOcupada() {
        String sessaoMenosOcupada;
        if (vendasManha <= vendasTarde && vendasManha <= vendasNoite) {
//            sessaoMenosOcupada = BUNDLE.getString("sessao.manha");
            sessaoMenosOcupada = "Manha";
        } else if (vendasTarde <= vendasManha && vendasTarde <= vendasNoite) {
//            sessaoMenosOcupada = BUNDLE.getString("sessao.tarde");
            sessaoMenosOcupada = "Tarde";
        } else {
//            sessaoMenosOcupada = BUNDLE.getString("sessao.noite");
            sessaoMenosOcupada = "Noite";
        }
        return sessaoMenosOcupada;
    }

    private double calcularLucroMedioPeca(double lucro, int quantidadeVendas) {
        return lucro / quantidadeVendas;
    }

    private double calcularLucroPorSessao(double lucro, int quantidadeVendas) {
        return lucro / quantidadeVendas;
    }

    public String calcularSessaoMaisLucrativaWicked() {
        double lucroManha = calcularLucroPorSessao(lucroWicked, vendasManhaWicked);
        double lucroTarde = calcularLucroPorSessao(lucroWicked, vendasTardeWicked);
        double lucroNoite = calcularLucroPorSessao(lucroWicked, vendasNoiteWicked);
        if (lucroManha >= lucroTarde && lucroManha >= lucroNoite) {
//            return BUNDLE.getString("sessao.manha");
            return "Manha";
        } else if (lucroTarde >= lucroManha && lucroTarde >= lucroNoite) {
//            return BUNDLE.getString("sessao.tarde");
            return "Tarde";
        } else {
//            return BUNDLE.getString("sessao.noite");
            return "Noite";
        }
    }

    public String calcularSessaoMenosLucrativaWicked() {
        double lucroManha = calcularLucroPorSessao(lucroWicked, vendasManhaWicked);
        double lucroTarde = calcularLucroPorSessao(lucroWicked, vendasTardeWicked);
        double lucroNoite = calcularLucroPorSessao(lucroWicked, vendasNoiteWicked);
        if (lucroManha <= lucroTarde && lucroManha <= lucroNoite) {
//            return BUNDLE.getString("sessao.manha");
            return "Manha";
        } else if (lucroTarde <= lucroManha && lucroTarde <= lucroNoite) {
//            return BUNDLE.getString("sessao.tarde");
            return "Tarde";
        } else {
//            return BUNDLE.getString("sessao.noite");
            return "Noite";
        }
    }

    public String calcularSessaoMaisLucrativaReiLeao() {
        double lucroManha = calcularLucroPorSessao(lucroReiLeao, vendasManhaReiLeao);
        double lucroTarde = calcularLucroPorSessao(lucroReiLeao, vendasTardeReiLeao);
        double lucroNoite = calcularLucroPorSessao(lucroReiLeao, vendasNoiteReiLeao);
        if (lucroManha >= lucroTarde && lucroManha >= lucroNoite) {
//            return BUNDLE.getString("sessao.manha");
            return "Manha";
        } else if (lucroTarde >= lucroManha && lucroTarde >= lucroNoite) {
//            return BUNDLE.getString("sessao.tarde");
            return "Tarde";
        } else {
//            return BUNDLE.getString("sessao.noite");
            return "Noite";
        }
    }

    public String calcularSessaoMenosLucrativaReiLeao() {
        double lucroManha = calcularLucroPorSessao(lucroReiLeao, vendasManhaReiLeao);
        double lucroTarde = calcularLucroPorSessao(lucroReiLeao, vendasTardeReiLeao);
        double lucroNoite = calcularLucroPorSessao(lucroReiLeao, vendasNoiteReiLeao);
        if (lucroManha <= lucroTarde && lucroManha <= lucroNoite) {
//            return BUNDLE.getString("sessao.manha");
            return "Manha";
        } else if (lucroTarde <= lucroManha && lucroTarde <= lucroNoite) {
//            return BUNDLE.getString("sessao.tarde");
            return "Tarde";
        } else {
//            return BUNDLE.getString("sessao.noite");
            return "Noite";
        }
    }

    public String calcularSessaoMaisLucrativaAuto() {
        double lucroManha = calcularLucroPorSessao(lucroAuto, vendasManhaAuto);
        double lucroTarde = calcularLucroPorSessao(lucroAuto, vendasTardeAuto);
        double lucroNoite = calcularLucroPorSessao(lucroAuto, vendasNoiteAuto);
        if (lucroManha >= lucroTarde && lucroManha >= lucroNoite) {
//            return BUNDLE.getString("sessao.manha");
            return "Manha";
        } else if (lucroTarde >= lucroManha && lucroTarde >= lucroNoite) {
//            return BUNDLE.getString("sessao.tarde");
            return "Tarde";
        } else {
//            return BUNDLE.getString("sessao.noite");
            return "Noite";
        }
    }

    public String calcularSessaoMenosLucrativaAuto() {
        double lucroManha = calcularLucroPorSessao(lucroAuto, vendasManhaAuto);
        double lucroTarde = calcularLucroPorSessao(lucroAuto, vendasTardeAuto);
        double lucroNoite = calcularLucroPorSessao(lucroAuto, vendasNoiteAuto);
        if (lucroManha <= lucroTarde && lucroManha <= lucroNoite) {
//            return BUNDLE.getString("sessao.manha");
            return "Manha";
        } else if (lucroTarde <= lucroManha && lucroTarde <= lucroNoite) {
//            return BUNDLE.getString("sessao.tarde");
            return "Tarde";
        } else {
//            return BUNDLE.getString("sessao.noite");
            return "Noite";
        }
    }

    public double getLucroMedioWicked() {
        return lucroMedioWicked;
    }

    public double getLucroMedioReiLeao() {
        return lucroMedioReiLeao;
    }

    public double getLucroMedioAuto() {
        return lucroMedioAuto;
    }

    public String getSessaoMaisLucrativaWicked() {
        return sessaoMaisLucrativaWicked;
    }

    public String getSessaoMenosLucrativaWicked() {
        return sessaoMenosLucrativaWicked;
    }

    public String getSessaoMaisLucrativaReiLeao() {
        return sessaoMaisLucrativaReiLeao;
    }

    public String getSessaoMenosLucrativaReiLeao() {
        return sessaoMenosLucrativaReiLeao;
    }

    public String getSessaoMaisLucrativaAuto() {
        return sessaoMaisLucrativaAuto;
    }

    public String getSessaoMenosLucrativaAuto() {
        return sessaoMenosLucrativaAuto;
    }
}