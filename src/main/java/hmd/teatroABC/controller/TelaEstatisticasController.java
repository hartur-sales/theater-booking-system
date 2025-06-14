package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Area;
import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.model.objects.Estatistica;
import hmd.teatroABC.util.FXMLLoaderUtil;
import hmd.teatroABC.util.Logging;
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
        configurarComboBox();
        peca1CardTitulo.setText("Peça 1 (" + pecas.get(0).getNome() + ")");
        peca2CardTitulo.setText("Peça 2 (" + pecas.get(3).getNome() + ")");
        peca3CardTitulo.setText("Peça 3 (" + pecas.get(6).getNome() + ")");
        totalVendasLabel.setText(totalVendasLabel.getText() + " " + estatisticas.getTotalVendas());
        pecaMaisVendidaLabel.setText(pecaMaisVendidaLabel.getText() + " " + estatisticas.getPecaMaisVendida());
        pecaMenosVendidaLabel.setText(pecaMenosVendidaLabel.getText() + " " + estatisticas.getPecaMenosVendida());
        sessaoMaisOcupadaLabel.setText(sessaoMaisOcupadaLabel.getText() + " " + estatisticas.getSessaoMaisOcupada());
        sessaoMenosOcupadaLabel.setText(sessaoMenosOcupadaLabel.getText() + " " + estatisticas.getSessaoMenosOcupada());
        lucroLabel1.setText(lucroLabel1.getText() + String.format("%.2f", estatisticas.getLucroPeca1()));
        lucroLabel2.setText(lucroLabel2.getText() + String.format("%.2f", estatisticas.getLucroPeca2()));
        lucroLabel3.setText(lucroLabel3.getText() + String.format("%.2f", estatisticas.getLucroPeca3()));
        receitaTotalLabel1.setText(receitaTotalLabel1.getText() + " " + String.format("%.2f", estatisticas.getReceitaPeca1()));
        receitaTotalLabel2.setText(receitaTotalLabel2.getText() + " " + String.format("%.2f", estatisticas.getReceitaPeca2()));
        receitaTotalLabel3.setText(receitaTotalLabel3.getText() + " " + String.format("%.2f", estatisticas.getReceitaPeca3()));
        sessaoMais1Label.setText(sessaoMais1Label.getText() + " " + estatisticas.getSessaoMaisLucrativaPeca1());
        sessaoMenos1Label.setText(sessaoMenos1Label.getText() + " " + estatisticas.getSessaoMenosLucrativaPeca1());
        sessaoMais2Label.setText(sessaoMais2Label.getText() + " " + estatisticas.getSessaoMaisLucrativaPeca2());
        sessaoMenos2Label.setText(sessaoMenos2Label.getText() + " " + estatisticas.getSessaoMenosLucrativaPeca2());
        sessaoMais3Label.setText(sessaoMais3Label.getText() + " " + estatisticas.getSessaoMaisLucrativaPeca3());
        sessaoMenos3Label.setText(sessaoMenos3Label.getText() + " " + estatisticas.getSessaoMenosLucrativaPeca3());
        ingressosPeca1.setText(ingressosPeca1.getText() + " " + estatisticas.getVendasPeca1());
        ingressosPeca2.setText(ingressosPeca2.getText() + " " + estatisticas.getVendasPeca2());
        ingressosPeca3.setText(ingressosPeca3.getText() + " " + estatisticas.getVendasPeca3());
        ticketMedioLabel.setText(ticketMedioLabel.getText() + " " + String.format("%.2f", estatisticas.getTicketMedio()));
        double[] receitaMediaAreas = estatisticas.getReceitasPorArea();
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
        registrarNoLog("Estatísticas visualizadas");
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
            case PLATEIA_A -> {
                receitaMediaPlateiaA.setVisible(true);
                receitaMediaPlateiaA.setManaged(true);
                totalPlatALabel.setVisible(true);
                totalPlatALabel.setManaged(true);
            }
            case PLATEIA_B -> {
                receitaMediaPlateiaB.setVisible(true);
                receitaMediaPlateiaB.setManaged(true);
                totalPlatBLabel.setVisible(true);
                totalPlatBLabel.setManaged(true);
            }
            case FRISA1 -> {
                receitaMediaFrisa.setVisible(true);
                receitaMediaFrisa.setManaged(true);
                totalFrisaLabel.setVisible(true);
                totalFrisaLabel.setManaged(true);
            }
            case CAMAROTE1 -> {
                receitaMediaCamarote.setVisible(true);
                receitaMediaCamarote.setManaged(true);
                totalCamaroteLabel.setVisible(true);
                totalCamaroteLabel.setManaged(true);
            }
            case BALCAO_NOBRE -> {
                receitaMediaBalcao.setVisible(true);
                receitaMediaBalcao.setManaged(true);
                totalBalcaoLabel.setVisible(true);
                totalBalcaoLabel.setManaged(true);
            }
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
            case MANHA -> {
                totalManhaLabel.setVisible(true);
                totalManhaLabel.setManaged(true);
                receitaTotalManhaLabel.setVisible(true);
                receitaTotalManhaLabel.setManaged(true);
                lucroManhaLabel.setVisible(true);
                lucroManhaLabel.setManaged(true);
            }
            case TARDE -> {
                totalTardeLabel.setVisible(true);
                totalTardeLabel.setManaged(true);
                receitaTotalTardeLabel.setVisible(true);
                receitaTotalTardeLabel.setManaged(true);
                lucroTardeLabel.setVisible(true);
                lucroTardeLabel.setManaged(true);
            }
            case NOITE -> {
                totalNoiteLabel.setVisible(true);
                totalNoiteLabel.setManaged(true);
                receitaTotalNoiteLabel.setVisible(true);
                receitaTotalNoiteLabel.setManaged(true);
                lucroNoiteLabel.setVisible(true);
                lucroNoiteLabel.setManaged(true);
            }
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
            bw.write("Estatística,Valor");
            bw.newLine();
            bw.write("Total de Vendas," + estatisticas.getTotalVendas());
            bw.newLine();
            bw.write("Peça com mais ingressos vendidos," + estatisticas.getPecaMaisVendida());
            bw.newLine();
            bw.write("Peça com menos ingressos vendidos," + estatisticas.getPecaMenosVendida());
            bw.newLine();
            bw.write("Sessão com maior ocupação," + estatisticas.getSessaoMaisOcupada());
            bw.newLine();
            bw.write("Sessão com menor ocupação," + estatisticas.getSessaoMenosOcupada());
            bw.newLine();
            bw.write("Ticket Médio," + String.format("%.2f", estatisticas.getTicketMedio()));
            bw.newLine();
            bw.write("Ingressos vendidos para a peça 1 (" + pecas.get(0).getNome() + ")," + estatisticas.getVendasPeca1());
            bw.newLine();
            bw.write("Lucro da peça 1 (" + pecas.get(0).getNome() +")," + String.format("%.2f",estatisticas.getLucroPeca1()));
            bw.newLine();
            bw.write("Receita total da peça 1 (" + pecas.get(0).getNome() + ")," + String.format("%.2f", estatisticas.getReceitaPeca1()));
            bw.newLine();
            bw.write("Sessão mais lucrativa da peça 1 (" + pecas.get(0).getNome() + ")," + estatisticas.getSessaoMaisLucrativaPeca1());
            bw.newLine();
            bw.write("Sessão menos lucrativa da peça 1 (" + pecas.get(0).getNome() + ")," + estatisticas.getSessaoMenosLucrativaPeca1());
            bw.newLine();
            bw.write("Ingressos vendidos para a peça 2 (" + pecas.get(3).getNome() + ")," + estatisticas.getVendasPeca2());
            bw.newLine();
            bw.write("Lucro da peça 2 (" + pecas.get(3).getNome() + ")," + String.format("%.2f", estatisticas.getLucroPeca2()));
            bw.newLine();
            bw.write("Receita total da peça 2 (" + pecas.get(3).getNome() + ")," + String.format("%.2f", estatisticas.getReceitaPeca2()));
            bw.newLine();
            bw.write("Sessão mais lucrativa da peça 2 (" + pecas.get(3).getNome() + ")," + estatisticas.getSessaoMaisLucrativaPeca2());
            bw.newLine();
            bw.write("Sessão menos lucrativa da peça 2 (" + pecas.get(3).getNome() + ")," + estatisticas.getSessaoMenosLucrativaPeca2());
            bw.newLine();
            bw.write("Ingressos vendidos para a peça 3 (" + pecas.get(6).getNome() + ")," + estatisticas.getVendasPeca3());
            bw.newLine();
            bw.write("Lucro da peça 3 (" + pecas.get(6).getNome() + ")," + String.format("%.2f", estatisticas.getLucroPeca3()));
            bw.newLine();
            bw.write("Receita total da peça 3 (" + pecas.get(6).getNome() + ")," + String.format("%.2f", estatisticas.getReceitaPeca3()));
            bw.newLine();
            bw.write("Sessão mais lucrativa da peça 3 (" + pecas.get(6).getNome() + ")," + estatisticas.getSessaoMaisLucrativaPeca3());
            bw.newLine();
            bw.write("Sessão menos lucrativa da peça 3 (" + pecas.get(6).getNome() + ")," + estatisticas.getSessaoMenosLucrativaPeca3());
            bw.newLine();
            bw.write("Ingressos vendidos na plateia A," + estatisticas.getVendasPlatA());
            bw.newLine();
            bw.write("Receita da plateia A," + String.format("%.2f", estatisticas.getReceitasPorArea()[0]));
            bw.newLine();
            bw.write("Ingressos vendidos na plateia B," + estatisticas.getVendasPlatB());
            bw.newLine();
            bw.write("Receita da plateia B," + String.format("%.2f", estatisticas.getReceitasPorArea()[1]));
            bw.newLine();
            bw.write("Ingressos vendidos nas frisas," + estatisticas.getVendasFrisa());
            bw.newLine();
            bw.write("Receita da frisa," + String.format("%.2f", estatisticas.getReceitasPorArea()[3]));
            bw.newLine();
            bw.write("Ingressos vendidos no camarote," + estatisticas.getVendasCamarote());
            bw.newLine();
            bw.write("Receita do camarote," + String.format("%.2f", estatisticas.getReceitasPorArea()[2]));
            bw.newLine();
            bw.write("Ingressos vendidos no balcão nobre," + estatisticas.getVendasBalcao());
            bw.newLine();
            bw.write("Receita do balcão nobre," + String.format("%.2f", estatisticas.getReceitasPorArea()[4]));
            bw.newLine();
            bw.write("Ingressos vendidos de manhã," + estatisticas.getVendasManha());
            bw.newLine();
            bw.write("Receita total de manhã," + String.format("%.2f", estatisticas.getReceitaManha()));
            bw.newLine();
            bw.write("Lucro de manhã," + String.format("%.2f", estatisticas.getLucroManha()));
            bw.newLine();
            bw.write("Ingressos vendidos à tarde," + estatisticas.getVendasTarde());
            bw.newLine();
            bw.write("Receita total à tarde," + String.format("%.2f", estatisticas.getReceitaTarde()));
            bw.newLine();
            bw.write("Lucro à tarde," + String.format("%.2f", estatisticas.getLucroTarde()));
            bw.newLine();
            bw.write("Ingressos vendidos à noite," + estatisticas.getVendasNoite());
            bw.newLine();
            bw.write("Receita total à noite," + String.format("%.2f", estatisticas.getReceitaNoite()));
            bw.newLine();
            bw.write("Lucro à noite," + String.format("%.2f", estatisticas.getLucroNoite()));
            bw.newLine();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Estatisticas exportadas com sucesso!");

            Scene cenaAlerta = alert.getDialogPane().getScene();
            cenaAlerta.getRoot().setStyle("-fx-background-color: #262424;");
            Label contentLabel = (Label) alert.getDialogPane().lookup(".content");
            if (contentLabel != null) {
                contentLabel.setStyle("-fx-text-fill: white;");
            }
            registrarNoLog("Estatísticas exportadas");
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

    private void registrarNoLog(String mensagem) {
        Logging.registrarEvento(mensagem);
    }
}
