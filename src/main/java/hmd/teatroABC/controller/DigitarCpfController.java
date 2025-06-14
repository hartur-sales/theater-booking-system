package hmd.teatroABC.controller;

import hmd.teatroABC.util.CpfUtil;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 22/11/2024
 * @brief Class DigitarCpfController
 */

public class DigitarCpfController {
    public Label erroLabel;

    public TextField cpfField;

    public Button okBotao, cancelarBotao;

    private boolean okClicado = false;

    public void initialize() {
        okBotao.setDisable(true);
        cpfField.textProperty().addListener((_, _, newValue) -> {
            if (newValue == null) return;

            String formatted = CpfUtil.adicionarMascaraTextField(newValue);
            String digits = CpfUtil.removerMascaraCpf(newValue);

            if (!formatted.equals(newValue)) {
               //https://stackoverflow.com/questions/55563254/set-caretposition-to-the-right-in-a-textfield-javafx
                Platform.runLater(
                        () -> {
                            cpfField.setText(formatted);
                            cpfField.positionCaret(formatted.length());
                        }
                );
                return;
            }

            if (digits.length() < 11) {
                okBotao.setDisable(true);
                erroLabel.setVisible(false);
                erroLabel.setText("");
            } else {
                boolean valido = verificarCpf(digits);
                okBotao.setDisable(!valido);
                erroLabel.setVisible(!valido);
                erroLabel.setText(valido ? "" : "CPF inválido");
            }
        });
    }

    public void botaoOkClicado() {
        okClicado = true;
        Stage stage = (Stage) okBotao.getScene().getWindow();
        stage.close();
    }

    public void botaoCancelarClicado() {
        okClicado = false;
        Stage stage = (Stage) cancelarBotao.getScene().getWindow();
        stage.close();
    }

    public String pegarCpf() {
        if (okClicado) {
            return CpfUtil.removerMascaraCpf(cpfField.getText());
        } else {
            return null;
        }
    }

    private boolean verificarCpf(String cpfString) {
        long cpf = Long.parseLong(cpfString);
        return CpfUtil.validarCPF(cpf);
    }
}


