package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.awt.font.NumericShaper;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {

    private Map<String, XYChart.Series<Number, Number>> mapOfChartsData = new HashMap<>();
    private Map<String, AxisPair> mapOfAxis = new HashMap<>();

    private AtomicInteger count = new AtomicInteger(0);

    private LocalDate initialDate = LocalDate.of(2020, Month.MARCH, 31);
    private LocalDate currentDate = initialDate;

    private int padding = 1;
    private long interval = 10;
    private boolean showAll = false;

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
    @FXML private Button showAllButton;
    @FXML private Button showTenDaysButton;
    @FXML private Button show30DaysButton;




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
        NumberAxis xAxis = new NumberAxis(currentDate.toEpochDay()-padding, currentDate.toEpochDay()+1+interval, 1);
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
            xAxis.setUpperBound(nextDate+padding);
            if(showAll){
                xAxis.setLowerBound(initialDate.toEpochDay()-padding);
            }
            else{ xAxis.setLowerBound(nextDate-padding-interval); }
        }
        // this is for updating data
        mapOfChartsData.get(tesla).getData().add(new XYChart.Data<>(nextDate, getRandomNumber())); // Integer.parseInt(dataToAdd.getText()))
        System.out.println(initialDate);
    }

    private long getAndIncrementCurrentDate(){
        LocalDate dateTemp = currentDate;
        currentDate = currentDate.plusDays(1);
        return dateTemp.toEpochDay();
    }

    private long getRandomNumber(){
        Random random = new Random();
        return Math.round(100+200*random.nextDouble());
    }

    @FXML
    public void updateTimeShow(ActionEvent event){
        Button button = (Button) event.getSource();
        NumberAxis xAxis = mapOfAxis.get(tesla).getxAxis();
        if(button.equals(showAllButton)){
            xAxis.setLowerBound(initialDate.toEpochDay()-1);
            showAll = true;
        }
        else if(button.equals(showTenDaysButton)){
            showAll = false;
            interval = 10;
            xAxis.setLowerBound(currentDate.toEpochDay()-padding*2-interval);
        }
        else if(button.equals(show30DaysButton)){
            showAll = false;
            interval = 30;
            xAxis.setLowerBound(currentDate.toEpochDay()-padding*2-interval);
        }
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