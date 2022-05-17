/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCategorie;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class AfficherCategorieController implements Initializable {

    private Label lblNom;
    @FXML
    private TableView<Categorie> productsTable;
    @FXML
    private TableColumn<Categorie, String> nameCol;
    
    
    ObservableList<Categorie> CategoriesList = FXCollections.observableArrayList() ; 
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
        ServiceCategorie sp = new ServiceCategorie();  
        List<Categorie> list = new ArrayList<>();
        list = sp.getAll();
        for (Categorie c : list) {
             
            CategoriesList.add(c) ; 
        }
        
         
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nomCategorie")) ;  
        
        
        
        
        productsTable.setItems(CategoriesList) ; 
    }    
    
    public void setNom(String nom) {
        lblNom.setText(nom) ; 
    }


    private void showSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    private void addProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCategory.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    private void showProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void editProduct(ActionEvent event) throws IOException {
        if (tfId.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a category to update.", ButtonType.OK);
            a.showAndWait();
        } else  if (tfName.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Invalid Name.", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceCategorie sp = new ServiceCategorie();
            Categorie p = new Categorie(Integer.parseInt(tfId.getText()),tfName.getText());
            sp.modifier(p);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Category updated !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
            tfId.setText("") ;
        }
    }


    @FXML
    private void deleteButton(ActionEvent event) throws IOException {
        if (tfId.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a category to delete.", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceCategorie sp = new ServiceCategorie(); 
            sp.supprimer(Integer.parseInt(tfId.getText()));
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Category deleted !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
            tfId.setText("") ;
        }
    }

    @FXML
    private void addCategory(ActionEvent event) throws IOException {
        if (tfName.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Invalid Name.", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceCategorie sp = new ServiceCategorie();
            Categorie p = new Categorie(1,tfName.getText());
            sp.ajouter(p);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Category added !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
        }
    }

     

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Categorie c = productsTable.getSelectionModel().getSelectedItem() ; 
        tfName.setText(c.getNomCategorie()) ; 
        tfId.setText(c.getId()+"") ;
    }

     
    
  

    @FXML
    private void searchMethod(KeyEvent event) {
        CategoriesList.clear();
        ServiceCategorie sp = new ServiceCategorie();  
        List<Categorie> list = new ArrayList<>();
        list = sp.getBySearch(searchInput.getText());
        for (Categorie c : list) {
             
            CategoriesList.add(c) ; 
        }
        
         
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nomCategorie")) ;  
        
        
        
        
        productsTable.setItems(CategoriesList) ; 
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

    private void goToFront(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\ihebx\\Desktop\\workshop1JAVAFinal\\workshop1JAVA\\workshop1JAVA\\src\\metatech\\gui\\back\\PublicationFXML.fxml")) ; 
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
    private void showEvent(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventAdmin.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }
     
    
}
