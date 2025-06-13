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
    private final List<Peca> pecas;
    private int vendasPeca1 = 0;
    private int vendasPeca2 = 0;
    private int vendasPeca3 = 0;
    private double receitaPeca1 = 0;
    private double receitaPeca2 = 0;
    private double receitaPeca3 = 0;
    private double lucroPeca1 = 0;
    private double lucroPeca2 = 0;
    private double lucroPeca3 = 0;
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

    public Estatistica() {
        this.pecas = Teatro.getPecas();
        calcularVendas();
        calcularReceita();
        lucroManha = calcularLucroSessao(receitaManha, vendasManha);
        lucroTarde = calcularLucroSessao(receitaTarde, vendasTarde);
        lucroNoite = calcularLucroSessao(receitaNoite, vendasNoite);
        lucroPeca1 = calcularLucroPeca(receitaPeca1, vendasPeca1);
        lucroPeca2 = calcularLucroPeca(receitaPeca2, vendasPeca2);
        lucroPeca3 = calcularLucroPeca(receitaPeca3, vendasPeca3);
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
        for (Peca peca : pecas) {
            String nome = peca.getNome();
            String sessao = peca.getSessao().getNome();
            int ingressosVendidos = peca.getIngressosVendidos();
            if (nome.equals("Wicked")) {
                vendasPeca1 += ingressosVendidos;
                if (sessao.equals("Manha")) vendasManhaWicked += ingressosVendidos;
                if (sessao.equals("Tarde")) vendasTardeWicked += ingressosVendidos;
                if (sessao.equals("Noite")) vendasNoiteWicked += ingressosVendidos;
            }
            if (nome.equals("Rei Leao")) {
                vendasPeca2 += ingressosVendidos;
                if (sessao.equals("Manha")) vendasManhaReiLeao += ingressosVendidos;
                if (sessao.equals("Tarde")) vendasTardeReiLeao += ingressosVendidos;
                if (sessao.equals("Noite")) vendasNoiteReiLeao += ingressosVendidos;
            }
            if (nome.equals("Auto da Compadecida")) {
                vendasPeca3 += ingressosVendidos;
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
        for (Peca peca : pecas) {
            Sessao sessao = peca.getSessao();
            int idSessao = getIndiceSessao(sessao);
            adicionarReceita(peca.getNome(), peca.getAssentos(), 0, receitas, idSessao, receitasPorSessao);
        }
        receitaPeca1 = receitas[0];
        receitaPeca2 = receitas[1];
        receitaPeca3 = receitas[2];

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
        return vendasPeca1 + vendasPeca2 + vendasPeca3;
    }

    public String calcularPecaMaisVendida() {
        String pecaMaisVendida;
        if (vendasPeca1 == 0 && vendasPeca2 == 0 && vendasPeca3 == 0) {
            pecaMaisVendida = "Não há vendas para calcular";
        } else if (vendasPeca1 >= vendasPeca2 && vendasPeca1 >= vendasPeca3) {
            pecaMaisVendida = "Peça 1 (" + pecas.getFirst().getNome() + ")";
        } else if (vendasPeca2 >= vendasPeca1 && vendasPeca2 >= vendasPeca3) {
//            pecaMaisVendida = BUNDLE.getString("Rei Leão");
            pecaMaisVendida = "Peça 2 (" + pecas.get(3).getNome() + ")";
        } else {
            pecaMaisVendida = "Peça 3 (" + pecas.get(6).getNome() + ")";
        }
        return pecaMaisVendida;
    }

    public String calcularPecaMenosVendida() {
        String pecaMenosVendida;
        if (vendasPeca1 == 0 && vendasPeca2 == 0 && vendasPeca3 == 0) {
            pecaMenosVendida = "Não há vendas para calcular";
        } else if (vendasPeca1 <= vendasPeca2 && vendasPeca1 <= vendasPeca3) {
            pecaMenosVendida = "Peça 1 (" + pecas.getFirst().getNome() + ")";
        } else if (vendasPeca2 <= vendasPeca1 && vendasPeca2 <= vendasPeca3) {
//            pecaMenosVendida = BUNDLE.getString("Rei Leao");
            pecaMenosVendida = "Peça 2 (" + pecas.get(3).getNome() + ")";
        } else {
            pecaMenosVendida = "Peça 3 (" + pecas.get(6).getNome() + ")";
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
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca1, vendasManhaWicked);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca1, vendasTardeWicked);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca1, vendasNoiteWicked);
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
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca1, vendasManhaWicked);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca1, vendasTardeWicked);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca1, vendasNoiteWicked);
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
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca2, vendasManhaReiLeao);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca2, vendasTardeReiLeao);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca2, vendasNoiteReiLeao);
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
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca2, vendasManhaReiLeao);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca2, vendasTardeReiLeao);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca2, vendasNoiteReiLeao);
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
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca3, vendasManhaAuto);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca3, vendasTardeAuto);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca3, vendasNoiteAuto);
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
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca3, vendasManhaAuto);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca3, vendasTardeAuto);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca3, vendasNoiteAuto);
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

    public double getTicketMedioPorCliente() {
        int totalIngressos = calcularTotalVendas();
        double totalLucro = receitaPeca1 + receitaPeca2 + receitaPeca3;
        if (totalIngressos == 0) return 0;
        return totalLucro / totalIngressos;
    }

    public double[] calcularReceitaMediaPorArea() {
        double totalA = 0, totalB = 0, totalC = 0, totalF = 0, totalN = 0;
        int qtdA = 0, qtdB = 0, qtdC = 0, qtdF = 0, qtdN = 0;

        for (Peca peca : pecas) {
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

    public int getVendasPeca1() {
        return vendasPeca1;
    }

    public int getVendasPeca2() {
        return vendasPeca2;
    }

    public int getVendasPeca3() {
        return vendasPeca3;
    }

    public double getLucroPeca1() {
        return lucroPeca1;
    }

    public double getLucroPeca2() {
        return lucroPeca2;
    }

    public double getLucroPeca3() {
        return lucroPeca3;
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

    public double getReceitaPeca1() {
        return receitaPeca1;
    }

    public double getReceitaPeca2() {
        return receitaPeca2;
    }

    public double getReceitaPeca3() {
        return receitaPeca3;
    }
}