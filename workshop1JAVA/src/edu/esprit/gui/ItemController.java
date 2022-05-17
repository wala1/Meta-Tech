/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class ItemController  {

    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
      
    private Produit produit;
    private MyListener myListener;
    
    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(produit);
    }

    public void setData(Produit produit, MyListener myListener) {
        this.produit = produit;
        this.myListener = myListener;
        nameLabel.setText(produit.getNomProd());
        
        if (produit.getCategorieProd().getNomCategorie().equals("Crypto")) {
            priceLable.setText(produit.getPrixProd() + " ETH"); 
        }  else {
            priceLable.setText(produit.getPrixProd() + " DT");
        }
        
        ImageView emp0photo = new ImageView(new Image(produit.getImageProd())  ) ;  
        img.setImage(emp0photo.getImage());
    }

    
    
}
