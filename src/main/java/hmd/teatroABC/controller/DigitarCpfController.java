package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Pessoa;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static hmd.teatroABC.util.FXMLLoaderUtil.BUNDLE;

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
        cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
            okBotao.setDisable(newValue.trim().isEmpty());
            if (!newValue.matches("\\d*")) {
                cpfField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (cpfField.getText().length() > 11) {
                cpfField.setText(cpfField.getText().substring(0, 11));
            }
        });
    }

    public void botaoOkClicado() {
        if (!verificarCpf(cpfField.getText())) {
            erroLabel.setVisible(true);
            erroLabel.setText("CPF inválido");
//            erroLabel.setText(BUNDLE.getString("cpf_invalido"));
            return;
        }
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
            return cpfField.getText();
        } else {
            return null;
        }
    }

    private boolean verificarCpf(String cpfString) {
        long cpf = Long.parseLong(cpfString);
        return Pessoa.validarCPF(cpf);
    }
}


