/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Event;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class RequestEventController implements Initializable {

    @FXML
    private ComboBox<?> comboBoxAdmin;
    @FXML
    private ImageView notifications;
    @FXML
    private ListView<Integer> listId;
    @FXML
    private ListView<String> listImage;
    @FXML
    private ListView<String> listStart;
    @FXML
    private ListView<Integer> listPart;
    @FXML
    private ListView<String> listEnd;
    @FXML
    private ListView<String> listDesc;
    @FXML
    private ListView<String> listTitle;
     private Parent fxml;
    private Stage stage;
    private Scene scene;
    @FXML
    private Label labelCount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ServiceEvent ev = new ServiceEvent();

          int num=ev.countRequests();
       labelCount.setText(String.valueOf(num));
        ObservableList<Event> evenements = ev.afficherRequests();
        for (Event e : evenements) {

            listImage.getItems().add(e.getImageEvent());
        }
        for (Event e : evenements) {

            listTitle.getItems().add(e.getTitle());
        }
        for (Event e : evenements) {

            listDesc.getItems().add(e.getDescription());
        }
        for (Event e : evenements) {

            listStart.getItems().add(e.getDateDebut());
        }
        for (Event e : evenements) {

            listEnd.getItems().add(e.getDateFin());
        }
        for (Event e : evenements) {

            listPart.getItems().add(e.getNbrPartMax());
        }
        for (Event e : evenements) {

            listId.getItems().add(e.getId());
        }

    }   
      public void refresh() {
                  ServiceEvent ev = new ServiceEvent();

          int num=ev.countRequests();
       labelCount.setText(String.valueOf(num));
        listImage.getItems().clear();
        listDesc.getItems().clear();
        listStart.getItems().clear();
        listEnd.getItems().clear();
        listPart.getItems().clear();
        listTitle.getItems().clear();
        listId.getItems().clear();
        //ServiceEvent ev = new ServiceEvent();
        ObservableList<Event> evenements = ev.afficherRequests();
        for (Event e : evenements) {

            listImage.getItems().add(e.getImageEvent());
        }
        for (Event e : evenements) {

            listTitle.getItems().add(e.getTitle());
        }
        for (Event e : evenements) {

            listDesc.getItems().add(e.getDescription());
        }
        for (Event e : evenements) {

            listStart.getItems().add(e.getDateDebut());
        }
        for (Event e : evenements) {

            listEnd.getItems().add(e.getDateFin());
        }
        for (Event e : evenements) {

            listPart.getItems().add(e.getNbrPartMax());
        }
        for (Event e : evenements) {

            listId.getItems().add(e.getId());
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
               // ServiceUser.disconnectAll();
               User.session=null;
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
    private void OnNotification(MouseEvent event) {
    }

    @FXML
    private void OnAddAdmin(MouseEvent event) {
    }

    @FXML
    private void OnRefuse(ActionEvent event) {
         int index = -1;
        if (listImage.getSelectionModel().getSelectedIndex() != -1) {
            index = listImage.getSelectionModel().getSelectedIndex();
        }
        if (listDesc.getSelectionModel().getSelectedIndex() != -1) {
            index = listDesc.getSelectionModel().getSelectedIndex();
        }
        if (listStart.getSelectionModel().getSelectedIndex() != -1) {
            index = listStart.getSelectionModel().getSelectedIndex();
        }
        if (listEnd.getSelectionModel().getSelectedIndex() != -1) {
            index = listEnd.getSelectionModel().getSelectedIndex();
        }
        if (listPart.getSelectionModel().getSelectedIndex() != -1) {
            index = listPart.getSelectionModel().getSelectedIndex();
        }
        if (listTitle.getSelectionModel().getSelectedIndex() != -1) {
            index = listTitle.getSelectionModel().getSelectedIndex();
        }

        int id = listId.getItems().get(index);
        
        System.out.println(id);
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setHeaderText("Refuse Request!");
                    alert.setContentText("Are You sure you want to Refuse this evet request ?");
                    alert.setTitle("refuse request ");
                    alert.showAndWait().ifPresent(response -> {
     if (response == ButtonType.OK) {
     
         ServiceEvent ev = new ServiceEvent();
        ev.supprimer(id);
        refresh();
     }else System.out.println("cancel");
         
 });
        
    }

    @FXML
    private void OnApprove(ActionEvent event) {
         int index = -1;
        if (listImage.getSelectionModel().getSelectedIndex() != -1) {
            index = listImage.getSelectionModel().getSelectedIndex();
        }
        if (listDesc.getSelectionModel().getSelectedIndex() != -1) {
            index = listDesc.getSelectionModel().getSelectedIndex();
        }
        if (listStart.getSelectionModel().getSelectedIndex() != -1) {
            index = listStart.getSelectionModel().getSelectedIndex();
        }
        if (listEnd.getSelectionModel().getSelectedIndex() != -1) {
            index = listEnd.getSelectionModel().getSelectedIndex();
        }
        if (listPart.getSelectionModel().getSelectedIndex() != -1) {
            index = listPart.getSelectionModel().getSelectedIndex();
        }
        if (listTitle.getSelectionModel().getSelectedIndex() != -1) {
            index = listTitle.getSelectionModel().getSelectedIndex();
        }

        int id = listId.getItems().get(index);
        
        System.out.println(id);
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setHeaderText("Accept Request!");
                    alert.setContentText("Are You sure you want to Accept this evet request ?");
                    alert.setTitle("Accept request ");
                    alert.showAndWait().ifPresent(response -> {
     if (response == ButtonType.OK) {
     
         ServiceEvent ev = new ServiceEvent();
       ev.setApproved(id);
        refresh();
     }else System.out.println("cancel");
         
 });
        
       
    }

    @FXML
    private void OnEvents(ActionEvent event) {
         try {
            fxml = FXMLLoader.load(getClass().getResource("EventsAdmin.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    
    }

    
}
