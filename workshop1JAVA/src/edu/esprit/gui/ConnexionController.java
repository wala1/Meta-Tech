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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class ConnexionController implements Initializable {
@FXML
    private TextField textFieldemailUser;
    @FXML
    private PasswordField textFieldpassUser;
    @FXML
    private Button BtnLogin;
    private ServiceUser ServiceUser;
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

    @FXML
    private void OnSignup(ActionEvent event) {
           try {
            fxml = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    
    }

    @FXML
    private void onForgot(ActionEvent event) {
        
            try {
            fxml = FXMLLoader.load(getClass().getResource("EmailPass.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnLogin(ActionEvent event) {
          String test1 = ServiceUser.checkRole(textFieldemailUser.getText());
        System.err.println("********************" + test1);

        User u = new User();

        u.setEmail(textFieldemailUser.getText());
        u.setPassword(textFieldpassUser.getText());

        if (ServiceUser.login(u.getEmail(), u.getPassword())) {

            Parent home_page_parent;

            if (ServiceUser.checkBlock(textFieldemailUser.getText()) == 1) {
                ServiceUser.setConnected(textFieldemailUser.getText());
                String name = ServiceUser.getConnected().getUsername();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Authentification Failed");
                alert.setContentText("Sorry \"" + name + ",\"you are blocked , you can't have access to your account");
                alert.setTitle("Connection Failed! ");
                alert.showAndWait();
                ServiceUser.disconnectAll();
            } else if (ServiceUser.checkBlock(textFieldemailUser.getText()) == 0) {
                ServiceUser.setConnected(textFieldemailUser.getText());
                String name = ServiceUser.getConnected().getUsername();
                if (ServiceUser.checkRole(textFieldemailUser.getText()).equals("admin")) {
//            
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Connection succeeded! ");

                    alert.setHeaderText("Welcome to Metatech");
                    alert.setContentText(name + ",You have successfully connected as an Admin");
                    alert.showAndWait();
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Users.fxml"));

                        fxml = loader.load();

                    } catch (IOException ex) {
                        Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(fxml);
                    stage.setScene(scene);
                    stage.show();

                } else if (ServiceUser.checkRole(textFieldemailUser.getText()).equals("client")) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("success ");

                    alert.setHeaderText("Welcome To MetaTech");
                    alert.setContentText(name + ",You have successfully connected!");

                    alert.showAndWait();

                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));

                        fxml = loader.load();

                    } catch (IOException ex) {
                        Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(fxml);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Authentification Failed");
            alert.setContentText("Check your email or password");
            alert.setTitle("Failed! ");
            alert.showAndWait();

        }
    }
    
}
