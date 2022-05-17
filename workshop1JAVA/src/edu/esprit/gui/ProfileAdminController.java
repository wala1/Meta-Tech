/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.User;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class ProfileAdminController implements Initializable {

    @FXML
    private ComboBox<?> comboBoxAdmin;
    @FXML
    private Label labelGetUsername;
    @FXML
    private Label labelGetEmail;
    @FXML
    private TextField tfEditUsername;
    @FXML
    private TextField tfEditEmail;
    @FXML
    private TextField tfEditPhoneNumber;
    @FXML
    private PasswordField tfEditPassword;
    @FXML
    private PasswordField tfEditConfirmPassword;
    @FXML
    private TextField tfEditImage;
       private Parent fxml;
    private Stage stage;
    private Scene scene;
    String username;
    String email;
    int phoneNumber;
    String pass;
    String image;
    int id;
        private ServiceUser ServiceUser;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         this.ServiceUser = new ServiceUser();
        username = User.session.getUsername();
        email = User.session.getEmail();
        phoneNumber = User.session.getPhoneNumber();
        pass = User.session.getPassword();
         labelGetUsername.setText(username);
         labelGetEmail.setText(email);
        id=User.session.getId();
        image = User.session.getImageUser();
       
        tfEditUsername.setText(username);
        tfEditEmail.setText(email);
        tfEditPassword.setText(pass);
        tfEditConfirmPassword.setText(pass);
        tfEditPhoneNumber.setText(String.valueOf(phoneNumber));
        tfEditImage.setText(image);
    }    

    @FXML
    private void goToCategories(ActionEvent event) {
    }

    @FXML
    private void goToSubCategories(ActionEvent event) {
    }

    @FXML
    private void OnLogout(ActionEvent event) {
    }

    @FXML
    private void OnProfileAdmin(MouseEvent event) {
    }

    @FXML
    private void OnUpdate(ActionEvent event) {
         String username = tfEditUsername.getText();
        String email = tfEditEmail.getText();
        String pass = tfEditPassword.getText();
        String image = tfEditImage.getText();
        String confirmPass = tfEditConfirmPassword.getText();
        int phoneNumber = Integer.parseInt(tfEditPhoneNumber.getText());
        User u = new User(id,username, pass, email, phoneNumber, image);
        StringBuilder errors = new StringBuilder();

        if (tfEditPhoneNumber.getText().trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty() || pass.trim().isEmpty() || username.length() < 4 || tfEditPhoneNumber.getText().length() < 8 || pass.length() < 8 || pass.equals(confirmPass)==false) {
            if (username.trim().isEmpty()) {
                errors.append("*A username is required \n");
            } else if (username.length() < 4) {
                errors.append("*Your username must contain at least 4 characters \n");
            }

            if (email.trim().isEmpty()) {
                errors.append("*An email is required \n");
            } else if (email.length() < 4) {
                errors.append("*Your Email must contain at least 4 characters \n");
            }
            if (pass.trim().isEmpty()) {
                errors.append("*A password is required \n");
            } else if (pass.length() < 8 || confirmPass != pass) {
                if (pass.length() < 8) {
                    errors.append("*Your password must contain at least 8 characters \n");
                } else if (pass.equals(confirmPass)==false) {
                    errors.append("*Passwords does not match  \n");
                }
            }

            if (tfEditPhoneNumber.getText().trim().isEmpty()) {
                errors.append("*A phone number is required \n");
            } else if (tfEditPhoneNumber.getText().length() < 8 || tfEditPhoneNumber.getText().length() > 8) {
                errors.append("*Your phone number must have exactly 8 numbers \n");

            }

        }
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modification Failed");
            alert.setContentText(errors.toString());
            alert.showAndWait();

        } else {
            ServiceUser.modifier(u);
             labelGetUsername.setText(username);
         labelGetEmail.setText(email);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Changes was saved!");

            alert.setHeaderText("Modify Profile ");
            alert.setContentText("Your have successfully changed ypur profile");

            alert.showAndWait();

        }
    }

    @FXML
    private void OnUsers(ActionEvent event) {
             try {
            fxml = FXMLLoader.load(getClass().getResource("Users.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    
        
    }

    @FXML
    private void OnAddAdmin(MouseEvent event) {
    }
    
}
