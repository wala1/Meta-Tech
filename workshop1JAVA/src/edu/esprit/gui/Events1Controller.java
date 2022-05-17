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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class Events1Controller implements Initializable {

    @FXML
    private HBox productComingSoon;
    @FXML
    private ScrollPane sp;
    @FXML
    private GridPane gp;
    private List<Event> events = new ArrayList<>();

    private List<Event> getData() {

        ServiceEvent se = new ServiceEvent();
        List<Event> events = new ArrayList<>();
        events = se.getAll();
        return events;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        events.addAll(getData());
        int column = 0;
        int row = 1;
try {
            for (int i = 0; i < events.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemEvents1.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println(events.toString());
                ItemEvents1Controller itemController = fxmlLoader.getController();
                itemController.setData(events.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }
                gp.add(anchorPane, column++, row);
                //set grid width
                gp.setMinWidth(Region.USE_COMPUTED_SIZE);
                gp.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gp.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gp.setMinHeight(Region.USE_COMPUTED_SIZE);
                gp.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gp.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

//            
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
