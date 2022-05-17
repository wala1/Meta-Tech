/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.gui.back;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import metatech.entities.Publication;
import metatech.entities.PublicationCellCallBack;
import metatech.services.ServicePublication;
import metatech.utils.AlertMaker;

/**
 * FXML Controller class
 *
 * @author Abn
 */
public class CellPublicationFXMLController implements Initializable , PublicationCellCallBack{

    @FXML
    private ImageView iv_image;
    @FXML
    private Text t_text;
    @FXML
    private Text t_description;
    @FXML
    private TextField t_comment;
    @FXML
    private Text t_id;

    Publication p = new Publication();
    ServicePublication sp = new ServicePublication();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setAllFields(Publication publication) {
        t_description.setText(publication.getTitre());
        t_comment.setText(publication.getDescription());
        t_id.setText(String.valueOf(publication.getId()));
        if(getClass().getResourceAsStream("/metatech/gui/front/resources/" + publication.getImage()) != null)
        iv_image.setImage(new Image(getClass().getResourceAsStream("/metatech/gui/front/resources/" + publication.getImage())));
         p = new Publication();
        p = publication;
    }

    
    
}
