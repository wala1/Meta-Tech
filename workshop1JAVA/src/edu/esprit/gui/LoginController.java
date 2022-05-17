/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class LoginController implements Initializable {

    @FXML
    private TextField textFieldemailUser;
    @FXML
    private PasswordField textFieldpassUser;
    @FXML
    private Button BtnLogin;
    private ServiceUser ServiceUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
  this.ServiceUser = new ServiceUser();
    }    

    @FXML
    private void onForgot(ActionEvent event) {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Welcome Client");
                alert.showAndWait();

    }

    @FXML
    private void OnLogin(ActionEvent event) {
          System.err.println("click");
            String email = textFieldemailUser.getText();
        String pass = textFieldpassUser.getText();
        if (ServiceUser.login1(email, pass) != null) {
            User u = ServiceUser.login1(email, pass);
            if (ServiceUser.checkRole(email).equals("[" + (char) 34 + "ROLE_ADMIN" + (char) 34 + "]")) {
                System.out.println("admin");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Welcome Admin");
                alert.showAndWait();
            } else if (ServiceUser.checkRole(email).equals("[" + (char) 34 + "ROLE_CLIENT" + (char) 34 + "]")) {
                System.out.println("client");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Welcome Client");
                alert.showAndWait();
            }

        } else {
            System.err.println("no connection");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("veuillez inserer un mdp ou email valides ");
            alert.showAndWait();
        }




    }
    
}
