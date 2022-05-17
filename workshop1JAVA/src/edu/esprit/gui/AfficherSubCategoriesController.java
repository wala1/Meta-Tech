/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.SousCategorie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCategorie;
import edu.esprit.services.ServiceSousCategorie;
import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class AfficherSubCategoriesController implements Initializable {

    @FXML
    private TableView<SousCategorie> productsTable;
    @FXML
    private TableColumn<SousCategorie, String> nameCol;
    
    ObservableList<SousCategorie> SousCategoriesList = FXCollections.observableArrayList() ;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfId;
    @FXML
    private ImageView logo;
    @FXML
    private TextField searchInput;
    
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceSousCategorie sp = new ServiceSousCategorie();  
        List<SousCategorie> list = new ArrayList<>();
        list = sp.getAll();
        for (SousCategorie c : list) {
             
            SousCategoriesList.add(c) ; 
        }
        
         
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nomSousCategorie")) ;  
        
        
        
        
        productsTable.setItems(SousCategoriesList) ; 
    }    

    private void showProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    private void showCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    private void addProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddSubCategory.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }


    @FXML
    private void editProduct(ActionEvent event) throws IOException {
        if (tfId.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a subcategory to update.", ButtonType.OK);
            a.showAndWait();
        } else  if (tfName.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Invalid Name.", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceSousCategorie sp = new ServiceSousCategorie();
            SousCategorie p = new SousCategorie(Integer.parseInt(tfId.getText()),tfName.getText());
            sp.modifier(p);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Sub Category updated !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
            tfId.setText("") ;
        }
    }


    @FXML
    private void deleteButton(ActionEvent event) throws IOException {
        if (tfId.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a subcategory to delete.", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceSousCategorie sp = new ServiceSousCategorie(); 
            sp.supprimer(Integer.parseInt(tfId.getText()));
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Sub Category deleted !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
            tfId.setText("") ;
        }
    }

    @FXML
    private void addSubCategory(ActionEvent event) throws IOException {
        if (tfName.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Name can't be empty", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceSousCategorie sp = new ServiceSousCategorie();
            SousCategorie p = new SousCategorie(1,tfName.getText());
            sp.ajouter(p);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Sub Category added !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        SousCategorie c = productsTable.getSelectionModel().getSelectedItem() ; 
        tfName.setText(c.getNomSousCategorie()) ;  
        tfId.setText(c.getId()+"") ;
    }

     

    

     
    @FXML
    private void searchMethod(KeyEvent event) {
        SousCategoriesList.clear();
        ServiceSousCategorie sp = new ServiceSousCategorie();  
        List<SousCategorie> list = new ArrayList<>();
        list = sp.getBySearch(searchInput.getText());
        for (SousCategorie c : list) {
             
            SousCategoriesList.add(c) ; 
        }
        
         
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nomSousCategorie")) ;  
        
        
        
        
        productsTable.setItems(SousCategoriesList) ; 
    }
     
    
    @FXML
    private void goToCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void goToSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    

    @FXML
    private void showDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showPublicity(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfaceFront.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showBlog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../metatech/gui/back/PublicationFXML.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showCart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("flk.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Sign Out!");
        alert.setContentText("Are You sure you want to sign out ?");
        alert.setTitle("Log out ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // ServiceUser.disconnectAll();
                User.session = null;

                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("connect.fxml"));

                    fxml = loader.load();

                } catch (IOException ex) {
                    Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxml);
                stage.setScene(scene);
                stage.show();

            } else {
                System.out.println("cancel");
            }

        });
    }
    
    
    @FXML
    private void showEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventAdmin.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }
    
}
