package hmd.teatroABC.controller;

import hmd.teatroABC.model.entities.Peca;
import hmd.teatroABC.model.entities.Sessao;
import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.model.objects.Estatistica;
import hmd.teatroABC.model.objects.Graficos;
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
    private BarChart<String, Number> lucroPecaSessaoGrafico;//Lucro por peça/sessão



    @FXML
    private Button botaoVoltar;



    public void initialize() {
        //ex de código
        configurarGraficoLucroPecaSessao();
        configurarGraficoOcupacaoSessao();
        configurarGraficoVendaPorArea();
    }

    private void configurarGraficoOcupacaoSessao() {
        Graficos g = new Graficos();

        PieChart.Data sessao1 = new PieChart.Data("Manhã - " + g.getTotalVendasPorSessao(Sessao.MANHA)
                ,g.getTotalVendasPorSessao(Sessao.MANHA));
        PieChart.Data sessao2 = new PieChart.Data("Tarde - " + g.getTotalVendasPorSessao(Sessao.TARDE)
                , g.getTotalVendasPorSessao(Sessao.TARDE));
        PieChart.Data sessao3 = new PieChart.Data("Noite - " + g.getTotalVendasPorSessao(Sessao.NOITE)
                , g.getTotalVendasPorSessao(Sessao.NOITE));

        ocupacaoSessaoGrafico.setData(FXCollections.observableArrayList(sessao1, sessao2, sessao3));
        ocupacaoSessaoGrafico.setLegendVisible(true);
    }

    private void configurarGraficoLucroPecaSessao() {
        Graficos g = new Graficos();
        Peca peca1 = Teatro.getPecas().get(0);
        Peca peca2 = Teatro.getPecas().get(3);
        Peca peca3 = Teatro.getPecas().get(6);
        XYChart.Series<String, Number> seriePeca1 = new XYChart.Series<>();
        seriePeca1.setName(peca1.getNome());
        seriePeca1.getData().add(new XYChart.Data<>("Manhã", g.getLucroPorPecaESessao(peca1.getNome(), Sessao.MANHA)));
        seriePeca1.getData().add(new XYChart.Data<>("Tarde", g.getLucroPorPecaESessao(peca1.getNome(), Sessao.TARDE)));
        seriePeca1.getData().add(new XYChart.Data<>("Noite", g.getLucroPorPecaESessao(peca1.getNome(), Sessao.NOITE)));

        XYChart.Series<String, Number> seriePeca2 = new XYChart.Series<>();
        seriePeca2.setName(peca2.getNome());
        seriePeca2.getData().add(new XYChart.Data<>("Manhã", g.getLucroPorPecaESessao(peca2.getNome(), Sessao.MANHA)));
        seriePeca2.getData().add(new XYChart.Data<>("Tarde", g.getLucroPorPecaESessao(peca2.getNome(), Sessao.TARDE)));
        seriePeca2.getData().add(new XYChart.Data<>("Noite", g.getLucroPorPecaESessao(peca2.getNome(), Sessao.NOITE)));

        XYChart.Series<String, Number> seriePeca3 = new XYChart.Series<>();
        seriePeca3.setName(peca3.getNome());
        seriePeca3.getData().add(new XYChart.Data<>("Manhã", g.getLucroPorPecaESessao(peca3.getNome(), Sessao.MANHA)));
        seriePeca3.getData().add(new XYChart.Data<>("Tarde", g.getLucroPorPecaESessao(peca3.getNome(), Sessao.TARDE)));
        seriePeca3.getData().add(new XYChart.Data<>("Noite", g.getLucroPorPecaESessao(peca3.getNome(), Sessao.NOITE)));

        lucroPecaSessaoGrafico.getData().clear();
        lucroPecaSessaoGrafico.getData().addAll(Arrays.asList(seriePeca1, seriePeca2, seriePeca3)); // ≤ usa Arrays.asList para evitar aviso de tipagem nao checada
    }

    private void configurarGraficoVendaPorArea() {
        Graficos g = new Graficos();

        XYChart.Series<String, Number> area = new XYChart.Series<>();

        PieChart.Data areaA = new PieChart.Data("Plateia A - " + g.getTotalArea("A")
                ,g.getTotalArea("A"));
        PieChart.Data areaB = new PieChart.Data("Plateia B - " + g.getTotalArea("B")
                ,g.getTotalArea("B"));
        PieChart.Data areaF = new PieChart.Data("Frisa - " + g.getTotalArea("F")
                ,g.getTotalArea("F"));
        PieChart.Data areaC = new PieChart.Data("Camarote - " + g.getTotalArea("C")
                ,g.getTotalArea("C"));
        PieChart.Data areaN = new PieChart.Data("Balcão Nobre - " + g.getTotalArea("N")
                ,g.getTotalArea("N"));

       vendasAreaGrafico.setData(FXCollections.observableArrayList(areaA, areaB, areaF, areaC, areaN));
        vendasAreaGrafico.setLegendVisible(true);
    }

    public void botaoVoltarClicado() {
        Stage stage = (Stage) botaoVoltar.getScene().getWindow();
        stage.close();
    }
}
