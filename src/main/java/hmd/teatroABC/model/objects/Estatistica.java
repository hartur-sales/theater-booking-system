package hmd.teatroABC.model.objects;

import hmd.teatroABC.model.entities.*;

import java.util.List;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 22/11/2024
 * @brief Class Estatistica
 */

public class Estatistica {
    private final List<Peca> pecas;
    private final List<Ingresso> ingressos;
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
        this.ingressos = Teatro.getIngressos();
        this.pecas = Teatro.getPecas();
        totalVendas = ingressos.size();
        calcularVendas();
        calcularReceita();
        receitasPorArea = calcularReceitaMediaPorArea();
        lucroManha = calcularLucroPorVenda(receitaManha, vendasManha);
        lucroTarde = calcularLucroPorVenda(receitaTarde, vendasTarde);
        lucroNoite = calcularLucroPorVenda(receitaNoite, vendasNoite);
        lucroPeca1 = calcularLucroPorVenda(receitaPeca1, vendasPeca1);
        lucroPeca2 = calcularLucroPorVenda(receitaPeca2, vendasPeca2);
        lucroPeca3 = calcularLucroPorVenda(receitaPeca3, vendasPeca3);
        sessaoMaisLucrativaPeca1 = calcularLucratividadeDaSessao(receitaPeca1, vendasManhaPeca1, vendasTardePeca1, vendasNoitePeca1, true);
        sessaoMenosLucrativaPeca1 = calcularLucratividadeDaSessao(receitaPeca1, vendasManhaPeca1, vendasTardePeca1, vendasNoitePeca1, false);
        sessaoMaisLucrativaPeca2 = calcularLucratividadeDaSessao(receitaPeca2, vendasManhaPeca2, vendasTardePeca2, vendasNoitePeca2, true);
        sessaoMenosLucrativaPeca2 = calcularLucratividadeDaSessao(receitaPeca2, vendasManhaPeca2, vendasTardePeca2, vendasNoitePeca2, false);
        sessaoMaisLucrativaPeca3 = calcularLucratividadeDaSessao(receitaPeca3, vendasManhaPeca3, vendasTardePeca3, vendasNoitePeca3, true);
        sessaoMenosLucrativaPeca3 = calcularLucratividadeDaSessao(receitaPeca3, vendasManhaPeca3, vendasTardePeca3, vendasNoitePeca3, false);
        ticketMedio = calcularTicketMedio();
        pecaMaisVendida = calcularPecasMaisMenosVendidas()[0];
        pecaMenosVendida = calcularPecasMaisMenosVendidas()[1];
        sessaoMaisOcupada = calcularSessaoMaisMenosOcupadas()[0];
        sessaoMenosOcupada = calcularSessaoMaisMenosOcupadas()[1];
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

    /**
     * Calcula as vendas totais e por sessão para cada peça, utilizando a lista de ingressos vendidos.
     * Atribui os totais para cada peça (vendasPeca1, vendasPeca2, vendasPeca3) e as sessões (manhã, tarde, noite),
     * assim como os totais gerais por sessão.
     */
    private void calcularVendas() {
        for (Ingresso ingresso : ingressos) {
            String nome = ingresso.getPeca().getNome();
            Sessao sessao = ingresso.getPeca().getSessao();

            if (nome.equals(pecas.get(0).getNome())) {
                vendasPeca1++;
                switch (sessao) {
                    case MANHA -> vendasManhaPeca1++;
                    case TARDE -> vendasTardePeca1++;
                    case NOITE -> vendasNoitePeca1++;
                }
            } else if (nome.equals(pecas.get(3).getNome())) {
                vendasPeca2++;
                switch (sessao) {
                    case MANHA -> vendasManhaPeca2++;
                    case TARDE -> vendasTardePeca2++;
                    case NOITE -> vendasNoitePeca2++;
                }
            } else if (nome.equals(pecas.get(6).getNome())) {
                vendasPeca3++;
                switch (sessao) {
                    case MANHA -> vendasManhaPeca3++;
                    case TARDE -> vendasTardePeca3++;
                    case NOITE -> vendasNoitePeca3++;
                }
            }
            switch (sessao) {
                case MANHA -> vendasManha++;
                case TARDE -> vendasTarde++;
                case NOITE -> vendasNoite++;
            }
        }
    }

    /**
     * Calcula as receitas totais separadas por peça e por sessão,
     * utilizando a lista de ingressos vendidos.
     * <p>
     * O método inicializa os vetores que armazenam a receita total por peça
     * e por sessão, então faz uma chamada recursiva ao método {@link #adicionarReceita(List, int, double[], double[])}
     * para processar cada ingresso individualmente.
     * <p>
     * Os resultados são armazenados nas variáveis receitaPeca1, receitaPeca2 e receitaPeca3
     * e nas variáveis receitaManha, receitaTarde e receitaNoite (referentes às sessões).
     */
    private void calcularReceita() {
        double[] receitas = {0, 0, 0};
        double[] receitasPorSessao = {0, 0, 0};
        adicionarReceita(ingressos, 0, receitas, receitasPorSessao);

        receitaPeca1 = receitas[0];
        receitaPeca2 = receitas[1];
        receitaPeca3 = receitas[2];

        receitaManha = receitasPorSessao[0];
        receitaTarde = receitasPorSessao[1];
        receitaNoite = receitasPorSessao[2];
    }

    /**
     * Método recursivo responsável por percorrer a lista de ingressos, somando os valores correspondentes
     * às receitas por peça e por sessão.
     * <p>
     * A recursividade ocorre ao ser chamado dentro de si, incrementando o índice de processamento (indice).
     * Para cada chamada:
     * <ul>
     *   <li>Obtém o ingresso atual a ser processado.</li>
     *   <li>Recupera a peça, o nome da peça e a sessão associada ao ingresso.</li>
     *   <li>Acrescenta o preço do ingresso ao total da peça correspondente (no vetor receitas) e ao total da sessão (no vetor receitasPorSessao).</li>
     *   <li>Chama a si para processar o próximo ingresso, até que todos sejam processados (caso base: indice ≥ ingressos.size()).</li>
     * </ul>
     *
     * @param ingressos         Lista de ingressos vendidos.
     * @param indice            Índice do ingresso atual a ser processado.
     * @param receitas          Vetor que acumula as receitas para cada peça (posição 0: Wicked, 1: Rei Leao, 2: Auto da Compadecida).
     * @param receitasPorSessao Vetor que acumula as receitas para cada sessão (posição 0: Manhã, 1: Tarde, 2: Noite).
     */
    private void adicionarReceita(List<Ingresso> ingressos, int indice, double[] receitas, double[] receitasPorSessao) {
        if (indice >= ingressos.size()) {
            return;
        }

        Ingresso ingresso = ingressos.get(indice);
        Peca peca = ingresso.getPeca();
        Sessao sessao = peca.getSessao();
        int idSessao = getIndiceSessao(sessao);
        double preco = ingresso.getPreco();

        receitasPorSessao[idSessao] += preco;
        String nome = peca.getNome();

        if (nome.equals(pecas.get(0).getNome())) {
            receitas[0] += preco;
        } else if (nome.equals(pecas.get(3).getNome())) {
            receitas[1] += preco;
        } else if (nome.equals(pecas.get(6).getNome())) {
            receitas[2] += preco;
        }

        adicionarReceita(ingressos, indice + 1, receitas, receitasPorSessao);
    }

    /**
     * Calcula as peças mais e menos vendidas, com base nas contagens de vendas individuais.
     * <p>
     * O método retorna um array de duas posições do tipo String. O primeiro elemento ([0]) é a
     * peça mais vendida e o segundo ([1]), a peça menos vendida.
     * </p>
     *
     * <p>
     * Se não houver vendas, ambos os retornos serão "Não há vendas para calcular".
     * </p>
     *
     * @return String[] onde [0] = peça mais vendida, [1] = peça menos vendida
     */
    private String[] calcularPecasMaisMenosVendidas() {
        int[] vendas = {vendasPeca1, vendasPeca2, vendasPeca3};
        if (vendasPeca1 == 0 && vendasPeca2 == 0 && vendasPeca3 == 0) {
            return new String[]{"Não há vendas para calcular", "Não há vendas para calcular"};
        }

        int indiceMaisVendas = getMaiorIndice(vendas);
        int indiceMenosVendas = getMenorIndice(vendas);

        String mais = "Peça " + (indiceMaisVendas + 1) + " (" + pecas.get(indiceMaisVendas * 3).getNome() + ")";
        String menos = "Peça " + (indiceMenosVendas + 1) + " (" + pecas.get(indiceMenosVendas * 3).getNome() + ")";

        return new String[]{mais, menos};
    }

    /**
     * Calcula, dentre todas as sessões, qual foi a mais ocupada e a menos ocupada com base na quantidade de vendas.
     * <p>
     * Retorna um array de duas Strings:
     * [0] = nome da sessão mais ocupada ("Manhã", "Tarde" ou "Noite")
     * [1] = nome da sessão menos ocupada
     * Se não houver vendas, ambos retornam "Não há vendas para calcular".
     * </p>
     *
     * @return String[] onde [0] = sessão mais ocupada, [1] = sessão menos ocupada
     */
    private String[] calcularSessaoMaisMenosOcupadas() {
        int[] vendas = {vendasManha, vendasTarde, vendasNoite};
        String[] sessoes = {"Manha", "Tarde", "Noite"};

        if (vendasManha == 0 && vendasTarde == 0 && vendasNoite == 0) {
            return new String[]{"Não há vendas para calcular", "Não há vendas para calcular"};
        }

        int indiceMaisVendas = getMaiorIndice(vendas);
        int indiceMenosVendas = getMenorIndice(vendas);

        return new String[]{sessoes[indiceMaisVendas], sessoes[indiceMenosVendas]};
    }

    /**
     * Calcula a sessão mais ou menos lucrativa para determinada peça.
     *
     * @param receita       valor da receita da peça
     * @param vendasManha   vendas da peça na manhã
     * @param vendasTarde   vendas da peça na tarde
     * @param vendasNoite   vendas da peça na noite
     * @param maisLucrativa true se deseja a mais lucrativa, false para menos lucrativa
     * @return sessão mais ou menos lucrativa ("Manhã", "Tarde", "Noite"), ou mensagem de erro
     */
    private String calcularLucratividadeDaSessao(double receita, int vendasManha, int vendasTarde, int vendasNoite, boolean maisLucrativa) {
        double lucroManha = calcularLucroPorVenda(receita, vendasManha);
        double lucroTarde = calcularLucroPorVenda(receita, vendasTarde);
        double lucroNoite = calcularLucroPorVenda(receita, vendasNoite);
        double[] lucros = {lucroManha, lucroTarde, lucroNoite};
        String[] sessoes = {"Manha", "Tarde", "Noite"};
        if (lucroManha == 0 && lucroTarde == 0 && lucroNoite == 0) {
            return "Não há vendas para calcular";
        }
        int indice = maisLucrativa ? getMaiorIndice(lucros) : getMenorIndice(lucros);
        return sessoes[indice];
    }

    /**
     * Calcula o lucro médio por venda, evitando divisão por zero.
     *
     * @param receita          Valor total de receita.
     * @param quantidadeVendas Quantidade de vendas realizadas.
     * @return Lucro médio por venda, ou 0 caso não haja vendas.
     */
    private double calcularLucroPorVenda(double receita, int quantidadeVendas) {
        if (quantidadeVendas == 0) return 0;
        return receita / quantidadeVendas;
    }

    private double calcularTicketMedio() {
        int totalIngressos = ingressos.size();
        double totalLucro = receitaPeca1 + receitaPeca2 + receitaPeca3;
        if (totalIngressos == 0) return 0;
        return totalLucro / totalIngressos;
    }

    /**
     * Calcula a receita média por área considerando todos os ingressos vendidos.
     * A ordem do array retornado é: Plateia A, Plateia B, Camarotes, Frisas, Balcão Nobre.
     *
     * @return Array de médias de receita por área.
     */
    private double[] calcularReceitaMediaPorArea() {
        // Índices: 0-Plateia A | 1-Plateia B | 2-Camarote | 3-Frisa | 4-Balcão Nobre
        double[] totais = new double[5];
        int[] quantidades = new int[5];

        for (Ingresso ingresso : ingressos) {
            Area area = ingresso.getArea();
            double preco = ingresso.getPreco();

            int idx = switch (area) {
                case PLATEIA_A -> 0;
                case PLATEIA_B -> 1;
                case CAMAROTE1,
                     CAMAROTE2,
                     CAMAROTE3,
                     CAMAROTE4,
                     CAMAROTE5 -> 2;
                case FRISA1,
                     FRISA2,
                     FRISA3,
                     FRISA4,
                     FRISA5,
                     FRISA6 -> 3;
                case BALCAO_NOBRE -> 4;
            };

            totais[idx] += preco;
            quantidades[idx]++;
        }
        vendasPlatA = quantidades[0];
        vendasPlatB = quantidades[1];
        vendasCamarote = quantidades[2];
        vendasFrisa = quantidades[3];
        vendasBalcao = quantidades[4];

        double[] medias = new double[5];
        for (int i = 0; i < medias.length; i++) {
            medias[i] = quantidades[i] > 0 ? totais[i] / quantidades[i] : 0;
        }
        return medias;
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