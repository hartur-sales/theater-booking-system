package hmd.teatroABC.model.objects;

import hmd.teatroABC.model.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private double lucroPeca1;
    private double lucroPeca2;
    private double lucroPeca3;
    private double receitaManha;
    private double receitaTarde;
    private double receitaNoite;
    private double lucroManha;
    private double lucroTarde;
    private double lucroNoite;
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
    private double ticketMedio;
    private String pecaMaisVendida;
    private String pecaMenosVendida;
    private String sessaoMaisLucrativaPeca1;
    private String sessaoMenosLucrativaPeca1;
    private String sessaoMaisLucrativaPeca2;
    private String sessaoMenosLucrativaPeca2;
    private String sessaoMaisLucrativaPeca3;
    private String sessaoMenosLucrativaPeca3;
    private String sessaoMaisOcupada;
    private String sessaoMenosOcupada;
    private double[] receitasPorArea;

    public Estatistica() {
        this.pecas = Teatro.getPecas();
        this.ingressos = Teatro.getIngressos();
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

    public Estatistica(String filtroPeca, Sessao filtroSessao, Area filtroArea) {
        this.ingressos = Teatro.getIngressos()
                .stream()
                .filter(i -> filtroPeca == null || i.getPeca().getNome().equals(filtroPeca))
                .filter(i -> filtroSessao == null || i.getPeca().getSessao() == filtroSessao)
                .filter(i -> filtroArea == null || i.getArea().getNomeLocal().equals(filtroArea.getNomeLocal()))
                .collect(Collectors.toList());
        this.pecas = Teatro.getPecas();
        this.totalVendas = ingressos.size();
        System.out.println(ingressos);
    }

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

    public double calcularReceitaTotal() {
        double total = 0;
        for (Ingresso ingresso : ingressos) {
            total += ingresso.getPreco();
        }
        return total;
    }

    /**
     * Identifica a área do evento com a maior quantidade de ingressos vendidos.
     * <p>
     * Caso haja empate, retorna uma mensagem contendo todas as áreas empatadas, no formato:
     * "Empate entre [Área 1] e [Área 2]".
     * Caso exista apenas uma área com o maior número de vendas, retorna apenas o nome dessa área.
     * Caso não haja vendas registradas, retorna uma mensagem padrão indicando ausência de vendas.
     * <p>
     * O processo de verificação percorre todas as áreas, encontra o maior número de vendas,
     * e então busca todas as áreas que possuem essa mesma quantidade de ingressos vendidos
     * para montar a mensagem de empate, caso necessário.
     *
     * @return o nome da área mais ocupada, uma mensagem de empate, ou uma mensagem padrão se não houver vendas.
     */
    public String calcularAreaMaisOcupada() {
        int maiorContagem = 0;

        // Descobre o maior valor de vendas
        for (Area area : Area.values()) {
            int contagem = 0;
            for (Ingresso ingresso : ingressos) {
                if (ingresso.getArea() == area) {
                    contagem++;
                }
            }
            if (contagem > maiorContagem) {
                maiorContagem = contagem;
            }
        }

        if (maiorContagem == 0) {
            return "Não há vendas para calcular";
        }

        List<String> areasEmpatadas = new ArrayList<>();
        for (Area area : Area.values()) {
            int contagem = 0;
            for (Ingresso ingresso : ingressos) {
                if (ingresso.getArea() == area) {
                    contagem++;
                }
            }
            if (contagem == maiorContagem) {
                areasEmpatadas.add(area.getNomeLocal());
            }
        }

        if (areasEmpatadas.size() == 1) {
            return areasEmpatadas.getFirst();
        } else {
            return "Empate entre " + String.join(" e ", areasEmpatadas);
        }
    }

    /**
     * Identifica a área do evento com a menor quantidade de ingressos vendidos,
     * considerando apenas áreas que possuem pelo menos uma venda.
     * <p>
     * Caso haja empate, retorna uma mensagem contendo todas as áreas empatadas, no formato:
     * "Empate entre [Área 1] e [Área 2]".
     * Caso exista apenas uma área com o menor número de vendas, retorna apenas o nome dessa área.
     * Caso não haja vendas registradas, retorna uma mensagem padrão indicando ausência de vendas.
     * <p>
     * O processo de verificação percorre todas as áreas, identifica o menor número de vendas (>0),
     * e então busca todas as áreas que apresentam essa mesma quantidade de vendas para montar
     * a mensagem de empate, caso seja o caso.
     *
     * @return o nome da área menos ocupada, uma mensagem de empate, ou uma mensagem padrão se não houver vendas.
     */
    public String calcularAreaMenosOcupada() {
        Integer menorContagem = null;

        for (Area area : Area.values()) {
            int contagem = 0;
            for (Ingresso ingresso : ingressos) {
                if (ingresso.getArea() == area) {
                    contagem++;
                }
            }
            if (contagem > 0 && (menorContagem == null || contagem < menorContagem)) {
                menorContagem = contagem;
            }
        }

        if (menorContagem == null) {
            return "Não há vendas para calcular";
        }

        List<String> areasEmpatadas = new ArrayList<>();
        for (Area area : Area.values()) {
            int contagem = 0;
            for (Ingresso ingresso : ingressos) {
                if (ingresso.getArea() == area) {
                    contagem++;
                }
            }
            if (contagem == menorContagem) {
                areasEmpatadas.add(area.getNomeLocal());
            }
        }

        if (areasEmpatadas.size() == 1) {
            return areasEmpatadas.getFirst();
        } else {
            return "empate entre " + String.join(" e ", areasEmpatadas);
        }
    }

    /**
     * Identifica a peça com maior número de ingressos vendidos.
     * <p>
     * O método percorre todos os ingressos para contar a quantidade de vendas das peças.
     * Caso não haja vendas, retorna a mensagem "Nenhuma venda registrada".
     * Se houver empate entre duas ou mais peças na quantidade máxima de vendas,
     * retorna uma mensagem indicando o empate e os nomes das peças empatadas, separados por vírgula.
     * Caso apenas uma peça se destaque, retorna somente o seu nome.
     * </p>
     *
     * @return O nome da peça mais vendida, ou uma mensagem indicando empate, ou uma mensagem padrão caso não haja vendas.
     */
    public String calcularPecaMaisVendida() {
        String[] nomesDasPecas = {
                pecas.get(0).getNome(),
                pecas.get(3).getNome(),
                pecas.get(6).getNome()
        };

        int[] contagens = new int[3];

        for (Ingresso ingresso : ingressos) {
            String nome = ingresso.getPeca().getNome();
            if (nome.equals(nomesDasPecas[0])) {
                contagens[0]++;
            } else if (nome.equals(nomesDasPecas[1])) {
                contagens[1]++;
            } else if (nome.equals(nomesDasPecas[2])) {
                contagens[2]++;
            }
        }

        int maior = 0;
        for (int cont : contagens) {
            if (cont > maior) maior = cont;
        }

        if (maior == 0) {
            return "Não há vendas para calcular";
        }

        List<String> pecasEmpatadas = new ArrayList<>();
        for (int i = 0; i < contagens.length; i++)
            if (contagens[i] == maior) pecasEmpatadas.add(nomesDasPecas[i]);

        if (pecasEmpatadas.size() == 1) {
            return pecasEmpatadas.getFirst();
        } else {
            return "empate entre " + String.join(" e ", pecasEmpatadas);
        }
    }

    /**
     * Identifica a sessão (Manhã, Tarde ou Noite) com o maior número de ingressos vendidos.
     * <p>
     * O método percorre todos os ingressos e contabiliza as vendas para cada sessão.
     * Caso nenhuma venda tenha sido registrada, retorna "Nenhuma venda registrada.".
     * Se houver empate entre duas ou três sessões na maior quantidade de vendas,
     * retorna uma mensagem indicando o empate e os nomes das sessões empatadas, separados por vírgula.
     * Caso apenas uma sessão se destaque, retorna somente o seu nome.
     * </p>
     *
     * @return O nome da sessão mais ocupada, ou uma mensagem indicando empate, ou uma mensagem padrão caso não haja vendas.
     */
    public String calcularSessaoMaisOcupada() {
        int manha = 0, tarde = 0, noite = 0;

        for (Ingresso i : ingressos) {
            Sessao sessao = i.getPeca().getSessao();
            switch (sessao) {
                case MANHA -> manha++;
                case TARDE -> tarde++;
                case NOITE -> noite++;
            }
        }

        if (manha == 0 && tarde == 0 && noite == 0) {
            return "Nenhuma venda registrada.";
        }

        int max = Math.max(manha, Math.max(tarde, noite));
        List<String> sessoesEmpatadas = new ArrayList<>();

        if (manha == max) sessoesEmpatadas.add("Manhã");
        if (tarde == max) sessoesEmpatadas.add("Tarde");
        if (noite == max) sessoesEmpatadas.add("Noite");

        if (sessoesEmpatadas.size() == 1) {
            return sessoesEmpatadas.getFirst();
        } else {
            return "empate entre " + String.join(", ", sessoesEmpatadas);
        }
    }

    /**
     * Retorna a sessão com o menor número de ingressos vendidos.
     * <p>
     * O método contabiliza o número de ingressos vendidos para cada sessão
     * (manhã, tarde e noite). Utiliza a Stream API do Java para calcular o menor valor
     * dentre as sessões ocupadas (aquelas com pelo menos uma venda), utilizando o método
     * {@code Integer::compareTo} para comparar as quantidades.
     * <br>
     * Caso não haja vendas registradas, retorna uma mensagem indicando a ausência de vendas.
     * Se houver empate no menor número de vendas entre sessões, a mensagem retorna o empate,
     * listando as sessões empatadas separadas por "e".
     * </p>
     *
     * @return Uma {@code String} indicando a sessão menos ocupada, ou "Empate entre ..." em caso de empate,
     * ou "Nenhuma venda registrada." se não houver vendas.
     */
    public String calcularSessaoMenosOcupada() {
        int manha = 0, tarde = 0, noite = 0;

        for (Ingresso i : ingressos) {
            Sessao sessao = i.getPeca().getSessao();
            switch (sessao) {
                case MANHA -> manha++;
                case TARDE -> tarde++;
                case NOITE -> noite++;
            }
        }

        if (manha == 0 && tarde == 0 && noite == 0) {
            return "Nenhuma venda registrada.";
        }

        List<Integer> valores = new ArrayList<>();
        if (manha > 0) valores.add(manha);
        if (tarde > 0) valores.add(tarde);
        if (noite > 0) valores.add(noite);

        int min = valores.stream().min(Integer::compareTo).orElse(0);
        List<String> sessoesEmpatadas = new ArrayList<>();

        if (manha == min && manha > 0) sessoesEmpatadas.add("Manhã");
        if (tarde == min && tarde > 0) sessoesEmpatadas.add("Tarde");
        if (noite == min && noite > 0) sessoesEmpatadas.add("Noite");

        if (sessoesEmpatadas.size() == 1) {
            return sessoesEmpatadas.getFirst();
        } else {
            return "empate entre " + String.join(", ", sessoesEmpatadas);
        }
    }

    public String getPecaMaisVistaPorSessao() {
        int[] vendasPorPeca = {vendasManhaPeca1, vendasTardePeca1, vendasNoitePeca1,
                               vendasManhaPeca2, vendasTardePeca2, vendasNoitePeca2,
                               vendasManhaPeca3, vendasTardePeca3, vendasNoitePeca3};

        int maiorIndice = getMaiorIndice(vendasPorPeca);
        return "Peça " + (maiorIndice / 3 + 1) + " (" + pecas.get(maiorIndice).getNome() + ")";
    }

    public String calcularPorcentagemOcupacao() {
        if (ingressos.isEmpty()) {
            return "Nenhum ingresso vendido para essa área.";
        }

        Area area = ingressos.getFirst().getArea();
        int vendidos = ingressos.size();
        int capacidade = area.getnLugares();

        double porcentagem = ((double) vendidos / capacidade) * 100;
        return String.format("%.2f%%", porcentagem);
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