/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Event;
import edu.esprit.services.ServiceEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.entities.User;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class EventsAdminController implements Initializable {

    @FXML
    private ComboBox<?> comboBoxAdmin;
    @FXML
    private ListView<String> listImage;
    @FXML
    private ListView<Integer> listPart;
    @FXML
    private ListView<String> listDesc;
    @FXML
    private ListView<String> listTitle;
    @FXML
    private ListView<String> listStart;
    @FXML
    private ListView<String> listEnd;
    @FXML
    private ListView<Integer> listId;
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfPart;
    @FXML
    private TextField tfImage;
    @FXML
    private TextArea tfDescription;
    private TextField tfStart;
    private TextField tfId;
    @FXML
    private Button OnUpdate1;
    @FXML
    private DatePicker date_d_event;
    @FXML
    private DatePicker date_f_event;
    @FXML
    private ImageView notifications;
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
        ObservableList<Event> evenements = ev.afficher();
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
        listImage.getItems().clear();
        listDesc.getItems().clear();
        listStart.getItems().clear();
        listEnd.getItems().clear();
        listPart.getItems().clear();
        listTitle.getItems().clear();
        listId.getItems().clear();
        ServiceEvent ev = new ServiceEvent();
        ObservableList<Event> evenements = ev.afficher();
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
    private void OnAddAdmin(MouseEvent event) {
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
    private void OnDelete(ActionEvent event) {
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
        ServiceEvent ev = new ServiceEvent();
        ev.supprimer(id);
        refresh();
    }

    @FXML
    private void OnEdit(ActionEvent event) {
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
        System.out.println("f fenetre te3 edit" + id);
        ServiceEvent ev = new ServiceEvent();
        Event e = ev.getEvent(id);
        tfTitle.setText(e.getTitle());

        tfImage.setText(e.getImageEvent());
        //tfStart.setText(e.getDateDebut());
        // date_d_event.setValue(e.getDateDebut());
        // date_f_event.setValue(e.getDateDebut());

        tfDescription.setText(e.getDescription());
        // tfEnd.setText(e.getDateFin());
        tfPart.setText(Integer.toString(e.getNbrPartMax()));
        OnUpdate1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ServiceEvent ev = new ServiceEvent();
                String title = tfTitle.getText();
                String description = tfDescription.getText();
                // String start = tfStart.getText();
                //  String end = tfEnd.getText();
                String dateD = date_d_event.getValue().toString();
                String dateF = date_f_event.getValue().toString();
                String image = tfImage.getText();
                int part = Integer.parseInt(tfPart.getText());
                Event e = new Event(title, description, image, dateD, dateF, part);
                ev.modifier1(e, id);
                refresh();
                tabPane.getSelectionModel().select(0);
                tfTitle.setText("");

                tfDescription.setText("");
                tfStart.setText("");
                //tfEnd.setText("");
                //tfImage.setText("");
                tfPart.setText("");
            }
        });

        tabPane.getSelectionModel().select(1);
        refresh();

    }

    @FXML
    private void OnAdd(ActionEvent event) {
        String nom = tfTitle.getText();
        String dateD = date_d_event.getValue().toString();
        String dateF = date_f_event.getValue().toString();

        String image = tfImage.getText();
        //float prix=Float.parseFloat(prix_event.getText());
        String description = tfDescription.getText();
        int nbr = Integer.parseInt(tfPart.getText());
        LocalDate dateDeb = LocalDate.parse(dateD);
        LocalDate dateFin = LocalDate.parse(dateF);
        if (LocalDate.now().compareTo(dateDeb) < 0 && dateFin.compareTo(dateDeb) > 0) {
            Event e = new Event(nom, description, image, dateD, dateF, nbr);

            ServiceEvent ev = new ServiceEvent();
            ev.ajouter(e);
            refresh();
            tabPane.getSelectionModel().select(0);
            tfTitle.setText("");
            tfImage.setText("");
            tfDescription.setText("");

            tfPart.setText("");

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Warning");
            alert.setContentText(" wrong field");
            alert.show();
        }

    }

    @FXML
    private void OnNotification(MouseEvent event) {
            try {
            fxml = FXMLLoader.load(getClass().getResource("RequestEvent.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnMetaTech(MouseEvent event) {
         try {
            fxml = FXMLLoader.load(getClass().getResource("Events.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    
    }

    @FXML
    private void OnEvents(ActionEvent event) {
    }
}
