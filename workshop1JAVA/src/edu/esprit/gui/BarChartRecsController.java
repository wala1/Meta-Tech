/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.services.ServicePublicity;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mejri
 */
public class BarChartRecsController implements Initializable {

    @FXML
    private BarChart<String,Number> RecBarChart;
    @FXML
    private NumberAxis countAxis;
    @FXML
    private CategoryAxis stateAxis;
    @FXML
    private Button loadChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadchart(MouseEvent event) throws SQLException {
        ServicePublicity RS = new ServicePublicity();



        // Series 1 
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        dataSeries1.setName("Above 100");

        dataSeries1.getData().add(new XYChart.Data<String, Number>("", RS.AboveOneHundred()));

        // Series 2 
        XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<String, Number>();
        dataSeries2.setName("Under 100");

        dataSeries2.getData().add(new XYChart.Data<String, Number>("", RS.UnderOneHundred()));

        // Add Series to BarChart.
        RecBarChart.getData().add(dataSeries1);
        RecBarChart.getData().add(dataSeries2);

        RecBarChart.setTitle("Publicity States");
        
    }

@FXML
    private void back(MouseEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("interfaceFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);

    }
    
}
