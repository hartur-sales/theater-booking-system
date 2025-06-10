package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Area;
import hmd.teatroABC.model.entities.Ingresso;
import hmd.teatroABC.model.entities.Pessoa;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.util.FXMLLoaderUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hmd.teatroABC.model.entities.AreaUtil.getAreaPorIdentificador;
import static hmd.teatroABC.model.entities.Teatro.TELA_COMPRA_FINALIZADA;
import static hmd.teatroABC.model.entities.Teatro.TELA_SELECIONAR_ASSENTOS;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 24/11/2024
 * @brief Class FinalizarCompraController
 */

public class FinalizarCompraController {
    public VBox vboxFidelidade;
    public DatePicker selecionarData;
    public TextField cpfField, nomeField, telefoneField, ruaField, numeroField, complementoField, cepField,
            bairroField, cidadeField;
    public ChoiceBox<String> estadoBox;
    public Button finalizarBotao;
    public ToggleGroup pagamento, querFidelidade;
    public RadioButton fidelidadeSim, fidelidadeNao;
    public Button voltarBotao;

    private ArrayList<String> assentosSelecionados;

    private TelaIngressoController ingressoController = new TelaIngressoController();
    public Label plateiaAAssentosLabel, plateiaBAssentosLabel, frisaAssentosLabel, camaroteAssentosLabel, balcaoAssentosLabel,
            plateiaAValorLabel, plateiaBValorLabel, camaroteValorLabel, frisaValorLabel, balcaoValorLabel, totalLabel, valorTotalLabel,
            plateiaALabel, plateiaBLabel, camaroteLabel, frisaLabel, balcaoLabel, erroLabel;

    private boolean cpfValido = false;

    public void initialize() {
        vboxFidelidade.setVisible(false);
        finalizarBotao.setDisable(true);

        plateiaAAssentosLabel.setText("");
        plateiaBAssentosLabel.setText("");
        frisaAssentosLabel.setText("");
        camaroteAssentosLabel.setText("");
        camaroteAssentosLabel.setText("");
        balcaoAssentosLabel.setText("");
        plateiaAValorLabel.setText("");
        plateiaBValorLabel.setText("");
        camaroteValorLabel.setText("");
        frisaValorLabel.setText("");
        balcaoValorLabel.setText("");

        List<String> estados = List.of("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG",
                "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
        ObservableList<String> list = FXCollections.observableArrayList(estados);
        estadoBox.setItems(list);

        /*
           cepField.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.replaceAll("\\D", "");
            if (newValue.length() > 5) {
                newValue = newValue.substring(0, 5) + "-" + newValue.substring(5, Math.min(newValue.length(), 8));
            }
            cepField.setText(newValue);
        });
        */
        criarListeners();
    }

    public void mostrarFidelidade() {
        vboxFidelidade.setVisible(true);
    }

    public void ocultarFidelidade() {
        if (vboxFidelidade.isVisible()) vboxFidelidade.setVisible(false);
    }

    public void resumoDaCompra(ArrayList<String> assentosSelecionados) {
        this.assentosSelecionados = assentosSelecionados;

        double totalPlateiaA = 0.0;
        double totalPlateiaB = 0.0;
        double totalFrisa = 0.0;
        double totalCamarote = 0.0;
        double totalBalcao = 0.0;
        boolean temPlateiaA = false;
        boolean temPlateiaB = false;
        boolean temFrisa = false;
        boolean temCamarote = false;
        boolean temBalcao = false;

        for (String lugares : this.assentosSelecionados) {
            char localidade = lugares.charAt(0);
            switch (localidade) {
                case 'A':
                    plateiaAAssentosLabel.setText(plateiaAAssentosLabel.getText() + lugares + " ");
                    totalPlateiaA += Area.PLATEIA_A.getPreco();
                    plateiaAValorLabel.setText(String.format("%.2f", totalPlateiaA));
                    temPlateiaA = true;
                    break;
                case 'B':
                    plateiaBAssentosLabel.setText(plateiaBAssentosLabel.getText() + lugares + " ");
                    totalPlateiaB += Area.PLATEIA_B.getPreco();
                    plateiaBValorLabel.setText(String.format("%.2f", totalPlateiaB));
                    temPlateiaB = true;
                    break;
                case 'F':
                    frisaAssentosLabel.setText(frisaAssentosLabel.getText() + lugares + " ");
                    totalFrisa += Area.FRISA1.getPreco();
                    frisaValorLabel.setText(String.format("%.2f", totalFrisa));
                    temFrisa = true;
                    break;
                case 'C':
                    camaroteAssentosLabel.setText(camaroteAssentosLabel.getText() + lugares + " ");
                    totalCamarote += Area.CAMAROTE1.getPreco();
                    camaroteValorLabel.setText(String.format("%.2f", totalCamarote));
                    temCamarote = true;
                    break;
                case 'N':
                    balcaoAssentosLabel.setText(balcaoAssentosLabel.getText() + lugares + " ");
                    totalBalcao += Area.BALCAO_NOBRE.getPreco();
                    balcaoValorLabel.setText(String.format("%.2f", totalBalcao));
                    temBalcao = true;
                    break;
                default:
                    System.out.println("Lugar ou valor não encontrados");
            }
        }

        ocultarLabels(temPlateiaA, plateiaAAssentosLabel, plateiaAValorLabel, plateiaALabel);
        ocultarLabels(temPlateiaB, plateiaBAssentosLabel, plateiaBValorLabel, plateiaBLabel);
        ocultarLabels(temFrisa, frisaAssentosLabel, frisaValorLabel, frisaLabel);
        ocultarLabels(temCamarote, camaroteAssentosLabel, camaroteValorLabel, camaroteLabel);
        ocultarLabels(temBalcao, balcaoAssentosLabel, balcaoValorLabel, balcaoLabel);

        double total = totalPlateiaA + totalPlateiaB + totalFrisa + totalCamarote + totalBalcao;
        valorTotalLabel.setText(String.format("%.2f", total));
    }

    private void ocultarLabels(boolean condicao, Label... labels) {
        if (!condicao) {
            for (Label label : labels) {
                label.setManaged(false);
            }
        }
    }

    public void voltarTrigger() throws IOException {
        FXMLLoader compraSceneLoader = FXMLLoaderUtil.loadFXML(TELA_SELECIONAR_ASSENTOS);
        Scene compraScene = new Scene(compraSceneLoader.getRoot());
        TelaIngressoController controller = compraSceneLoader.getController();
        controller.chamarOutroMetodo();
        controller.configurarAposVoltar(assentosSelecionados);
        controller.configurarAssentos(ingressoController.getPecaSelecionada(), ingressoController.getSessaoSelecionada());
        Stage compraStage = (Stage) voltarBotao.getScene().getWindow();
        compraStage.setScene(compraScene);
        compraStage.show();
    }

    public void finalizarCompraTrigger() throws IOException {
        Pessoa pessoaCriada = cadastroFinal();
        criarIngresso(pessoaCriada);
        Teatro.adicionarPessoa(pessoaCriada);
        Teatro.escreverLog();
        Teatro.atualizarPecas();
        FXMLLoader compraFinalizada = FXMLLoaderUtil.loadFXML(TELA_COMPRA_FINALIZADA);
        Scene compraFinalizadaScene = new Scene(compraFinalizada.getRoot());
        CompraFinalizadaController controller = compraFinalizada.getController();
        controller.setStageAnterior((Stage) finalizarBotao.getScene().getWindow());
        controller.setCpfDigitado(cpfField.getText());
        Stage compraFinalizadaStage = new Stage();
        compraFinalizadaStage.initOwner(finalizarBotao.getScene().getWindow());
        compraFinalizadaStage.initModality(Modality.WINDOW_MODAL);
        compraFinalizadaStage.setScene(compraFinalizadaScene);
        compraFinalizadaStage.showAndWait();
    }

    public Pessoa cadastroFinal() {
        String cpf = cpfField.getText();

        boolean naoFidelidade = fidelidadeNao.isSelected();
        boolean escolheuFidelidade = fidelidadeSim.isSelected();

        if (naoFidelidade && Pessoa.validarCPF(Long.parseLong(cpf))) {
            return new Pessoa(cpf, false);
        } else if (escolheuFidelidade && Pessoa.validarCPF(Long.parseLong(cpf))) {
            return new Pessoa(cpf, true, nomeField.getText(), telefoneField.getText(), ruaField.getText()
                    + " " + numeroField.getText() + " " + complementoField.getText() + " " + cepField.getText() + " " + bairroField.getText()
                    + " " + cidadeField.getText() + " " + estadoBox.getValue(), selecionarData.getValue());
        }
        return null;
    }

    private void criarIngresso(Pessoa pessoa) {
        for (String assento : assentosSelecionados) {
            char identificador = assento.charAt(0);
            int segundoNumero = assento.charAt(1) - '0';
            double preco = TelaIngressoController.getPrecoPorIdentificador(identificador);
            Ingresso ing = new Ingresso(getAreaPorIdentificador(identificador, segundoNumero), ingressoController.encontrarPeca(), assento, preco);
            ingressoController.encontrarPeca().adicionarAssento(assento);
            ingressoController.encontrarPeca().aumentarIngressosVendidos();
            pessoa.adicionarIngresso(ing);
            criarLog(ing, pessoa);
        }
    }

    public void setTelaIngressoController(TelaIngressoController controller) {
        this.ingressoController = controller;
    }

    private void criarListeners() {
        List<TextField> camposTexto = Arrays.asList(nomeField, telefoneField, ruaField, numeroField, complementoField,
                cepField, bairroField, cidadeField);

        // Adicionando o listener a todos os campos de texto
        camposTexto.forEach(campo -> campo.textProperty().addListener((_, _, _) -> desabilitarFinalizarCompra()));

        // Adicionando o listener para os controles de seleção
        List<ToggleGroup> toggleGroups = Arrays.asList(querFidelidade, pagamento); // Adicione outros ToggleGroups se necessário

        // Adicionando o listener aos ToggleGroups
        toggleGroups.forEach(toggleGroup -> toggleGroup.selectedToggleProperty().addListener((_, _, _) -> desabilitarFinalizarCompra()));

        selecionarData.valueProperty().addListener((_, _, _) -> desabilitarFinalizarCompra());

        //https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        //impede que caracteres não numéricos sejam inseridos no campo
        numeroField.textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numeroField.setText(newValue.replaceAll("\\D", ""));
            }
            if (numeroField.getText().length() > 3) {
                numeroField.setText(cpfField.getText().substring(0, 3));
            }
        });

        cpfField.textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cpfField.setText(newValue.replaceAll("\\D", ""));
            }

            if (cpfField.getText().length() > 11) {
                cpfField.setText(cpfField.getText().substring(0, 11));
            }

            if (cpfField.getText().length() == 11) {
                cpfValido = Pessoa.validarCPF(Long.parseLong(cpfField.getText()));
                if (!cpfValido) {
                    erroLabel.setVisible(true);
                    erroLabel.setText("CPF inválido");
                } else {
                    erroLabel.setText("");
                    erroLabel.setVisible(false);
                }
            }
            desabilitarFinalizarCompra();
        });

        cepField.textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cepField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        telefoneField.textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telefoneField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    private void desabilitarFinalizarCompra() {
        boolean cpfPreenchido = cpfField.getText().length() == 11;
        boolean pagamentoSelecionado = pagamento.getSelectedToggle() != null;

        if (cpfPreenchido && fidelidadeNao.isSelected()) {
            finalizarBotao.setDisable(!pagamentoSelecionado || !cpfValido);
        } else if (cpfPreenchido && fidelidadeSim.isSelected()) {
            boolean camposObrigatoriosPreenchidos = !nomeField.getText().isEmpty() &&
                    !telefoneField.getText().isEmpty() &&
                    !ruaField.getText().isEmpty() &&
                    !numeroField.getText().isEmpty() &&
                    !complementoField.getText().isEmpty() &&
                    !cepField.getText().isEmpty() &&
                    !bairroField.getText().isEmpty() &&
                    !cidadeField.getText().isEmpty() &&
                    estadoBox.getValue() != null &&
                    selecionarData.getValue() != null;
            finalizarBotao.setDisable(!camposObrigatoriosPreenchidos || !pagamentoSelecionado || !cpfValido);
        } else {
            finalizarBotao.setDisable(true);
        }
    }

    private void criarLog(Ingresso ing, Pessoa pessoa) {
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
                ", PAGAMENTO: " + getMetodoPagamento();

        Teatro.log.add(logMessage);
    }

    private String getMetodoPagamento() {
        ToggleButton selectedToggle = (ToggleButton) pagamento.getSelectedToggle();
        return selectedToggle.getText();
    }

}
