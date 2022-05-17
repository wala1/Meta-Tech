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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.entities.publicity;
import edu.esprit.services.ServicePublicity;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * FXML Controller class
 *
 * @author mejri
 */
public class AjouterPublicityController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfDisc;
    @FXML
    private TextField tfImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouterPublicity(ActionEvent event) throws IOException, AddressException, MessagingException {

        StringBuilder errors = new StringBuilder();
        if (tfNom.getText().trim().isEmpty() || tfDesc.getText().length() < 8) {
            if (tfDesc.getText().trim().isEmpty()) {
                errors.append("*A Description is required \n");
            } else if (tfDesc.getText().length() < 8) {
                errors.append("*Your Description must contain at least 8 characters \n");
            }

            if (tfNom.getText().trim().isEmpty()) {
                errors.append("*An Name is required \n");
            } else if (tfNom.getText().length() < 2) {
                errors.append("*Your Name must contain at least 2 characters \n");
            }

        }
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setContentText(errors.toString());
            alert.showAndWait();

        } else {

            publicity p = new publicity(1, tfNom.getText(), tfDesc.getText(), Integer.parseInt(tfPrice.getText()), Integer.parseInt(tfDisc.getText()), tfImage.getText());
            ServicePublicity sp = new ServicePublicity();
            sp.ajouter(p);
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Publicity Added ! ", ButtonType.OK);
            a.show();

            //FXMLLoader loader  = new FXMLLoader(getClass().getResource("interfaceFront.fxml")) ; 
            //Parent root = loader.load();
            //tfNom.getScene().setRoot(root);
            //AfficherPublicityController apc = loader.getController();
            //apc.setNomPub(tfNom.getText());
            //apc.setDescPub(tfDesc.getText());
            Stage nouveauStage;
            Parent root = FXMLLoader.load(getClass().getResource("interfaceFront.fxml"));
            nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            nouveauStage.setScene(scene);

        }

// Create some properties and get the default Session;
        final String username = "mayssa.mejri@esprit.tn";
        final String password = "213JFT4045";

        Properties props = new Properties();

        props.put(
                "mail.smtp.auth", "true");
        props.put(
                "mail.smtp.starttls.enable", "true");
        props.put(
                "mail.smtp.host", "smtp.gmail.com");
        props.put(
                "mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        // Create a message.
        MimeMessage msg = new MimeMessage(session);

        // extracts the senders and adds them to the message.
        // Sender is a comma-separated list of e-mail addresses as per RFC822.
        {
            InternetAddress[] TheAddresses = InternetAddress.parse("mayssa.mejri@esprit.tn");
            msg.addFrom(TheAddresses);
        }

        // Extract the recipients and assign them to the message.
        // Recipient is a comma-separated list of e-mail addresses as per RFC822.
        {
            InternetAddress[] TheAddresses = InternetAddress.parse("mejrimeyssa14@gmail.com");
            msg.addRecipients(javax.mail.Message.RecipientType.TO, TheAddresses);
        }

        // Subject field
        msg.setSubject( "Publicity Added");

        // Create the Multipart to be added the parts to
        Multipart mp = new MimeMultipart();

        // Create and fill the first message part
        {
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText("New Publicity has been added");

            // Attach the part to the multipart;
            mp.addBodyPart(mbp);
        }
        String Attachments = null;

        // Attach the files to the message
        if (null != Attachments) {
            int StartIndex = 0, PosIndex = 0;
            while (-1 != (PosIndex = Attachments.indexOf("///", StartIndex))) {
                // Create and fill other message parts;
                MimeBodyPart mbp = new MimeBodyPart();
                FileDataSource fds
                        = new FileDataSource(Attachments.substring(StartIndex, PosIndex));
                mbp.setDataHandler(new DataHandler(fds));
                mbp.setFileName(fds.getName());
                mp.addBodyPart(mbp);
                PosIndex += 3;
                StartIndex = PosIndex;
            }
            // Last, or only, attachment file;
            if (StartIndex < Attachments.length()) {
                MimeBodyPart mbp = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(Attachments.substring(StartIndex));
                mbp.setDataHandler(new DataHandler(fds));
                mbp.setFileName(fds.getName());
                mp.addBodyPart(mbp);
            }
        }

        // Add the Multipart to the message
        msg.setContent(mp);

        // Set the Date: header
        msg.setSentDate(
                new Date());

        // Send the message;
        javax.mail.Transport.send(msg);
    }
   
    @FXML
    private void Print(MouseEvent event) throws FileNotFoundException, DocumentException, IOException{
        String file
                = "D:\blank.pdf";

     
        Document doc = new Document();
        try{
       PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\ihebx\\Desktop\\workshop1JAVAFinal\\workshop1JAVA\\Product.pdf"));

       // PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\mejri\\OneDrive\\Bureau\\Product.pdf"));
        doc.open();
       Image img=Image.getInstance("C:\\Users\\ihebx\\Desktop\\workshop1JAVAFinal\\workshop1JAVA\\workshop1JAVA\\src\\gui\\images\\logo1.png");
  //     C:\Users\ihebx\Desktop\workshop1JAVAFinal\workshop1JAVA
        doc.add(img);
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("List Of Product Coming Soon\n"));
        doc.add(new Paragraph("\n"));


        PdfPTable table=new PdfPTable(5);
        table.setWidthPercentage(80);
        
        PdfPCell cell;
        ///////////////////////////////////////////////////////////////////////
         
        cell = new PdfPCell(new Phrase("Name",FontFactory.getFont("Comic Sans Ms",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
       
        
        cell = new PdfPCell(new Phrase("Description",FontFactory.getFont("Comic Sans Ms",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
      
        
        cell = new PdfPCell(new Phrase("Discount",FontFactory.getFont("Comic Sans Ms",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        
        
          
        cell = new PdfPCell(new Phrase("Price",FontFactory.getFont("Comic Sans Ms",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase("Image",FontFactory.getFont("Comic Sans Ms",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        
        ////////////////////////////////////////////////////////////////////////
        
        cell = new PdfPCell(new Phrase(tfNom.getText().toString(),FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
       
        
        cell = new PdfPCell(new Phrase(tfDesc.getText().toString(),FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
      
        
        cell = new PdfPCell(new Phrase(tfDisc.getText().toString(),FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        
          
        cell = new PdfPCell(new Phrase(tfPrice.getText().toString(),FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase(tfImage.getText().toString(),FontFactory.getFont("Arial",11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        doc.add(table);
        
        
        
        
        
        doc.close();
         Desktop.getDesktop().open(new File("C:\\Users\\ihebx\\Desktop\\workshop1JAVAFinal\\workshop1JAVA\\Product.pdf"));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(DocumentException e){
            e.printStackTrace();
        }
    }

}
