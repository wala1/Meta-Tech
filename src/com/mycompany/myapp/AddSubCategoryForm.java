/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;


import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.SousCategorie;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceSubCategories;

/**
 *
 * @author Abdelaziz
 */
public class AddSubCategoryForm extends SideMenuBaseForm {
    public AddSubCategoryForm(Resources res , Form previous , int addForm, String nomCateg, int id) {
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
        
        // ======================= FORM ============================
        if (addForm == 1 ) {
            Label title = new Label("Add Brand") ; 
            title.getAllStyles().setAlignment(CENTER) ; 
            title.getAllStyles().setFgColor(0x000000) ; 

            TextField name = new TextField("", "Name Brand", 16, TextArea.ANY); 
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

            Button add = new Button("Add");
            add.setUIID("ProduitAdd");
            
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((name.getText().length()==0))
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    else
                    {
                        try {
                            //Dialog.show("Success","Thank you for your review.",new Command("OK"));
                            SousCategorie t = new SousCategorie(1, name.getText() );

                            if( ServiceSubCategories.getInstance().addSubCategory(t))
                            {
                               Dialog.show("Success",name.getText()+" has been added successfully.",new Command("OK"));
                            }else
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", " ", new Command("OK"));
                        }

                    }


                }
            });

            addAll(title,name,add) ;
        
        
        // ============ EDIT FORM ==========================
        } else {
            
            Label title = new Label("Edit Brand") ; 
            title.getAllStyles().setAlignment(CENTER) ; 
            title.getAllStyles().setFgColor(0x000000) ; 

            TextField name = new TextField(nomCateg, "", 16, TextArea.ANY); 
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

            Button add = new Button("Edit");
            add.setUIID("ProduitAdd");
             
            
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((name.getText().length()==0))
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    else
                    {
                        try {
                            //Dialog.show("Success","Thank you for your review.",new Command("OK"));
                            SousCategorie t = new SousCategorie(id, name.getText() );

                            if( ServiceSubCategories.getInstance().editSubCategory(t))
                            {
                               Dialog.show("Success","Brand has been edited successfully.",new Command("OK"));
                            }else
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", " ", new Command("OK"));
                        }

                    }


                }
            });

            addAll(title,name,add) ;
            
            
        }
        setupSideMenu(res);
    } 
    
    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
    
}

