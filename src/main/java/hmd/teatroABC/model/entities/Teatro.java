package hmd.teatroABC.model.entities;

import hmd.teatroABC.controller.FinalizarCompraController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 22/11/2024
 * @brief Class Teatro
 */

public class Teatro {
    private static List<Peca> pecas = new ArrayList<>();
    public static List<Pessoa> pessoas = new ArrayList<>();
    public static List<String> log = new ArrayList<>();
    static File pecasFile = new File("src/main/java/hmd/teatroABC/model/database/pecas.txt");
    static File pessoasFile = new File("src/main/java/hmd/teatroABC/model/database/pessoas.txt");
    static File logFile = new File("src/main/resources/out/log.csv");

    public static final String TELA_SELECIONAR_ASSENTOS = "/hmd/teatroABC/tela_selecionar_assentos.fxml";
    public static final String TELA_DIGITAR_CPF = "/hmd/teatroABC/digitar_cpf_tela.fxml";
    public static final String TELA_ESTATISTICAS = "/hmd/teatroABC/estatisticas_tela.fxml";
    public static final String TELA_IMPRIMIR_INGRESSO = "/hmd/teatroABC/imprimir_ingresso.fxml";
    public static final String TELA_INICIAL = "/hmd/teatroABC/tela_inicial.fxml";
    public static final String TELA_COMPRA_FINALIZADA = "/hmd/teatroABC/compra_finalizada_tela.fxml";
    public static final String TELA_FINALIZAR_COMPRA = "/hmd/teatroABC/teste.fxml";

    public static final int STAGE_WIDTH = 1200;
    public static final int STAGE_HEIGHT = 800;

    public void carregarPecas() {
        try (BufferedReader br = new BufferedReader(new FileReader(pecasFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split(",", -1);
                String nome = partes[0];
                Sessao turno = Sessao.valueOf(partes[1]);
                int ingressosVendidos = Integer.parseInt(partes[2]);
                List<String> assentosOcupados = partes[3].isEmpty()
                        ? new ArrayList<>()
                        : List.of(partes[3].split(";"));
                File poster = new File(partes[4]);
                Peca peca = new Peca(poster, turno, nome);
                peca.adicionarAssentos(assentosOcupados);
                peca.setIngressosVendidos(ingressosVendidos);
                pecas.add(peca);
            }
        } catch (IOException e) {
            System.out.println("erro");
        }
    }

    public static void atualizarPecas() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pecasFile))) {
            for (Peca peca : pecas) {
                String linha = peca.getNome() + "," +
                        peca.getSessao() + "," +
                        peca.getIngressosVendidos() + "," +
                        String.join(";", peca.getAssentos()) + "," +
                        peca.getPoster().getPath();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarPessoas() {
        try (BufferedReader br = new BufferedReader(new FileReader(pessoasFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split(",", -1);
                String cpf = partes[0];
                boolean ehFidelidade = Boolean.parseBoolean(partes[1]);
                Pessoa pessoa = new Pessoa(cpf, ehFidelidade);

                if (partes.length > 2 && partes[2].startsWith("Ingressos:")) {
                    String ingressosData = partes[2].substring(10);
                    if (!ingressosData.isEmpty()) {
                        String[] ingressosArray = ingressosData.split(";");
                        for (String ingressoStr : ingressosArray) {
                            String[] ingressoPartes = ingressoStr.split("-");

                            String nomePeca = ingressoPartes[0];
                            Sessao sessao = Sessao.valueOf(ingressoPartes[1]);
                            String assento = ingressoPartes[2];
                            double preco = Double.parseDouble(ingressoPartes[3]);

                            char identificador = assento.charAt(0);
                            int segundoNumero = assento.charAt(1) - '0';


                            Peca peca = pecas.stream()
                                    .filter(p -> p.getNome().equals(nomePeca) && p.getSessao() == sessao)
                                    .findFirst()
                                    .orElse(null);
                            if (peca != null) {
                                Ingresso ingresso = new Ingresso(FinalizarCompraController.getAreaPorIdentificador(identificador, segundoNumero), peca, assento, preco);
                                pessoa.adicionarIngresso(ingresso);
                            } else {
                                System.err.println("Peça não encontrada para ingresso: " + ingressoStr);
                            }
                        }
                    }
                }
                pessoas.add(pessoa);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar pessoas: " + e.getMessage(), e);
        }
    }

    public static void adicionarPessoa(Pessoa pessoa) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pessoasFile, true))) {
            StringBuilder addPessoa = new StringBuilder();
            addPessoa.append(pessoa.getCpf()).append(",")
                    .append(pessoa.isEhFidelidade())
                    .append(",Ingressos:");
            List<String> ingressos = new ArrayList<>();
            for (Ingresso ingresso : pessoa.getIngressos()) {
                String ingressoInfo = ingresso.getPeca().getNome() + "-" +
                        ingresso.getPeca().getSessao() + "-" +
                        ingresso.getAssento() + "-" +
                        ingresso.getPreco();
                ingressos.add(ingressoInfo);
            }
            addPessoa.append(String.join(";", ingressos));
            bw.write(addPessoa.toString());
            bw.newLine();
            pessoas.add(pessoa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Pessoa> buscarPessoaPorCpf(String cpf) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getCpf().equals(cpf))
                .collect(Collectors.toList());
    }

    public static void escreverLog() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
            for (String calculo : log) {
                bw.write(calculo);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Peca> getPecas() {
        return new ArrayList<>(pecas);
    }
}
