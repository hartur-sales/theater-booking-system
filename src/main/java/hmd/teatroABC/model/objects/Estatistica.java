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
    private final int totalVendas;
    private int vendasPeca1;
    private int vendasPeca2;
    private int vendasPeca3;
    private double receitaPeca1;
    private double receitaPeca2;
    private double receitaPeca3;
    private final double lucroPeca1;
    private final double lucroPeca2;
    private final double lucroPeca3;
    private double receitaManha;
    private double receitaTarde;
    private double receitaNoite;
    private final double lucroManha;
    private final double lucroTarde;
    private final double lucroNoite;
    private int vendasManha;
    private int vendasTarde;
    private int vendasNoite;
    private int vendasManhaPeca1;
    private int vendasTardePeca1;
    private int vendasNoitePeca1;
    private int vendasManhaPeca2;
    private int vendasTardePeca2;
    private int vendasNoitePeca2;
    private int vendasManhaPeca3;
    private int vendasTardePeca3;
    private int vendasNoitePeca3;
    private int vendasPlatA;
    private int vendasPlatB;
    private int vendasCamarote;
    private int vendasFrisa;
    private int vendasBalcao;
    private final double ticketMedio;
    private final String pecaMaisVendida;
    private final String pecaMenosVendida;
    private final String sessaoMaisLucrativaPeca1;
    private final String sessaoMenosLucrativaPeca1;
    private final String sessaoMaisLucrativaPeca2;
    private final String sessaoMenosLucrativaPeca2;
    private final String sessaoMaisLucrativaPeca3;
    private final String sessaoMenosLucrativaPeca3;
    private final String sessaoMaisOcupada;
    private final String sessaoMenosOcupada;
    private final double[] receitasPorArea;

    public Estatistica() {
        this.pecas = Teatro.getPecas();
        calcularVendas();
        calcularReceita();
        receitasPorArea = calcularReceitaMediaPorArea();
        totalVendas = calcularTotalVendas();
        lucroManha = calcularLucroSessao(receitaManha, vendasManha);
        lucroTarde = calcularLucroSessao(receitaTarde, vendasTarde);
        lucroNoite = calcularLucroSessao(receitaNoite, vendasNoite);
        lucroPeca1 = calcularLucroPeca(receitaPeca1, vendasPeca1);
        lucroPeca2 = calcularLucroPeca(receitaPeca2, vendasPeca2);
        lucroPeca3 = calcularLucroPeca(receitaPeca3, vendasPeca3);
        sessaoMaisLucrativaPeca1 = calcularSessaoMaisLucrativaPeca1();
        sessaoMenosLucrativaPeca1 = calcularSessaoMenosLucrativaPeca1();
        sessaoMaisLucrativaPeca2 = calcularSessaoMaisLucrativaPeca2();
        sessaoMenosLucrativaPeca2 = calcularSessaoMenosLucrativaPeca2();
        sessaoMaisLucrativaPeca3 = calcularSessaoMaisLucrativaPeca3();
        sessaoMenosLucrativaPeca3 = calcularSessaoMenosLucrativaPeca3();
        ticketMedio = calcularTicketMedio();
        pecaMaisVendida = calcularPecaMaisVendida();
        pecaMenosVendida = calcularPecaMenosVendida();
        sessaoMaisOcupada = calcularSessaoMaisOcupada();
        sessaoMenosOcupada = calcularSessaoMenosOcupada();
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
            Sessao sessao = peca.getSessao();
            int ingressosVendidos = peca.getIngressosVendidos();
            if (nome.equals(pecas.get(0).getNome())) {
                vendasPeca1 += ingressosVendidos;
                switch (sessao) {
                    case MANHA -> vendasManhaPeca1 += ingressosVendidos;
                    case TARDE -> vendasTardePeca1 += ingressosVendidos;
                    case NOITE -> vendasNoitePeca1 += ingressosVendidos;
                }
            }
            if (nome.equals(pecas.get(3).getNome())) {
                vendasPeca2 += ingressosVendidos;
                switch (sessao) {
                    case MANHA -> vendasManhaPeca2 += ingressosVendidos;
                    case TARDE -> vendasTardePeca2 += ingressosVendidos;
                    case NOITE -> vendasNoitePeca2 += ingressosVendidos;
                }
            }
            if (nome.equals(pecas.get(6).getNome())) {
                vendasPeca3 += ingressosVendidos;
                switch (sessao) {
                    case MANHA -> vendasManhaPeca3 += ingressosVendidos;
                    case TARDE -> vendasTardePeca3 += ingressosVendidos;
                    case NOITE -> vendasNoitePeca3 += ingressosVendidos;
                }

            }

            switch (sessao) {
                case MANHA -> vendasManha += ingressosVendidos;
                case TARDE -> vendasTarde += ingressosVendidos;
                case NOITE -> vendasNoite += ingressosVendidos;
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

    private int calcularTotalVendas() {
        return vendasPeca1 + vendasPeca2 + vendasPeca3;
    }

    private String calcularPecaMaisVendida() {
        int[] vendas = {vendasPeca1, vendasPeca2, vendasPeca3};
        if (vendasPeca1 == 0 && vendasPeca2 == 0 && vendasPeca3 == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMaisVendas = getMaiorIndice(vendas);
        return "Peça " + (indiceMaisVendas + 1) + " (" + pecas.get((indiceMaisVendas * 3)).getNome() + ")";
    }

    private String calcularPecaMenosVendida() {
        int[] vendas = {vendasPeca1, vendasPeca2, vendasPeca3};
        if (vendasPeca1 == 0 && vendasPeca2 == 0 && vendasPeca3 == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMenosVendas = getMenorIndice(vendas);
        return "Peça " + (indiceMenosVendas + 1) + " (" + pecas.get((indiceMenosVendas * 3)).getNome() + ")";
    }

    private String calcularSessaoMaisOcupada() {
        int[] vendas = {vendasManha, vendasTarde, vendasNoite};
        if (vendasManha == 0 && vendasTarde == 0 && vendasNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMaisVendas = getMaiorIndice(vendas);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMaisVendas];
    }

    private String calcularSessaoMenosOcupada() {
        int[] vendas = {vendasManha, vendasTarde, vendasNoite};
        if (vendasManha == 0 && vendasTarde == 0 && vendasNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMenosVendas = getMenorIndice(vendas);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMenosVendas];
    }

    private String calcularSessaoMaisLucrativaPeca1() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca1, vendasManhaPeca1);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca1, vendasTardePeca1);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca1, vendasNoitePeca1);
        double[] lucros = {lucroManha, lucroTarde, lucroNoite};
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMaisLucro = getMaiorIndice(lucros);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMaisLucro];
    }

    private String calcularSessaoMenosLucrativaPeca1() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca1, vendasManhaPeca1);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca1, vendasTardePeca1);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca1, vendasNoitePeca1);
        double[] lucros = {lucroManha, lucroTarde, lucroNoite};
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMenosLucro = getMenorIndice(lucros);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMenosLucro];
    }

    private String calcularSessaoMaisLucrativaPeca2() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca2, vendasManhaPeca2);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca2, vendasTardePeca2);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca2, vendasNoitePeca2);
        double[] lucros = {lucroManha, lucroTarde, lucroNoite};
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMaisLucro = getMaiorIndice(lucros);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMaisLucro];
    }

    private String calcularSessaoMenosLucrativaPeca2() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca2, vendasManhaPeca2);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca2, vendasTardePeca2);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca2, vendasNoitePeca2);
        double[] lucros = {lucroManha, lucroTarde, lucroNoite};
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMenosLucro = getMenorIndice(lucros);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMenosLucro];
    }

    private String calcularSessaoMaisLucrativaPeca3() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca3, vendasManhaPeca3);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca3, vendasTardePeca3);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca3, vendasNoitePeca3);
        double[] lucros = {lucroManha, lucroTarde, lucroNoite};
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMaisLucro = getMaiorIndice(lucros);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMaisLucro];
    }

    private String calcularSessaoMenosLucrativaPeca3() {
        double lucroManha = calcularLucroDaPecaPorSessao(receitaPeca3, vendasManhaPeca3);
        double lucroTarde = calcularLucroDaPecaPorSessao(receitaPeca3, vendasTardePeca3);
        double lucroNoite = calcularLucroDaPecaPorSessao(receitaPeca3, vendasNoitePeca3);
        double[] lucros = {lucroManha, lucroTarde, lucroNoite};
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indiceMenosLucro = getMenorIndice(lucros);
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        return sessoes[indiceMenosLucro];
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

    private double calcularTicketMedio() {
        int totalIngressos = calcularTotalVendas();
        double totalLucro = receitaPeca1 + receitaPeca2 + receitaPeca3;
        if (totalIngressos == 0) return 0;
        return totalLucro / totalIngressos;
    }

    private double[] calcularReceitaMediaPorArea() {
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

    private int getMaiorIndice(int[] valores) {
        int maiorIndice = 0;
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] > valores[maiorIndice]) {
                maiorIndice = i;
            }
        }
        return maiorIndice;
    }

    private int getMaiorIndice(double[] valores) {
        int maiorIndice = 0;
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] > valores[maiorIndice]) {
                maiorIndice = i;
            }
        }
        return maiorIndice;
    }

    private int getMenorIndice(int[] valores) {
        int menorIndice = 0;
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] < valores[menorIndice]) {
                menorIndice = i;
            }
        }
        return menorIndice;
    }

    private int getMenorIndice(double[] valores) {
        int menorIndice = 0;
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] < valores[menorIndice]) {
                menorIndice = i;
            }
        }
        return menorIndice;
    }

    public int getTotalVendas() {
        return totalVendas;
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

    public String getSessaoMaisLucrativaPeca1() {
        return sessaoMaisLucrativaPeca1;
    }

    public String getSessaoMenosLucrativaPeca1() {
        return sessaoMenosLucrativaPeca1;
    }

    public String getSessaoMaisLucrativaPeca2() {
        return sessaoMaisLucrativaPeca2;
    }

    public String getSessaoMenosLucrativaPeca2() {
        return sessaoMenosLucrativaPeca2;
    }

    public String getSessaoMaisLucrativaPeca3() {
        return sessaoMaisLucrativaPeca3;
    }

    public String getSessaoMenosLucrativaPeca3() {
        return sessaoMenosLucrativaPeca3;
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

    public double getTicketMedio() {
        return ticketMedio;
    }

    public String getPecaMaisVendida() {
        return pecaMaisVendida;
    }

    public String getPecaMenosVendida() {
        return pecaMenosVendida;
    }

    public String getSessaoMaisOcupada() {
        return sessaoMaisOcupada;
    }

    public String getSessaoMenosOcupada() {
        return sessaoMenosOcupada;
    }

    public double[] getReceitasPorArea() {
        return receitasPorArea;
    }
}