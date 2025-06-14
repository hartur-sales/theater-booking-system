package hmd.teatroABC.util;

import hmd.teatroABC.model.entities.Ingresso;
import hmd.teatroABC.model.entities.Pessoa;
import hmd.teatroABC.model.entities.Teatro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {
    /**
     * Registra uma entrada no log do sistema referente à compra de um ingresso.
     */
    public static void registrarCompra(Ingresso ing, Pessoa pessoa, String metodoPagamento) {
        String logMessage = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                " -> " +
                pessoa.getCpf() +
                " " +
                pessoa.isEhFidelidade() +
                " - ASSENTO: " +
                ing.getAssento() +
                ", AREA: " +
                ing.getArea().getNomeLocal() +
                ", PECA: " +
                ing.getPeca().getNome() +
                ", PAGAMENTO: " + metodoPagamento;

        Teatro.log.add(logMessage);
        Teatro.escreverLog();
    }

    /**
     * Registra uma entrada no log do sistema quando um evento acontece no sistema.
     */
    public static void registrarEvento(String mensagem) {
        String logMessage = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                " -> " + mensagem;

        Teatro.log.add(logMessage);
        Teatro.escreverLog();
    }
}
