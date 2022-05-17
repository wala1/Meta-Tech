/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Avis;
import edu.esprit.services.ServiceAvis;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class EditReviewController  {
    private Avis a ;
    @FXML
    private TextArea descReview;
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView star4;
    @FXML
    private ImageView star5;
    private Stage stage;
    private Scene scene;
    /**
     * Initializes the controller class.
     */
    
    public void setId(Avis avis){
        a = avis ; 
        if (avis.getRatingAvis()==1) { 
            star1.setOpacity(1);
            star2.setOpacity(0.45);
            star3.setOpacity(0.45);
            star4.setOpacity(0.45);
            star5.setOpacity(0.45);
        } else if (avis.getRatingAvis()==2) { 
            star1.setOpacity(1);
            star2.setOpacity(1);
            star3.setOpacity(0.45);
            star4.setOpacity(0.45);
            star5.setOpacity(0.45);
        } else if (avis.getRatingAvis()==3) {  
            star1.setOpacity(1);
            star2.setOpacity(1);
            star3.setOpacity(1);
            star4.setOpacity(0.45);
            star5.setOpacity(0.45);
        } else if (avis.getRatingAvis()==4) {  
            star1.setOpacity(1);
            star2.setOpacity(1);
            star3.setOpacity(1);
            star4.setOpacity(1);
            star5.setOpacity(0.45);
        } else {
            star1.setOpacity(1);
            star2.setOpacity(1);
            star3.setOpacity(1);
            star4.setOpacity(1);
            star5.setOpacity(1);
        }
          
        descReview.setText(avis.getDescAvis());
        
        
    }
    
        

    @FXML
    private void backToDetailsProd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsProduit.fxml"));	
        Parent root = loader.load(); 	

        DetailsProduitController scene2Controller = loader.getController();
        scene2Controller.setId(a.getIdProd()+"");

        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editReview(ActionEvent event) throws IOException {
        ServiceAvis sp = new ServiceAvis();
        a.setDescAvis(descReview.getText());
        sp.modifier(this.a);
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Review Edited !", ButtonType.OK);
        a.showAndWait();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsProduit.fxml"));	
        Parent root = loader.load(); 	

        DetailsProduitController scene2Controller = loader.getController();
        scene2Controller.setId(this.a.getIdProd()+"");

        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clickStar1(MouseEvent event) {
        a.setRatingAvis(1) ; 
        star1.setOpacity(1);
        star2.setOpacity(0.45);
        star3.setOpacity(0.45);
        star4.setOpacity(0.45);
        star5.setOpacity(0.45);
    }

    @FXML
    private void clickStar2(MouseEvent event) {
        a.setRatingAvis(2) ; 
        star1.setOpacity(1);
        star2.setOpacity(1);
        star3.setOpacity(0.45);
        star4.setOpacity(0.45);
        star5.setOpacity(0.45);
    }

    @FXML
    private void clickStar3(MouseEvent event) {
        a.setRatingAvis(3) ; 
        star1.setOpacity(1);
        star2.setOpacity(1);
        star3.setOpacity(1);
        star4.setOpacity(0.45);
        star5.setOpacity(0.45);
    }

    @FXML
    private void clickStar4(MouseEvent event) {
         a.setRatingAvis(4) ; 
        star1.setOpacity(1);
        star2.setOpacity(1);
        star3.setOpacity(1);
        star4.setOpacity(1);
        star5.setOpacity(0.45);
    }

    @FXML
    private void clickStar5(MouseEvent event) {
         a.setRatingAvis(5) ; 
        star1.setOpacity(1);
        star2.setOpacity(1);
        star3.setOpacity(1);
        star4.setOpacity(1);
        star5.setOpacity(1);
    }
    
}
