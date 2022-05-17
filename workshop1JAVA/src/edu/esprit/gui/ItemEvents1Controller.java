/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Event;
import edu.esprit.services.ServiceEvent;
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
 * @author ihebx
 */
public class ItemEvents1Controller implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private ImageView img;
    @FXML
    private Label priceLable;
      private Event event;
    ServiceEvent p = new ServiceEvent();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setData(Event event) {
        this.event = event;
        nameLabel.setText(event.getTitle());
        priceLable.setText(event.getDescription());
        
    ImageView image = new ImageView(new Image(event.getImageEvent()));
         img.setImage(image.getImage());   
     //   priceLable.setText(FXMain.CURRENCY + pub.getPrixPub());

    }
    @FXML
    private void click(MouseEvent event) {
    }
    
}
