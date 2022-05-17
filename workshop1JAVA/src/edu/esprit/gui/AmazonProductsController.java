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
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class AmazonProductsController implements Initializable {

    @FXML
    private WebView web;
    private WebEngine webEngine ; 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        webEngine = web.getEngine();
        webEngine.load("https://www.amazon.fr/s?k=pc&__mk_fr_FR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=337RHWAP9L1KH&sprefix=pc%2Caps%2C141&ref=nb_sb_noss_1");
    }    

    @FXML
    private void goToProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        web.getScene().setRoot(root);
    }
    
}
