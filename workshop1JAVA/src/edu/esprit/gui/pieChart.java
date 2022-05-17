/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Command;
import edu.esprit.entities.Produit;
import edu.esprit.services.ServiceCommand;
import edu.esprit.services.ServicePanier;
import java.text.NumberFormat;
import java.util.List;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

/**
 *
 * @author Logidee
 */
public class pieChart extends Application {

    private static NumberFormat percentage = NumberFormat.getPercentInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Order Class Analysis");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        
        ObservableList<PieChart.Data> pieChartData=FXCollections.observableArrayList(
                new PieChart.Data("Pc Gamer",0.18),
                new PieChart.Data("Casque Gaming MSI",0.09),
                new PieChart.Data("Chair MSI",0.12),
                new PieChart.Data("Sours MSI",0.02),
                new PieChart.Data("Smart Watch",0.23)
            );
        final PieChart chart = new PieChart(pieChartData);
        
        chart.setTitle("MetaTech Percentage of Best Sales Product");
        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(percentage.format((data.getPieValue())), "-", data.getName())));
        ((Group) scene.getRoot()).getChildren().add(chart);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public static void main(String[] args){
        launch(args);
    }

}
