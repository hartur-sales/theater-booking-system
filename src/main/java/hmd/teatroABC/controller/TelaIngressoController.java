package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Area;
import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.util.FXMLLoaderUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static hmd.teatroABC.model.entities.Teatro.TELA_FINALIZAR_COMPRA;
import static hmd.teatroABC.model.entities.Teatro.TELA_INICIAL;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 21/11/2024
 * @brief Class TelaCompraController
 */

public class TelaIngressoController {
    @FXML
    private Button voltarBotao, continuarBotao;

    public GridPane plateiaAGrid;
    public GridPane plateiaBGrid;
    public GridPane camarote1Grid;
    public GridPane camarote2Grid;
    public GridPane camarote3Grid;
    public GridPane camarote4Grid;
    public GridPane camarote5Grid;
    public GridPane balcaoGrid;

    public VBox frisa1Box;
    public VBox frisa2Box;
    public VBox frisa3Box;
    public VBox frisa4Box;
    public VBox frisa5Box;
    public VBox frisa6Box;

    private final List<String> plateiaAAssentos = new ArrayList<>();
    private final List<String> plateiaBAssentos = new ArrayList<>();
    private final List<String> camaroteAssentos = new ArrayList<>();
    private final List<String> frisaAssentos = new ArrayList<>();
    private final List<String> balcaoAssentos = new ArrayList<>();
    private final List<ToggleButton> botoesExistentes = new ArrayList<>();
    private ArrayList<String> botoesClicados = new ArrayList<>();

    public Label labelPrecoTotal;

    private String pecaSelecionada;
    private Sessao sessaoSelecionada;
    private double total = 0.0;

    public void initialize() {
        continuarBotao.setDisable(true);
        labelPrecoTotal.setText("R$ 0.0");
    }

    protected void chamarOutroMetodo() {
        registrarAssentos(plateiaAGrid, plateiaAAssentos);
        registrarAssentos(plateiaBGrid, plateiaBAssentos);
        registrarAssentos(camarote1Grid, camaroteAssentos);
        registrarAssentos(camarote2Grid, camaroteAssentos);
        registrarAssentos(camarote3Grid, camaroteAssentos);
        registrarAssentos(camarote4Grid, camaroteAssentos);
        registrarAssentos(camarote5Grid, camaroteAssentos);
        registrarAssentos(balcaoGrid, balcaoAssentos);
        registrarAssentos(frisa1Box, frisaAssentos);
        registrarAssentos(frisa2Box, frisaAssentos);
        registrarAssentos(frisa3Box, frisaAssentos);
        registrarAssentos(frisa4Box, frisaAssentos);
        registrarAssentos(frisa5Box, frisaAssentos);
        registrarAssentos(frisa6Box, frisaAssentos);
    }

    private void registrarAssentos(Parent container, List<String> listaAssentos) {
        AtomicBoolean clicado = new AtomicBoolean(false);
        for (Node node : container.getChildrenUnmodifiable()) {
            if (node instanceof ToggleButton button) {
                listaAssentos.add(button.getId());
                botoesExistentes.add(button);
                button.setText(button.getId());
                button.setOnAction(_ -> {
                    habilitarBotaoConfirmar();
                    clicado.set(button.isSelected());
                    if (clicado.get()) {
                        botoesClicados.add(button.getId());
                    } else {
                        botoesClicados.remove(button.getId());
                    }
                    atualizarTotalLabel(button.getId(), clicado.get());
                });
            }
        }
    }

    public void configurarAssentos(String pecaSelecionada, Sessao sessaoSelecionada) {
        this.pecaSelecionada = pecaSelecionada;
        this.sessaoSelecionada = sessaoSelecionada;

        Optional<Peca> pecaEscolhida = Teatro.getPecas().stream().filter(peca -> peca.getNome().equals(pecaSelecionada) && peca.getSessao().equals(sessaoSelecionada)).findFirst();
        List<String> lugaresIndisponiveis;

        if (pecaEscolhida.isPresent()) {
            Peca peca = pecaEscolhida.get();
            lugaresIndisponiveis = peca.getAssentos();

            for (String lugar : lugaresIndisponiveis) {
                if (!lugar.equals(" ")) {
                    char localidade = lugar.charAt(0);
                    switch (localidade) {
                        case 'A':
                            desativarAssentos(plateiaAGrid, lugar);
                            break;
                        case 'B':
                            desativarAssentos(plateiaBGrid, lugar);
                            break;
                        case 'F':
                            desativarAssentos(frisa1Box, lugar);
                            desativarAssentos(frisa2Box, lugar);
                            desativarAssentos(frisa3Box, lugar);
                            desativarAssentos(frisa4Box, lugar);
                            desativarAssentos(frisa5Box, lugar);
                            desativarAssentos(frisa6Box, lugar);
                            break;
                        case 'C':
                            desativarAssentos(camarote1Grid, lugar);
                            desativarAssentos(camarote2Grid, lugar);
                            desativarAssentos(camarote3Grid, lugar);
                            desativarAssentos(camarote4Grid, lugar);
                            desativarAssentos(camarote5Grid, lugar);
                            break;
                        case 'N':
                            desativarAssentos(balcaoGrid, lugar);
                            break;
                        default:
                            System.out.println("Lugar inexistente");
                    }
                }
            }
        } else {
            System.out.println("Nenhuma peça encontrada para a seleção do usuário.");
        }
    }

    public void configurarAposVoltar(ArrayList<String> assentosSelecionados) {
        if (assentosSelecionados != null) {
            for (String lugar : assentosSelecionados) {
                char localidade = lugar.charAt(0);
                int numero = Integer.parseInt(lugar.substring(1));

                Parent container = null;
                switch (localidade) {
                    case 'A' -> container = plateiaAGrid;
                    case 'B' -> container = plateiaBGrid;
                    case 'F' -> {
                        if (numero >= 1 && numero <= 6) {
                            container = switch (numero) {
                                case 1 -> frisa1Box;
                                case 2 -> frisa2Box;
                                case 3 -> frisa3Box;
                                case 4 -> frisa4Box;
                                case 5 -> frisa5Box;
                                case 6 -> frisa6Box;
                                default -> null;
                            };
                        }
                    }
                    case 'C' -> {
                        if (numero >= 1 && numero <= 5) {
                            container = switch (numero) {
                                case 1 -> camarote1Grid;
                                case 2 -> camarote2Grid;
                                case 3 -> camarote3Grid;
                                case 4 -> camarote4Grid;
                                case 5 -> camarote5Grid;
                                default -> null;
                            };
                        }
                    }
                    case 'N' -> container = balcaoGrid;
                }

                if (container != null) {
                    for (Node node : container.getChildrenUnmodifiable()) {
                        if (node instanceof ToggleButton toggleButton) {
                            if (toggleButton.getId().equals(lugar)) {
                                toggleButton.setSelected(true);
                                atualizarTotalLabel(lugar, true);
                            }
                        }
                    }
                }
            }
            continuarBotao.setDisable(false);
        }
    }

    private static void desativarAssentos(Parent container, String lugar) {
        for (Node node : container.getChildrenUnmodifiable()) {
            if (node instanceof ToggleButton button) {
                if (button.getId().equals(lugar)) {
                    button.setDisable(true);
                    break;
                }
            }
        }
    }

    private void atualizarTotalLabel(String id, boolean clicado) {
        char identificadorPreco = id.charAt(0);
        double preco = getPrecoPorIdentificador(identificadorPreco);

        if (preco != -1) {
            total += clicado ? preco : -preco;
            labelPrecoTotal.setText("R$ " + total);
        } else {
            System.out.println("Lugar inexistente, preço não encontrado");
        }
    }

    public static double getPrecoPorIdentificador(char identificador) {
        return switch (identificador) {
            case 'A' -> Area.PLATEIA_A.getPreco();
            case 'B' -> Area.PLATEIA_B.getPreco();
            case 'F' -> Area.FRISA1.getPreco();
            case 'C' -> Area.CAMAROTE1.getPreco();
            case 'N' -> Area.BALCAO_NOBRE.getPreco();
            default -> -1;
        };
    }

    private void habilitarBotaoConfirmar() {
        for (ToggleButton botao : botoesExistentes) {
            if (botao.isSelected()) {
                continuarBotao.setDisable(false);
                return;
            } else {
                continuarBotao.setDisable(true);
            }
        }
    }

    public void telaInicialTrigger() throws IOException {
        FXMLLoader telaInicialLoader = FXMLLoaderUtil.loadFXML(TELA_INICIAL);
        Scene telaInicialScene = new Scene(telaInicialLoader.getRoot(), 1189, 770);
        Stage telaInicialStage = (Stage) voltarBotao.getScene().getWindow();
        telaInicialStage.setScene(telaInicialScene);
        telaInicialStage.show();
    }

    public void finalizarCompraTrigger() throws IOException {
        FXMLLoader finalizarCompraLoader = FXMLLoaderUtil.loadFXML(TELA_FINALIZAR_COMPRA);
        Scene finalizarCompraScene = new Scene(finalizarCompraLoader.getRoot());
        FinalizarCompraController controllerCompra = finalizarCompraLoader.getController();
        controllerCompra.setTelaIngressoController(this);
        controllerCompra.resumoDaCompra(botoesClicados);
        Stage finalizarCompraStage = (Stage) continuarBotao.getScene().getWindow();
        finalizarCompraStage.setScene(finalizarCompraScene);
        finalizarCompraStage.show();
    }

    public String getPecaSelecionada() {
        return pecaSelecionada;
    }

    public Sessao getSessaoSelecionada() {
        return sessaoSelecionada;
    }

    public Peca encontrarPeca() {
        for (Peca peca : Teatro.getPecas()) {
            if (peca.getNome().equals(pecaSelecionada) && peca.getSessao().equals(sessaoSelecionada)) {
                return peca;
            }
        }
        return null;
    }
}


