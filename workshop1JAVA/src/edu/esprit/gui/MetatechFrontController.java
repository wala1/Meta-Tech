/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.publicity;
import edu.esprit.services.ServiceCoupon;
import edu.esprit.services.ServicePublicity;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mejri
 */
public class MetatechFrontController implements Initializable {

    @FXML
    private HBox productComingSoon;
    @FXML
    private ScrollPane sp;

    @FXML
    private GridPane gp;

    private List<publicity> pub = new ArrayList<>();
    @FXML
    private Label codeCoupon;
    ServiceCoupon sc = new ServiceCoupon();
    @FXML
    private Button btnnews;
    ObservableList<publicity> PublicityList = FXCollections.observableArrayList();
    @FXML
    private TextField keywordTextField;

    private List<publicity> getData() {
        List<publicity> pub = new ArrayList<>();
        ServicePublicity ps = new ServicePublicity();
        pub = ps.getAll();

        return pub;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle ressources) {
        codeCoupon.setText("-20% For Code : " + sc.RandomCode());
        pub.addAll(getData());
        int column = 0;
        int row = 1;
        try {
//            

            for (int i = 0; i < pub.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemPublicity.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println(pub.toString());
                ItemPublicityController itemController = fxmlLoader.getController();
                itemController.setData(pub.get(i));

                if (column == 2) {
                    column = 0;
                    row++;
                }
                gp.add(anchorPane, column++, row);
                //set grid width
                gp.setMinWidth(Region.USE_COMPUTED_SIZE);
                gp.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gp.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gp.setMinHeight(Region.USE_COMPUTED_SIZE);
                gp.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gp.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

//            
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

//    @FXML
//    private void ajouterPublicityy(ActionEvent event) throws IOException {
//        Stage nouveauStage; // stage : view
//        Parent root = FXMLLoader.load(getClass().getResource("interfaceFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
//        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
//        Scene scene = new Scene(root);
//        nouveauStage.setScene(scene);
//    }

    @FXML
    private void news(MouseEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("News.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("interfaceFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void store(MouseEvent event) throws IOException {
                        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
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
        Parent root = FXMLLoader.load(getClass().getResource("../../../metatech/gui/front/PublicationFXML.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
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
    private void showProduct(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("ListProduitFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void OnProfile(MouseEvent event) throws IOException {
             Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    


    

}
