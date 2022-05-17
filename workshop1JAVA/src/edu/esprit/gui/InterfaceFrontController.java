/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.entities.publicity;
import edu.esprit.services.ServicePublicity;
import edu.esprit.tests.FXMain;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;


/**
 * FXML Controller class
 *
 * @author mejri
 */
public class InterfaceFrontController implements Initializable {

    @FXML
    private TableView<publicity> TviewPub;
    @FXML
    private TableColumn<publicity, String> TVName;
    @FXML
    private TableColumn<publicity, Float> TVDisc;
    @FXML
    private TableColumn<publicity, Float> TVPrice;
    @FXML
    private TableColumn<publicity, String> TVImage;
    private TableColumn<publicity, String> col_btnDelet;
    private TableColumn<publicity, String> col_btnMod;
    ObservableList<publicity> PublicityList = FXCollections.observableArrayList();

    ServicePublicity pub = new ServicePublicity();
    @FXML
    private TableColumn<publicity, ?> TVid;
    private static Stage stg;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private TextField keywordTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TviewPub.setEditable(true);
        refrech();//    
        Modifier();       

        col_btnMod = new TableColumn("Modify");

        col_btnDelet = new TableColumn("Delete");

        javafx.util.Callback<TableColumn<publicity, String>, TableCell<publicity, String>> cellFactory
                = new Callback<TableColumn<publicity, String>, TableCell<publicity, String>>() {
            public TableCell call(final TableColumn<publicity, String> param) {
                final TableCell<publicity, String> cell = new TableCell<publicity, String>() {

                    final Button btn = new Button("supprimer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                publicity u = getTableView().getItems().get(getIndex());
                                System.out.println(u.getId());
                                pub.supprimer(u.getId());
                                //AlertDialog.showNotification("Supressin !", "Supressin", AlertDialog.image_checked);
                                refrech();
  // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<publicity> filteredData = new FilteredList<>(PublicityList, b -> true);
         // 2. Set the filter Predicate whenever the filter changes.
        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(publicity -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String searchKeyword = newValue.toLowerCase();

                if (publicity.getNomPub().toLowerCase().indexOf(searchKeyword) != -1) {
                    return true;
                } else if (String.valueOf(publicity.getPrixPub()).toLowerCase().indexOf(searchKeyword) != -1) {
                    return true;
                } else if (String.valueOf(publicity.getPromoPub()).toLowerCase().indexOf(searchKeyword) != -1) {
                    return true;
                } else {
                    return false;
                    }
            });
        });
        SortedList<publicity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TviewPub.comparatorProperty());
                    TviewPub.setItems(sortedData);
        
                            });
                            setGraphic(btn);

                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };


        col_btnDelet.setCellFactory(cellFactory);
        TviewPub.getColumns().add(col_btnDelet);

    }

    public void refrech() {

       
        
        TVid.setCellValueFactory(new PropertyValueFactory<>("id"));
        TVName.setCellValueFactory(new PropertyValueFactory<>("nomPub"));
        TVDisc.setCellValueFactory(new PropertyValueFactory<>("promoPub"));
        TVDisc.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        TVPrice.setCellValueFactory(new PropertyValueFactory<>("prixPub"));
        TVPrice.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        TVImage.setCellValueFactory(new PropertyValueFactory<>("imageName"));
        //   ImageView ph=new ImageView(new Image(this.getClass().getResourceAsStream("")));
        TviewPub.getItems().clear();
        // TviewPub.setItems(PublicityList) ; 

        TviewPub.setItems(pub.Affichertout());
         }
    @FXML
    private void ajouterPublicity(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("AjouterPublicity.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    public void Modifier() {
        TVName.setOnEditCommit((TableColumn.CellEditEvent<publicity, String> event) -> {
            TablePosition<publicity, String> pos = event.getTablePosition();

            String nom = event.getNewValue();

            int row = pos.getRow();
            publicity s = event.getTableView().getItems().get(row);

            s.setNomPub(nom);
            pub.modifier(s, s.getId());
            System.out.println("TVName");
        });
        TVDisc.setOnEditCommit((TableColumn.CellEditEvent<publicity, Float> event) -> {
            TablePosition<publicity, Float> pos = event.getTablePosition();

            Float disc = event.getNewValue();

            int row = pos.getRow();
            publicity t = event.getTableView().getItems().get(row);
            t.setPromoPub(disc);
            pub.modifier(t, t.getId());
            System.out.println("TVDisc");
        });
        TVPrice.setOnEditCommit((TableColumn.CellEditEvent<publicity, Float> event) -> {
            TablePosition<publicity, Float> pos = event.getTablePosition();

            Float Prix = event.getNewValue();

            int row = pos.getRow();
            publicity ab = event.getTableView().getItems().get(row);

            ab.setPrixPub(Prix);
            pub.modifier(ab, ab.getId());
            System.out.println("TVPrice");
        });
        TVImage.setOnEditCommit((TableColumn.CellEditEvent<publicity, String> event) -> {
            TablePosition<publicity, String> pos = event.getTablePosition();

            String image = event.getNewValue();

            int row = pos.getRow();
            publicity i = event.getTableView().getItems().get(row);

            i.setImageName(image);
            pub.modifier(i, i.getId());
            System.out.println("TVImage");
        });

    }



    

    @FXML
    private void mail(MouseEvent event) throws IOException {
        FXMain m = new FXMain();
        root = FXMLLoader.load(getClass().getResource("SendMail.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void loadStat(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("BarChartRecs.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(InterfaceFrontController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void search(){
    // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<publicity> filteredData = new FilteredList<>(PublicityList, b -> true);
         // 2. Set the filter Predicate whenever the filter changes.
        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(publicity -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String searchKeyword = newValue.toLowerCase();

                if (publicity.getNomPub().toLowerCase().indexOf(searchKeyword) != -1) {
                    return true;
                } else if (String.valueOf(publicity.getPrixPub()).toLowerCase().indexOf(searchKeyword) != -1) {
                    return true;
                } else if (String.valueOf(publicity.getPromoPub()).toLowerCase().indexOf(searchKeyword) != -1) {
                    return true;
                } else {
                    return false;
                    }
            });
        });
        SortedList<publicity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TviewPub.comparatorProperty());
                    TviewPub.setItems(sortedData);}
    @FXML
    private void coupon(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("coupon.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void publicityy(ActionEvent event) {
    }

    @FXML
    private void front(MouseEvent event) throws IOException {
           Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("MetatechFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml")) ; 
        Parent root = loader.load() ; 
        TviewPub.getScene().setRoot(root);
    }

    @FXML
    private void goToCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        TviewPub.getScene().setRoot(root);
    }
    
    @FXML
    private void showUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml")) ; 
        Parent root = loader.load() ; 
        TviewPub.getScene().setRoot(root);
    }
    
    @FXML
    private void goToSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        TviewPub.getScene().setRoot(root);
    }

    @FXML
    private void showBlog(ActionEvent event) throws IOException {
     FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../metatech/gui/back/PublicationFXML.fxml")) ; 
        Parent root = loader.load() ; 
        TviewPub.getScene().setRoot(root);
    }

    @FXML
    private void showCart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("flk.fxml")) ; 
        Parent root = loader.load() ; 
        TviewPub.getScene().setRoot(root);
    }
 

    
}
