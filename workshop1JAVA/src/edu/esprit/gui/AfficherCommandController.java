/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Command;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommand;
import edu.esprit.services.iService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Material;

/**
 * FXML Controller class
 *
 * @author Logidee
 */
public class AfficherCommandController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Command> table;

    @FXML
    private TableColumn<Command, Integer> id;

    @FXML
    private TableColumn<Command, String> firstName;

    @FXML
    private TableColumn<Command, String> street;

    @FXML
    private TableColumn<Command, Integer> zip;

    @FXML
    private TableColumn<Command, Long> phone;

    @FXML
    private TableColumn<Command, String> payment;

    @FXML
    private TableColumn<Command, Date> date;

    @FXML
    private TableColumn<Command, String> etat;

    @FXML
    private TableColumn<Command, Double> subtotal;

    int from = 0, to = 0;
    int itemPerPage = 5;

    @FXML
    private Pagination pagination;

   
    ServiceCommand scmd = new ServiceCommand();
    private final List<Command> commands = scmd.getAll();
    ObservableList<Command> list = FXCollections.observableArrayList(commands);

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //id.setCellValueFactory(new PropertyValueFactory<Command, Integer>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Command, String>("firstName"));
         street.setCellValueFactory(new PropertyValueFactory<Command, String>("street_Adress"));
         zip.setCellValueFactory(new PropertyValueFactory<Command, Integer>("zip_PostalCode"));
         phone.setCellValueFactory(new PropertyValueFactory<Command, Long>("phone_Number"));
         payment.setCellValueFactory(new PropertyValueFactory<Command, String>("payement_method"));
         date.setCellValueFactory(new PropertyValueFactory<Command, Date>("date"));
         etat.setCellValueFactory(new PropertyValueFactory<Command, String>("etat"));
         subtotal.setCellValueFactory(new PropertyValueFactory<Command, Double>("subtotal"));
         table.setItems(list);
        
        
    }


    @FXML
    void deleteCommand(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        Command cmd = table.getSelectionModel().getSelectedItem();
        ServiceCommand scmd = new ServiceCommand();
        scmd.supprimer(cmd.getId());
    }

    @FXML
    void updateCommand(ActionEvent event) throws IOException {
        ServiceCommand scmd = new ServiceCommand();
        Command cmd = table.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormCommand.fxml"));
        Parent root = loader.load();
        FormCommandController apc = loader.getController();

        apc.setFirstName(cmd.getFirstName());
        apc.setLastName(cmd.getLastName());
        apc.setStreetAdress(cmd.getStreet_Adress());
        apc.setCity(cmd.getCity());
        apc.setZipPostal("" + cmd.getZip_PostalCode());
        apc.setCountry(cmd.getCountry());
        apc.setCompany(cmd.getCompany());
        apc.setPhone("" + cmd.getPhone_Number());
        apc.setPaymentMethod(cmd.getPayement_method());
        apc.setDate(cmd.getDate());
        apc.setDeliveryComment(cmd.getDelivery_comment());
        apc.setOrderComment(cmd.getOrder_comment());
        apc.setCodeCoupon("" + cmd.getCodeCoup());
        apc.setNewsLetter(cmd.getNewsletter());
        apc.setId("" + cmd.getId());
        //System.out.println("update : "+cmd.getFirstName());
        table.getScene().setRoot(root);
    }

}
