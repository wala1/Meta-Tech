/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.SmsApi;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import edu.esprit.entities.User;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField textFieldusername;
    @FXML
    private TextField textFieldemail;
    @FXML
    private PasswordField textFieldpwd;
    @FXML
    private PasswordField textFieldconfirmpwd;
    @FXML
    private TextField textFieldnum;
    private ServiceUser ServiceUser;
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField textFieldimage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ServiceUser = new ServiceUser();
    }

    @FXML
    private void OnSignUp1(ActionEvent event) {
        String username = textFieldusername.getText();
        String email = textFieldemail.getText();
        String pass = textFieldpwd.getText();
        String confirmPass = textFieldconfirmpwd.getText();
        String image = textFieldimage.getText();
        int num;
        // num=Integer.parseInt(textFieldnum.getText());

        StringBuilder errors = new StringBuilder();
        if (textFieldnum.getText().trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty() || pass.trim().isEmpty() || username.length() < 4 || textFieldnum.getText().length() < 8 || pass.length() < 8 || pass.equals(confirmPass)==false || email.matches("^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                        + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$")==false) {
            if (username.trim().isEmpty()) {
                errors.append("*A username is required \n");
            } else if (username.length() < 4) {
                errors.append("*Your username must contain at least 4 characters \n");
            }

            if (email.trim().isEmpty()) {
                errors.append("*An email is required \n");
            }else if(email.matches("^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                        + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$")==false){
            errors.append("*Invalid Email Adress \n");
            } /*else if (email.length() < 4) {
                errors.append("*Your Email must contain at least 4 characters \n");
            }*/
            if (pass.trim().isEmpty()) {
                errors.append("*A password is required \n");
            } else if (pass.length() < 8 || pass.equals(confirmPass)==false) {
                if (pass.length() < 8) {
                    errors.append("*Your password must contain at least 8 characters \n");
                } else if (pass.equals(confirmPass)==false) {
                    errors.append("*Passwords does not match  \n");
                }
            }

            if (textFieldnum.getText().trim().isEmpty()) {
                errors.append("*A phone number is required \n");
            } else if (textFieldnum.getText().length() < 8 || textFieldnum.getText().length() > 8) {
                errors.append("*Your phone number must have exactly 8 numbers \n");

            }

        }
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setContentText(errors.toString());
            alert.showAndWait();
           

        } else {
            try {
                num = Integer.parseInt(textFieldnum.getText());
            } catch (NumberFormatException ex) {
                System.out.println("please add a valid phone number ");
                return;
            }
            User u = new User(username, pass, email, num, image);
            ServiceUser.ajouter(u);
            

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inscription succeeded! ");

            alert.setHeaderText("Registration succeeded!");
            alert.setContentText(",You have successfully registred on metaTech");
            alert.showAndWait();
             num = Integer.parseInt(textFieldnum.getText());
            System.out.println(num);
                        SmsApi.sendSMS("+216"+num,"Thank you for joining Metatech");
            try {
                fxml = FXMLLoader.load(getClass().getResource("connect.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();

        }

    }

    @FXML
    private void OnSignin1(ActionEvent event) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("connect.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

}
