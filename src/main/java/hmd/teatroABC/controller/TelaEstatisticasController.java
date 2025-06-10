package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Area;
import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.model.objects.Estatistica;
import hmd.teatroABC.util.FXMLLoaderUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static hmd.teatroABC.model.entities.Teatro.*;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 22/11/2024
 * @brief Class TelaEstatisticasController
 */

public class TelaEstatisticasController {
    @FXML
    private Label totalVendasLabel, pecaMaisVendidaLabel, pecaMenosVendidaLabel, sessaoMaisOcupadaLabel, sessaoMenosOcupadaLabel,
            lucroMedioLabel1, lucroMedioLabel2, lucroMedioLabel3, sessaoMais1Label, sessaoMenos1Label, sessaoMais2Label, sessaoMenos2Label, sessaoMais3Label, sessaoMenos3Label,
            receitaTotalLabel1, receitaTotalLabel2, receitaTotalLabel3, receitaMediaPlateiaA, receitaMediaPlateiaB, receitaMediaFrisa, receitaMediaCamarote, receitaMediaBalcao,
            ingressosPeca1, ingressosPeca2, ingressosPeca3;

    @FXML
    private Button voltarBotao, botaoExportar, botaoGrafico;

    @FXML
    private VBox visaoGeralCard, peca1Card, peca2Card, peca3Card, areasCard;

    @FXML
    private ComboBox<String> filtroPecaCombo;

    @FXML
    private ComboBox<Sessao> filtroSessaoCombo;

    @FXML
    private ComboBox<Area> filtroAreaCombo;

    private final Estatistica estatisticas = new Estatistica();

    private final Sessao[] sessoes = Sessao.values();
    private final List<Area> areas = List.of(Area.PLATEIA_A, Area.PLATEIA_B, Area.BALCAO_NOBRE, Area.FRISA1, Area.CAMAROTE1);
    private final List<Peca> pecas = Teatro.getPecas();

    public void initialize() {
        configurarComboBox();
        estatisticas.carregarEstatisticas();
        totalVendasLabel.setText(totalVendasLabel.getText() + " " + estatisticas.calcularTotalVendas());
        pecaMaisVendidaLabel.setText(pecaMaisVendidaLabel.getText() + " " + estatisticas.calcularPecaMaisVendida());
        pecaMenosVendidaLabel.setText(pecaMenosVendidaLabel.getText() + " " + estatisticas.calcularPecaMenosVendida());
        sessaoMaisOcupadaLabel.setText(sessaoMaisOcupadaLabel.getText() + " " + estatisticas.calcularSessaoMaisOcupada());
        sessaoMenosOcupadaLabel.setText(sessaoMenosOcupadaLabel.getText() + " " + estatisticas.calcularSessaoMenosOcupada());
        lucroMedioLabel1.setText(lucroMedioLabel1.getText() + estatisticas.getLucroMedioWicked());
        lucroMedioLabel2.setText(lucroMedioLabel2.getText() + estatisticas.getLucroMedioReiLeao());
        lucroMedioLabel3.setText(lucroMedioLabel3.getText() + estatisticas.getLucroMedioAuto());
        sessaoMais1Label.setText(sessaoMais1Label.getText() + " " + estatisticas.getSessaoMaisLucrativaWicked());
        sessaoMenos1Label.setText(sessaoMenos1Label.getText() + " " + estatisticas.getSessaoMenosLucrativaWicked());
        sessaoMais2Label.setText(sessaoMais2Label.getText() + " " + estatisticas.getSessaoMaisLucrativaReiLeao());
        sessaoMenos2Label.setText(sessaoMenos2Label.getText() + " " + estatisticas.getSessaoMenosLucrativaReiLeao());
        sessaoMais3Label.setText(sessaoMais3Label.getText() + " " + estatisticas.getSessaoMaisLucrativaAuto());
        sessaoMenos3Label.setText(sessaoMenos3Label.getText() + " " + estatisticas.getSessaoMenosLucrativaAuto());
    }

    public void telaInicialTrigger() throws IOException {
        FXMLLoader telaInicialLoader = FXMLLoaderUtil.loadFXML(TELA_INICIAL);
        Scene telaInicialScene = new Scene(telaInicialLoader.getRoot(), STAGE_WIDTH, STAGE_HEIGHT);
        Stage telaInicialStage = (Stage) voltarBotao.getScene().getWindow();
        telaInicialStage.setScene(telaInicialScene);
        telaInicialStage.show();
    }

    @FXML
    private void aplicarFiltro() {
    }

    @FXML
    private void limparFiltro() {
        filtroPecaCombo.getSelectionModel().clearSelection();
        filtroSessaoCombo.getSelectionModel().clearSelection();
        filtroAreaCombo.getSelectionModel().clearSelection();
        filtroPecaCombo.setPromptText("Peça");
        filtroSessaoCombo.setPromptText("Sessão");
        filtroAreaCombo.setPromptText("Área");
    }

    @FXML
    private void abrirGrafico() throws IOException {
        FXMLLoader telaGraficos = FXMLLoaderUtil.loadFXML(TELA_GRAFICOS);
        Scene telaGraficosScene = new Scene(telaGraficos.getRoot());
        Stage graficosStage = new Stage();
        graficosStage.initOwner(botaoGrafico.getScene().getWindow());
        graficosStage.initModality(Modality.WINDOW_MODAL);
        graficosStage.setScene(telaGraficosScene);
        graficosStage.setHeight(STAGE_HEIGHT - 100);
        graficosStage.setWidth(STAGE_WIDTH - 100);
        graficosStage.setTitle("Gráficos de Estatísticas");
        graficosStage.getIcons().add(new Image(Objects.requireNonNull(TelaEstatisticasController.class.getResourceAsStream("/images/icon.png"))));
        graficosStage.showAndWait();
    }

    @FXML
    private void exportarCsv() {
        File estatisticaExportada = new File("src/main/resources/out/estatisticas.csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(estatisticaExportada))) {
//            bw.write(BUNDLE.getString("estatisticas_maiscula"));
            bw.write("Estatística,Valor");
            bw.newLine();
//            bw.write(BUNDLE.getString("total_vendas") + " " + estatisticas.calcularTotalVendas());
            bw.newLine();
//            bw.write(BUNDLE.getString("peca_mais_vendida") + " " + estatisticas.calcularPecaMaisVendida());
            bw.write("Peça Mais Vendida," + estatisticas.calcularPecaMaisVendida());
            bw.newLine();
//            bw.write(BUNDLE.getString("peca_menos_vendida") + " " + estatisticas.calcularPecaMenosVendida());
            bw.write("Peça Menos Vendida," + estatisticas.calcularPecaMenosVendida());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_mais_ocupada") + " " + estatisticas.calcularSessaoMaisOcupada());
            bw.write("Sessão Mais Ocupada," + estatisticas.calcularSessaoMaisOcupada());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_menos_ocupada") + " " + estatisticas.calcularSessaoMenosOcupada());
            bw.write("Sessão Menos Ocupada," + estatisticas.calcularSessaoMenosOcupada());
            bw.newLine();
//            bw.write(BUNDLE.getString("lucro_medio_peca1") + estatisticas.getLucroMedioWicked());
            bw.write("Lucro Médio (Wicked)," + estatisticas.getLucroMedioWicked());
            bw.newLine();
//            bw.write(BUNDLE.getString("lucro_medio_peca2") + estatisticas.getLucroMedioReiLeao());
            bw.write("Lucro Médio (Rei Leão)," + estatisticas.getLucroMedioReiLeao());
            bw.newLine();
//            bw.write(BUNDLE.getString("lucro_medio_peca3") + estatisticas.getLucroMedioAuto());
            bw.write("Lucro Médio (Auto da Compadecida)," + estatisticas.getLucroMedioAuto());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_mais_vendida_peca1") + " " + estatisticas.getSessaoMaisLucrativaWicked());
            bw.write("Sessão Mais Vendida (Wicked)," + estatisticas.getSessaoMaisLucrativaWicked());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_menos_vendida_peca1") + " " + estatisticas.getSessaoMenosLucrativaWicked());
            bw.write("Sessão Menos Vendida (Wicked)," + estatisticas.getSessaoMenosLucrativaWicked());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_mais_vendida_peca2") + " " + estatisticas.getSessaoMaisLucrativaReiLeao());
            bw.write("Sessão Mais Vendida (Rei Leão)," + estatisticas.getSessaoMaisLucrativaReiLeao());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_menos_vendida_peca2") + " " + estatisticas.getSessaoMenosLucrativaReiLeao());
            bw.write("Sessão Menos Vendida (Rei Leão)," + estatisticas.getSessaoMenosLucrativaReiLeao());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_mais_vendida_peca3") + " "  + estatisticas.getSessaoMaisLucrativaAuto());
            bw.write("Sessão Mais Vendida (Auto da Compadecida)," + estatisticas.getSessaoMaisLucrativaAuto());
            bw.newLine();
//            bw.write(BUNDLE.getString("sessao_menos_vendida_peca3") + " "  + estatisticas.getSessaoMenosLucrativaAuto());
            bw.write("Sessão Menos Vendida (Auto da Compadecida)," + estatisticas.getSessaoMenosLucrativaAuto());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle(BUNDLE.getString("sucesso_alerta"));
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
//            alert.setContentText(BUNDLE.getString("sucesso_estatisticas"));
            alert.setContentText("Estatisticas exportadas com sucesso!");

            Scene cenaAlerta = alert.getDialogPane().getScene();
            cenaAlerta.getRoot().setStyle("-fx-background-color: #262424;");
            Label contentLabel = (Label) alert.getDialogPane().lookup(".content");
            if (contentLabel != null) {
                contentLabel.setStyle("-fx-text-fill: white;");
            }

            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configurarComboBox() {
        filtroSessaoCombo.getItems().addAll(sessoes);
        filtroAreaCombo.getItems().addAll(areas);
        filtroPecaCombo.getItems().add(pecas.get(0).getNome());
        filtroPecaCombo.getItems().add(pecas.get(3).getNome());
        filtroPecaCombo.getItems().add(pecas.get(6).getNome());

        //https://stackoverflow.com/questions/19242747/javafx-editable-combobox-showing-tostring-on-item-selection
        filtroSessaoCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Sessao sessao) {
                return sessao != null ? sessao.getNome() : "";
            }

            @Override
            public Sessao fromString(String n) {
                for (Sessao s : Sessao.values()) {
                    if (s.getNome().equals(n)) {
                        return s;
                    }
                }
                return null;
            }
        });
        filtroAreaCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Area area) {
                return area != null ? area.getNomeLocal() : "";
            }

            @Override
            public Area fromString(String n) {
                for (Area a : Area.values()) {
                    if (a.getNomeLocal().equals(n)) {
                        return a;
                    }
                }
                return null;
            }
        });
    }
}
