/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.SousCategorie;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceSubCategories;
import java.util.ArrayList;

/**
 *
 * @author Abdelaziz
 */
public class BrandBackForm extends SideMenuBaseForm {
     public BrandBackForm(Resources res , Form previous ) {
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
        // ====================== Add buttons =====================================
        Label title = new Label("Manage Brands") ; 
        title.getAllStyles().setMargin(70,70,0,0) ;
        title.getAllStyles().setAlignment(CENTER) ; 
        title.getAllStyles().setFgColor(0x000000) ; 

        Button addCategorie = new Button("New Brand"); 
        addCategorie.setUIID("smallButtons") ;
        addCategorie.getAllStyles().setAlignment(LEFT) ;
        addCategorie.getAllStyles().setBgColor(0x000000) ; 
    
         
        
        addCategorie.addPointerReleasedListener((evt) -> {
            new AddSubCategoryForm(res,this,1,"",1).show();
        }) ;
        
        addAll(title,FlowLayout.encloseCenter(addCategorie)) ;
        
        
        // ============ TABLE CATEGORIES ==============================
        ArrayList<SousCategorie> Brands = ServiceSubCategories.getInstance().getAllSubCategories() ; 
        
        for (SousCategorie categ : Brands) {
            Container cnt = new Container(BoxLayout.x()) ;
            Label name = new Label(categ.getNomSousCategorie()) ;
            name.getAllStyles().setMarginTop(70);
            Button edit = new Button("");
            edit.setUIID("editButtons") ;
            Button delete = new Button("");
            delete.setUIID("deleteButtons") ;
            
            FontImage.setMaterialIcon(edit, FontImage.MATERIAL_EDIT);
            FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
            // ===== edit
            edit.addPointerReleasedListener((evt) -> {
                new AddSubCategoryForm(res,this,0,categ.getNomSousCategorie(),categ.getId()).show();
            }) ;
            
            // ===== delete
            delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                    try {
                        if( ServiceSubCategories.getInstance().DeleteSubCategory(categ.getId()))
                        {
                           Dialog.show("Success",categ.getNomSousCategorie()+" has been deleted.",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }

                }

            });
            
            
            cnt.addAll(edit,delete) ;
            addAll(FlowLayout.encloseCenter(name),FlowLayout.encloseCenter(cnt)) ; 
        }
         
        
        
        setupSideMenu(res);
        
    }
     
     @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

