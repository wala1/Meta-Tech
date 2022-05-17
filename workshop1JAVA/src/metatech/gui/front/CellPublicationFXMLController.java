/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.gui.front;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import metatech.entities.Commentaire;
import metatech.entities.Publication;
import metatech.entities.PublicationCellCallBack;
import metatech.services.ServiceCommentaire;
import metatech.services.ServicePublication;
import metatech.services.ServiceUser;
import metatech.utils.AlertMaker;
import metatech.utils.BadWords;
import metatech.utils.TextToEmotion;

/**
 * FXML Controller class
 *
 * @author Abn
 */
public class CellPublicationFXMLController implements Initializable , PublicationCellCallBack {

    @FXML
    private ImageView iv_image;
    @FXML
    private Text t_text;
    @FXML
    private TextField t_comment;
    @FXML
    private ListView<Commentaire> listComment;
    @FXML
    private Text t_description;

    Publication p = new Publication();
    ServiceCommentaire sc = new ServiceCommentaire();
    ServiceUser serviceUser = new ServiceUser();
    ServicePublication sp = new ServicePublication();
    ObservableList<Commentaire> myTransport = FXCollections.observableArrayList();
    private Button btnDeletePub;
    private Button btnEditPub;
    @FXML
    private Text t_scroe;
    @FXML
    private Button btn_happiness;
    @FXML
    private Button btnShare;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
             listComment.setItems(myTransport);
             listComment.setCellFactory((param) -> {
                 ListCell<Commentaire> cell = new ListCell<Commentaire>() {
                     @Override
                     protected void updateItem(Commentaire item, boolean empty) {
                         super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                     
                         if(item != null  )
                        {
                            Button btnDelete = new Button("delete");
                            btnDelete.setOnAction((event) -> {
                            sc.supprimer(item.getId());
                            myTransport.setAll(sc.getByPubId(p.getId()));
                            listComment.setItems(myTransport);
                            });
                          Text page = new Text( item.getDescription() + " at "  + item.getTemps() + " by " + item.getUserId() );
                          // connected user
                          HBox hb = new HBox(page);
                          if(item.getUserId() == serviceUser.getConnected().getId())
                             hb.getChildren().add(btnDelete);
                           
                          setGraphic(hb);
                        }  
                   
                     
                     }   
                 };
                    return cell;
             });         
             
                
                  listComment.setItems(myTransport);
                  t_scroe.setText("");
                  String allComments = "" ;
         for(Commentaire c : listComment.getItems())
         {  
             allComments += c.getDescription();
         }
         if(!allComments.equals("") && allComments.length() > 10)
         {
             btn_happiness.setDisable(false);
         } else btn_happiness.setDisable(true);

    }    

    @FXML
    private void Comment(ActionEvent event) {
     
        if( ! t_comment.getText().equals(""))
        {
        Commentaire c = new Commentaire(0, BadWords.check(t_comment.getText()) , new Date(2022 , 1 , 1), p.getId(), 1);
        sc.ajouter(c);
        myTransport.setAll(sc.getByPubId(p.getId()));

        listComment.setItems(myTransport);
        AlertMaker.sendNotificationApi("Comment Added", "Your comment was successfully added!");
        } else 
        AlertMaker.sendNotificationApi("Comment can't be Added", "Your comment was not added! please fill the field !");

        
    }
    
     @Override
    public void setAllFields(Publication item) {
        System.out.println("dd"+item);
        t_description.setText(item.getDescription());
        t_text.setText("Created by " + item.getUserId()+ " in " + item.getTemps());
        System.out.println(getClass().getResourceAsStream("resources/" + item.getImage()));

        if(getClass().getResourceAsStream("resources/" + item.getImage()) != null)
        iv_image.setImage(new Image(getClass().getResourceAsStream("resources/" + item.getImage())));
        p = new Publication();
        p = item;
         myTransport.setAll(sc.getByPubId(item.getId()));
         String allComments = "" ;
         for(Commentaire c : listComment.getItems())
         {  
             allComments += c.getDescription();
         }
         if(!allComments.equals("") && allComments.length() > 10)
         {
             btn_happiness.setDisable(false);
         } else btn_happiness.setDisable(true);
         
         if(p.getUserId() != serviceUser.getConnected().getId())
             btnShare.setDisable(false);
         else
              btnShare.setDisable(true);
         
    }

    @FXML
    private void onClickHappiness(ActionEvent event) {
         String allComments = "" ;
         for(Commentaire c : listComment.getItems())
         {  
             allComments += c.getDescription();
         }
         if(!allComments.equals("") && allComments.length() > 10)
         t_scroe.setText( TextToEmotion.Check(allComments));
         else 
         t_scroe.setText("");
         
    }

    @FXML
    private void onClickShare(ActionEvent event) {
        sp.ajouter(p);
        AlertMaker.sendNotificationApi("Publication Shared", "Publication Shared Successfully");
        btnShare.setDisable(true);
    }
    
}
