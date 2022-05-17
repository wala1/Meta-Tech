/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.publicity;
import edu.esprit.services.ServicePublicity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import edu.esprit.tests.FXMain;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author mejri
 */
public class ItemPublicityController implements Initializable {

    @FXML
    private Label nameLabel;


    @FXML
    private ImageView img;

    private publicity pub;
    ServicePublicity p = new ServicePublicity();
    @FXML
    private HBox pricelb;

    public void setData(publicity pub) {
        this.pub = pub;
        nameLabel.setText(pub.getNomPub());
         ImageView image = new ImageView(new Image(pub.getImageName()));
         img.setImage(image.getImage());   
     //   priceLable.setText(FXMain.CURRENCY + pub.getPrixPub());

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
