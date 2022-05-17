/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Event;
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
 * @author wala
 */
public class SelectItemsController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private ImageView img;
    @FXML
     private Label Description;
       private Event event1;
    private MyListenerEvent myListener;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label part;
    @FXML
    private Label max;

    

    /**
     * Initializes the controller class.
     * 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
public void setData(Event event1, MyListenerEvent myListener) {
        this.event1 = event1;
        this.myListener = myListener;
        nameLabel.setText(event1.getTitle());
        //        String dateD = date_d_event.getValue().toString();
      //        LocalDate dateDeb = LocalDate.parse(dateD);

      /*  start.setText(event1.getDateDebut());
        end.setText(event1.getDateFin());
        part.setText(String.valueOf(event1.getNbrPart()));
        max.setText(String.valueOf(event1.getNbrPartMax()));*/
       Description.setText(event1.getDescription());
        
        ImageView emp0photo = new ImageView(new Image(event1.getImageEvent())  ) ;  
        img.setImage(emp0photo.getImage());
    }
    
    @FXML
    private void click(MouseEvent event) {
                myListener.onClickListener(event1);

    }
       
}
