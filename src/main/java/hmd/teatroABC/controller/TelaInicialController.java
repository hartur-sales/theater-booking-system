package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.util.FXMLLoaderUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static hmd.teatroABC.model.entities.Teatro.*;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 21/11/2024
 * @brief class TelaInicialController
 */

public class TelaInicialController {
    public Button botaoManha, botaoTarde, botaoNoite, imprimirBotao, estatisticasBotao;

    public HBox botoesBox, postersBox;

    public VBox rootBox;

    public ImageView imagem1, imagem2, imagem3;

    public ToggleButton peca1Botao, peca2Botao, peca3Botao;

    public void initialize() {
        botoesBox.setVisible(false);
        imagem1.setImage(Teatro.getPecas().get(0).getPosterImg());
        imagem2.setImage(Teatro.getPecas().get(3).getPosterImg());
        imagem3.setImage(Teatro.getPecas().get(6).getPosterImg());

        DoubleProperty stageWidth = new SimpleDoubleProperty();
        DoubleProperty stageHeight = new SimpleDoubleProperty();

        imagem1.fitWidthProperty().bind(Bindings.multiply(stageWidth, 0.5));
        imagem1.fitHeightProperty().bind(Bindings.multiply(stageHeight, 0.5));
        imagem1.setPreserveRatio(true);

        imagem2.fitWidthProperty().bind(Bindings.multiply(stageWidth, 0.5));
        imagem2.fitHeightProperty().bind(Bindings.multiply(stageHeight, 0.5));
        imagem2.setPreserveRatio(true);


        imagem3.fitWidthProperty().bind(Bindings.multiply(stageWidth, 0.5));
        imagem3.fitHeightProperty().bind(Bindings.multiply(stageHeight, 0.5));
        imagem3.setPreserveRatio(true);

        // Vincular a largura e altura do stage (Scene) à propriedade stageWidth e stageHeight
        stageWidth.bind(rootBox.widthProperty());
        stageHeight.bind(rootBox.heightProperty());
    }

    public void pecaSelecionada() {
        botoesBox.setVisible(true);
    }

    public void comprarIngressoTrigger(ActionEvent event) throws IOException {
        String pecaSelecionada;
        if (peca1Botao.isSelected()) {
            pecaSelecionada = "Wicked";
        } else if (peca2Botao.isSelected()) {
            pecaSelecionada = "Rei Leao";
        } else {
            pecaSelecionada = "Auto da Compadecida";
        }
        Sessao sessaoSelecionada;
        if (event.getSource() == botaoManha) {
            sessaoSelecionada = Sessao.MANHA;
        } else if (event.getSource() == botaoTarde) {
            sessaoSelecionada = Sessao.TARDE;
        } else {
            sessaoSelecionada = Sessao.NOITE;
        }

        FXMLLoader compraSceneLoader = FXMLLoaderUtil.loadFXML(TELA_SELECIONAR_ASSENTOS);
        Scene compraScene = new Scene(compraSceneLoader.getRoot());
        TelaIngressoController controller = compraSceneLoader.getController();
        Stage compraStage = (Stage) peca1Botao.getScene().getWindow();
        compraStage.setScene(compraScene);
        controller.chamarOutroMetodo();
        controller.configurarAssentos(pecaSelecionada, sessaoSelecionada);
        compraStage.show();
    }

    public void imprimirIngressoTrigger() throws IOException {
        FXMLLoader digitarCpf = FXMLLoaderUtil.loadFXML(TELA_DIGITAR_CPF);
        Scene digitarCpfScene = new Scene(digitarCpf.getRoot());
        DigitarCpfController controllerCpf = digitarCpf.getController();
        Stage digitarCpfStage = new Stage();
        digitarCpfStage.initOwner(imprimirBotao.getScene().getWindow());
        digitarCpfStage.initModality(Modality.WINDOW_MODAL);
        digitarCpfStage.setScene(digitarCpfScene);
        digitarCpfStage.showAndWait();

        String cpfDigitado = controllerCpf.pegarCpf();
        if (cpfDigitado != null) {
            abrirImprimir(cpfDigitado);
        }
    }

    public void verEstatisticasTrigger() throws IOException {
        FXMLLoader estatisticaLoader = FXMLLoaderUtil.loadFXML(TELA_ESTATISTICAS);
        Scene estatisticasScene = new Scene(estatisticaLoader.getRoot(), 1189, 770);
        Stage estatisticasTelaStage = (Stage) estatisticasBotao.getScene().getWindow();
        estatisticasTelaStage.setScene(estatisticasScene);
        estatisticasTelaStage.show();
    }

    private void abrirImprimir(String cpfDigitado) throws IOException {
        FXMLLoader imprimirLoader = FXMLLoaderUtil.loadFXML(TELA_IMPRIMIR_INGRESSO);
        Scene imprimirScene = new Scene(imprimirLoader.getRoot(), 1189, 770);
        ImprimirIngressoController controllerImprimir = imprimirLoader.getController();
        Stage imprimirStage = (Stage) imprimirBotao.getScene().getWindow();
        imprimirStage.setScene(imprimirScene);
        controllerImprimir.setCpfBuscado(cpfDigitado);
        controllerImprimir.criarIngresso();
        imprimirStage.show();
    }
}