/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.entities.SousCategorie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCategorie;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceSousCategorie;
import java.awt.Desktop;
import java.awt.Graphics;
//import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
    import javafx.scene.image.Image ;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
 


/**
 * FXML Controller class
 *
 * @author Abdelaziz
 */
public class AfficherProduitBackController implements Initializable {

    @FXML
    private TableView<Produit> productsTable;
    @FXML
    private TableColumn<Produit, String> imageCol;
    @FXML
    private TableColumn<Produit, String> nameCol;
    @FXML
    private TableColumn<Produit, String> priceCol;
    @FXML
    private TableColumn<Produit, String> discountCol;
    private TableColumn<Produit, String> editCol;
    
    ObservableList<Produit> ProductList = FXCollections.observableArrayList() ; 
    ObservableList<String> options = FXCollections.observableArrayList() ; 
    ObservableList<String> options1 = FXCollections.observableArrayList() ; 
    
    private Parent fxml;
    private Stage stage;
    private Scene scene;
    
    @FXML
    private TextField tfName;
    @FXML
    private TextArea tfDesc;
    @FXML
    private TextField tfImage;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfDiscount;
    @FXML
    private TextField tfStock;
    @FXML
    private TextField tfId;
    @FXML
    private ComboBox<String> tfComboBox;
    @FXML
    private ComboBox<String> tfComboBox1;
    @FXML
    private ImageView logo;
    @FXML
    private TextField searchInput;
    @FXML
    private ListView<String> listName;
    @FXML
    private ListView<Float> listPrice;
    @FXML
    private ListView<Float> listDiscount;
    @FXML
    private ListView<ImageView> listImage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServiceCategorie sp = new ServiceCategorie();  
        List<Categorie> list = new ArrayList<>();
        list = sp.getAll();
        for (Categorie c : list) {
            options.add(c.getNomCategorie());  
        }
        
        ServiceSousCategorie sp1 = new ServiceSousCategorie();  
        List<SousCategorie> list1 = new ArrayList<>();
        list1 = sp1.getAll();
        for (SousCategorie s : list1) {
            options1.add(s.getNomSousCategorie());  
        }
        
        tfComboBox.setItems(options);
        tfComboBox1.setItems(options1);

        loadData() ; 
    }    

    private void loadData() {
        ServiceProduit sp = new ServiceProduit();  
        List<Produit> list = new ArrayList<>();
        list = sp.getAll();
        for (Produit p : list) {
            ImageView emp0photo = new ImageView(new Image(p.getImageProd())  ) ; 
            emp0photo.setFitHeight(100);
            emp0photo.setFitWidth(100);
            p.setImg(emp0photo) ; 
             
            ProductList.add(p) ; 
            imageCol.setCellValueFactory(new PropertyValueFactory<>("img")) ; 
            nameCol.setCellValueFactory(new PropertyValueFactory<>("nomProd")) ; 
            priceCol.setCellValueFactory(new PropertyValueFactory<>("prixProd")) ; 
            discountCol.setCellValueFactory(new PropertyValueFactory<>("promoProd")) ; 
            
            
            listName.getItems().add(p.getNomProd()) ; 
            listImage.getItems().add(emp0photo) ; 
            listPrice.getItems().add(p.getPrixProd()) ; 
            listDiscount.getItems().add(p.getPromoProd()) ; 
 
        }
        
        
        //imageCol.setPrefWidth(80) ; 
        
        
        
        
        
        productsTable.setItems(ProductList) ; 
        
    }

    private void showCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    private void showSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void addProduct(ActionEvent event) throws IOException {
        
        boolean resultPrice = (tfPrice.getText()).matches("[0-9]+.*[0-9]+");
        boolean resultDiscount = (tfDiscount.getText()).matches("[0-9]+.*[0-9]+");
        boolean resultStock = (tfStock.getText()).matches("[0-9]+");
         
         if (tfName.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Name can't be empty.", ButtonType.OK);
            a.showAndWait();
        } else if (tfImage.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Image can't be empty.", ButtonType.OK);
            a.showAndWait();
            
            
            
            
        } else if ((tfPrice.getText().isEmpty()) ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter a valid price.", ButtonType.OK);
            a.showAndWait();
        } else if (!resultPrice) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Price can't be neither a string, nor a negative value.", ButtonType.OK);
            a.showAndWait();
        } else if (Float.parseFloat(tfPrice.getText()) <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Price should have a positive value.", ButtonType.OK);
            a.showAndWait();
            
            
            
            
            
        } else if ((tfDiscount.getText().isEmpty())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter a valid discount.", ButtonType.OK);
            a.showAndWait();
        } else if (!resultDiscount) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Discount Price can't be neither a string, nor a negative value.", ButtonType.OK);
            a.showAndWait();
        } else if (Float.parseFloat(tfDiscount.getText()) <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Discount should have a positive value.", ButtonType.OK);
            a.showAndWait();
            
        } else if (tfStock.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter a valid stock.", ButtonType.OK);
            a.showAndWait();
        } else if (!resultStock) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Stock can't be a string .", ButtonType.OK);
            a.showAndWait();
        } else if (Integer.parseInt(tfStock.getText()) < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Stock should have a positive value.", ButtonType.OK);
            a.showAndWait();
            
        } else {
             
            
            ServiceCategorie cp = new ServiceCategorie();
            Categorie c = cp.getByName(tfComboBox.getValue()); 
            
            ServiceSousCategorie csp = new ServiceSousCategorie();
            SousCategorie s = csp.getByName(tfComboBox1.getValue()); 
            
            
            
            ServiceProduit sp = new ServiceProduit();
            
            Produit p = new Produit(1, tfName.getText(), tfDesc.getText(), tfImage.getText(), Float.parseFloat(tfPrice.getText()) , Float.parseFloat(tfDiscount.getText()), Integer.parseInt(tfStock.getText()), c , s ,"0");
            //Produit p1 = new Produit(1,"r","r", "https://cdn.futura-sciences.com/buildsv6/images/mediumoriginal/6/5/2/652a7adb1b_98148_01-intro-773.jpg", 35 , 25, 22, c , s ,"0");
            sp.ajouter(p); 
            Alert b = new Alert(Alert.AlertType.INFORMATION, "Product added !", ButtonType.OK);
            b.showAndWait(); 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
        }
    }

    @FXML
    private void editProduct(ActionEvent event) throws IOException {
        boolean resultPrice = (tfPrice.getText()).matches("[0-9]+.*[0-9]+");
        boolean resultDiscount = (tfDiscount.getText()).matches("[0-9]+.*[0-9]+");
        boolean resultStock = (tfStock.getText()).matches("[0-9]+");
        
        if (tfId.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Select a product to update.", ButtonType.OK);
            a.showAndWait();
        } else if (tfName.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Name can't be empty.", ButtonType.OK);
            a.showAndWait();
        } else if (tfImage.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Image can't be empty.", ButtonType.OK);
            a.showAndWait();
            
        } else if ((tfPrice.getText().isEmpty()) ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter a valid price.", ButtonType.OK);
            a.showAndWait();
        } else if (!resultPrice) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Price can't be neither a string, nor a negative value.", ButtonType.OK);
            a.showAndWait();
        } else if (Float.parseFloat(tfPrice.getText()) <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Price should have a positive value.", ButtonType.OK);
            a.showAndWait();
            
        } else if ((tfDiscount.getText().isEmpty())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter a valid discount.", ButtonType.OK);
            a.showAndWait();
        } else if (!resultDiscount) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Discount Price can't be neither a string, nor a negative value.", ButtonType.OK);
            a.showAndWait();
        } else if (Float.parseFloat(tfDiscount.getText()) <= 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Discount should have a positive value.", ButtonType.OK);
            a.showAndWait();
            
        } else if (tfStock.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter a valid stock.", ButtonType.OK);
            a.showAndWait();
        } else if (!resultStock) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Stock can't be a string .", ButtonType.OK);
            a.showAndWait();
        } else if (Integer.parseInt(tfStock.getText()) < 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Stock should have a positive value.", ButtonType.OK);
            a.showAndWait();
            
        } else {
            
            ServiceCategorie cp = new ServiceCategorie();
            Categorie c = cp.getByName(tfComboBox.getValue()); 
            
            ServiceSousCategorie csp = new ServiceSousCategorie();
            SousCategorie s = csp.getByName(tfComboBox1.getValue());
            
            ServiceProduit sp = new ServiceProduit();
            
            Produit p = new Produit(Integer.parseInt(tfId.getText()) , tfName.getText(), tfDesc.getText(), tfImage.getText(), Float.parseFloat(tfPrice.getText()) , Float.parseFloat(tfDiscount.getText()), Integer.parseInt(tfStock.getText()), c , s ,"0");
           
            sp.modifier(p);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Product updated !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
            tfId.setText("") ;
        }
    }


    @FXML
    private void deleteButton(ActionEvent event) throws IOException {
        if (tfId.getText().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a product to delete.", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceProduit sp = new ServiceProduit(); 
            sp.supprimer(Integer.parseInt(tfId.getText()));
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Product deleted !", ButtonType.OK);
            a.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherProduitBack.fxml"));
            Parent root = loader.load();
            tfName.getScene().setRoot(root);
            tfId.setText("") ;
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Produit p = productsTable.getSelectionModel().getSelectedItem() ; 
        tfName.setText(p.getNomProd()) ;
        tfDesc.setText(p.getDescProd()) ; 
        tfImage.setText(p.getImageProd()) ;
        tfPrice.setText(p.getPrixProd()+"") ; 
        tfDiscount.setText(p.getPromoProd()+"") ;
        tfStock.setText(p.getStockProd()+"") ; 
        tfComboBox.setValue((p.getCategorieProd()).getNomCategorie());
        tfComboBox1.setValue((p.getSousCategorieProd()).getNomSousCategorie());
        tfId.setText(p.getId()+"") ;
    }

    @FXML
    private void goToCategories(ActionEvent event) throws IOException{
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCategorie.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void goToSubCategories(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSubCategories.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    private void goToFront(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showPublicity(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfaceFront.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProduitFront.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showBlog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../metatech/gui/back/PublicationFXML.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
    }

    @FXML
    private void showCart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("flk.fxml")) ; 
        Parent root = loader.load() ; 
        productsTable.getScene().setRoot(root);
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
         try {
            fxml = FXMLLoader.load(getClass().getResource("EventsAdmin.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxml);
        stage.setScene(scene);
        stage.show();
    }
    
    
     

    @FXML
    private void downloadPDF(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
        Document doc = new Document();
        
        PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\ihebx\\Desktop\\workshop1JAVAFinal\\workshop1JAVA\\Productlist.pdf")) ; 
        doc.open() ;
        
        ServiceProduit sp = new ServiceProduit();  
        List<Produit> list = new ArrayList<>();
        list = sp.getAll();
        
        float [] pointColumnWidths = {100F, 100F, 100f,100F};   
        PdfPTable table = new PdfPTable(4);   
        
        
        Font red = new Font(FontFamily.HELVETICA, 24, Font.NORMAL, BaseColor.ORANGE);
        Chunk redText = new Chunk("Products List ", red);
        Paragraph c = new Paragraph(redText) ;
        c.setAlignment(Element.ALIGN_CENTER);
        doc.add(c) ; 
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        Paragraph t = new Paragraph(dtf.format(now)) ;
        
        t.setAlignment(Element.ALIGN_CENTER);
        doc.add(t) ; 
        doc.add(new Paragraph(" ")) ; 

        // Adding cells to the table  
        Font orange = new Font(FontFamily.HELVETICA, 16, Font.NORMAL, BaseColor.ORANGE);
        Chunk c1 = new Chunk("Nom", orange);
        Chunk c2 = new Chunk("Quantity", orange);
        Chunk c3 = new Chunk("Price", orange);
        Chunk c4 = new Chunk("Dsicount", orange);

        table.addCell(new PdfPCell(new Paragraph(c1)));       
        table.addCell(new PdfPCell(new Paragraph(c2)));       
        table.addCell(new PdfPCell(new Paragraph(c3)));
        table.addCell(new PdfPCell(new Paragraph(c4)));
        for (Produit p:list) {
            table.addCell(new PdfPCell(new Paragraph(p.getNomProd())));  
            table.addCell(new PdfPCell(new Paragraph(p.getStockProd()+"")));
            if (p.getCategorieProd().getNomCategorie().equals("Crypto")) {
                table.addCell(new PdfPCell(new Paragraph(p.getPrixProd()+" ETH"))); 
                table.addCell(new PdfPCell(new Paragraph(p.getPromoProd()+" ETH"))); 
            } else {
                table.addCell(new PdfPCell(new Paragraph(p.getPrixProd()+" DT"))); 
                table.addCell(new PdfPCell(new Paragraph(p.getPromoProd()+" DT"))); 
            } 
            
        }
        doc.add(table) ; 
      
       
        
         
        doc.close() ; 
        Desktop.getDesktop().open(new File("C:\\Users\\ihebx\\Desktop\\workshop1JAVAFinal\\workshop1JAVA\\Productlist.pdf")) ; 

    }

    @FXML
    private void searchMethod(KeyEvent event) {
        ProductList.clear();
        ServiceProduit sp = new ServiceProduit();  
        List<Produit> list = new ArrayList<>();
        list = sp.getBySearch(searchInput.getText());
        for (Produit p : list) {
            ImageView emp0photo = new ImageView(new Image(p.getImageProd())  ) ; 
            emp0photo.setFitHeight(100);
            emp0photo.setFitWidth(100);
            p.setImg(emp0photo) ; 
             
            ProductList.add(p) ; 
            imageCol.setCellValueFactory(new PropertyValueFactory<>("img")) ; 
            nameCol.setCellValueFactory(new PropertyValueFactory<>("nomProd")) ; 
            priceCol.setCellValueFactory(new PropertyValueFactory<>("prixProd")) ; 
            discountCol.setCellValueFactory(new PropertyValueFactory<>("promoProd")) ; 
             
        }
          
        
        
        
        
        
        productsTable.setItems(ProductList) ; 
    }

    
}
