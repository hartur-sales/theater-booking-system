package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Teatro;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Arrays;

public class TelaGraficoController {
    //TODO talvez usar a classe Estatistica pra obter os dados dos gráficos
    @FXML
    private PieChart ocupacaoSessaoGrafico; //Ocupação por sessão
    @FXML
    private PieChart vendasAreaGrafico; //Comparativo de vendas por área

    @FXML
    private BarChart<String, Number> lucroPecaSessaoGrafico; //Lucro por peça/sessão

    @FXML
    private Button botaoVoltar;

    public void initialize() {
        //ex de código
        configurarGraficoLucroPecaSessao();
        configurarGraficoOcupacaoSessao();
    }

    private void configurarGraficoOcupacaoSessao() {
        PieChart.Data sessao1 = new PieChart.Data("Manhã - 85", 85);
        PieChart.Data sessao2 = new PieChart.Data("Tarde", 65);
        PieChart.Data sessao3 = new PieChart.Data("Noite", 40);

        ocupacaoSessaoGrafico.setData(FXCollections.observableArrayList(sessao1, sessao2, sessao3));
        ocupacaoSessaoGrafico.setLegendVisible(true);
    }

    private void configurarGraficoLucroPecaSessao() {
        Peca peca1 = Teatro.getPecas().get(0);
        Peca peca2 = Teatro.getPecas().get(3);
        Peca peca3 = Teatro.getPecas().get(6);
        XYChart.Series<String, Number> seriePeca1 = new XYChart.Series<>();
        seriePeca1.setName(peca1.getNome());
        seriePeca1.getData().add(new XYChart.Data<>("Manhã", 200));
        seriePeca1.getData().add(new XYChart.Data<>("Tarde", 150));
        seriePeca1.getData().add(new XYChart.Data<>("Noite", 180));

        XYChart.Series<String, Number> seriePeca2 = new XYChart.Series<>();
        seriePeca2.setName(peca2.getNome());
        seriePeca2.getData().add(new XYChart.Data<>("Manhã", 120));
        seriePeca2.getData().add(new XYChart.Data<>("Tarde", 230));
        seriePeca2.getData().add(new XYChart.Data<>("Noite", 140));

        XYChart.Series<String, Number> seriePeca3 = new XYChart.Series<>();
        seriePeca3.setName(peca3.getNome());
        seriePeca3.getData().add(new XYChart.Data<>("Manhã", 170));
        seriePeca3.getData().add(new XYChart.Data<>("Tarde", 90));
        seriePeca3.getData().add(new XYChart.Data<>("Noite", 160));

        lucroPecaSessaoGrafico.getData().clear();
        lucroPecaSessaoGrafico.getData().addAll(Arrays.asList(seriePeca1, seriePeca2, seriePeca3)); // ≤ usa Arrays.asList para evitar aviso de tipagem nao checada
    }

    public void botaoVoltarClicado() {
        Stage stage = (Stage) botaoVoltar.getScene().getWindow();
        stage.close();
    }
}
