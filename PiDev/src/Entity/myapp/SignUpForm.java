/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.Display;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.components.FloatingHint;
import com.codename1.components.SpanLabel;
import com.codename1.ui.TextField;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.services.ServiceUser;
import com.codename1.ui.Dialog;
import com.codename1.io.ConnectionRequest;
import com.codename1.ui.Component;
import com.codename1.ui.Stroke;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;



/**
 *
 * @author ihebx
 */
public class SignUpForm extends Form {

    public SignUpForm(Resources theme) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("LoginForm");
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
                TextField imageUser = new TextField("", "Image", 400, TextField.ANY);

        styleInput(username) ;
         styleInput(email) ;
          styleInput(password) ;
                    styleInput(imageUser) ;
      styleInput(confirmPassword) ;
          
        username.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        Button SignUp = new Button("SignUp","LoginButton");
        Button signIn = new Button("Sign In");
                          Label tof=   new Label(theme.getImage("logoapp.png"), "LogoLabel");

        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        Container content = BoxLayout.encloseY(
              new SpanLabel("Create New Customer Account", "editProfileLabels"),
                new FloatingHint(username),
                //createLineSeparator(),
                new FloatingHint(email),
                //createLineSeparator
                                new FloatingHint(imageUser),

                new FloatingHint(password),
               // createLineSeparator(),
                new FloatingHint(confirmPassword)
               // createLineSeparator()
        );
        content.setScrollableY(true);
      /*  add(BorderLayout.CENTER, BoxLayout.encloseYBottom(
                FlowLayout.encloseCenterBottom(content)
        ));*/
        add(BorderLayout.NORTH, BoxLayout.encloseY(
               content,
                SignUp,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
                
        ));
        SignUp.requestFocus();
        SignUp.addActionListener((e) -> {
        ServiceUser.getInstance().signup(username, password, email, imageUser,confirmPassword,  theme);
        Dialog.show("Welcome","Account is created!","OK",null);
        new LoginForm(theme).show();
        });

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
