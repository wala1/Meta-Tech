/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;



import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Publicity;
import com.mycompany.myapp.services.ServicePublicity;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Mejri
 */
public class AddPubForm extends SideMenuBaseForm{
        public AddPubForm(Resources res , Form previous  , int addForm ) {
              super(BoxLayout.y());
        Toolbar tb = getToolbar();
        
        tb.setTitleCentered(false);   

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
          
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("", "Title") 
                                )
                            )
                         
                );
         
        tb.setTitleComponent(titleCmp);
        this.getToolbar().addCommandToRightBar("Back", null, (evt) -> {
            previous.showBack();
        });
        
        
         if (addForm == 1 ) {
            Label title = new Label("Add Publicity") ; 
            title.getAllStyles().setAlignment(CENTER) ; 
            title.getAllStyles().setMargin(70,70,0,0) ;
            title.getAllStyles().setFgColor(0x000000) ; 

     TextField name = new TextField("", "Name", 16, TextArea.ANY); 
            styleInput(name) ; 
            
            TextField desc = new TextField("", "Description", 16, TextArea.ANY); 
            styleInput(desc) ; 
       //     TextField image = new TextField("", "Image", 16, TextArea.ANY); 
       //     styleInput(image) ;          

            TextField price = new TextField("", "Price", 16, TextArea.ANY); 
            styleInput(price) ; 
            
            TextField discount = new TextField("", "Discount", 16, TextArea.ANY); 
            styleInput(discount) ;
         
                  Button Upload = new Button("Upload Image");
           Label lblImage = new Label();
                Upload.addPointerReleasedListener((evt) -> {
                   String path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(),-1)    ;
                   if(path !=null){
                       try {
                           Image img = Image.createImage(path);
                           lblImage.setIcon(img);
                           revalidate();
                           } catch (IOException ex) {
                           ex.printStackTrace();
                       }
                   }
                             
                             });     
            
            
            Button add = new Button("Add");
            add.getAllStyles().setMarginTop(70);

            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((name.getText().length()==0)||(desc.getText().length()==0)||(price.getText().length()==0)||(discount.getText().length()==0)) 
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    else
                    {
                        try {
                                Publicity t = new Publicity(1,name.getText(),desc.getText(),"",Integer.parseInt(price.getText()),0) ;

                            if( ServicePublicity.getInstance().ajoutPublicity(t))
                            {
                               Dialog.show("Success",name.getText()+" has been added successfully.",new Command("OK"));
                            }else
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", " ", new Command("OK"));



                    }


                    }}
            });
            
            Container holder = new Container(BoxLayout.x());
            addAll(title,name,desc,price,discount,Upload,add) ;
        
         }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        }
    
    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
