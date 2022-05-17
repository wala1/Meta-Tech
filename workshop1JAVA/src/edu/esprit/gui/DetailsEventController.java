/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Event;
import edu.esprit.services.ServiceEvent;
import edu.esprit.services.ServiceUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ihebx
 */

public class DetailsEventController implements Initializable {
    int id;
            private ServiceEvent ServiceEvent;
    @FXML
    private Button participate;
    private String nom;
    @FXML
    private Label fruitNameLable;
    @FXML
    private ImageView fruitImg;
    @FXML
    private Label startAt;
    @FXML
    private Label start;
    @FXML
    private Label EndAt;
    @FXML
    private Label end;
    @FXML
    private Label Npart;
    @FXML
    private Label part;
    @FXML
    private Label maxN;
    @FXML
    private Label max;
    @FXML
    private Label labelCart;
    @FXML
    private TextField tfCart;
    @FXML
    private Label labelVal;
    @FXML
    private TextField tfVal1;
    @FXML
    private TextField tfVal2;
    @FXML
    private Label labelCVC;
    @FXML
    private TextField tfCvc;
    @FXML
    private Button payButton;


    /**
     * Initializes the controller class.
     */
  

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
   //System.out.println(ServiceEvent.getEvent(id));
      
       System.out.println(id);
        // TODO
    }
      public void setId(int id) {
        this.id=id;
    }
      public void loadData()
      {
           ServiceEvent se= new ServiceEvent();
        System.out.println(se.getEvent(id));

      }
    

    @FXML
    private void OnParticipate(ActionEvent event) {
        System.out.println(id);
        ServiceEvent se= new ServiceEvent();
        System.out.println(se.getEvent(id));
    }

}
