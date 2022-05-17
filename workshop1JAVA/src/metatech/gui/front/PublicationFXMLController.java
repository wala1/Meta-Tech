/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.gui.front;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import metatech.entities.Publication;
import metatech.services.ServicePublication;
import metatech.services.ServiceUser;
import metatech.utils.AlertMaker;
import metatech.utils.TextToEmotion;
import metatech.utils.Upload;
import metatech.utils.WeatherApi;
/**
 * FXML Controller class
 *
 * @author Abn
 */
public class PublicationFXMLController implements Initializable {

    @FXML
    private ListView<Publication> lv_publication;
    @FXML
    private TextArea t_description;
    @FXML
    private TextField t_titre;
    @FXML
    private Text t_image;

    Publication p = new Publication();
    ServicePublication sp = new ServicePublication();
    ServiceUser su = new ServiceUser();
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Label t_temperature;
        Stage stage;
    Scene scene;
    @FXML
    private Label t_temperature1;
    @FXML
    private Label t_temperature2;
     
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadUserList();
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        t_temperature.setText(String.valueOf( WeatherApi.getWeather()));
    }    

 
    @FXML
    private void onClickPublishBtn(ActionEvent event) {

            
        if(!t_titre.getText().equals("") &&  !t_description.getText().equals("") && !t_image.getText().equals(""))
        { 
            
        if(p == null)
        {
            p = new Publication(0, t_titre.getText(), t_description.getText(), t_image.getText(), new Date(2022, 19, 4), 1);
        
        sp.ajouter(p);
       
        AlertMaker.sendNotificationApi("Publication added", "Your Publication was sucefully Published");

        }
        else
        {
                    p.setDescription(t_description.getText());
                    p.setTitre(t_titre.getText());
                    p.setImage(t_image.getText());
                    sp.modifier(p);
                    AlertMaker.sendNotificationApi("Publication Edited", "Your Publication was sucefully Edited");

        }
        
         loadUserList();
        } else 
        {
            AlertMaker.sendNotificationApi("Publication can't be added !", "Please check all fields !");
        }
        
        
    }
    

    @FXML
    private void onClickAttachBtn(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter exjpg = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter exjpg2 = new FileChooser.ExtensionFilter("JPEG (Joint Photographic Experts Group)", "*.jpeg");
        FileChooser.ExtensionFilter expng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(exjpg, exjpg2, expng);
        fileChooser.setTitle("Choose an image File");

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (file.length() < 6000000) {

                try {
                    t_image.setText(file.getName());
                    p.setImage(file.getName());
                    Upload u = new Upload();
                    u.upload(file);
                    
                    System.out.println(file.getName());
                } catch (IOException ex) {
                    Logger.getLogger(PublicationFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Permiss");
                alert.setHeaderText("Permission denied");
                alert.setContentText("Your Image file is too big to upload \nplease choose another image");
            }

        }

        /*
         FileChooser fileChooser = new FileChooser();
         file = fileChooser.showOpenDialog(null);
         if (file != null) {
         /*Image img = new Image(file.toURI().toString(), 100, 150, true, true);
         picview.imageProperty().unbind();
         picview.setImage(img);
         picview.setFitWidth(200);
         picview.setFitHeight(150);
         } else {
         System.out.println("e404");
         }
         */
    }

        public void loadUserList() {
        lv_publication.getItems().clear();  
        
        ObservableList<Publication> myTransport = FXCollections.observableArrayList();
        lv_publication.setCellFactory((ListView<Publication> param) -> {
            ListCell<Publication> cell = new ListCell<Publication>() {
                @Override
                protected void updateItem(Publication item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item != null)
                    {
                    HBox page = new HBox();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CellPublicationFXML.fxml"));
                    try {
                        page = loader.load();
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                    CellPublicationFXMLController ctrl = loader.getController(); 
                    System.out.println(item);
                    ctrl.setAllFields(item);
                    setGraphic(page); 
                    }
                }
            };
            return cell;
        });

        myTransport.setAll(sp.getAll());
        lv_publication.getItems().setAll(myTransport);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        t_image.setText("");
        t_description.setText("");
        t_titre.setText("");
        t_image.setText("");
        p = null ;
    }
        
    @FXML
    private void onClickDeletePublication(ActionEvent event) {
        if(!lv_publication.getSelectionModel().isEmpty() || lv_publication.getSelectionModel().getSelectedItem() != null )
        {
         sp.supprimer( lv_publication.getSelectionModel().getSelectedItem().getId());
        AlertMaker.sendNotificationApi("Publications", " Votre publication a eté supprimée !");
        loadUserList();
        }
        
    }

    @FXML
    private void onClickEditPublication(ActionEvent event) {
        
       if(lv_publication.getSelectionModel().getSelectedItem() != null && lv_publication.getSelectionModel().getSelectedItem().getUserId() == su.getConnected().getId() )
                {
           p = lv_publication.getSelectionModel().getSelectedItem() ;
           t_description.setText(p.getDescription());
           t_titre.setText(p.getTitre());
           t_image.setText(p.getImage());
           
                }
        
    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
         btnDelete.setDisable(true);
            btnEdit.setDisable(true);
            if(lv_publication.getSelectionModel().getSelectedItem() != null && lv_publication.getSelectionModel().getSelectedItem().getUserId() == su.getConnected().getId() )
                {
                    
            btnDelete.setDisable(false);
            btnEdit.setDisable(false);
                }
    }

    @FXML
    private void onClickLogout(MouseEvent event) {
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.setWidth(1220);
        stage.setHeight(850);

        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/metatech/gui/back/PublicationFXML.fxml")), 1200, 800);
        } catch (IOException ex) {
             Logger.getLogger(PublicationFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clickAllPublications(ActionEvent event) {
        lv_publication.getItems().clear();  
        
        ObservableList<Publication> myTransport = FXCollections.observableArrayList();
        lv_publication.setCellFactory((ListView<Publication> param) -> {
            ListCell<Publication> cell = new ListCell<Publication>() {
                @Override
                protected void updateItem(Publication item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item != null)
                    {
                    HBox page = new HBox();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CellPublicationFXML.fxml"));
                    try {
                        page = loader.load();
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                    CellPublicationFXMLController ctrl = loader.getController(); 
                    System.out.println(item);
                    ctrl.setAllFields(item);
                    setGraphic(page); 
                    }
                }
            };
            return cell;
        });

        myTransport.setAll(sp.getAll());
        lv_publication.getItems().setAll(myTransport);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        t_image.setText("");
        t_description.setText("");
        t_titre.setText("");
        t_image.setText("");
    }

    @FXML
    private void clickMyPubs(ActionEvent event) {
        lv_publication.getItems().clear();  
        
        ObservableList<Publication> myTransport = FXCollections.observableArrayList();
        lv_publication.setCellFactory((ListView<Publication> param) -> {
            ListCell<Publication> cell = new ListCell<Publication>() {
                @Override
                protected void updateItem(Publication item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item != null)
                    {
                    HBox page = new HBox();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CellPublicationFXML.fxml"));
                    try {
                        page = loader.load();
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                    CellPublicationFXMLController ctrl = loader.getController(); 
                    System.out.println(item);
                    ctrl.setAllFields(item);
                    setGraphic(page); 
                    }
                }
            };
            return cell;
        });

        myTransport.setAll(sp.getMyPubs());
        lv_publication.getItems().setAll(myTransport);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        t_image.setText("");
        t_description.setText("");
        t_titre.setText("");
        t_image.setText("");
    }

    @FXML
    private void clickOtherPubs(ActionEvent event) {
        lv_publication.getItems().clear();  
        
        ObservableList<Publication> myTransport = FXCollections.observableArrayList();
        lv_publication.setCellFactory((ListView<Publication> param) -> {
            ListCell<Publication> cell = new ListCell<Publication>() {
                @Override
                protected void updateItem(Publication item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item != null)
                    {
                    HBox page = new HBox();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CellPublicationFXML.fxml"));
                    try {
                        page = loader.load();
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                    CellPublicationFXMLController ctrl = loader.getController(); 
                    System.out.println(item);
                    ctrl.setAllFields(item);
                    setGraphic(page); 
                    }
                }
            };
            return cell;
        });

        myTransport.setAll(sp.getOtherPubs());
        lv_publication.getItems().setAll(myTransport);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        t_image.setText("");
        t_description.setText("");
        t_titre.setText("");
        t_image.setText("");
    }

     @FXML
    private void showEvent(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("../../../edu/esprit/gui/Events.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    

    @FXML
    private void showBlog(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("../../../edu/esprit/gui/PublicationFXML.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void showCart(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("../../../edu/esprit/gui/AfficherPanier.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }


    @FXML
    private void showHome(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("../../../edu/esprit/gui/MetatechFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("ListProduitFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }
    
    @FXML
    private void showNews(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("../../../edu/esprit/gui/News.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

}
