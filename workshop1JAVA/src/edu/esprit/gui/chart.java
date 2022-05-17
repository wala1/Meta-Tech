/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Logidee
 */
public class chart extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
    }

    private void init(Stage primaryStage) {
        HBox root = new HBox();
        Scene scene = new Scene(root, 450,330);
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Months");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Percentage %");
        LineChart<String, Number> lineChart =new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("Delivered Order");
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.getData().add(new XYChart.Data<String, Number>("January", 20));
        data.getData().add(new XYChart.Data<String, Number>("Febraury ", 30));
        data.getData().add(new XYChart.Data<String, Number>("March", 50));
        data.getData().add(new XYChart.Data<String, Number>("April", 40));
        data.getData().add(new XYChart.Data<String, Number>("May", 90));
        data.getData().add(new XYChart.Data<String, Number>("June", 80));
        data.getData().add(new XYChart.Data<String, Number>("July ", 85));
        
        lineChart.getData().add(data);
        root.getChildren().add(lineChart);
        primaryStage.setTitle("MetaTech");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String [] args){
        launch(args);
    }
    
}

/*
    API:
      - Linechart
      - Piechart
    Metier:
      - codeCoupon Reduction pourcentage
      - Excel import data
      - Recherche 

*/