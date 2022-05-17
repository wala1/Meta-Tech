/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Event;
import edu.esprit.services.ServiceEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class AddEventUserController implements Initializable {

    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea tfDescription;
    @FXML
    private DatePicker date_d_event;
    @FXML
    private DatePicker date_f_event;
    @FXML
    private TextField tfPart;
    @FXML
    private TextField tfImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnSendRequest(ActionEvent event) {
        String nom = tfTitle.getText();
        String dateD = date_d_event.getValue().toString();
        String dateF = date_f_event.getValue().toString();
        String image = tfImage.getText();
        String description = tfDescription.getText();
        int nbr = Integer.parseInt(tfPart.getText());
        LocalDate dateDeb = LocalDate.parse(dateD);
        LocalDate dateFin = LocalDate.parse(dateF);
        if (LocalDate.now().compareTo(dateDeb) < 0 && dateFin.compareTo(dateDeb) > 0) {
            Event e = new Event(nom, description, image, dateD, dateF, nbr);

            ServiceEvent ev = new ServiceEvent();
            ev.AddEventRequest(e);
            tfTitle.setText("");
            tfImage.setText("");
            tfDescription.setText("");

            tfPart.setText("");
            /*     Image image1=new Image("C:/Usersihebx/Desktop/workshop1JAVA/image/error.png");

        Notifications notifications=Notifications.create();
        notifications.graphic(new ImageView(image1));
        notifications.text("Your request has been successfully sent to the Admin , wait for his approval ");
        notifications.title("Request sent! ");
        notifications.hideAfter(Duration.seconds(6));
        notifications.darkStyle();
        notifications.show();*/
           // Image whatsAppImg = new Image("https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/whatsapp-128.png");
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Request sent!");

            tray.setMessage("Wait for Admin to approve your request  ");
            //tray.setNotification(notification);
            // tray.setAnimation(Animations.FADE);
           
            tray.setRectangleFill(Paint.valueOf("#fdb819"));
            tray.showAndDismiss(Duration.seconds(4));

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Warning");
            alert.setContentText(" check date");
            alert.show();
        }

    }

}
