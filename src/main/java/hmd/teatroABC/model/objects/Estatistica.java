package hmd.teatroABC.model.objects;

import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
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
    private double receitaWicked = 0;
    private double receitaReiLeao = 0;
    private double receitaAuto = 0;
    private double lucroWicked = 0;
    private double lucroReiLeao = 0;
    private double lucroAuto = 0;
    private double receitaManha = 0;
    private double receitaTarde = 0;
    private double receitaNoite = 0;
    private double lucroManha = 0;
    private double lucroTarde = 0;
    private double lucroNoite = 0;
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
    private int vendasPlatA = 0;
    private int vendasPlatB = 0;
    private int vendasCamarote = 0;
    private int vendasFrisa = 0;
    private int vendasBalcao = 0;
    private String sessaoMaisLucrativaWicked = "";
    private String sessaoMenosLucrativaWicked = "";
    private String sessaoMaisLucrativaReiLeao = "";
    private String sessaoMenosLucrativaReiLeao = "";
    private String sessaoMaisLucrativaAuto = "";
    private String sessaoMenosLucrativaAuto = "";

    public void carregarEstatisticas() {
        this.pecasEstatisticas = Teatro.getPecas();
        calcularVendas();
        calcularReceita();
        lucroManha = calcularLucroSessao(receitaManha, vendasManha);
        lucroTarde = calcularLucroSessao(receitaTarde, vendasTarde);
        lucroNoite = calcularLucroSessao(receitaNoite, vendasNoite);
        lucroWicked = calcularLucroPeca(receitaWicked, vendasWicked);
        lucroReiLeao = calcularLucroPeca(receitaReiLeao, vendasReiLeao);
        lucroAuto = calcularLucroPeca(receitaAuto, vendasAuto);
        sessaoMaisLucrativaWicked = calcularSessaoMaisLucrativaPeca1();
        sessaoMenosLucrativaWicked = calcularSessaoMenosLucrativaPeca1();
        sessaoMaisLucrativaReiLeao = calcularSessaoMaisLucrativaPeca2();
        sessaoMenosLucrativaReiLeao = calcularSessaoMenosLucrativaPeca2();
        sessaoMaisLucrativaAuto = calcularSessaoMaisLucrativaPeca3();
        sessaoMenosLucrativaAuto = calcularSessaoMenosLucrativaPeca3();
    }

    //public void carregarEstatisticasFiltradas(String filtroPeca, hmd.teatroABC.model.entities.Sessao filtroSessao, hmd.teatroABC.model.entities.Area filtroArea) {
    //        this.pecasEstatisticas = Teatro.getPecas().stream()
    //            .filter(peca -> (filtroPeca == null || peca.getNome().equals(filtroPeca)))
    //            .filter(peca -> (filtroSessao == null || peca.getSessao().equals(filtroSessao)))
    //            .filter(peca -> {
    //                if (filtroArea == null) return true;
    //                // Verifica se algum assento vendido pertence à área filtrada
    //                return peca.getAssentos().stream().anyMatch(a -> a.charAt(0) == filtroArea.getIdentificador());
    //            })
    //            .toList();
    //        // Zera estatísticas antes de recalcular
    //        vendasWicked = vendasReiLeao = vendasAuto = 0;
    //        lucroWicked = lucroReiLeao = lucroAuto = 0;
    //        vendasManha = vendasTarde = vendasNoite = 0;
    //        vendasManhaWicked = vendasTardeWicked = vendasNoiteWicked = 0;
    //        vendasManhaReiLeao = vendasTardeReiLeao = vendasNoiteReiLeao = 0;
    //        vendasManhaAuto = vendasTardeAuto = vendasNoiteAuto = 0;
    //        // Recalcula estatísticas apenas para as peças filtradas
    //        calcularVendas();
    //        calcularLucro();
    //        lucroMedioWicked = calcularLucroMedioPeca(lucroWicked, vendasWicked);
    //        lucroMedioReiLeao = calcularLucroMedioPeca(lucroReiLeao, vendasReiLeao);
    //        lucroMedioAuto = calcularLucroMedioPeca(lucroAuto, vendasAuto);
    //        sessaoMaisLucrativaWicked = calcularSessaoMaisLucrativaWicked();
    //        sessaoMenosLucrativaWicked = calcularSessaoMenosLucrativaWicked();
    //        sessaoMaisLucrativaReiLeao = calcularSessaoMaisLucrativaReiLeao();
    //        sessaoMenosLucrativaReiLeao = calcularSessaoMenosLucrativaReiLeao();
    //        sessaoMaisLucrativaAuto = calcularSessaoMaisLucrativaAuto();
    //        sessaoMenosLucrativaAuto = calcularSessaoMenosLucrativaAuto();
    //    }

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

    private void calcularReceita() {
        double[] receitas = {0, 0, 0};
        double[] receitasPorSessao = {0, 0, 0};
        for (Peca peca : pecasEstatisticas) {
            Sessao sessao = peca.getSessao();
            int idSessao = getIndiceSessao(sessao);
            adicionarReceita(peca.getNome(), peca.getAssentos(), 0, receitas, idSessao, receitasPorSessao);
        }
        receitaWicked = receitas[0];
        receitaReiLeao = receitas[1];
        receitaAuto = receitas[2];

        receitaManha = receitasPorSessao[0];
        receitaTarde = receitasPorSessao[1];
        receitaNoite = receitasPorSessao[2];
    }

    private void adicionarReceita(String nome, List<String> assentosVendidos, int indice, double[] receitas, int idSessao, double[] receitasPorSessao) {
        if (indice >= assentosVendidos.size()) {
            return;
        }
        String assento = assentosVendidos.get(indice);
        char identificador = assento.charAt(0);
        double preco = AreaUtil.getPrecoPorIdentificador(identificador);
        receitasPorSessao[idSessao] += preco;
        switch (nome) {
            case "Wicked":
                receitas[0] += preco;
                break;
            case "Rei Leao":
                receitas[1] += preco;
                break;
            case "Auto da Compadecida":
                receitas[2] += preco;
                break;
        }
        adicionarReceita(nome, assentosVendidos, indice + 1, receitas, idSessao, receitasPorSessao);
    }

    public int calcularTotalVendas() {
        return vendasWicked + vendasReiLeao + vendasAuto;
    }

    public String calcularPecaMaisVendida() {
        String pecaMaisVendida;
        if (vendasWicked == 0 && vendasReiLeao == 0 && vendasAuto == 0) {
            pecaMaisVendida = "Não há vendas para calcular";
        } else if (vendasWicked >= vendasReiLeao && vendasWicked >= vendasAuto) {
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
        if (vendasWicked == 0 && vendasReiLeao == 0 && vendasAuto == 0) {
            pecaMenosVendida = "Não há vendas para calcular";
        } else if (vendasWicked <= vendasReiLeao && vendasWicked <= vendasAuto) {
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
        if (vendasManha == 0 && vendasTarde == 0 && vendasNoite == 0) {
            sessaoMaisOcupada = "Não há vendas para calcular";
        } else if (vendasManha >= vendasTarde && vendasManha >= vendasNoite) {
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
        if (vendasManha == 0 && vendasTarde == 0 && vendasNoite == 0) {
            sessaoMenosOcupada = "Não há vendas para calcular";
        } else if (vendasManha <= vendasTarde && vendasManha <= vendasNoite) {
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

    private double calcularLucroPeca(double receita, int quantidadeVendas) {
        if (quantidadeVendas == 0) return 0;
        return receita / quantidadeVendas;
    }

    private double calcularLucroDaPecaPorSessao(double receita, int quantidadeVendas) {
        if (quantidadeVendas == 0) return 0;
        return receita / quantidadeVendas;
    }

    private double calcularLucroSessao(double receita, int quantidadeVendas) {
        if (quantidadeVendas == 0) return 0;
        return receita / quantidadeVendas;
    }

    public String calcularSessaoMaisLucrativaPeca1() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaWicked, vendasManhaWicked);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaWicked, vendasTardeWicked);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaWicked, vendasNoiteWicked);
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        } else if (lucroManha >= lucroTarde && lucroManha >= lucroNoite) {
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

    public String calcularSessaoMenosLucrativaPeca1() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaWicked, vendasManhaWicked);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaWicked, vendasTardeWicked);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaWicked, vendasNoiteWicked);
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        } else if (lucroManha <= lucroTarde && lucroManha <= lucroNoite) {
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

    public String calcularSessaoMaisLucrativaPeca2() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaReiLeao, vendasManhaReiLeao);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaReiLeao, vendasTardeReiLeao);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaReiLeao, vendasNoiteReiLeao);
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        } else if (lucroManha >= lucroTarde && lucroManha >= lucroNoite) {
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

    public String calcularSessaoMenosLucrativaPeca2() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaReiLeao, vendasManhaReiLeao);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaReiLeao, vendasTardeReiLeao);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaReiLeao, vendasNoiteReiLeao);
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        } else if (lucroManha <= lucroTarde && lucroManha <= lucroNoite) {
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

    public String calcularSessaoMaisLucrativaPeca3() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaAuto, vendasManhaAuto);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaAuto, vendasTardeAuto);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaAuto, vendasNoiteAuto);
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        } else if (lucroManha >= lucroTarde && lucroManha >= lucroNoite) {
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

    public String calcularSessaoMenosLucrativaPeca3() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaAuto, vendasManhaAuto);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaAuto, vendasTardeAuto);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaAuto, vendasNoiteAuto);
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        } else if (lucroManha <= lucroTarde && lucroManha <= lucroNoite) {
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

    public double getReceitaTotalPorPeca(String nome) {
        return switch (nome) {
            case "Wicked" -> receitaWicked;
            case "Rei Leao" -> receitaReiLeao;
            case "Auto da Compadecida" -> receitaAuto;
            default -> 0;
        };
    }

    public double getTicketMedioPorCliente() {
        int totalIngressos = calcularTotalVendas();
        double totalLucro = receitaWicked + receitaReiLeao + receitaAuto;
        if (totalIngressos == 0) return 0;
        return totalLucro / totalIngressos;
    }

    public double[] calcularReceitaMediaPorArea() {
        double totalA = 0, totalB = 0, totalC = 0, totalF = 0, totalN = 0;
        int qtdA = 0, qtdB = 0, qtdC = 0, qtdF = 0, qtdN = 0;

        for (Peca peca : pecasEstatisticas) {
            List<String> assentos = peca.getAssentos();
            for (String assento : assentos) {
                char area = assento.charAt(0);
                double preco = AreaUtil.getPrecoPorIdentificador(area);
                switch (area) {
                    case 'A' -> {
                        totalA += preco;
                        qtdA++;
                    }
                    case 'B' -> {
                        totalB += preco;
                        qtdB++;
                    }
                    case 'C' -> {
                        totalC += preco;
                        qtdC++;
                    }
                    case 'F' -> {
                        totalF += preco;
                        qtdF++;
                    }
                    case 'N' -> {
                        totalN += preco;
                        qtdN++;
                    }
                }
            }
        }

        vendasPlatA = qtdA;
        vendasPlatB = qtdB;
        vendasCamarote = qtdC;
        vendasFrisa = qtdF;
        vendasBalcao = qtdN;
        double mediaA = qtdA > 0 ? totalA / qtdA : 0;
        double mediaB = qtdB > 0 ? totalB / qtdB : 0;
        double mediaC = qtdC > 0 ? totalC / qtdC : 0;
        double mediaF = qtdF > 0 ? totalF / qtdF : 0;
        double mediaN = qtdN > 0 ? totalN / qtdN : 0;
        return new double[]{mediaA, mediaB, mediaC, mediaF, mediaN};
    }

    private int getIndiceSessao(Sessao sessao) {
        return switch (sessao) {
            case MANHA -> 0;
            case TARDE -> 1;
            case NOITE -> 2;
        };
    }

    public int getVendasWicked() {
        return vendasWicked;
    }

    public int getVendasReiLeao() {
        return vendasReiLeao;
    }

    public int getVendasAuto() {
        return vendasAuto;
    }

    public double getLucroWicked() {
        return lucroWicked;
    }

    public double getLucroReiLeao() {
        return lucroReiLeao;
    }

    public double getLucroAuto() {
        return lucroAuto;
    }

    public double getReceitaManha() {
        return receitaManha;
    }

    public double getReceitaTarde() {
        return receitaTarde;
    }

    public double getReceitaNoite() {
        return receitaNoite;
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

    public int getVendasManha() {
        return vendasManha;
    }

    public int getVendasTarde() {
        return vendasTarde;
    }

    public int getVendasNoite() {
        return vendasNoite;
    }

    public double getLucroManha() {
        return lucroManha;
    }

    public double getLucroTarde() {
        return lucroTarde;
    }

    public double getLucroNoite() {
        return lucroNoite;
    }

    public int getVendasPlatA() {
        return vendasPlatA;
    }

    public int getVendasPlatB() {
        return vendasPlatB;
    }

    public int getVendasCamarote() {
        return vendasCamarote;
    }

    public int getVendasFrisa() {
        return vendasFrisa;
    }

    public int getVendasBalcao() {
        return vendasBalcao;
    }
}