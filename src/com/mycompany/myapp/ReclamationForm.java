/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
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
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources; 
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceProducts;
import com.mycompany.myapp.services.ServiceReclamations;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Abdelaziz
 */
public class ReclamationForm extends SideMenuBaseForm {
    public ReclamationForm(Resources res) {
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
                                    new Label("Contact Us", "Title") 
                                )
                            )
                         
                );
         
        tb.setTitleComponent(titleCmp); 
        
        
        Label info = new Label("Fill the form") ; 
        info.getAllStyles().setAlignment(CENTER) ;  
        info.getAllStyles().setMargin(50, 10, 0, 0); // === margin (top,bottom,left,right)
        info.getAllStyles().setFgColor(0x00000) ; // ==== Text Color
        
        
        /*TextField name = new TextField("","Name"); 
        
        TextField email= new TextField("", "Email");
        TextField desc= new TextField("", "Description");*/
        Button btnValider = new Button("Send Report");
        btnValider.setUIID("ProduitAdd");
        btnValider.getAllStyles().setBgColor(0xfdb819);
        
        
        // =================== AJOUT AVIS ============================
             
            TextField name = new TextField("", "Name", 16, TextArea.ANY); 
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
            
            TextField email = new TextField("", "Email", 16, TextArea.ANY); 
            Style emailStyle = email.getAllStyles();
            Stroke borderStroke2 = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
            emailStyle.setBorder(RoundRectBorder.create().
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(borderStroke2));
            emailStyle.setBgColor(0xffffff);
            emailStyle.setBgTransparency(100);
            emailStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            emailStyle.setMargin(Component.BOTTOM, 3); 
            emailStyle.setFgColor(0x464646) ;
            
            TextField desc = new TextField("", "Description", 16, TextArea.ANY); 
            Style descStyle = desc.getAllStyles();
            Stroke borderStroke3 = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
            descStyle.setBorder(RoundRectBorder.create().
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(borderStroke3));
            descStyle.setBgColor(0xffffff);
            descStyle.setBgTransparency(100);
            descStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            descStyle.setMargin(Component.BOTTOM, 3); 
            descStyle.setFgColor(0x464646) ;
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((name.getText().length()==0)||(email.getText().length()==0)||(desc.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Reclamation t = new Reclamation(name.getText(), email.getText(), desc.getText());
                        if( ServiceReclamations.getInstance().addReclamation(t))
                        {
                           Dialog.show("Success","Your report was sent successfully.",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(info,name,email,desc,btnValider);
         
        
        
        
         setupSideMenu(res);
    }
    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
    
}

