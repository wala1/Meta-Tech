/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceUser;
import com.sun.mail.smtp.SMTPTransport;
import static java.lang.Compiler.command;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ihebx
 */
public class ForgotPassForm extends Form {

    TextField email;

    public ForgotPassForm(Resources res) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("IMGLogin");
           setUIID("LoginForm");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        // setUIID("Activate");
        /*add(BorderLayout.NORTH,
                BoxLayout.encloseXCenter(
                        // new Label(res.getImage("duke.png"), "LogoLabel"),
                        new Label("Find Your Account", "LogoLabel")
                )
        );*/
        email = new TextField("", "Enter your email address", 20, TextField.EMAILADDR);
        styleInput(email);
                          Label tof=   new Label(res.getImage("logoapp.png"), "LogoLabel");

        email.setSingleLineTextArea(false);
        Button Submit = new Button("Submit","LoginButton");
        Label haveAnAccount = new Label("connect?");
        Button signIn = new Button("return");
        //Button signIn=new Button("Renouveler votre mot de passe");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("CenterLink");
        Container content = BoxLayout.encloseY(
                new SpanLabel("Please enter your email or mobile number to search for your account", "CenterLabel"),
                new FloatingHint(email),
                // add(createLineSeparator()),
                Submit,
                FlowLayout.encloseCenter(haveAnAccount,signIn)
                
                
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        Submit.requestFocus();
        Submit.addActionListener(e -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDialog = ip.showInfiniteBlocking();

            sendMail(res);

            ipDialog.dispose();
            Dialog.show("Reset Password", "Check your inbox to reset our password", "OK", null);
            new LoginForm(res).show();
            refreshTheme();

        });

    }

    //sendMail
    public void sendMail(Resources res) {
        try {

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp"); //SMTP protocol
            props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtps.auth", "true"); //enable authentication

            Session session = Session.getInstance(props, null); // aleh 9rahach 5ater mazlna masabinach javax.mail .jar

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress("Reintialisation mot de passe <monEmail@domaine.com>"));
            msg.setRecipients(Message.RecipientType.TO, email.getText().toString());
            msg.setSubject("Application nom  : Confirmation du ");
            msg.setSentDate(new Date(System.currentTimeMillis()));

            String mp = ServiceUser.getInstance().getPasswordByEmail(email.getText().toString(), res);//mp taw narj3lo
            String txt = "Welcometo MetaTech: This is your new Password : " + mp + " tape it and then click on button Submit";
            ;

            msg.setText(txt);

            SMTPTransport st = (SMTPTransport) session.getTransport("smtps");

            st.connect("smtp.gmail.com", 465, "zied.mathlouthi@esprit.tn", "213JMT5356");

            st.sendMessage(msg, msg.getAllRecipients());

            System.out.println("server response : " + st.getLastServerResponse());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void styleInput(TextField name) {
        Style loginStyle = name.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        loginStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        loginStyle.setBgColor(0xffffff);
        loginStyle.setBgTransparency(100);
        loginStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            loginStyle.setMargin(Component.BOTTOM, 3); 
        loginStyle.setFgColor(0x464646) ;
    }

}
