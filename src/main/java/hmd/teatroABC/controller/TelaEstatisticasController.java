package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Area;
import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.model.objects.Estatistica;
import hmd.teatroABC.util.FXMLLoaderUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private Label peca1CardTitulo, peca2CardTitulo, peca3CardTitulo, totalVendasLabel, pecaMaisVendidaLabel, pecaMenosVendidaLabel, sessaoMaisOcupadaLabel, sessaoMenosOcupadaLabel,
            lucroLabel1, lucroLabel2, lucroLabel3, sessaoMais1Label, sessaoMenos1Label, sessaoMais2Label, sessaoMenos2Label, sessaoMais3Label, sessaoMenos3Label,
            receitaTotalLabel1, receitaTotalLabel2, receitaTotalLabel3, receitaMediaPlateiaA, receitaMediaPlateiaB, receitaMediaFrisa, receitaMediaCamarote, receitaMediaBalcao,
            ingressosPeca1, ingressosPeca2, ingressosPeca3, ticketMedioLabel, totalManhaLabel, totalTardeLabel, totalNoiteLabel, receitaTotalManhaLabel, receitaTotalTardeLabel,
            receitaTotalNoiteLabel, lucroManhaLabel, lucroTardeLabel, lucroNoiteLabel, totalPlatALabel, totalPlatBLabel, totalFrisaLabel, totalCamaroteLabel, totalBalcaoLabel;

    @FXML
    private Button voltarBotao, botaoExportar, botaoGrafico;

    @FXML
    private VBox visaoGeralCard, peca1Card, peca2Card, peca3Card, areasCard, sessaoCard, sessaoCardBox, areasCardBox;

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
        estatisticas.carregarEstatisticas();
        configurarComboBox();
        peca1CardTitulo.setText("Peça 1 (" + pecas.get(0).getNome() + ")");
        peca2CardTitulo.setText("Peça 2 (" + pecas.get(3).getNome() + ")");
        peca3CardTitulo.setText("Peça 3 (" + pecas.get(6).getNome() + ")");
        totalVendasLabel.setText(totalVendasLabel.getText() + " " + estatisticas.calcularTotalVendas());
        pecaMaisVendidaLabel.setText(pecaMaisVendidaLabel.getText() + " " + estatisticas.calcularPecaMaisVendida());
        pecaMenosVendidaLabel.setText(pecaMenosVendidaLabel.getText() + " " + estatisticas.calcularPecaMenosVendida());
        sessaoMaisOcupadaLabel.setText(sessaoMaisOcupadaLabel.getText() + " " + estatisticas.calcularSessaoMaisOcupada());
        sessaoMenosOcupadaLabel.setText(sessaoMenosOcupadaLabel.getText() + " " + estatisticas.calcularSessaoMenosOcupada());
        lucroLabel1.setText(lucroLabel1.getText() + String.format("%.2f", estatisticas.getLucroWicked()));
        lucroLabel2.setText(lucroLabel2.getText() + String.format("%.2f", estatisticas.getLucroReiLeao()));
        lucroLabel3.setText(lucroLabel3.getText() + String.format("%.2f", estatisticas.getLucroAuto()));
        receitaTotalLabel1.setText(receitaTotalLabel1.getText() + " " + String.format("%.2f", estatisticas.getReceitaTotalPorPeca("Wicked")));
        receitaTotalLabel2.setText(receitaTotalLabel2.getText() + " " + String.format("%.2f", estatisticas.getReceitaTotalPorPeca("Rei Leao")));
        receitaTotalLabel3.setText(receitaTotalLabel3.getText() + " " + String.format("%.2f", estatisticas.getReceitaTotalPorPeca("Auto da Compadecida")));
        sessaoMais1Label.setText(sessaoMais1Label.getText() + " " + estatisticas.getSessaoMaisLucrativaWicked());
        sessaoMenos1Label.setText(sessaoMenos1Label.getText() + " " + estatisticas.getSessaoMenosLucrativaWicked());
        sessaoMais2Label.setText(sessaoMais2Label.getText() + " " + estatisticas.getSessaoMaisLucrativaReiLeao());
        sessaoMenos2Label.setText(sessaoMenos2Label.getText() + " " + estatisticas.getSessaoMenosLucrativaReiLeao());
        sessaoMais3Label.setText(sessaoMais3Label.getText() + " " + estatisticas.getSessaoMaisLucrativaAuto());
        sessaoMenos3Label.setText(sessaoMenos3Label.getText() + " " + estatisticas.getSessaoMenosLucrativaAuto());
        ingressosPeca1.setText(ingressosPeca1.getText() + " " + estatisticas.getVendasWicked());
        ingressosPeca2.setText(ingressosPeca2.getText() + " " + estatisticas.getVendasReiLeao());
        ingressosPeca3.setText(ingressosPeca3.getText() + " " + estatisticas.getVendasAuto());
        ticketMedioLabel.setText(ticketMedioLabel.getText() + " " + String.format("%.2f", estatisticas.getTicketMedioPorCliente()));
        double[] receitaMediaAreas = estatisticas.calcularReceitaMediaPorArea();
        totalPlatALabel.setText(totalPlatALabel.getText() + estatisticas.getVendasPlatA());
        totalPlatBLabel.setText(totalPlatBLabel.getText() + estatisticas.getVendasPlatB());
        totalFrisaLabel.setText(totalFrisaLabel.getText() + estatisticas.getVendasFrisa());
        totalCamaroteLabel.setText(totalCamaroteLabel.getText() + estatisticas.getVendasCamarote());
        totalBalcaoLabel.setText(totalBalcaoLabel.getText() + estatisticas.getVendasBalcao());
        receitaMediaPlateiaA.setText(receitaMediaPlateiaA.getText() + " " + String.format("%.2f", receitaMediaAreas[0]));
        receitaMediaPlateiaB.setText(receitaMediaPlateiaB.getText() + " " + String.format("%.2f", receitaMediaAreas[1]));
        receitaMediaBalcao.setText(receitaMediaBalcao.getText() + " " + String.format("%.2f", receitaMediaAreas[4]));
        receitaMediaFrisa.setText(receitaMediaFrisa.getText() + " " + String.format("%.2f", receitaMediaAreas[3]));
        receitaMediaCamarote.setText(receitaMediaCamarote.getText() + " " + String.format("%.2f", receitaMediaAreas[2]));
        totalManhaLabel.setText(totalManhaLabel.getText() + estatisticas.getVendasManha());
        totalTardeLabel.setText(totalTardeLabel.getText() + estatisticas.getVendasTarde());
        totalNoiteLabel.setText(totalNoiteLabel.getText() + estatisticas.getVendasNoite());
        receitaTotalManhaLabel.setText(receitaTotalManhaLabel.getText() + String.format("%.2f", estatisticas.getReceitaManha()));
        receitaTotalTardeLabel.setText(receitaTotalTardeLabel.getText() + String.format("%.2f", estatisticas.getReceitaTarde()));
        receitaTotalNoiteLabel.setText(receitaTotalNoiteLabel.getText() + String.format("%.2f", estatisticas.getReceitaNoite()));
        lucroManhaLabel.setText(lucroManhaLabel.getText() + String.format("%.2f", estatisticas.getLucroManha()));
        lucroTardeLabel.setText(lucroTardeLabel.getText() + String.format("%.2f", estatisticas.getLucroTarde()));
        lucroNoiteLabel.setText(lucroNoiteLabel.getText() + String.format("%.2f", estatisticas.getLucroNoite()));
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
        mostrarVisaoPadrao();
        String filtroPeca = filtroPecaCombo.getSelectionModel().getSelectedItem();
        Sessao filtroSessao = filtroSessaoCombo.getSelectionModel().getSelectedItem();
        Area filtroArea = filtroAreaCombo.getSelectionModel().getSelectedItem();

        if (filtroPeca == null && filtroSessao == null && filtroArea == null) {
            mostrarVisaoPadrao();
            return;
        }
        if (filtroPeca != null && filtroSessao == null && filtroArea == null) {
            if (filtroPeca.equals(pecas.get(0).getNome())) {
                mostrarCardUnico(peca1Card);
            } else if (filtroPeca.equals(pecas.get(3).getNome())) {
                mostrarCardUnico(peca2Card);
            } else if (filtroPeca.equals(pecas.get(6).getNome())) {
                mostrarCardUnico(peca3Card);
            }
        }
        if (filtroSessao != null && filtroPeca == null && filtroArea == null) {
            mostrarCardUnico(sessaoCard);
            configurarVisibilidadeLabelSessao(filtroSessao);
        }
        if (filtroArea != null && filtroPeca == null && filtroSessao == null) {
            mostrarCardUnico(areasCard);
            configurarVisibilidadeLabelArea(filtroArea);
        }
    }

    private void configurarVisibilidadeLabelArea(Area filtroArea) {
        Node[] componentesParaOcultar = {
                receitaMediaPlateiaA, totalPlatALabel,
                receitaMediaPlateiaB, totalPlatBLabel,
                receitaMediaFrisa, totalFrisaLabel,
                receitaMediaCamarote, totalCamaroteLabel,
                receitaMediaBalcao, totalBalcaoLabel
        };
        for (Node componente : componentesParaOcultar) {
            componente.setVisible(false);
            componente.setManaged(false);
        }
        switch (filtroArea) {
            case PLATEIA_A:
                receitaMediaPlateiaA.setVisible(true);
                receitaMediaPlateiaA.setManaged(true);
                totalPlatALabel.setVisible(true);
                totalPlatALabel.setManaged(true);
                break;
            case PLATEIA_B:
                receitaMediaPlateiaB.setVisible(true);
                receitaMediaPlateiaB.setManaged(true);
                totalPlatBLabel.setVisible(true);
                totalPlatBLabel.setManaged(true);
                break;
            case FRISA1:
                receitaMediaFrisa.setVisible(true);
                receitaMediaFrisa.setManaged(true);
                totalFrisaLabel.setVisible(true);
                totalFrisaLabel.setManaged(true);
                break;
            case CAMAROTE1:
                receitaMediaCamarote.setVisible(true);
                receitaMediaCamarote.setManaged(true);
                totalCamaroteLabel.setVisible(true);
                totalCamaroteLabel.setManaged(true);
                break;
            case BALCAO_NOBRE:
                receitaMediaBalcao.setVisible(true);
                receitaMediaBalcao.setManaged(true);
                totalBalcaoLabel.setVisible(true);
                totalBalcaoLabel.setManaged(true);
                break;
        }
    }

    private void configurarVisibilidadeLabelSessao(Sessao filtroSessao) {
        Node[] componentesParaOcultar = {
                totalManhaLabel, receitaTotalManhaLabel,
                totalTardeLabel, receitaTotalTardeLabel,
                totalNoiteLabel, receitaTotalNoiteLabel,
                lucroManhaLabel, lucroTardeLabel, lucroNoiteLabel
        };
        for (Node componente : componentesParaOcultar) {
            componente.setVisible(false);
            componente.setManaged(false);
        }
        switch (filtroSessao) {
            case MANHA:
                totalManhaLabel.setVisible(true);
                totalManhaLabel.setManaged(true);
                receitaTotalManhaLabel.setVisible(true);
                receitaTotalManhaLabel.setManaged(true);
                lucroManhaLabel.setVisible(true);
                lucroManhaLabel.setManaged(true);
                break;
            case TARDE:
                totalTardeLabel.setVisible(true);
                totalTardeLabel.setManaged(true);
                receitaTotalTardeLabel.setVisible(true);
                receitaTotalTardeLabel.setManaged(true);
                lucroTardeLabel.setVisible(true);
                lucroTardeLabel.setManaged(true);
                break;
            case NOITE:
                totalNoiteLabel.setVisible(true);
                totalNoiteLabel.setManaged(true);
                receitaTotalNoiteLabel.setVisible(true);
                receitaTotalNoiteLabel.setManaged(true);
                lucroNoiteLabel.setVisible(true);
                lucroNoiteLabel.setManaged(true);
                break;
        }
    }

    @FXML
    private void limparFiltro() {
        filtroPecaCombo.getSelectionModel().clearSelection();
        filtroSessaoCombo.getSelectionModel().clearSelection();
        filtroAreaCombo.getSelectionModel().clearSelection();
        //https://bugs.openjdk.org/browse/JDK-8296653
        filtroPecaCombo.setPromptText("Peça");
        filtroSessaoCombo.setPromptText("Sessão");
        filtroAreaCombo.setPromptText("Área");
        mostrarVisaoPadrao();
    }

    private void mostrarCardUnico(VBox card) {
        visaoGeralCard.setVisible(false);
        visaoGeralCard.setManaged(false);
        peca1Card.setVisible(false);
        peca1Card.setManaged(false);
        peca2Card.setVisible(false);
        peca2Card.setManaged(false);
        peca3Card.setVisible(false);
        peca3Card.setManaged(false);
        areasCard.setVisible(false);
        areasCard.setManaged(false);
        sessaoCard.setVisible(false);
        sessaoCard.setManaged(false);

        card.setVisible(true);
        card.setManaged(true);
    }

    private void mostrarVisaoPadrao() {
        sessaoCard.setVisible(true);
        sessaoCard.setManaged(true);
        for (Node children : sessaoCardBox.getChildren()) {
            if (children instanceof Label label) {
                label.setVisible(true);
                label.setManaged(true);
            }
        }
        visaoGeralCard.setVisible(true);
        visaoGeralCard.setManaged(true);
        peca1Card.setVisible(true);
        peca1Card.setManaged(true);
        peca2Card.setVisible(true);
        peca2Card.setManaged(true);
        peca3Card.setVisible(true);
        peca3Card.setManaged(true);
        areasCard.setVisible(true);
        areasCard.setManaged(true);
        for (Node children : areasCardBox.getChildren()) {
            if (children instanceof Label label) {
                label.setVisible(true);
                label.setManaged(true);
            }
        }
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
            bw.write("Lucro Médio (Wicked)," + estatisticas.getLucroWicked());
            bw.newLine();
//            bw.write(BUNDLE.getString("lucro_medio_peca2") + estatisticas.getLucroMedioReiLeao());
            bw.write("Lucro Médio (Rei Leão)," + estatisticas.getLucroReiLeao());
            bw.newLine();
//            bw.write(BUNDLE.getString("lucro_medio_peca3") + estatisticas.getLucroMedioAuto());
            bw.write("Lucro Médio (Auto da Compadecida)," + estatisticas.getLucroAuto());
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
//            bw.write(BUNDLE.getString("sessao_mais_vendida_peca3") +   " "  + estatisticas.getSessaoMaisLucrativaAuto());
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
