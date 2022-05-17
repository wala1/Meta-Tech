/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.services.ServiceUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import edu.esprit.entities.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class EmailPassController implements Initializable {

    private ServiceUser ServiceUser;
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField textfieldEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ServiceUser = new ServiceUser();
    }

    @FXML
    private void OnSearch(ActionEvent event) {

        String email = textfieldEmail.getText();
        if (ServiceUser.findEmail(email) == true) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Wait ");

            alert1.setHeaderText("This may take a moment ");
            alert1.setContentText("Thanks for waiting ");

            alert1.showAndWait();
            if (ServiceUser.sendMailPass(email) == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message sent ");

                alert.setHeaderText("Verification Code ");
                alert.setContentText("A verification code has been sent to your email address :" + email);

                alert.showAndWait();
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("code.fxml"));

                    fxml = loader.load();
                    CodeController cc = loader.getController();
                    cc.inisialiseEmail(email);
                } catch (IOException ex) {
                    Logger.getLogger(EmailPassController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxml);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Send message Error");
                alert.setContentText("An Error occured while sending email,the address may not be found  ");
                alert.setTitle("Email was not sent! ");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Email not found!");
                alert.setContentText("Your message wasn't delivered to \"  "+email+"\" because the address couldn't be found or is unable to receive email");
                alert.setTitle("Address not found! ");
                alert.showAndWait();

        }
    }

    private void OnSignin2(ActionEvent event) {
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
    private void OnSignin1(ActionEvent event) {
    }

}
