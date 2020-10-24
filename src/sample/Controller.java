package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {

    private Map<String, XYChart.Series<Number, Number>> mapOfChartsData = new HashMap<>();
    private Map<String, AxisPair> mapOfAxis = new HashMap<>();

    private AtomicInteger count = new AtomicInteger(0);

    private LocalDate date = LocalDate.of(2020, Month.MARCH, 31);
    private LocalDate date2 = LocalDate.of(2020, Month.APRIL, 5);

    private StringConverter<Number> stringConverter = new StringConverter<>() {
        @Override
        public String toString(Number number) {
            return LocalDate.ofEpochDay(number.longValue()).toString();
        }

        @Override
        public Number fromString(String s) {
            return 0;
        }
    };

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

    private void createChart(Pane paneView, String stockName){
        paneView.getChildren().clear();
        // create x, y Axis
        NumberAxis xAxis = new NumberAxis(date.toEpochDay()-1, date2.toEpochDay()+5, 1);
        xAxis.setLabel("Date");
        xAxis.setTickLabelFormatter(stringConverter);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Stock Value");
        // set x, y Axis to Map
        mapOfAxis.put(stockName, new AxisPair(xAxis, yAxis));
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
        stockChart.setMaxWidth(600);
        // set stockChart to paneView
        paneView.getChildren().add(stockChart);
    }

    @FXML
    private void updateData(){
        // this part is for updating lower-/upperbound of xAxis
        long nextDate = getAndIncrementCurrentDate();
        NumberAxis xAxis = mapOfAxis.get(tesla).getxAxis();
        if(xAxis.getUpperBound() == nextDate){
            xAxis.setUpperBound(nextDate+1);
            xAxis.setLowerBound(nextDate-10);
        }
        // this is for updating data
        mapOfChartsData.get(tesla).getData().add(new XYChart.Data<>(nextDate, getRandomNumber())); // Integer.parseInt(dataToAdd.getText()))
    }

    private long getAndIncrementCurrentDate(){
        LocalDate dateTemp = date;
        date = date.plusDays(1);
        return dateTemp.toEpochDay();
    }

    private long getRandomNumber(){
        Random random = new Random();
        return Math.round(100+200*random.nextDouble());
    }

    class AxisPair{
        private NumberAxis xAxis, yAxis;

        public AxisPair(NumberAxis xAxis, NumberAxis yAxis){
            this.xAxis = xAxis;
            this.yAxis = yAxis;
        }

        public NumberAxis getxAxis() {
            return xAxis;
        }

        public NumberAxis getyAxis() {
            return yAxis;
        }

        public void setxAxis(NumberAxis xAxis) {
            this.xAxis = xAxis;
        }

        public void setyAxis(NumberAxis yAxis) {
            this.yAxis = yAxis;
        }
    }

}