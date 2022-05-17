package edu.esprit.gui;

import edu.esprit.entities.Command;
import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommand;
import edu.esprit.services.ServicePanier;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class FormCommandController implements Initializable{

    Command cmd=new Command();
    @FXML
    private TextField firstName;
    
    public void setFirstName(String firstName){
        this.firstName.setText(firstName);
    }
    
    @FXML
    private TextField company;

    @FXML
    private TextField zip;

    @FXML
    private TextField lastName;

    @FXML
    private TextField streetAdress;

    @FXML
    private TextField city;

    @FXML
    private TextArea deliveryComment;

    @FXML
    private DatePicker date;
    
    @FXML
    private RadioButton check;

    @FXML
    private RadioButton bank;

    @FXML
    private RadioButton cash;

    @FXML
    private ComboBox country;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TableColumn image;

    @FXML
    private TableColumn nameQty;

    @FXML
    private TableColumn subTotal;

    @FXML
    private ComboBox devise;

    @FXML
    private CheckBox newsletter;

    @FXML
    private TextArea orderComment;

    @FXML
    private TextField codeCoup;

    @FXML
    private Text cartSubTotal;

    @FXML
    private Text orderTotal;

    @FXML
    private Label labelCountry;
    
    @FXML
    private Label labelPayment;
    
    @FXML
    private Label labelNews;
    
    @FXML
    private Label labelDate;
    
    @FXML
    private TableView table;
    
    @FXML
    private Label id;
    
    @FXML
    private Text sizeCart;
    
    
    
    public void setLastName(String lastName){
        this.lastName.setText(lastName);
    }
            
    public void setStreetAdress(String StreetAdress){
        this.streetAdress.setText(StreetAdress);
    }
    
    public void setCity(String city){
        this.city.setText(city);
    }
            
    public void setZipPostal(String zip){
        this.zip.setText(zip);
    }
                  
    public void setCountry(String country){
        labelCountry.setText(country);
        switch(country){
            case "Tunis": this.country.getSelectionModel().select(1);break;
            case "Maroco": this.country.getSelectionModel().select(2);break;
            case "Algeria": this.country.getSelectionModel().select(3);break;
            case "Egypt": this.country.getSelectionModel().select(4);break;
            default: ;
        }
    }
    
    public void setCompany(String company){
        this.company.setText(company);
        
    }
            
    public void setPhone(String phone){
        this.phoneNumber.setText(phone);
    }
           
    public void setPaymentMethod(String payment){
        if(payment.equals("check")){
            labelPayment.setText("check");
            this.check.isDisable();
        }else if(payment.equals("bank")){
                labelPayment.setText("bank");
                this.check.isDisable();
        }else if(payment.equals("cash")){
                labelPayment.setText("cash");
                this.check.isDisable();
        }
    }
         
    public void setDate(String date){
        labelDate.setText(date);
    }     
    
    public void setDeliveryComment(String delivery){
        deliveryComment.setText(delivery);
    }
    
    public void setOrderComment(String order){
        orderComment.setText(order);
    }
    
    public void setCodeCoupon(String code){
        codeCoup.setText(code);
    }
    
    public void setNewsLetter(String news){
        newsletter.setText(null);
        if(newsletter.equals("1")){
            labelNews.setText("1");
            newsletter.selectedProperty();
        }
    }
    
    public void setId(String id){
        this.id.setText(id);
    }
    
    @FXML
    void onSelectCountry(ActionEvent event) {
        String s = country.getSelectionModel().getSelectedItem().toString();
        labelCountry.setText(s);
    }

    @FXML
    void onSelectDevise(ActionEvent event) {

    }
    
    @FXML
    void onSelectPayment(ActionEvent event) {
        if(check.isSelected()){
            labelPayment.setText(check.getText());
            labelPayment.setText("check");
        }else if(bank.isSelected()){
                labelPayment.setText(bank.getText());
                labelPayment.setText("bank");
        }else if(cash.isSelected()){
                labelPayment.setText(cash.getText());
                labelPayment.setText("cash");
        }
    }
    
    @FXML
    void onNews(ActionEvent event) {
        labelNews.setText(null);
        if(newsletter.isSelected()){
            labelNews.setText("1");
        }
    }
    
    @FXML
    void onDate(ActionEvent event) {
        LocalDate myDate = date.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        labelDate.setText(myFormattedDate);
    }

    @FXML
    private ListView item;

    @FXML
    private ListView name;

    @FXML
    private ListView price;

    @FXML
    private ListView quantite;
    
    ServicePanier sp = new ServicePanier();
    ObservableList<Panier> PanierList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for(int j=0;j<10;j++){
            list.add(j+"");
        }
        list2.add(" ");
        list2.add("@");
        list2.add("!");
        list2.add("?");
        list2.add("#");
        list2.add("-");
        list2.add("_");
        list2.add("=");
        list2.add("^");
        list2.add("\\");
        list2.add("`");
        list2.add("|");
        list2.add("[");
        list2.add("]");
        list2.add("~");
        
        ObservableList<String> countryList = FXCollections.observableArrayList("Tunis","Maroco","Algeria","Egypt");
        country.setItems(countryList);
        ObservableList<String> deviseList = FXCollections.observableArrayList("DT","Euro","Dollar");
        devise.setItems(deviseList);
        
        List<Panier> list = new ArrayList<>();
        list = sp.getPanier(104);
        sizeCart.setText(""+list.size());
        double subtott=0;
        for (Panier p : list) {
            String s = p.getProduit().getImageProd();
            ImageView emp0photo = new ImageView(new Image(s));
            emp0photo.setFitHeight(60);
            emp0photo.setFitWidth(60);
            p.setImg(emp0photo);
            System.out.println(p.getProduit());
            item.getItems().add(emp0photo);
            name.getItems().add(p.getProduit().getNomProd());
            price.getItems().add(p.getProduit().getPrixProd());
            quantite.getItems().add(p.getQuantite());
            //subTotal.setText(p.getProduit().getPrixProd()*p.getQuantite());
            subtott+=p.getProduit().getPrixProd()*p.getQuantite();
        }
        cartSubTotal.setText(""+subtott);
        orderTotal.setText(""+subtott*5.00);
    } 
    
    
    
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> list2 = new ArrayList<>();
    
    @FXML
    void placeOrder(ActionEvent event) throws IOException {
        
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "You can't added order without values", ButtonType.OK);
            a.showAndWait();
        } else {
            String x;
            boolean b=false;
            boolean b2=false;
            String s;
            s= firstName.getText();
            for(int i=0; i<s.length();i++){
                x = s.charAt(i)+"";
                if(list.contains(x)){
                    b=true;
                }
                if(list2.contains(x)){
                    b2=true;
                }
            }
            if(b && b2){
                Alert a = new Alert(Alert.AlertType.ERROR, "First Name can't be a number, Symbol or space.\n it can be only a characters!", ButtonType.OK);
                a.showAndWait();
            }else{ 
                if(b2){
                    Alert a = new Alert(Alert.AlertType.ERROR, "First Name can't be a Symbol. \n it can be only a characters!", ButtonType.OK);
                    a.showAndWait();
                }else{ 
                    if(b){
                        Alert a = new Alert(Alert.AlertType.ERROR, "First Name can't be a number. \n it can be only a characters!", ButtonType.OK);
                        a.showAndWait();
                    }
                }
            }

            x="";
            b=false;
            b2=false;
            s= lastName.getText();
            for(int i=0; i<s.length();i++){
                x = s.charAt(i)+"";
                if(list.contains(x)){
                    b=true;
                }
                if(list2.contains(x)){
                    b2=true;
                }
            }
            if(b && b2){
                Alert a = new Alert(Alert.AlertType.ERROR, "Last Name can't be a number, Symbol or space.\n it can be only a characters!", ButtonType.OK);
                a.showAndWait();
            }else{ 
                if(b2){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Last Name can't be a Symbol. \n it can be only a characters!", ButtonType.OK);
                    a.showAndWait();
                }else{ 
                    if(b){
                        Alert a = new Alert(Alert.AlertType.ERROR, "Last Name can't be a number. \n it can be only a characters!", ButtonType.OK);
                        a.showAndWait();
                    }
                }
            }

            x="";
            b2=false;
            s= streetAdress.getText();
            for(int i=0; i<s.length();i++){
                x = s.charAt(i)+"";

                if(list2.contains(x)){
                    b2=true;
                }
            } 
            if(b2){
                Alert a = new Alert(Alert.AlertType.ERROR, "Street Adress can't be a Symbol. \n it can be only a characters with number and space!", ButtonType.OK);
                a.showAndWait();
            }

            x="";
            b=false;
            b2=false;
            s= city.getText();
            for(int i=0; i<s.length();i++){
                x = s.charAt(i)+"";
                if(list.contains(x)){
                    b=true;
                }
                if(list2.contains(x)){
                    b2=true;
                }
            }
            if(b && b2){
                Alert a = new Alert(Alert.AlertType.ERROR, "City can't be a number, Symbol or space.\n it can be only a characters!", ButtonType.OK);
                a.showAndWait();
            }else{ 
                if(b2){
                    Alert a = new Alert(Alert.AlertType.ERROR, "City can't be a Symbol. \n it can be only a characters!", ButtonType.OK);
                    a.showAndWait();
                }else{ 
                    if(b){
                        Alert a = new Alert(Alert.AlertType.ERROR, "City can't be a number. \n it can be only a characters!", ButtonType.OK);
                        a.showAndWait();
                    }
                }
            }
        
        
        
      
            ServiceCommand scmd = new ServiceCommand();
            User user = new User(104,"oumaima");
            /*
            double subtot = Double.parseDouble(orderTotal.getText());
            */

            int zipPostal = Integer.parseInt(zip.getText());
            long phone = Long.parseLong(phoneNumber.getText());
            int codeCoupon = Integer.parseInt(codeCoup.getText());
            int idCmd = Integer.parseInt(id.getText());
            Command command=new Command(idCmd,firstName.getText(), lastName.getText(), streetAdress.getText(), city.getText(), zipPostal, labelCountry.getText(),company.getText(), phone, labelPayment.getText(), labelNews.getText(),labelDate.getText(), deliveryComment.getText(), orderComment.getText(), "en cours", codeCoupon, 2.5658, user);
            System.out.println("command : "+command.toString());

            if(command.getId() != 0){
                scmd.modifier(command);
            }else{
                scmd.ajouter(command);
            }
        }
        
        
           
        
    }

}
