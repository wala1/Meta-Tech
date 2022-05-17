/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;
 
import edu.esprit.entities.Avis;
import edu.esprit.entities.Produit;
import edu.esprit.entities.SousCategorie;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceSousCategorie;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jdk.nashorn.internal.parser.JSONParser;


/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class DetailsProduitController  {

    @FXML
    private ImageView imgProduit;
    @FXML
    private Label priceProduit;
    @FXML
    private Label descProduit;
    @FXML
    private Label promoProduit;
    
    
    private Produit p ; 
    private String id  ; 
    @FXML
    private Label titleProduit;
    @FXML
    private Label conversionRate;
    ObservableList<String> options = FXCollections.observableArrayList() ; 
    @FXML
    private ComboBox<String> comboRate;
    @FXML
    private TextArea tfDescAvis;
    @FXML
    private GridPane grid;
    private List<Avis> reviews = new ArrayList<>();
    private List<Produit> products = new ArrayList<>();
    @FXML
    private ImageView pstar1;
    @FXML
    private ImageView pstar2;
    @FXML
    private ImageView pstar3;
    @FXML
    private ImageView pstar4;
    @FXML
    private ImageView pstar5;
    @FXML
    private ImageView rstar1;
    @FXML
    private ImageView rstar2;
    @FXML
    private ImageView rstar3;
    @FXML
    private ImageView rstar4;
    @FXML
    private ImageView rstar5;
    
    private int rate = 0 ;
    @FXML
    private Label priceConverted;
    @FXML
    private ScrollPane scroll;
    private MyListener myListener;
    @FXML
    private ScrollPane scroll1;
    @FXML
    private GridPane grid1;
    
    private Stage stage;
    private Scene scene;
    
    
    
    public void setId(String t){
        ServiceProduit sp = new ServiceProduit();   
        p  = sp.getById(Integer.parseInt(t));
        titleProduit.setText(p.getNomProd()) ;         
        priceProduit.setText(p.getPrixProd()+"") ;
        promoProduit.setText(p.getPromoProd()+"") ;
        
        if (p.getCategorieProd().getNomCategorie().equals("Crypto")) {
            priceProduit.setText(p.getPrixProd()+" ETH") ;
            promoProduit.setText(p.getPromoProd()+" ETH") ; 
        } else {
            priceProduit.setText(p.getPrixProd()+" DT") ;
            promoProduit.setText(p.getPromoProd()+" DT") ; 

        }
        
        
        descProduit.setText(p.getDescProd()) ;
        
        ImageView emp0photo = new ImageView(new Image(p.getImageProd())  ) ;   
        imgProduit.setImage(emp0photo.getImage()) ;
        
        if (p.getCategorieProd().getNomCategorie().equals("Crypto")) {
            options.add("USD");  
        }  else {
            options.add("EUR");  
            options.add("USD"); 
            options.add("CAD");   
        }
        comboRate.setItems(options);
        
        if (p.getPromoProd()!=0) {
            if (p.getCategorieProd().getNomCategorie().equals("Crypto")) { 
                priceConverted.setText(p.getPromoProd()+" ETH") ; 
            } else {
                priceConverted.setText(p.getPromoProd()+" DT") ;
            } 
        } else {
            if (p.getCategorieProd().getNomCategorie().equals("Crypto")) { 
                priceConverted.setText(p.getPrixProd()+" ETH") ; 
            } else {
                priceConverted.setText(p.getPrixProd()+" DT") ;
            }
        }  
        // ======== stars
        
        
        //priceConverted.setText()
        // ========= reviews 
        load_data() ; 
        load_recoms() ; 
        
        
    }
    
    
    
    
    
    
        

    @FXML
    private void showProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        imgProduit.getScene().setRoot(root);
    }

    @FXML
    private void convertPrice(ActionEvent event) {
         if (!(p.getCategorieProd().getNomCategorie().equals("Crypto"))) {
            try {

               URL url = new URL("http://127.0.0.1:8000/ConvertRate/USD");
               if (comboRate.getSelectionModel().getSelectedItem()=="USD") {
                   url = new URL("http://127.0.0.1:8000/ConvertRate/USD");
               } else if (comboRate.getSelectionModel().getSelectedItem()=="EUR") {
                   url = new URL("http://127.0.0.1:8000/ConvertRate/EUR");
               } else if (comboRate.getSelectionModel().getSelectedItem()=="CAD") {
                   url = new URL("http://127.0.0.1:8000/ConvertRate/CAD");
               }


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

                   Float total  ;
                   if (p.getPromoProd()!=0) {
                       total = (Float.parseFloat(rate))*(p.getPromoProd()) ;
                   } else {
                       total = (Float.parseFloat(rate))*(p.getPrixProd()) ;
                   }

                   if (comboRate.getSelectionModel().getSelectedItem()=="USD") {
                       priceConverted.setText(total+" USD") ;
                   } else if (comboRate.getSelectionModel().getSelectedItem()=="EUR") {
                       priceConverted.setText(total+" EUR") ;
                   } else if (comboRate.getSelectionModel().getSelectedItem()=="CAD") {
                       priceConverted.setText(total+" CAD") ;
                   }


                   //JSON simple library Setup with Maven is used to convert strings to JSON
                   /*JSONParser parse = new JSONParser();
                   JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                   //Get the first JSON object in the JSON array
                   System.out.println(dataObject.get(0));

                   JSONObject countryData = (JSONObject) dataObject.get(0);

                   System.out.println(countryData.get("woeid"));*/

               }
           } catch (Exception e) {
               e.printStackTrace();
           }
            
            
            
         } else { // ========= crypto api 
             
             
             try {

               URL url = new URL("http://127.0.0.1:8000/ConvertCrypto"); 


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
                   String test2 = rate ;
                   int sub2 = 2 ; 
                   int finish2 = test2.length()-4 ; 
                   String test3  = test2.substring(sub2,finish2) ;
                   String arr1 = test3.replace(',','.');
                   
                   System.out.println(arr1) ; 
                   Float total ;
                    
                   if (p.getPromoProd()!=0) {
                       total = (Float.parseFloat(arr1))*(p.getPromoProd()*1000) ;
                   } else {
                       total = (Float.parseFloat(arr1))*(p.getPrixProd()*1000) ;
                   }

                    
                       priceConverted.setText(total+" DT") ; 


                   

               }
           } catch (Exception e) {
               e.printStackTrace();
           }
             
         }
    }

    @FXML
    private void addReview(ActionEvent event) throws MalformedURLException, IOException {
        if (rate == 0 ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Make sure to put a rating.", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceAvis sp = new ServiceAvis();    

            Avis a = new Avis(1, this.p.getId() , 105, rate, tfDescAvis.getText() );
            //Produit p1 = new Produit(1,"r","r", "https://cdn.futura-sciences.com/buildsv6/images/mediumoriginal/6/5/2/652a7adb1b_98148_01-intro-773.jpg", 35 , 25, 22, c , s ,"0");
            sp.ajouter(a); 
             
            Alert b = new Alert(Alert.AlertType.INFORMATION, "Thank you for your review.", ButtonType.OK);
             b.showAndWait(); 
             rate = 0 ; 
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsProduit.fxml"));	
            Parent root = loader.load(); 	

            DetailsProduitController scene2Controller = loader.getController();
            scene2Controller.setId(this.p.getId()+"");

            //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            
           /*
                String ur = "http://127.0.0.1:8000/AddReview?id_prod="+this.p.getId()+"&id_user="+88+"&desc="+p.getDescAvis()+"&rating="+p.getRatingAvis() ;
               URL url = new URL("http://127.0.0.1:8000/AddReview"); 

                
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("POST");
               Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("firstParam", paramValue1)
                        .appendQueryParameter("secondParam", paramValue2)
                        .appendQueryParameter("thirdParam", paramValue3);
                String query = builder.build().getEncodedQuery();
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
                   Alert b = new Alert(Alert.AlertType.INFORMATION, "Thank you for your review.", ButtonType.OK);
                    b.showAndWait(); 
                    rate = 0 ; 
                } */
        }
    }

    @FXML
    private void clickStar1(MouseEvent event)  {
        rate = 1 ; 
        rstar1.setOpacity(1);
        rstar2.setOpacity(0.45);
        rstar3.setOpacity(0.45);
        rstar4.setOpacity(0.45);
        rstar5.setOpacity(0.45);

    }

    @FXML
    private void clickStar2(MouseEvent event) {
        rate = 2 ; 
        rstar1.setOpacity(1);
        rstar2.setOpacity(1);
        rstar3.setOpacity(0.45);
        rstar4.setOpacity(0.45);
        rstar5.setOpacity(0.45);
    }

    @FXML
    private void clickStar3(MouseEvent event) {
        rate = 3 ; 
        rstar1.setOpacity(1);
        rstar2.setOpacity(1);
        rstar3.setOpacity(1);
        rstar4.setOpacity(0.45);
        rstar5.setOpacity(0.45);
    }

    @FXML
    private void clickStar4(MouseEvent event) {
        rate = 4 ; 
        rstar1.setOpacity(1);
        rstar2.setOpacity(1);
        rstar3.setOpacity(1);
        rstar4.setOpacity(1);
        rstar5.setOpacity(0.45);
    }

    @FXML
    private void clickStar5(MouseEvent event) {
        rate = 5 ; 
        rstar1.setOpacity(1);
        rstar2.setOpacity(1);
        rstar3.setOpacity(1);
        rstar4.setOpacity(1);
        rstar5.setOpacity(1);
    }
    
    
    
    
    void load_data() {
        reviews.addAll(getData()) ;
        
        int count = reviews.size() ; 
        int sum = 0 ; 
        
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < reviews.size(); i++) {
                //== stars
                sum = sum + reviews.get(i).getRatingAvis() ; 
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemAvis.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemAvisController itemController = fxmlLoader.getController();
                itemController.setData(reviews.get(i));

                if (column == 1) {
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
            
            int total = 0 ; 
            if (count == 0) {
                total = 5 ; 
            } else {
                 total = (int)(sum/count) ; 
    
            }
            if (total == 1) {  
                pstar2.setVisible(false);
                pstar3.setVisible(false);
                pstar4.setVisible(false);
                pstar5.setVisible(false);

            } else if (total == 2) { 
                
                pstar3.setVisible(false);
                pstar4.setVisible(false);
                pstar5.setVisible(false);

            } else if (total == 3) { 
                 
                pstar4.setVisible(false);
                pstar5.setVisible(false);

            } else if (total == 4) { 
                pstar5.setVisible(false);

            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
    }


    private List<Avis> getData() {
         

        ServiceAvis sp = new ServiceAvis();  
        List<Avis> list = new ArrayList<>();
        list = sp.getByIdProd(p.getId());
        return list ; 
    }
    
    
    
    
    void load_recoms() {
        products.addAll(getDataRecoms()) ;
        
        int count = reviews.size() ; 
        int sum = 0 ; 
        
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < products.size(); i++) {
                //== stars 
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(products.get(i), myListener);

                /*if (column == 1) {
                    column = 0;
                    row++;
                }*/

                grid1.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid1.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid1.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid1.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid1.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid1.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid1.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
            
             
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
    }


    private List<Produit> getDataRecoms() {
         

        ServiceProduit sp = new ServiceProduit();  
        List<Produit> list = new ArrayList<>();
        list = sp.getByCategorie(p.getCategorieProd().getId());
        return list ; 
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
    private void showHome(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("MetatechFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }
    
    
    
    
    
    
}
