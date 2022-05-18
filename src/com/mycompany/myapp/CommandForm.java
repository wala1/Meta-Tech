/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.Properties;
import com.codename1.messaging.Message;
import com.codename1.share.EmailShare;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.Form;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.services.ServiceCommande;
import com.mycompany.myapp.services.ServicePanier;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Logidee
 */
public class CommandForm extends Form{
    public CommandForm(Form previous,double subtotal, Resources res) {
        super(BoxLayout.y());
             
        
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Container titleCmp = BoxLayout.encloseY(
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Pass Your Command", "Title") 
                                )
                            )
                );
        tb.setTitleComponent(titleCmp);
                        
        
        Form homeForm = new Form(BoxLayout.y());
        this.getToolbar().addCommandToOverflowMenu("Refresh", null, (evt) -> {
            new CommandForm(previous,subtotal, res).show();
        });
        
        Label title = new Label("","Title");
        add(title);
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
        
            TextField firstName = new TextField("", "", 16, TextArea.ANY); 
            styleInput(firstName) ;
            
            TextField lastName = new TextField("", "", 16, TextArea.ANY); 
            styleInput(lastName) ;
            
            TextField street_Adress = new TextField("", "", 16, TextArea.ANY); 
            styleInput(street_Adress) ; 
            
            TextField city = new TextField("", "", 16, TextArea.ANY); 
            styleInput(city) ; 
            
            TextField zipPostalCode = new TextField("", "", 16, TextArea.NUMERIC); 
            styleInput(zipPostalCode) ;
            
            TextField country = new TextField("", "", 16, TextArea.ANY); 
            styleInput(country) ;
            
            TextField company = new TextField("", "", 16, TextArea.ANY); 
            styleInput(company) ; 
            
            TextField phone_Number = new TextField("", "", 16, TextArea.PHONENUMBER); 
            styleInput(phone_Number) ; 
            
            RadioButton check = new RadioButton("Check / Money order");
            RadioButton bank = new RadioButton("Bank transfer payment");
            RadioButton cash = new RadioButton("Cash On Delivery");
            ButtonGroup bg =new ButtonGroup(check, bank, cash);
            
            /*TextField payement_method = new TextField("", "", 16, TextArea.ANY); 
            styleInput(payement_method) ;*/
            
            TextArea delivery_comment = new TextArea(3,5); 
            //styleInput(delivery_comment) ;
            
            TextArea order_comment = new TextArea(3,6); 
            //styleInput(order_comment) ;
            
            TextField codeCoup = new TextField("", "Code Coupon", 16, TextArea.ANY); 
            styleInput(codeCoup) ;
            
            Label Subtotal = new Label(subtotal+"");

            Button add = new Button("Place Order");
            add.setUIID("LoginButton");
            /*long phone = Long.parseLong(phone_Number.getText());
            add.addActionListener(e-> {
                ServiceCommande.getInstance().addCommand(firstName.getText(),
                lastName.getText(),
                street_Adress.getText(),
                city.getText(),
                Integer.parseInt(zipPostalCode.getText()),
                country.getText(),
                company.getText(),
                phone,
                payement_method.getText(),
                delivery_comment.getText(),
                order_comment.getText(),
                codeCoup.getText(),
                subtotal
                );
            });       
            */
            Font largePlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);
            
            Label shippingAdd = new Label("Shipping Address", "WalkthruBlack");
            shippingAdd.getAllStyles().setFont(largePlainSystemFont);
            add(shippingAdd);
            
            Label firstNameLabel = new Label("First Name*");
            firstNameLabel.getAllStyles().setBgColor(0xC00000);
            firstNameLabel.getAllStyles().setFgColor(0xC00000) ;
            add(firstNameLabel);
            add(firstName);
            
            Label lastNameLabel = new Label("Last Name*");
            lastNameLabel.getAllStyles().setBgColor(0xC00000);
            lastNameLabel.getAllStyles().setFgColor(0xC00000) ;
            add(lastNameLabel);
            add(lastName);
            
            Label street_AdressLabel = new Label("Street Adress*");
            street_AdressLabel.getAllStyles().setBgColor(0xC00000);
            street_AdressLabel.getAllStyles().setFgColor(0xC00000) ;
            add(street_AdressLabel);
            add(street_Adress);
            
            Label cityLabel = new Label("City*");
            cityLabel.getAllStyles().setBgColor(0xC00000);
            cityLabel.getAllStyles().setFgColor(0xC00000) ;
            add(cityLabel);
            add(city);
            
            Label Zip_PostalCodeLabel = new Label("Zip/PostalCode*");
            Zip_PostalCodeLabel.getAllStyles().setBgColor(0xC00000);
            Zip_PostalCodeLabel.getAllStyles().setFgColor(0xC00000) ;
            add(Zip_PostalCodeLabel);
            add(zipPostalCode);
            
            Label CountryLabel = new Label("Country*");
            CountryLabel.getAllStyles().setBgColor(0xC00000);
            CountryLabel.getAllStyles().setFgColor(0xC00000) ;
            add(CountryLabel);
            add(country);
            
            Label CompanyLabel = new Label("Company*");
            CompanyLabel.getAllStyles().setBgColor(0xC00000);
            CompanyLabel.getAllStyles().setFgColor(0xC00000) ;
            add(CompanyLabel);
            add(company);
            
            Label Phone_NumberLabel = new Label("Phone Number*");
            Phone_NumberLabel.getAllStyles().setBgColor(0xC00000);
            Phone_NumberLabel.getAllStyles().setFgColor(0xC00000) ;
            add(Phone_NumberLabel);
            add(phone_Number);
            
            Label Payementmethod = new Label("Payement Method", "WalkthruBlack");
            Payementmethod.getAllStyles().setFont(largePlainSystemFont);
            add(Payementmethod);
            
            //add(payement_method);
            addAll(check, bank, cash);
            Label Shippingmethod = new Label("Shipping Methods", "WalkthruBlack");
            
            Shippingmethod.getAllStyles().setFont(largePlainSystemFont);
            add(Shippingmethod);
            
            Label DeliveryCommentLabel = new Label("Delivery Comment*");
            DeliveryCommentLabel.getAllStyles().setBgColor(0xC00000);
            DeliveryCommentLabel.getAllStyles().setFgColor(0xC00000) ;
            add(DeliveryCommentLabel);
            add(delivery_comment);
            
            
            Label OrderCommentLabel = new Label("Order Comment*");
            OrderCommentLabel.getAllStyles().setBgColor(0xC00000);
            OrderCommentLabel.getAllStyles().setFgColor(0xC00000) ;
            add(OrderCommentLabel);
            add(order_comment);
            
            add(codeCoup);
            //add(Subtotal);
            add(add);
            add.addPointerReleasedListener(e->{
                Commande commande = new Commande();
                if(firstName.getText().equals("")){
                    Dialog.show("Error ", "your first name is vide", "OK", null);
                    order_comment.getAllStyles().setFgColor(0xC00000) ;
                }else{
                    commande.setFirstName(firstName.getText());
                    System.out.println(firstName.getText());
                }
                
                if(lastName.getText().equals("")){
                    Dialog.show("Error ", "your last name is vide", "OK", null);
                }else{
                    commande.setLastName(lastName.getText());
                    System.out.println(lastName.getText());
                }
                
                if(street_Adress.getText().equals("")){
                    Dialog.show("Error ", "your street adress is vide", "OK", null);
                }else{
                    commande.setStreetAdress(street_Adress.getText());
                    System.out.println(street_Adress.getText());
                }
                
                if(city.getText().equals("")){
                    Dialog.show("Error ", "your city is vide", "OK", null);
                }else{
                    commande.setCity(city.getText());
                    System.out.println(city.getText());
                }
                
                if(zipPostalCode.getText().equals("")){
                    Dialog.show("Error ", "your zip/PostalCode is vide", "OK", null);
                }else{
                    commande.setZip_PostalCode(zipPostalCode.getText());
                    System.out.println(zipPostalCode.getText());
                }
                
                if(country.getText().equals("")){
                    Dialog.show("Error ", "your country is vide", "OK", null);
                }else{
                    commande.setCountry(country.getText());
                    System.out.println(country.getText());
                }
                
                if(company.getText().equals("")){
                    Dialog.show("Error ", "your company is vide", "OK", null);
                }else{
                    commande.setCompany(company.getText());
                    System.out.println(company.getText());
                }
                
                if(phone_Number.getText().equals("")){
                    Dialog.show("Error ", "your phone Number is vide", "OK", null);
                }else{
                    commande.setPhoneNumber(phone_Number.getText());
                    System.out.println(phone_Number.getText());
                }
                 
                if(!bg.isSelected()){
                    Dialog.show("Error ", "you did not select your Payement method", "OK", null);
                }else{
                    switch(bg.getSelectedIndex()){
                        case 0:commande.setPayementMethod("check");break;
                        case 1:commande.setPayementMethod("bank");break;
                        case 2:commande.setPayementMethod("cash");break;
                        default:break;
                    }
                    System.out.println(bg.toString());
                }
                
                if(!delivery_comment.getText().equals("")){
                    commande.setDelivery_comment(delivery_comment.getText());
                    System.out.println(delivery_comment.getText());
                }
                if(!order_comment.getText().equals("")){
                    commande.setOrder_comment(order_comment.getText());
                    System.out.println(order_comment.getText());
                }
                if(!codeCoup.getText().equals("")){
                    commande.setCodeCoupon(codeCoup.getText());
                    System.out.println(codeCoup.getText());
                }
                double reed = subtotal * 0.25;
                if(ServiceCommande.getInstance().addCommand(commande, reed)){
                    ServicePanier.getInstance().ClearPanier(215);
                    Dialog.show("Success ", "your command added successfuly, check your email account", "OK", null);
                    new ProfileForm(res).show();
                    
                }
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