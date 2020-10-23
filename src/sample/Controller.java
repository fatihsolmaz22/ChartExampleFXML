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
        /*paneView.getChildren().clear();
        NumberAxis xAxis = new NumberAxis(0, 5, 1);
        xAxis.setLabel("Term");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Marks");
        LineChart<Number, Number> marksChart = new LineChart<>(xAxis, yAxis);
        marksChart.setTitle("Student Performance");
        series = new XYChart.Series<>();
        series.setName("performance");
        series.getData().add(new XYChart.Data<>(0, 0));
        series.getData().add(new XYChart.Data<>(1, 100));
        series.getData().add(new XYChart.Data<>(2, 400));
        series.getData().add(new XYChart.Data<>(3, 200));

        marksChart.getData().add(series);
        marksChart.setMaxWidth(300);
        marksChart.setMaxHeight(400);

        paneView.getChildren().add(marksChart);

         */

        createChart(paneView, tesla);
    }

    /*
    @FXML
    private void updateData(){
        series.getData().add(new XYChart.Data<>(4, Integer.parseInt(dataToAdd.getText())));
    }

     */

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