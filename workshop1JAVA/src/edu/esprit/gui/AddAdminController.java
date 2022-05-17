/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Admin;
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
import javafx.scene.control.ButtonType;
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
public class AddAdminController implements Initializable {

    @FXML
    private ComboBox<?> comboBoxAdmin;
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
            private ServiceUser ServiceUser;

    private Scene scene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              this.ServiceUser = new ServiceUser();

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
    private void goToCategories(ActionEvent event) {
    }

    @FXML
    private void goToSubCategories(ActionEvent event) {
    }

    @FXML
    private void OnLogout(ActionEvent event) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Sign Out!");
        alert.setContentText("Are You sure you want to sign out ?");
        alert.setTitle("Log out ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ServiceUser.disconnectAll();
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("connect.fxml"));

                    fxml = loader.load();

                } catch (IOException ex) {
                    Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxml);
                stage.setScene(scene);
                stage.show();

            } else {
                System.out.println("cancel");
            }

        });
    }

    @FXML
    private void OnProfileAdmin(MouseEvent event) {
    }

    @FXML
    private void OnAdd(ActionEvent event) {
         String username = tfEditUsername.getText();
        String email = tfEditEmail.getText();
        String pass = tfEditPassword.getText();
        String confirmPass = tfEditConfirmPassword.getText();
        String image = tfEditImage.getText();
        int num;
        // num=Integer.parseInt(textFieldnum.getText());

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
            } else if (pass.length() < 8 || pass.equals(confirmPass)==false) {
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
            alert.setTitle("Failed to Add");
            alert.setContentText(errors.toString());
            alert.showAndWait();

        } else {
            try {
                num = Integer.parseInt(tfEditPhoneNumber.getText());
            } catch (NumberFormatException ex) {
                System.out.println("please add a valid phone number ");
                return;
            }
            User u = new Admin(username, pass, email, num, image);
            ServiceUser.ajouter(u);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success! ");

            alert.setHeaderText("Added Successfully");
            alert.setContentText("The Admin has been added successfully");
            alert.showAndWait();
            try {
                fxml = FXMLLoader.load(getClass().getResource("Users.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();

        }

    }
    
}
