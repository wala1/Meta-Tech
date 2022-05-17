/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
//import java.awt.Insets;
import javafx.geometry.Insets;
//import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.services.ServiceEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ihebx
 */
public class UsersController implements Initializable {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> tcImage;
    @FXML
    private TableColumn<User, String> tcUsername;
    @FXML
    private TableColumn<User, String> tcEmail;
    @FXML
    private TableColumn<User, String> tcPhoneNumber;
    private ServiceUser ServiceUser;
    ObservableList<User> userList;
    ObservableList<User> usersList = FXCollections.observableArrayList();
    ObservableList<User> usersList1 = FXCollections.observableArrayList();

    User users;
    @FXML
    private TableColumn<User, String> tcActions;
    @FXML
    private TableColumn<User, Integer> tcId;
    @FXML
    private ComboBox<String> comboBoxAdmin;
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    int test;
    @FXML
    private TableView<User> tableViewAdmin;
    @FXML
    private TableColumn<User, String> tcIdAdmin;
    @FXML
    private TableColumn<User, String> tcImageAdmin;
    @FXML
    private TableColumn<User, String> tcUsernameAdmin;
    @FXML
    private TableColumn<User, String> tcEmailAdmin;
    @FXML
    private TableColumn<User, Integer> tcPhoneNumberAdmin;
    @FXML
    private TableColumn<User, String> tcActionsAdmin;
    @FXML
    private Label labelCount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ServiceUser = new ServiceUser();

        //ObservableList<String> list = FXCollections.observableArrayList(ServiceUser.getConnected().getUsername(), "Home", "Log Out");
        //comboBoxAdmin.getSelectionModel().selectFirst();
        //comboBoxAdmin.addEventHandler(EventType.ROOT, eventHandler);
        tableViewAdmin.setEditable(true);
        tableView.setEditable(true);
        Refresh();
        RefreshTableAdmin();
        ServiceEvent ev = new ServiceEvent();
        int num = ev.countRequests();
        labelCount.setText(String.valueOf(num));
    }

    private void refreshTable() {

        usersList.clear();
        usersList.addAll(ServiceUser.AfficherTout());
        tableView.setItems(usersList);
    }

    private void refreshTable1() {

        usersList1.clear();
        usersList1.addAll(ServiceUser.AfficherAdmin());
        tableViewAdmin.setItems(usersList1);
    }

    private void RefreshTableAdmin() {
        ServiceUser ServiceUser = new ServiceUser();
        tcImageAdmin.setCellValueFactory(new PropertyValueFactory<>("img"));
        tcUsernameAdmin.setCellValueFactory(new PropertyValueFactory<>("username"));
        tcEmailAdmin.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcPhoneNumberAdmin.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tcIdAdmin.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableViewAdmin.getItems().clear();
        tableViewAdmin.setItems(ServiceUser.AfficherAdmin());
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<User, String> param) -> {
            //     make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //      that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        // FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        //  FontAwesomeIconView blockIcon = new FontAwesomeIconView(FontAwesomeIcon.B);
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView unblockIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill: #9999d0;"
                        );
                        unblockIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill: #0f6b1d;" //#750505 9999d0
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            users = tableViewAdmin.getSelectionModel().getSelectedItem();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Delete Account");
                            alert.setContentText("Are You sure you want to delete \" " + users.getEmail() + "\" ?");
                            alert.setTitle("Delete user ");
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {

                                    ServiceUser.delete(users);
                                    refreshTable1();
                                } else {
                                    System.out.println("cancel delete operation");
                                }

                            });

                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            users = tableViewAdmin.getSelectionModel().getSelectedItem();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Block Account");
                            alert.setContentText("Are You sure you want to Block \" " + users.getEmail() + "\" ?");
                            alert.setTitle("Block Account ");
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {

                                    ServiceUser.block(users);
                                    refreshTable1();
                                } else {
                                    System.out.println("cancel delete operation");
                                }

                            });
                        }
                        );
                        unblockIcon.setOnMouseClicked((MouseEvent event) -> {
                            users = tableView.getSelectionModel().getSelectedItem();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Unlock Account");
                            alert.setContentText("Are You sure you want to unblock \" " + users.getEmail() + "\" ?");
                            alert.setTitle("Unblock Account ");
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {

                                    ServiceUser.unBlock(users);
                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert1.setTitle("Account was Successfullu unblocked! ");

                                    alert1.setHeaderText("Block");
                                    alert1.setContentText(",You have successfully unlocked this Account");
                                    alert1.showAndWait();
                                    refreshTable1();
                                } else {
                                    System.out.println("cancel delete operation");
                                }

                            });
                        }
                        );

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(unblockIcon, new Insets(2, 4, 0, 1));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        tcActionsAdmin.setCellFactory(cellFoctory);

    }

    private void Refresh() {
        ServiceUser ServiceUser = new ServiceUser();
        tcImage.setCellValueFactory(new PropertyValueFactory<>("img"));
        tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableView.getItems().clear();
        tableView.setItems(ServiceUser.AfficherTout());
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<User, String> param) -> {
            //     make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //      that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        // FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView unblockIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill: #9999d0;"
                        );
                        unblockIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill: #0f6b1d;" //#750505 9999d0
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            users = tableView.getSelectionModel().getSelectedItem();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Delete Account");
                            alert.setContentText("Are You sure you want to delete \" " + users.getEmail() + "\" ?");
                            alert.setTitle("Delete user ");
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {

                                    ServiceUser.delete(users);
                                    refreshTable();
                                } else {
                                    System.out.println("cancel delete operation");
                                }

                            });

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            users = tableView.getSelectionModel().getSelectedItem();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Block Account");
                            alert.setContentText("Are You sure you want to Block \" " + users.getEmail() + "\" ?");
                            alert.setTitle("Block Account ");
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {

                                    ServiceUser.block(users);
                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert1.setTitle("Account was Successfullu blocked! ");

                                    alert1.setHeaderText("Block");
                                    alert1.setContentText(",You have successfully Blocked this Account");
                                    alert1.showAndWait();
                                    refreshTable1();
                                } else {
                                    System.out.println("cancel delete operation");
                                }

                            });
                        }
                        );

                        unblockIcon.setOnMouseClicked((MouseEvent event) -> {
                            users = tableView.getSelectionModel().getSelectedItem();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Unlock Account");
                            alert.setContentText("Are You sure you want to unblock \" " + users.getEmail() + "\" ?");
                            alert.setTitle("Unblock Account ");
                            alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.OK) {

                                    ServiceUser.unBlock(users);
                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert1.setTitle("Account was Successfullu unblocked! ");

                                    alert1.setHeaderText("Block");
                                    alert1.setContentText(",You have successfully unlocked this Account");
                                    alert1.showAndWait();
                                    refreshTable1();
                                } else {
                                    System.out.println("cancel delete operation");
                                }

                            });
                        }
                        );
                        HBox managebtn = new HBox(editIcon, deleteIcon, unblockIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(unblockIcon, new Insets(2, 4, 0, 1));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        tcActions.setCellFactory(cellFoctory);

    }

    @FXML

    private void goToCategories(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    
           
    }

    @FXML
    private void goToSubCategories(ActionEvent event) {
         try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    
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
    private void OnProfileAdmin(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileAdmin.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnAddAdmin(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAdmin.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void OnEvents(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventsAdmin.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnProduct(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnBlog(ActionEvent event) {
         try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../metatech/gui/back/PublicationFXML.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnPublicity(ActionEvent event) {
         try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfaceFront.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnCart(ActionEvent event) {
         try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("flk.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

}
