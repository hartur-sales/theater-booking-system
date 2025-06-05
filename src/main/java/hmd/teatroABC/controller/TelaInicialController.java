package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.util.FXMLLoaderUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    public Button botaoManha, botaoTarde, botaoNoite, imprimirBotao, estatisticasBotao, btnManha1;

    @FXML
    private HBox botoesBox1, botoesBox2, botoesBox3;

    @FXML
    private BorderPane rootBox;

    @FXML
    private ImageView imagem1, imagem2, imagem3;

    public void initialize() {
        botoesBox1.setVisible(false);
        botoesBox2.setVisible(false);
        botoesBox3.setVisible(false);
        imagem1.setImage(Teatro.getPecas().get(0).getPosterImg());
        imagem2.setImage(Teatro.getPecas().get(3).getPosterImg());
        imagem3.setImage(Teatro.getPecas().get(6).getPosterImg());
    }

    public void showBox1(ActionEvent event) {
        botoesBox1.setVisible(true);
        botoesBox2.setVisible(false);
        botoesBox3.setVisible(false);
    }

    public void showBox2(ActionEvent event) {
        botoesBox2.setVisible(true);
        botoesBox1.setVisible(false);
        botoesBox3.setVisible(false);
    }

    public void showBox3(ActionEvent event) {
        botoesBox3.setVisible(true);
        botoesBox1.setVisible(false);
        botoesBox2.setVisible(false);
    }

    public void comprarPeca1(ActionEvent event) throws IOException {
        Sessao sessaoSelecionada = selecionarSessao(event);

        comprarIngressoTrigger("Wicked", sessaoSelecionada);
    }

    public void comprarPeca2(ActionEvent event) throws IOException {
        Sessao sessaoSelecionada = selecionarSessao(event);

        comprarIngressoTrigger("Rei Leao", sessaoSelecionada);
    }

    public void comprarPeca3(ActionEvent event) throws IOException {
        Sessao sessaoSelecionada = selecionarSessao(event);

        comprarIngressoTrigger("Auto da Compadecida", sessaoSelecionada);
    }

    public void comprarIngressoTrigger(String pecaSelecionada, Sessao sessaoSelecionada) throws IOException {
        FXMLLoader compraSceneLoader = FXMLLoaderUtil.loadFXML(TELA_SELECIONAR_ASSENTOS);
        Scene compraScene = new Scene(compraSceneLoader.getRoot());
        TelaIngressoController controller = compraSceneLoader.getController();
        Stage compraStage = (Stage) btnManha1.getScene().getWindow();
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
        Scene estatisticasScene = new Scene(estatisticaLoader.getRoot(), STAGE_WIDTH, STAGE_HEIGHT);
        Stage estatisticasTelaStage = (Stage) estatisticasBotao.getScene().getWindow();
        estatisticasTelaStage.setScene(estatisticasScene);
        estatisticasTelaStage.show();
    }

    private void abrirImprimir(String cpfDigitado) throws IOException {
        FXMLLoader imprimirLoader = FXMLLoaderUtil.loadFXML(TELA_IMPRIMIR_INGRESSO);
        Scene imprimirScene = new Scene(imprimirLoader.getRoot(), STAGE_WIDTH, STAGE_HEIGHT);
        ImprimirIngressoController controllerImprimir = imprimirLoader.getController();
        Stage imprimirStage = (Stage) imprimirBotao.getScene().getWindow();
        imprimirStage.setScene(imprimirScene);
        controllerImprimir.setCpfBuscado(cpfDigitado);
        controllerImprimir.criarIngresso();
        imprimirStage.show();
    }

    private Sessao selecionarSessao(ActionEvent event) {
        Sessao sessaoSelecionada;
        Button botaoClicado = (Button) event.getSource();
        if (botaoClicado.getText().equals("Manhã")) {
            sessaoSelecionada = Sessao.MANHA;
        } else if (botaoClicado.getText().equals("Tarde")) {
            sessaoSelecionada = Sessao.TARDE;
        } else {
            sessaoSelecionada = Sessao.NOITE;
        }
        return sessaoSelecionada;
    }

}