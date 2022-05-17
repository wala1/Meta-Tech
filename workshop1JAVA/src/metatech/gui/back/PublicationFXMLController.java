/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.gui.back;

import edu.esprit.entities.User;
import edu.esprit.gui.UsersController;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import metatech.entities.Publication;
import metatech.entities.PublicationCallBack;
import metatech.gui.back.CellPublicationFXMLController;
import metatech.services.ServicePublication;
import metatech.utils.AlertMaker;

/**
 * FXML Controller class
 *
 * @author Abn
 */
public class PublicationFXMLController implements Initializable , PublicationCallBack {

    @FXML
    private ListView<Publication> tbPublication;

    private ServicePublication sp;

    ObservableList<Publication>  publicationList = FXCollections.observableArrayList();
    
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    
    int counter = 0 ;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField t_search;
    @FXML
    private ImageView logo;
    public PublicationFXMLController() {
        sp = new ServicePublication();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        publicationList.setAll(sp.getAll());
        
        updateData();
        btnDelete.setDisable(true);
    }

     

     

    @FXML
    private void onSearchKeyPressed(KeyEvent event) {
        
             tbPublication.setCellFactory((ListView<Publication> param) -> {
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
        
        if(!t_search.getText().equals(""))
            publicationList.setAll(sp.searshAll(t_search.getText()));
        else 
             publicationList.setAll(sp.getAll());
        
                
    }

    @Override
    public void refreshList() {
            updateData();
    }

    @FXML
    private void onClickDelete(ActionEvent event) {
            if(tbPublication.getSelectionModel().getSelectedItem() != null)
                {
                    sp.supprimer(tbPublication.getSelectionModel().getSelectedItem().getId());
                    btnDelete.setDisable(true);
                    updateData();
                    AlertMaker.sendNotificationApi("Publication Deleted", "Publication deleted Successfully !");
                
                }
                     btnDelete.setDisable(true);

    }

    @FXML
    private void onClickOrderById(ActionEvent event) {
        counter ++ ;
        counter = counter % 2 ;
        if(counter == 0)
        publicationList.setAll(sp.getAllSorted("id", "DESC"));
        else 
        publicationList.setAll(sp.getAllSorted("id", "ASC"));

        tbPublication.setItems(publicationList);
                 btnDelete.setDisable(true);

    }

    @FXML
    private void onClickOrderByTitle(ActionEvent event) {
        counter ++ ;
        counter = counter % 2 ;
        if(counter == 0)
         publicationList.setAll(sp.getAllSorted("titre_publ", "DESC"));
        else 
         publicationList.setAll(sp.getAllSorted("titre_publ", "ASC"));
        tbPublication.setItems(publicationList);
                 btnDelete.setDisable(true);

    }

    @FXML
    private void onClickOrderByDate(ActionEvent event) {
         counter ++ ;
        counter = counter % 2 ;
        if(counter == 0)
        publicationList.setAll(sp.getAllSorted("temps_publ", "DESC"));
        else 
        publicationList.setAll(sp.getAllSorted("temps_publ", "ASC"));

        tbPublication.setItems(publicationList);
                 btnDelete.setDisable(true);

    }

    @FXML
    private void onClickOrderByDescription(ActionEvent event) {
         counter ++ ;
        counter = counter % 2 ;
        if(counter == 0)
        publicationList.setAll(sp.getAllSorted("description_publ", "DESC"));
        else
        publicationList.setAll(sp.getAllSorted("description_publ", "ASC"));

        tbPublication.setItems(publicationList);
                 btnDelete.setDisable(true);

    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
            btnDelete.setDisable(true);
            if(tbPublication.getSelectionModel().getSelectedItem() != null)
                {
                    
            btnDelete.setDisable(false);
                }
    }
    
    private void updateData()
    {
        tbPublication.setCellFactory((ListView<Publication> param) -> {
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
            //Set up the ImageView
          
       
            //Set up the ImageView
            final ImageView imageview = new ImageView();

            imageview.setFitHeight(100);
            imageview.setFitWidth(100);
            //Set up the Table
            TableCell<Publication, String> cell = new TableCell<Publication, String>() {
                public void updateItem(String item, boolean empty) {
                    if (item != null && !item.isEmpty()) {
                        System.out.println(getClass().getResourceAsStream("resources/" + item));

                if(getClass().getResourceAsStream("resources/" + item) != null)
                 imageview.setImage(new Image(getClass().getResourceAsStream("resources/" + item)));
              //          imageview.setImage(new Image(item));
                        setGraphic(imageview);
                    } 
                }
            };
           
      
        publicationList.setAll(sp.getAll());
        tbPublication.setItems(publicationList);
    }

    @FXML
    private void goToCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    @FXML
    private void goToSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    private void goToFront(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    @FXML
    private void showDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    @FXML
    private void showUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/users.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    @FXML
    private void showPublicity(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/InterfaceFront.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    @FXML
    private void showBlog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../metatech/gui/back/PublicationFXML.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }

    @FXML
    private void showCart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/flk.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
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

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/connect.fxml"));

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../edu/esprit/gui/EventAdmin.fxml")) ; 
        Parent root = loader.load() ; 
        tbPublication.getScene().setRoot(root);
    }
    

}
