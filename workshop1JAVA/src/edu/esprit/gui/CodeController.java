/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.services.ServiceUser;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class CodeController implements Initializable {

        private ServiceUser ServiceUser;
         String email;
    @FXML
    private TextField textfieldCode;
     private Parent fxml;
    private Stage stage;
    private Scene scene;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                this.ServiceUser = new ServiceUser();

    }    
    public void inisialiseEmail(String email){
    this.ServiceUser = new ServiceUser();    
    this.email= email;
    }


    @FXML
    private void OnContinue(ActionEvent event) {
        System.out.println(email);
        String code=textfieldCode.getText();
        if(ServiceUser.verifyCode(code, email)==true)
        {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPass.fxml"));

                fxml = loader.load();
                NewPassController np = loader.getController();
                np.inisialiseEmail(email);
                np.inisialiseCode(code);
                
            } catch (IOException ex) {
                Logger.getLogger(CodeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();
        }else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("code Error");
            alert.setContentText("The number you entered doesn’t match your code. Please try again. ");
            alert.setTitle("Warning! ");
            alert.showAndWait();
        }
            
        
    }

    @FXML
    private void OnSignin3(ActionEvent event) {
    }
    
}
