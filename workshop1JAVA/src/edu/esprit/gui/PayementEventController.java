/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.services.ServiceEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class PayementEventController implements Initializable {
     private Parent fxml;
         private Stage stage;
    private Scene scene;

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
    int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public void setId(int id) {
        this.id=id;
    }

    @FXML
    private void OnPay(ActionEvent event) {
         ServiceEvent se= new ServiceEvent();
         System.out.println(id);
         se.participerEvenement(id,154);
         
            try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Events.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(PayementEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
         TrayNotification tray = new TrayNotification();
            tray.setTitle("Request sent!");

            tray.setMessage("Wait for Admin to approve your request  ");
             tray.setRectangleFill(Paint.valueOf("#fdb819"));
            tray.showAndDismiss(Duration.seconds(8));
    }

    
}
