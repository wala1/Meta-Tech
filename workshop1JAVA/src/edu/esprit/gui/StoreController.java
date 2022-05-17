/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mejri
 */
public class StoreController implements Initializable {

    @FXML
    private WebView WView;
    private WebEngine webEngine;
    private StackPane root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        webEngine = WView.getEngine();
        webEngine.load("http://127.0.0.1:8000/storelocator");  
        }    

    @FXML
    private void home(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("MetatechFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }
    
}
