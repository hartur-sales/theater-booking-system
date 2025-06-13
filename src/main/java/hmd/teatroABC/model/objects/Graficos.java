package hmd.teatroABC.model.objects;

import hmd.teatroABC.model.entities.Area;
import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graficos {
    private final Map<String, Map<Sessao, Integer>> vendasPorPecaESessao = new HashMap<>();
    private final Map<Sessao, Integer> totalPorSessao = new EnumMap<>(Sessao.class);
    private final List<Peca> pecasEstatisticas = new ArrayList<>();
    private final int valorIngresso = 50;

    public Graficos() {
        carregarDados(Teatro.pecasFile);
    }

    private void carregarDados(File caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",", 6);
                if (partes.length < 4) continue;

                String nomePeca = partes[0];
                Sessao sessao = Sessao.valueOf(partes[1]);
                int vendidos = Integer.parseInt(partes[2]);
                String assentosStr = partes[3];

                List<String> assentos = assentosStr.isEmpty()
                        ? new ArrayList<>()
                        : Arrays.asList(assentosStr.split(";"));

                // Cria Peca fictícia para guardar os assentos
                Peca p = new Peca(null, sessao, nomePeca, "");
                p.setAssentos(assentos);
                pecasEstatisticas.add(p);

                // Atualiza vendas por sessão
                totalPorSessao.merge(sessao, vendidos, Integer::sum);

                // Atualiza vendas por peça e sessão
                vendasPorPecaESessao
                        .computeIfAbsent(nomePeca, k -> new EnumMap<>(Sessao.class))
                        .merge(sessao, vendidos, Integer::sum);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    public int getTotalVendasPorSessao(Sessao sessao) {
        return totalPorSessao.getOrDefault(sessao, 0);
    }

    public int getLucroPorPecaESessao(String nomePeca, Sessao sessao) {
        int vendidos = vendasPorPecaESessao
                .getOrDefault(nomePeca, new EnumMap<>(Sessao.class))
                .getOrDefault(sessao, 0);
        return vendidos * valorIngresso;
    }

    public int getTotalArea(String area) {
        int qtdA = 0, qtdB = 0, qtdC = 0, qtdF = 0, qtdM = 0;

        for (Peca peca : pecasEstatisticas) {
            List<String> assentos = peca.getAssentos();
            if (assentos == null) continue;

            for (String assento : assentos) {
                if (assento == null || assento.isEmpty()) continue;
                char letra = assento.charAt(0);

                switch (letra) {
                    case 'A' -> qtdA++;
                    case 'B' -> qtdB++;
                    case 'C' -> qtdC++;
                    case 'F' -> qtdF++;
                    case 'N' -> qtdM++;
                }
            }
        }

        return switch (area.toUpperCase()) {
            case "A" -> qtdA;
            case "B" -> qtdB;
            case "C" -> qtdC;
            case "F" -> qtdF;
            case "N" -> qtdM;
            default -> 0;
        };
    }

    public Set<String> getNomesDasPecas() {
        return vendasPorPecaESessao.keySet();
    }
}
