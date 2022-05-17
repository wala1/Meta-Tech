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
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class SideBarBackController implements Initializable {

    @FXML
    private ImageView logo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void showDashboard(ActionEvent event) {
    }

    @FXML
    private void showUser(ActionEvent event) {
    }

    @FXML
    private void showPublicity(ActionEvent event) {
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml")) ; 
        Parent root = loader.load() ; 
        logo.getScene().setRoot(root);
    }

    @FXML
    private void goToCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        logo.getScene().setRoot(root);
    }

    @FXML
    private void goToSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        logo.getScene().setRoot(root);
    }

    @FXML
    private void showBlog(ActionEvent event) {
    }

    @FXML
    private void showCart(ActionEvent event) {
    }

    @FXML
    private void goToFront(ActionEvent event) {
    }

    @FXML
    private void logout(ActionEvent event) {
    }
    
    
     
    
}
