/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;


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
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Avis;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.entities.SousCategorie;
import com.mycompany.myapp.services.ServiceAvis;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceProducts;
import com.mycompany.myapp.services.ServiceSubCategories;
import java.util.ArrayList;

/**
 *
 * @author Abdelaziz
 */
public class AddProductForm extends SideMenuBaseForm {
    public AddProductForm(Resources res , Form previous , int id , int addForm ) {
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
            Label title = new Label("Add Product") ; 
            title.getAllStyles().setAlignment(CENTER) ; 
            title.getAllStyles().setMargin(70,70,0,0) ;
            title.getAllStyles().setFgColor(0x000000) ; 

            TextField name = new TextField("", "Name", 16, TextArea.ANY); 
            styleInput(name) ; 
            
            TextField desc = new TextField("", "Description", 16, TextArea.ANY); 
            styleInput(desc) ; 
            
            TextField price = new TextField("", "Price", 16, TextArea.ANY); 
            styleInput(price) ; 
            
            TextField discount = new TextField("", "Discount", 16, TextArea.ANY); 
            styleInput(discount) ; 
            
            TextField image = new TextField("", "Image", 16, TextArea.ANY); 
            styleInput(image) ; 
            
            TextField stock = new TextField("", "Stock", 16, TextArea.ANY); 
            styleInput(stock) ;
           
            // ====== comboBox categories
            ArrayList<Categorie> Categories = ServiceCategories.getInstance().getAllCategories() ;
            ComboBox categories = new ComboBox(); 
            
            for (Categorie categ : Categories) {
                categories.addItem(categ.getNomCategorie());  
            }
            
            
            // ====== comboBox brands
            ArrayList<SousCategorie> sousCategories = ServiceSubCategories.getInstance().getAllSubCategories() ;
            ComboBox brands = new ComboBox(); 

            for (SousCategorie categ : sousCategories) {
                brands.addItem(categ.getNomSousCategorie());    
            }
            
            
            
            
            Button add = new Button("Add");
            add.setUIID("ProduitAdd");
            
            add.getAllStyles().setMarginTop(70);

            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((name.getText().length()==0)||(desc.getText().length()==0)||(price.getText().length()==0)||(discount.getText().length()==0)||(image.getText().length()==0)||(stock.getText().length()==0)) 
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    else
                    {
                        try {
                            //Dialog.show("Success","Thank you for your review.",new Command("OK"));
                                Produit t = new Produit(1,name.getText(),desc.getText(),image.getText(),Integer.parseInt(price.getText()), Integer.parseInt(discount.getText()), Integer.parseInt(stock.getText()),categories.getSelectedItem().toString(), brands.getSelectedItem().toString(),"") ;

                            if( ServiceProducts.getInstance().addProduct(t))
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
            
            Container holder = new Container(BoxLayout.x());
            holder.addAll(categories,brands) ; 
            addAll(title,name,desc,price,discount,image,stock,holder,add) ;
        
        
        // ============ EDIT FORM ==========================
        } else if (addForm==0) {
            


            ArrayList<Produit> Products = ServiceProducts.getInstance().getProductByID(id+"") ;
        
            for (Produit prod : Products) {
                Label title = new Label("Edit Product") ; 
                title.getAllStyles().setAlignment(CENTER) ; 
                title.getAllStyles().setMargin(70,70,0,0) ;
                title.getAllStyles().setFgColor(0x000000) ; 
                
                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            
                Image im = URLImage.createToStorage(placeholder,"images_"+prod.getId(),prod.getImageProd(),URLImage.RESIZE_SCALE) ;

                ImageViewer imageURL = new ImageViewer(im.scaled(550, 630));  
            
                
                TextField name = new TextField(prod.getNomProd()+"", "", 16, TextArea.ANY); 
                styleInput(name) ; 

                TextField desc = new TextField(prod.getDescProd(), "", 16, TextArea.ANY); 
                styleInput(desc) ; 
                
                //String[] pr = (prod.getPrixProd()+"").split(".",2) ; 
                TextField price = new TextField((int)prod.getPrixProd() + "", "", 16, TextArea.ANY); 
                styleInput(price) ; 
                
                //String[] prom = (prod.getPromoProd()+"").split(".",2) ; 
                TextField discount = new TextField((int)prod.getPromoProd()+"", "", 16, TextArea.ANY); 
                styleInput(discount) ; 

                TextField image = new TextField(prod.getImageProd(), "", 16, TextArea.ANY); 
                styleInput(image) ; 
                
                //String[] st = (prod.getStockProd()+"").split(".",2) ;
                TextField stock = new TextField((int)prod.getStockProd()+"", "", 16, TextArea.ANY); 
                styleInput(stock) ;

                // ====== comboBox categories
                ArrayList<Categorie> Categories = ServiceCategories.getInstance().getAllCategories() ;
                ComboBox categories = new ComboBox( prod.getCategorieProd()); 

                for (Categorie categ : Categories) {
                    if (!categ.getNomCategorie().equals(prod.getCategorieProd())) {
                        categories.addItem(categ.getNomCategorie());  
                    }
                }


                // ====== comboBox brands
                ArrayList<SousCategorie> sousCategories = ServiceSubCategories.getInstance().getAllSubCategories() ;
                ComboBox brands = new ComboBox(prod.getSousCategorieProd()); 

                for (SousCategorie categ : sousCategories) {
                    if (!categ.getNomSousCategorie().equals(prod.getSousCategorieProd())) {
                        brands.addItem(categ.getNomSousCategorie());   
                    }
                }




                Button add = new Button("Edit");
                add.setUIID("ProduitAdd");
                
                add.getAllStyles().setMarginTop(70);

                add.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if ((name.getText().length()==0)||(desc.getText().length()==0)||(price.getText().length()==0)||(discount.getText().length()==0)||(image.getText().length()==0)||(stock.getText().length()==0)) 
                            Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                        else
                        {
                            try {
                                //Dialog.show("Success","Thank you for your review.",new Command("OK"));
                                    Produit t = new Produit(prod.getId(),name.getText(),desc.getText(),image.getText(),Integer.parseInt(price.getText()), Integer.parseInt(discount.getText()), Integer.parseInt(stock.getText()),categories.getSelectedItem().toString(), brands.getSelectedItem().toString(),"") ;

                                if( ServiceProducts.getInstance().editProduct(t))
                                {
                                   Dialog.show("Success",name.getText()+" has been added successfully.",new Command("OK"));
                                }else
                                    Dialog.show("Error", "Make sure to fill the fields properly.", new Command("OK"));
                            } catch (NumberFormatException e) {
                                Dialog.show("ERROR", " ", new Command("OK"));
                            }

                        }


                    }
                });

                Container holder = new Container(BoxLayout.x());
                holder.addAll(categories,brands) ; 
                
                Container cntX = new Container(BoxLayout.x());
                Container cntY = new Container(BoxLayout.y());
                
                cntY.addAll(name,price,discount) ; 
                cntX.addAll(imageURL,cntY) ; 
                
                addAll(title,cntX,stock,image,desc,holder,add) ;
                
                
                
                // ========================== SHOW AVIS ======================================
            Label Testimonials = new Label("Testimonials");
            Testimonials.getAllStyles().setMargin(250,40,0,0);
            Testimonials.getAllStyles().setFgColor(0x000000);
            Testimonials.getAllStyles().setAlignment(CENTER);
            add(Testimonials) ;
            ArrayList<Avis> Avis = ServiceAvis.getInstance().getAllAvis(prod.getId()+"") ;
            for (Avis avi : Avis) {
                // ======== Username =============
                Label nomUser = new Label(avi.getIdUser()+""); 
                nomUser.getAllStyles().setFgColor(0x000000);
                nomUser.getAllStyles().setMarginTop(35);
                
                // ========== Rating ================
                Slider starRank2 = new Slider();
                starRank2.setEditable(true);
                starRank2.setMinValue(0);
                starRank2.setMaxValue(10);
                Font fnt2 = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                        derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);

                Style s2 = new Style(0xffff33, 0, fnt2, (byte)0);
                Image fullStar2 = FontImage.createMaterial(FontImage.MATERIAL_STAR, s2).toImage();
                s2.setOpacity(0);
                s2.setFgColor(100);
                Image emptyStar2 = FontImage.createMaterial(FontImage.MATERIAL_STAR, s2).toImage();
                initStarRankStyle(starRank2.getSliderEmptySelectedStyle(), emptyStar2);
                initStarRankStyle(starRank2.getSliderEmptyUnselectedStyle(), emptyStar2);
                initStarRankStyle(starRank2.getSliderFullSelectedStyle(), fullStar2);
                initStarRankStyle(starRank2.getSliderFullUnselectedStyle(), fullStar2);
                
                String test2 = avi.getRatingAvis() ;
                int sub2 = test2.indexOf(".")   ; 
                String last2 = test2.substring(0,sub2);

                int wid2 = (int)Integer.parseInt(last2) ;
            
            
                starRank2.setPreferredSize(new Dimension(fullStar2.getWidth() * wid2, fullStar2.getHeight()));
                
                 
                
                // ========== Description ================
                
                Label descAvisUser = new Label(avi.getDescAvis().toString());
                Container cnt2 = new Container(BoxLayout.x());
                cnt2.addAll(nomUser,FlowLayout.encloseCenter(starRank2)); 
                
                addAll(cnt2, descAvisUser) ; 
                 
                // ============DELETE=============== 
                
                     
                    Button btnDelete = new Button("Delete");  
                    btnDelete.setUIID("deleteButtons") ;
                    // ===== delete
                    btnDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                       
                            try {
                                if( ServiceAvis.getInstance().DeleteReview(avi.getId()))
                                {
                                   Dialog.show("Success","Review has been deleted.",new Command("OK"));
                                }else
                                    Dialog.show("ERROR", "Server error", new Command("OK"));
                            } catch (NumberFormatException e) {
                                Dialog.show("ERROR", " ", new Command("OK"));
                            }

                        }
 
                    }); 
                    
                    Container cnt4 = new Container(BoxLayout.x()) ; 
                    cnt4.add( btnDelete) ;
                    add(cnt4) ; 
                 
                       
            }
            
            
            
            
                
                
                
            }
            
        }
        
        setupSideMenu(res);
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
    
     private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(100);
    }
     
     @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

