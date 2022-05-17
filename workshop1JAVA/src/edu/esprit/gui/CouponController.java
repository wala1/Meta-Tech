/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Coupon;
import edu.esprit.entities.publicity;
import edu.esprit.services.ServiceCoupon;
import edu.esprit.tests.FXMain;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author mejri
 */
public class CouponController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private ListView<Integer> listId;
    @FXML
    private ListView<String> listCode;
    @FXML
    private TextField tfCode;
    private Button OnUpdate1;
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 ServiceCoupon sc = new ServiceCoupon();
        ObservableList<Coupon> coupons = sc.afficher();

        for (Coupon e : coupons) {

            listCode.getItems().add(e.getCodeCoupon());
        }
       
        
        for (Coupon e : coupons) {

            listId.getItems().add(e.getId());
        }                
                
                
                }    

    
    public void refresh() {
        listCode.getItems().clear();
        listId.getItems().clear();
        ServiceCoupon ev = new ServiceCoupon();
        ObservableList<Coupon> coupons = ev.afficher();
        for (Coupon e : coupons) {

            listCode.getItems().add(e.getCodeCoupon());
        }
        for (Coupon e : coupons) {

            listId.getItems().add(e.getId());
        }

    }
    
      @FXML
    private void OnDelete(ActionEvent event) {
        int index = -1;
        if (listCode.getSelectionModel().getSelectedIndex() != -1) {
            index = listCode.getSelectionModel().getSelectedIndex();
        }


        int id = listId.getItems().get(index);
        System.out.println(id);
        ServiceCoupon ev = new ServiceCoupon();
        ev.supprimer(id);
        refresh();
    }

    private void OnEdit(ActionEvent event) {
        int index = -1;
        if (listCode.getSelectionModel().getSelectedIndex() != -1) {
            index = listCode.getSelectionModel().getSelectedIndex();
        }
      
        int id = listId.getItems().get(index);
        System.out.println("f fenetre te3 edit" + id);
        ServiceCoupon ev = new ServiceCoupon();
        Coupon e = ev.getCoupon(id);
        tfCode.setText(e.getCodeCoupon());
        OnUpdate1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ServiceCoupon ev = new ServiceCoupon();
                String code = tfCode.getText();
                Coupon e = new Coupon(code);
                ev.modifier(e, id);
                refresh();
                tabPane.getSelectionModel().select(0);
                tfCode.setText("");
           }
        });

        tabPane.getSelectionModel().select(1);
        refresh();

    }

   @FXML
    private void OnAdd(ActionEvent event) {
        
               String code = tfCode.getText();
            Coupon c = new Coupon(code);

            ServiceCoupon ev = new ServiceCoupon();
            ev.ajouter(c);
            refresh();
            tabPane.getSelectionModel().select(0);
            tfCode.setText("");
            
            
    }






    @FXML
    private void loadStat(MouseEvent event) {
      
    }

    
    public void search(){
  }
  
    @FXML
    private void publicityy(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("interfaceFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void front(MouseEvent event) throws IOException {
                Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("MetatechFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }
    
    
}
    
    


   
    

