package hmd.teatroABC.model.objects;

import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.util.AreaUtil;

import java.util.*;

public class Graficos {
    private final Map<String, Map<Sessao, Double>> vendasPorPecaESessao = new HashMap<>();
    private final Map<Sessao, Integer> totalPorSessao = new EnumMap<>(Sessao.class);
    private final List<Peca> pecas = Teatro.getPecas();
    private final Estatistica estatistica = new Estatistica();

    public Graficos() {
        carregarDados();
    }

    private void carregarDados() {
        totalPorSessao.put(Sessao.MANHA, estatistica.getVendasManha());
        totalPorSessao.put(Sessao.TARDE, estatistica.getVendasTarde());
        totalPorSessao.put(Sessao.NOITE, estatistica.getVendasNoite());

        for (Peca peca : pecas) {
            String nomePeca = peca.getNome();
            Sessao sessao = peca.getSessao();
            double total = 0;

            for (String assento : peca.getAssentos()) {
                double preco = AreaUtil.getPrecoPorIdentificador(assento.charAt(0));
                total += preco;
            }

            vendasPorPecaESessao
                    .computeIfAbsent(nomePeca, _ -> new EnumMap<>(Sessao.class))
                    .merge(sessao, total, Double::sum);
        }
    }

    public int getTotalVendasPorSessao(Sessao sessao) {
        return totalPorSessao.getOrDefault(sessao, 0);
    }

    public double getLucroPorPecaESessao(String nomePeca, Sessao sessao) {
        return vendasPorPecaESessao
                .getOrDefault(nomePeca, new EnumMap<>(Sessao.class))
                .getOrDefault(sessao, 0.0);
    }

    public int getTotalArea(String area) {
        int qtdA = 0, qtdB = 0, qtdC = 0, qtdF = 0, qtdM = 0;

        for (Peca peca : pecas) {
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
}
