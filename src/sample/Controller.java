package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {

    private XYChart.Series<Number, Number> series;

    private Map<String, XYChart.Series<Number, Number>> mapOfChartsData = new HashMap<>();

    private AtomicInteger count = new AtomicInteger(0);

    // Stocks
    private String tesla = "Tesla";

    @FXML private Pane paneView;
    @FXML private TextField dataToAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData(){

        createChart(paneView, tesla);
    }

    @FXML
    private void updateData(){
        mapOfChartsData.get(tesla).getData().add(new XYChart.Data<>(count.getAndIncrement(), Integer.parseInt(dataToAdd.getText())));
    }

    private void createChart(Pane paneView, String stockName){
        paneView.getChildren().clear();
        // create x, y Axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Stock Value");
        // new LineChart
        LineChart<Number, Number> stockChart = new LineChart<>(xAxis, yAxis);
        stockChart.setTitle(stockName);
        // create Chart Data
        XYChart.Series<Number, Number> chartData = new XYChart.Series<>();
        chartData.setName(stockName);
        // put into Map
        mapOfChartsData.put(stockName, chartData);
        // set up stockChart
        stockChart.getData().add(chartData);
        stockChart.setMaxHeight(400);
        stockChart.setMaxWidth(300);
        // set stockChart to paneView
        paneView.getChildren().add(stockChart);
    }
}