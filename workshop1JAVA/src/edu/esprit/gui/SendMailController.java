/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * FXML Controller class
 *
 * @author mejri
 */
public class SendMailController implements Initializable {

    @FXML
    private TextField tfTo;
    @FXML
    private TextField tfObj;
    @FXML
    private TextArea tfContent;
    @FXML
    private Button sendMail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void sendMAil(MouseEvent event) {
        try {
        final String username = "mayssa.mejri@esprit.tn";
        final String password = "213JFT4045";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

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
                InternetAddress[] TheAddresses = InternetAddress.parse(tfTo.getText());
                msg.addRecipients(javax.mail.Message.RecipientType.TO, TheAddresses);
            }

            // Subject field
            msg.setSubject(tfObj.getText());

            // Create the Multipart to be added the parts to
            Multipart mp = new MimeMultipart();

            // Create and fill the first message part
            {
                MimeBodyPart mbp = new MimeBodyPart();
                mbp.setText(tfContent.getText());

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
            msg.setSentDate(new Date());

            // Send the message;
            javax.mail.Transport.send(msg);
        } catch (MessagingException MsgException) {
            System.out.println("blows here");
            String ErrorMessage = MsgException.toString();
            Exception TheException = null;
            if ((TheException = MsgException.getNextException()) != null) {
                ErrorMessage += "\n" + TheException.toString();
            }
            int ErrorStatus = 1;

        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Send Mail");
        alert.setHeaderText("Results:");
        alert.setContentText("Mail Sent successfully!");
        alert.showAndWait();
    }
 @FXML
    private void back(MouseEvent event) throws IOException {
        Stage nouveauStage; // stage : view
        Parent root = FXMLLoader.load(getClass().getResource("interfaceFront.fxml"));  // PATH INTERFACE FXML // Parent : view+controller
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // remplace with new interface
        Scene scene = new Scene(root);
        nouveauStage.setScene(scene);

    }
}
