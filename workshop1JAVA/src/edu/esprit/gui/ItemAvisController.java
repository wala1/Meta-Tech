/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Produit;
import edu.esprit.services.ServiceAvis;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class ItemAvisController  {

    @FXML
    private Label nomAvis;
    @FXML
    private Label dateAvis;
    
    private Avis avis ;
    @FXML
    private Label descAvis;
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
    @FXML
    private ImageView deleteAvis;
    @FXML
    private ImageView editAvis;
    
    private Stage stage;
    private Scene scene;
    
    
    public void setData(Avis avis) {
        this.avis = avis ; 
        
        if (avis.getIdUser()!=88) {
            deleteAvis.setDisable(true);             
            deleteAvis.setOpacity(0);
            editAvis.setDisable(true);             
            editAvis.setOpacity(0);
        }
        nomAvis.setText(avis.getIdUser()+"");
         
        descAvis.setText(avis.getDescAvis());
        //dateAvis.setText(avis.getDateAvis()+""); 
        
        if (avis.getRatingAvis()==1) {
            star2.setVisible(false) ;
            star3.setVisible(false) ;
            star4.setVisible(false) ;
            star5.setVisible(false) ;
        } else if (avis.getRatingAvis()==2) { 
            star3.setVisible(false) ;
            star4.setVisible(false) ;
            star5.setVisible(false) ;
        } else if (avis.getRatingAvis()==3) { 
            star4.setVisible(false) ;
            star5.setVisible(false) ;
        } else if (avis.getRatingAvis()==4) {  
            star5.setVisible(false) ;
        }  
        
    }

    @FXML
    private void onClickDeleteAvis(MouseEvent event) throws IOException {
        ServiceAvis sp = new ServiceAvis();
        sp.supprimer(this.avis.getId());
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Review deleted !", ButtonType.OK);
        a.showAndWait();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsProduit.fxml"));	
            Parent root = loader.load(); 	

            DetailsProduitController scene2Controller = loader.getController();
            scene2Controller.setId(this.avis.getIdProd()+"");

            //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void goToEditReview(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditReview.fxml"));	
        Parent root = loader.load(); 	

        EditReviewController scene2Controller = loader.getController();
        scene2Controller.setId(avis);

        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
}
