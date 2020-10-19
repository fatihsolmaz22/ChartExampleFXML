package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private XYChart.Series<Number, Number> series;

    @FXML private Pane paneView;
    @FXML private TextField dataToAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData(){
        paneView.getChildren().clear();
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


    }

    @FXML
    private void updateData(){
        series.getData().add(new XYChart.Data<>(4, Integer.parseInt(dataToAdd.getText())));
    }
}
