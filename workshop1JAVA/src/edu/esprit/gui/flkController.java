/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Command;
import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommand;
import edu.esprit.services.ServicePanier;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Logidee
 */
public class flkController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Command> table;


    @FXML
    private TableColumn<Command, String> firstName;

    @FXML
    private TableColumn<Command, String> street;

    @FXML
    private TableColumn<Command, String> company;

    @FXML
    private TableColumn<Command, String> country;

    @FXML
    private TableColumn<Command, String> city;

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

    @FXML
    private Pagination pagination;

    private Label pag;
    
    ServiceCommand scmd = new ServiceCommand();
    List<Command> commands = scmd.getAll();
    ObservableList<Command> list = FXCollections.observableArrayList(commands);
    @FXML
    private ImageView logo;
    
    
    
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    
    

    @FXML
    void onExcel() throws FileNotFoundException {

        FileOutputStream fos = new FileOutputStream("List Order.csv", true);
        PrintWriter pw = new PrintWriter(fos);
        pw.println("ID;User;City;Company;Country;Phone;ZIP;State");

        for (Command cmd : commands) {
            pw.println(cmd.getId() + ";" + cmd.getUser_commande().getUsername() + ";"
                    + cmd.getCity() + ";" + cmd.getCompany() + ";" + cmd.getCountry()+ ";"
                    + cmd.getPhone_Number()+ ";" + cmd.getZip_PostalCode()+ ";" + cmd.getEtat()+ ";"
                    
            );
        }

        pw.close();

        Alert a = new Alert(Alert.AlertType.INFORMATION, "This file is downloaded in C:\\Users\\Logidee\\Downloads\\JAVA\\workshop1JAVA", ButtonType.OK);
        a.showAndWait();
    }

    @FXML
    private TextField search;

    private List<Command> getData(){
        ServiceCommand scmd = new ServiceCommand();
        List<Command> commands = new ArrayList<>();
        commands =scmd.getAll();
        return commands;
    }
    
    @FXML
    void onSearch() {
        commands.clear();
        String country = search.getText();
        commands.addAll(getData().stream().filter(e->e.getFirstName().contains(search.getText())).collect(Collectors.toList()));
        
        list = FXCollections.observableArrayList(commands);
        table.setItems(list);
    }

    @FXML
    void onAddCommand() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormCommand.fxml"));
        Parent root = loader.load();
        FormCommandController apc = loader.getController();

        table.getScene().setRoot(root);
    }

    @FXML
    void deleteCommand(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        Command cmd = table.getSelectionModel().getSelectedItem();
        scmd.supprimer(cmd.getId());
    }

    @FXML
    void updateCommand(ActionEvent event) throws IOException {
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

    @FXML
    void onCanceled(ActionEvent event) {

    }

    @FXML
    void onDeliver(ActionEvent event) {

    }
    
    
    @FXML
    void onPaginate() {
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                pag.setText("Here: "+t+""+t1+""+ov);
                System.out.println("Here: "+t+""+t1+""+ov);
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //id.setCellValueFactory(new PropertyValueFactory<Command, Integer>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Command, String>("firstName"));
        street.setCellValueFactory(new PropertyValueFactory<Command, String>("street_Adress"));
        company.setCellValueFactory(new PropertyValueFactory<Command, String>("street_Adress"));
        country.setCellValueFactory(new PropertyValueFactory<Command, String>("street_Adress"));
        city.setCellValueFactory(new PropertyValueFactory<Command, String>("street_Adress"));
        zip.setCellValueFactory(new PropertyValueFactory<Command, Integer>("zip_PostalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<Command, Long>("phone_Number"));
        payment.setCellValueFactory(new PropertyValueFactory<Command, String>("payement_method"));
        date.setCellValueFactory(new PropertyValueFactory<Command, Date>("date"));
        etat.setCellValueFactory(new PropertyValueFactory<Command, String>("etat"));
        subtotal.setCellValueFactory(new PropertyValueFactory<Command, Double>("subtotal"));
        
        table.setItems(list);

    }

    
    
    
    @FXML
    private void goToCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void goToSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    private void goToFront(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void showDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void showUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void showPublicity(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfaceFront.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void showBlog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../metatech/gui/back/PublicationFXML.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void showCart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("flk.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Sign Out!");
        alert.setContentText("Are You sure you want to sign out ?");
        alert.setTitle("Log out ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // ServiceUser.disconnectAll();
                User.session = null;

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
    private void showEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventAdmin.fxml")) ; 
        Parent root = loader.load() ; 
        table.getScene().setRoot(root);
    }

}
