/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.entities.SousCategorie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCategorie;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceProduit; 
import edu.esprit.services.ServiceSousCategorie;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class ListProduitFrontController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    
    private List<Produit> produits = new ArrayList<>();
    private MyListener myListener;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label fruitNameLable;
    @FXML
    private Label fruitPriceLabel;
    @FXML
    private ImageView fruitImg;
    
    private Produit chosen;
    private String test;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<String> comboCateg;
    @FXML
    private ComboBox<String> comboSubCateg;
    
    
    String search="" ; 
    String category="" ; 
    String subcategory ="";
    
    ObservableList<String> options = FXCollections.observableArrayList() ; 
    ObservableList<String> options1 = FXCollections.observableArrayList() ; 
    @FXML
    private Label textNotFound;
    @FXML
    private Button bouttonAmazon;
    /**
     * Initializes the controller class.
     */
    
    private void setChosenFruit(Produit produit) {
        fruitNameLable.setText(produit.getNomProd());  
        if (produit.getCategorieProd().getNomCategorie().equals("Crypto")) {
            fruitPriceLabel.setText(produit.getPrixProd() + " ETH"); 
        } else {
            fruitPriceLabel.setText(produit.getPrixProd() + " DT");
        }
        ImageView emp0photo = new ImageView(new Image(produit.getImageProd())  ) ; 
        fruitImg.setImage(emp0photo.getImage());
        chosenFruitCard.setStyle("-fx-background-color:  #fdb819;\n" +
                "    -fx-background-radius: 30;");
        
        //this.chosen.setNomProd(produit.getNomProd()) ;  
        
        test = produit.getId() +"" ; 
        System.out.println(produit.getId());

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceCategorie sp = new ServiceCategorie();  
        List<Categorie> list = new ArrayList<>();
        list = sp.getAll();
        for (Categorie c : list) {
            options.add(c.getNomCategorie());  
        }
        
        ServiceSousCategorie sp1 = new ServiceSousCategorie();  
        List<SousCategorie> list1 = new ArrayList<>();
        list1 = sp1.getAll();
        for (SousCategorie s : list1) {
            options1.add(s.getNomSousCategorie());  
        }
        
        comboCateg.setItems(options);
        comboSubCateg.setItems(options1);
        
        
        load_data("","","") ; 
        
        
    }    
    
    
    
    void load_data(String search, String cat , String subcat) {
        grid.getChildren().clear();
        produits.clear();
        
        if ((search=="")&&(cat=="")&&(subcat=="")) {
            produits.addAll(getData()) ;
        }
        
        if (search != "") {
             
            produits.addAll(getData().stream().filter(e -> e.getNomProd().contains(search)).collect(Collectors.toList())) ;
             
        } else {
        
            if ((cat!="")&&(subcat=="")) {
                System.out.println(cat) ; 
                produits.addAll(getData().stream().filter(e -> e.getCategorieProd().getNomCategorie().matches(cat) ).collect(Collectors.toList()));
            } else if ((subcat!="")&&(cat=="")) {
                System.out.println(cat) ; 
                produits.addAll(getData().stream().filter(e -> e.getSousCategorieProd().getNomSousCategorie().matches(subcat) ).collect(Collectors.toList()));
            } else if ((cat!="")&&(subcat!="")) {
                System.out.println(cat) ; 
                produits.addAll(getData().stream().filter(e -> (e.getCategorieProd().getNomCategorie().matches(cat)) && (e.getSousCategorieProd().getNomSousCategorie().matches(subcat)) ).collect(Collectors.toList()));
            }
        }
        
        if (produits.size() > 0) {
            bouttonAmazon.setDisable(true);
            bouttonAmazon.setOpacity(0);
            textNotFound.setDisable(true);
            textNotFound.setOpacity(0);
            setChosenFruit(produits.get(0));
            myListener = new MyListener() {
                 

                @Override
                public void onClickListener(Produit produit) {
                    setChosenFruit(produit);
                }
            };
        } else {
            bouttonAmazon.setDisable(false);
            bouttonAmazon.setOpacity(1);
            textNotFound.setDisable(false);
            textNotFound.setOpacity(1);
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    private List<Produit> getData() {
         

        ServiceProduit sp = new ServiceProduit();  
        List<Produit> list = new ArrayList<>();
        list = sp.getAll();
        return list ; 
    }

    @FXML
    private void detailsProd(ActionEvent event) throws IOException {
          
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsProduit.fxml"));	
        Parent root = loader.load(); 	

        DetailsProduitController scene2Controller = loader.getController();
        scene2Controller.setId(test);

        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
         
    }

    @FXML
    private void SearchProducts(ActionEvent event) {
        if (tfSearch.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Enter a product to search.", ButtonType.OK);
            a.showAndWait();
        } else {
            load_data(tfSearch.getText(), category, subcategory) ; 
        }
    }

     

    @FXML
    private void sortProductsCateg(ActionEvent event) {
        category = comboCateg.getSelectionModel().getSelectedItem();
        
        load_data(search, comboCateg.getSelectionModel().getSelectedItem(), subcategory) ; 
    }

    @FXML
    private void sortProductsSubCateg(ActionEvent event) {
        subcategory = comboSubCateg.getSelectionModel().getSelectedItem();
        load_data(search, category, comboSubCateg.getSelectionModel().getSelectedItem()) ; 
    }

    @FXML
    private void amazonProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AmazonProducts.fxml")) ; 
        Parent root = loader.load() ; 
        scroll.getScene().setRoot(root);
        
        /*try {
             
            URL url = new URL("http://127.0.0.1:8000/AllAmazon"); 
            

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println(informationString);
                String rate = informationString.toString() ; 
                
                 

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
    private void showNews(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("News.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void addToCart(ActionEvent event) {
        ServicePanier sp = new ServicePanier();
        User u = new User() ;
        u.setId(105) ; 
        Panier p = new Panier(u,chosen,2);
        sp.ajouter(p)  ;
    }
    
    
    
    
    
}
