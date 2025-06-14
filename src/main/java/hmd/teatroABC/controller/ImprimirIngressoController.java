package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Ingresso;
import hmd.teatroABC.model.entities.Pessoa;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.util.CpfUtil;
import hmd.teatroABC.util.FXMLLoaderUtil;
import hmd.teatroABC.util.Logging;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static hmd.teatroABC.model.entities.Teatro.*;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 28/11/2024
 * @brief Class ImprimirIngressoController
 */

public class ImprimirIngressoController {
    @FXML private Button voltarBotao;
    @FXML private Label exibindoLabel;
    @FXML
    private FlowPane flowPaneContainer;

    private String cpfBuscado;

    public void initialize() {
    }

    public void criarIngresso() {
        registrarNoLog("Visualizando ingressos para o CPF " + CpfUtil.adicionarMascaraCpf(cpfBuscado));
        List<Pessoa> pessoas = Teatro.buscarPessoaPorCpf(cpfBuscado);
        if (pessoas.isEmpty()) {
            exibindoLabel.setText("Nenhum ingresso encontrado para o CPF " + CpfUtil.adicionarMascaraCpf(cpfBuscado));
            exibindoLabel.setStyle("-fx-text-fill: red");
        } else {
            for (Pessoa pessoa : pessoas) {
                for (Ingresso ingresso : pessoa.getIngressos()) {
                    VBox ingressoContainer = new VBox(8);
                    ingressoContainer.getStyleClass().clear();
                    ingressoContainer.getStyleClass().add("ingresso-card");
                    Label cpfLabel = new Label("CPF do titular: " + CpfUtil.adicionarMascaraCpf(cpfBuscado));
                    Label pecaLabel = new Label("Peca: " + ingresso.getPeca().getNome());
                    Label sessaoLabel = new Label("Sessão: " + ingresso.getPeca().getSessao());
                    Label assentoLabel = new Label("Assento: " + ingresso.getAssento());
                    Label precoLabel = new Label("Preço: R$" + ingresso.getPreco());
                    Button exportarBotao = new Button("Exportar ingresso");
                    exportarBotao.getStyleClass().add("botao-exportar");
                    exportarBotao.setOnAction(_ -> exportarCsv(ingresso));

                    ingressoContainer.getChildren().addAll(cpfLabel, pecaLabel, sessaoLabel, assentoLabel, precoLabel, exportarBotao);
                    flowPaneContainer.getChildren().add(ingressoContainer);
                }
            }
        }
    }

    public void voltarTrigger() throws IOException {
        FXMLLoader telaInicialLoader = FXMLLoaderUtil.loadFXML(TELA_INICIAL);
        Scene telaInicialScene = new Scene(telaInicialLoader.getRoot(), STAGE_WIDTH, STAGE_HEIGHT);
        Stage telaInicialStage = (Stage) voltarBotao.getScene().getWindow();
        telaInicialStage.setScene(telaInicialScene);
        telaInicialStage.show();
    }

    private void exportarCsv(Ingresso ingresso) {
        File ingressoExportado = new File("src/main/resources/out/ingresso.csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ingressoExportado))) {
            bw.write("INGRESSO");
            bw.newLine();
            bw.write("CPF do titular: " + CpfUtil.adicionarMascaraCpf(cpfBuscado));
            bw.newLine();
            bw.write("Peca: " + ingresso.getPeca().getNome());
            bw.newLine();
            bw.write("Sessão: " + ingresso.getPeca().getSessao());
            bw.newLine();
            bw.write("Assento: " + ingresso.getAssento());
            bw.newLine();
            bw.write("Preço: R$" + ingresso.getPreco());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Ingresso exportado com sucesso!");

        Scene cenaAlerta = alert.getDialogPane().getScene();
        cenaAlerta.getRoot().setStyle("-fx-background-color: #181a28;");
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content");
        if (contentLabel != null) {
            contentLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: 500;");
        }

        registrarNoLog("Ingresso exportado");
        alert.showAndWait();
    }

    public void setCpfBuscado(String cpfBuscado) {
        this.cpfBuscado = cpfBuscado;
        exibindoLabel.setText(exibindoLabel.getText() + " " + CpfUtil.adicionarMascaraCpf(cpfBuscado));
    }

    private void registrarNoLog(String mensagem) {
        Logging.registrarEvento(mensagem);
    }
}
