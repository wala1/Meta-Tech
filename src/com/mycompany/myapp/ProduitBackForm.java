/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;


import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.ProductView;
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Avis;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ServiceAvis;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceProducts;
import java.util.ArrayList;

/**
 *
 * @author Abdelaziz
 */
public class ProduitBackForm extends SideMenuBaseForm {
    public ProduitBackForm(Resources res , Form previous ) {
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
        Label title = new Label("Manage Products") ; 
        title.getAllStyles().setAlignment(CENTER) ; 
        title.getAllStyles().setMargin(70,70,0,0) ; 
        title.getAllStyles().setFgColor(0x000000) ; 
        
        
        Button btnCategorie = new Button("Categories"); 
        btnCategorie.setUIID("smallButtons") ;
        Button btnBrand = new Button("Brands");     
        btnBrand.setUIID("smallButtons") ;
        Button btnAddProduct = new Button("Add Product"); 
        btnAddProduct.setUIID("smallButtons") ;
        btnCategorie.addPointerReleasedListener((evt) -> {
            new CategorieBackForm(res,this).show();
        }) ; 
        
        btnBrand.addPointerReleasedListener((evt) -> {
            new BrandBackForm(res,this).show();
        }) ;
       
        btnAddProduct.addPointerReleasedListener((evt) -> {
            new AddProductForm(res,this,1,1).show();
        }) ;
        
        Container cnt4 = new Container(BoxLayout.x()) ; 
        cnt4.addAll(btnAddProduct,btnCategorie, btnBrand) ;
        cnt4.getAllStyles().setMarginBottom(40);
        addAll(title,FlowLayout.encloseCenter(cnt4)) ; 
        
        
        
        // ========================= SHOW PRODUCTS =======================================
        ArrayList<Produit> Products  = ServiceProducts.getInstance().getAllTasks() ;
          
        
        for (Produit prod : Products) {
             
            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            Container cnt2 = new Container(BoxLayout.x());
            
            
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            
            Image im = URLImage.createToStorage(placeholder, "images_"+prod.getId(),prod.getImageProd(),URLImage.RESIZE_SCALE) ;

            ImageViewer image = new ImageViewer(im.scaled(400, 480));

            Label nomProd = new Label(prod.getNomProd()); 
            nomProd.getAllStyles().setFgColor(0x000000) ; 
            
            Label stockProd = new Label("Stock : "+ prod.getStockProd());
            
            Button edit = new Button("");
            edit.setUIID("editButtons") ;
            Button delete = new Button("");
            delete.setUIID("deleteButtons") ;
            
            FontImage.setMaterialIcon(edit, FontImage.MATERIAL_EDIT);
            FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
            // ===== edit
            
            edit.addPointerReleasedListener((evt) -> {
                new AddProductForm(res,this,prod.getId(),0).show();
            }) ;
            
            // ===== delete
            delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                    System.out.println("first") ; 
                    try {
                            System.out.println("second") ; 
                            if( ServiceProducts.getInstance().DeleteProduct(prod.getId()))
                            {
                                                    System.out.println("third") ; 

                               Dialog.show("Success",prod.getNomProd()+" has been deleted.",new Command("OK"));
                            }else
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }

                }

            });
            
            
            
            cnt2.addAll(edit,delete) ; 
            cnt.addAll(nomProd,stockProd,cnt2) ;  
            //holder.setLeadComponent(nomProd);
            holder.addAll(image, cnt); 
            add(holder) ; 
            
        }
        
        setupSideMenu(res);
        
    }
    
    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

