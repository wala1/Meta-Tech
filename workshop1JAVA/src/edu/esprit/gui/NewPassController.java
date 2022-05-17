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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class NewPassController implements Initializable {

    private Parent fxml;
    private Stage stage;
    private Scene scene;
    String email;
    private ServiceUser ServiceUser;
    private String code;
    @FXML
    private PasswordField textfieldpwd;
    @FXML
    private PasswordField textfieldConfirmpwd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ServiceUser = new ServiceUser();
    }

    public void inisialiseEmail(String email) {
        this.ServiceUser = new ServiceUser();
        this.email = email;
    }

    public void inisialiseCode(String code) {
        this.ServiceUser = new ServiceUser();
        this.code = code;
    }

    @FXML
    private void OnContinue1(ActionEvent event) {
        String password = textfieldpwd.getText();
        String confirmPassword = textfieldConfirmpwd.getText();
        if (password.length() < 8 || password.trim().isEmpty()) {

            if (password.trim().isEmpty()) {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("*A password is required  ");
                alert.setTitle("Warning! ");
                alert.showAndWait();
            } else if (password.length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText(" Your password must contain at least 8 characters  ");
                alert.setTitle("Warning! ");
                alert.showAndWait();
            }
        } else {
            if (password.equals(confirmPassword)) {

                if (ServiceUser.changePassCode(email, code, password)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Password changed!");

                    alert.setHeaderText("new password ");
                    alert.setContentText("The password for " + email + "has been successfully changed");

                    alert.showAndWait();
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("connect.fxml"));

                        fxml = loader.load();

                    } catch (IOException ex) {
                        Logger.getLogger(NewPassController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(fxml);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("New password Error");
                alert.setContentText(" Please make sure your passwords match ");
                alert.setTitle("Passwords do not match! ");
                alert.showAndWait();
            }
        }

    }

    private void OnSignin3(ActionEvent event) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("connect.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(EmailPassController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void OnSignup(ActionEvent event) {
    }

}
