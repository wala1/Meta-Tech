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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wala
 */
public class EventsController implements Initializable {

    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label fruitNameLable;
    @FXML
    private ImageView fruitImg;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    private List<Event> events = new ArrayList<>();
    private Event chosen;
    private Integer id;
    private Integer nMax;
    private Integer nPart;
    private String nom;
    private Stage stage;
    private Scene scene;
    String search = "";
    private MyListenerEvent myListener;
    @FXML
    private Label description;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label part;
    @FXML
    private Label max;
    @FXML
    private Label startAt;
    @FXML
    private Label endAt;
    @FXML
    private Label Npart;
    @FXML
    private Label maxN;
        private Parent fxml;
        
    @FXML
    private Button quit;
    String details1;
    @FXML
    private Button participate;

    /**
     * Initializes the controller class.
     */
    private void setChosenFruit(Event event) {
        fruitNameLable.setText(event.getTitle());
        ImageView emp0photo = new ImageView(new Image(event.getImageEvent()));
        fruitImg.setImage(emp0photo.getImage());
        description.setText(event.getDescription());
        start.setText(event.getDateFin());
        end.setText(event.getDateFin());
        part.setText(String.valueOf(event.getNbrPart()));
        max.setText(String.valueOf(event.getNbrPartMax()));
        
        chosenFruitCard.setStyle("-fx-background-color:  #fdb819;\n"
                + "    -fx-background-radius: 30;");
       id = event.getId() ;
       nMax=event.getNbrPartMax();
       nPart=event.getNbrPart();
       nom=event.getTitle();
       details1=event.getId()+"";
       System.out.println(event.getNbrPartMax());
        System.out.println("id="+id+"  max="+nMax+"  N part="+nPart+"  Nom="+nom);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load_data("");

    }

    void load_data(String search) {
        grid.getChildren().clear();
        events.clear();
        events.addAll(getData());

        /*if (search != "") {

            events.addAll(getData().stream().filter(e -> e.getTitle().contains(search)).collect(Collectors.toList()));

        }*/
        if (events.size() > 0) {

            setChosenFruit(events.get(0));
            myListener = new MyListenerEvent() {

                @Override
                public void onClickListener(Event event) {
                    setChosenFruit(event);
                }
            };
        }
        int column = 0;
        int  row = 1;
        try {
            for (int i = 0; i < events.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemEventFront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemEventFrontController itemController = fxmlLoader.getController();
                itemController.setData(events.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Event> getData() {

        ServiceEvent se = new ServiceEvent();
        List<Event> list = new ArrayList<>();
        list = se.getAll();
        return list;
    }

    @FXML
    private void OnParticipate(ActionEvent event) {
        ServiceEvent se= new ServiceEvent();
        System.out.println("id="+id+"  max="+nMax+"  N part="+nPart+"  Nom="+nom);
        
        if(nPart<nMax)
        {
                   // se.participerEvenement(id,154);
                    //    part.setText(String.valueOf(nPart+1));

                    /* Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("SUCCESS");
            alert.setContentText("You have successfully participated to \" "+nom+" 's\" Event ");
            alert.setTitle("Thank you for participating ");
            alert.showAndWait();*/
          
           // load_data("");
              Parent root;
       try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PayementEvent.fxml"));
                     root = (Parent)fxmlLoader.load();
                         PayementEventController scene2Controller = fxmlLoader.getController();
        scene2Controller.setId(id);
                     Stage stage=new Stage();
                     stage.setTitle("Add your own Event!");
                    stage.resizableProperty().setValue(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                    
                     
                   // fxml = loader.load();

                } catch (IOException ex) {
                   ex.printStackTrace();
                }
           
        }
         
        else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("You can't participate to this Event, it  has reached its maximum number of participants ");
            alert.setTitle("Participation Denied! ");
            alert.showAndWait();
        }
            
    }

    @FXML
    private void OnQuit(ActionEvent event) {
    }

   /* private void OnDetails(ActionEvent event) throws IOException {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsEvent.fxml"));	
        Parent root = loader.load(); 	

        DetailsEventController scene2Controller = loader.getController();
        scene2Controller.setId(id);
        

        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/

    
   

    @FXML
    private void OnProfile(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));

            fxml = loader.load();

        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }

    private void OnAdd(MouseEvent event) {
        Parent root;
        try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addEventUser.fxml"));
                     root = (Parent)fxmlLoader.load();
                     Stage stage=new Stage();
                     stage.setTitle("Add your own Event!");
                    stage.resizableProperty().setValue(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                    
                     
                   // fxml = loader.load();

                } catch (IOException ex) {
                   ex.printStackTrace();
                }
             
    }

    @FXML
    private void OnLogout(MouseEvent event) {
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
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxml);
                stage.setScene(scene);
                stage.show();
         
     }else System.out.println("cancel");
         
 });

    
    }

    @FXML
    private void OnProduct(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("ListProduitFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void OnBlog(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("PublicationFXML.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void OnCart(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("AfficherPanier.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

    @FXML
    private void OnHome(ActionEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("MetatechFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);
    }

}
