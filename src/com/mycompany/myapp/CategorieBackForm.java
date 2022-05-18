/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;


import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
import com.mycompany.myapp.services.ServiceAvis;
import com.mycompany.myapp.services.ServiceCategories;
import java.util.ArrayList;

/**
 *
 * @author Abdelaziz
 */
public class CategorieBackForm extends SideMenuBaseForm {
    public CategorieBackForm(Resources res , Form previous ) {
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
        Label title = new Label("Manage Categories") ; 
        title.getAllStyles().setMargin(70,70,0,0) ;
        title.getAllStyles().setAlignment(CENTER) ; 
        title.getAllStyles().setFgColor(0x000000) ; 

        Button addCategorie = new Button("New Category"); 
        addCategorie.setUIID("smallButtons") ;
        addCategorie.getAllStyles().setAlignment(LEFT) ;
        addCategorie.getAllStyles().setBgColor(0x000000) ; 
    
         
        
        addCategorie.addPointerReleasedListener((evt) -> {
            new AddCategoryForm(res,this,1,"",1).show();
        }) ;
        
        addAll(title,FlowLayout.encloseCenter(addCategorie)) ;
        
        
        // ============ TABLE CATEGORIES ==============================
        ArrayList<Categorie> Categories = ServiceCategories.getInstance().getAllCategories() ; 
        
        for (Categorie categ : Categories) {
            Container cnt = new Container(BoxLayout.x()) ;
            Label name = new Label(categ.getNomCategorie()) ;
            name.getAllStyles().setMarginTop(40);
            Button edit = new Button("");
            edit.setUIID("editButtons") ;
            Button delete = new Button("");
            delete.setUIID("deleteButtons") ;
            
            FontImage.setMaterialIcon(edit, FontImage.MATERIAL_EDIT);
            FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
            // ===== edit
            edit.addPointerReleasedListener((evt) -> {
                new AddCategoryForm(res,this,0,categ.getNomCategorie(),categ.getId()).show();
            }) ;
            
            // ===== delete
            delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                    try {
                        if( ServiceCategories.getInstance().DeleteCategory(categ.getId()))
                        {
                           Dialog.show("Success",categ.getNomCategorie()+" has been deleted.",new Command("OK"));
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

