/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.services.ServicePanier;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Logidee
 */
public class AfficherPanierController implements Initializable {


    @FXML
    private ListView item;

    @FXML
    private ListView name;

    @FXML
    private ListView price;

    @FXML
    private ListView quantite;

    @FXML
    private ListView subTotal;
    @FXML
    private Label labelTwo;

    @FXML
    void onClear(ActionEvent event) {
        ServicePanier sc = new ServicePanier();
        sc.clearPanier(104);
    }

    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    void onPreceed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormCommandFront.fxml"));
        Parent root = loader.load();
        FormCommandController apc = loader.getController();
        item.getScene().setRoot(root);
    }

    @FXML
    void onUpdate(ActionEvent event) {

    }

    ServicePanier sp = new ServicePanier();
    ObservableList<Panier> PanierList = FXCollections.observableArrayList();

    @FXML
    private Label labelOne;

    @FXML
    private Label labelThree;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<Panier> list = new ArrayList<>();
        list = sp.getPanier(104);
        double x = 0;
        for (Panier p : list) {
            String s = p.getProduit().getImageProd();
            ImageView emp0photo = new ImageView(new Image(s));
            emp0photo.setFitHeight(100);
            emp0photo.setFitWidth(100);
            p.setImg(emp0photo);
            System.out.println(p.getProduit());
            item.getItems().add(emp0photo);
            name.getItems().add(p.getProduit().getNomProd());
            price.getItems().add(p.getProduit().getPrixProd());
            quantite.getItems().add(p.getQuantite());
            subTotal.getItems().add(p.getProduit().getPrixProd()*p.getQuantite());
            x+=p.getProduit().getPrixProd()*p.getQuantite();
        }
        labelOne.setText(x+"");
        labelThree.setText(x+5.00+"");

        
    }

    @FXML
    private void showEvent(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("Events.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    

    @FXML
    private void showBlog(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("PublicationFXML.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void showCart(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("AfficherPanier.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }


    @FXML
    private void showHome(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("MetatechFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("ListProduitFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }
    
    @FXML
    private void showNews(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("News.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }
    
}
