/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
 

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
//import com.mycompany.myapp.ProduitsBack.BrandBackForm;
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.entities.SousCategorie;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceProducts;
import com.mycompany.myapp.services.ServiceSubCategories;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Abdelaziz
 */
public class ListProducts extends SideMenuBaseForm {
    public ListProducts(Resources res, String selectedCategorie,String selectedSubCategorie, String searchProd ) {
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
                                    new Label("Products", "Title") 
                                )
                            )
                         
                );
         
        tb.setTitleComponent(titleCmp);
        
        if (selectedCategorie == "Category") {
            add(new Label("All Products", ""));
        } else {
            add(new Label(selectedCategorie, ""));

        }
        
        if (selectedSubCategorie != "Brand") {
            add(new Label("Brand : "+selectedSubCategorie, ""));
        }
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        
        Form homeForm = new Form(BoxLayout.y());
        
        // ========================= COMOBO BOX CATEGORIES =======================================
        ArrayList<Categorie> Categories = ServiceCategories.getInstance().getAllCategories() ;
        
        ComboBox names = new ComboBox(selectedCategorie); 
        
        if (!selectedCategorie.equals("Category")) {
            names.addItem("Category"); 
        }
        
        for (Categorie categ : Categories) {
            if (!categ.getNomCategorie().equals(selectedCategorie)) { 
                names.addItem(categ.getNomCategorie()); 
            }
            
        }
        
        names.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form detailsForm = new Form(BoxLayout.y());
                 
                String categ = names.getSelectedItem().toString() ; 
                if (categ != "Category") {
                    ArrayList<Produit> Prods = ServiceProducts.getInstance().getProductsByCategory(categ) ;
                }  
                 
                new ListProducts(res,categ,selectedSubCategorie,searchProd).show();
                
                }
        });
        
        
        
        
        // ========================= COMOBO BOX SUB CATEGORIES =======================================
        ArrayList<SousCategorie> sousCategories = ServiceSubCategories.getInstance().getAllSubCategories() ;
        ComboBox names2 = new ComboBox(selectedSubCategorie);
        
        if (!selectedSubCategorie.equals("Brand")) {
            names2.addItem("Brand"); 
        }  
        
        for (SousCategorie categ : sousCategories) {
            if (!categ.getNomSousCategorie().equals(selectedSubCategorie)) { 
                names2.addItem(categ.getNomSousCategorie()); 
            }
        }
        
        names2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form detailsForm = new Form(BoxLayout.y());
                 
                String categ = names2.getSelectedItem().toString() ;
                if (categ != "Brand") {
                    ArrayList<Produit> Prods = ServiceProducts.getInstance().getProductsBySubCategory(categ) ;
                }
                 
                new ListProducts(res,selectedCategorie,categ,searchProd).show();
                
                }
        });
        
            
        
        
        // ============== SEARCH BAR ===========================
            TextField searchBar = new TextField("", "Search Product", 20, TextArea.ANY); 
            Style searchStyle = searchBar.getAllStyles();
            Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
            searchStyle.setBorder(RoundRectBorder.create().
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(borderStroke));
            searchStyle.setBgColor(0xffffff);
            searchStyle.setBgTransparency(100);
            searchStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            searchStyle.setMargin(Component.BOTTOM, 3); 
            searchStyle.setFgColor(0x464646) ;
            
            Button searchBtn = new Button("Search"); 
            //searchBtn.setUIID("smallButtons") ;
            
            searchBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((searchBar.getText().length()==0))
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    else
                    {
                        try {
                            //Dialog.show("Success","Thank you for your review.",new Command("OK"));
                               new ListProducts(res,selectedCategorie,selectedSubCategorie,searchBar.getText()).show();
 
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", " ", new Command("OK"));
                        }

                    }


                }
            });
            
            /*ArrayList<Produit> Products = ServiceProducts.getInstance().getAllTasks() ;
            searchBar.addDataChangedListener((evt1, evt2) -> {
                ArrayList<Produit> Products2 = ServiceProducts.getInstance().getProductsBySearch(searchBar.getText()) ;
                
            });*/
            
            
            /*searchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //ArrayList<Produit> Products = ServiceProducts.getInstance().getProductsBySearch(searchBar.getText()) ;  searchText
                System.out.println("BEFORE") ;
                new ListProducts(res,"Category","Brand","Iphone").show();
                                System.out.println("AFTER") ;

            }});*/
        
        
        
        Container cnt2 = new Container(BoxLayout.x());
        Container cnt3 = new Container(BoxLayout.x());
        cnt2.addAll(names,names2);
        cnt3.addAll(searchBar,searchBtn);
        addAll(cnt3,cnt2) ; 
        
        
        
        
        
        
        // ========================= SHOW PRODUCTS =======================================
        ArrayList<Produit> Products  = ServiceProducts.getInstance().getAllTasks() ;
        if (searchProd!="") {
           Products = ServiceProducts.getInstance().getProductsBySearch(searchProd) ; 
        }  
        
        if ((selectedCategorie!="Category")&&(selectedSubCategorie=="Brand")) {
             Products = ServiceProducts.getInstance().getProductsByCategory(selectedCategorie) ;
        } else if ((selectedCategorie=="Category")&&(selectedSubCategorie!="Brand")) {
            Products = ServiceProducts.getInstance().getProductsBySubCategory(selectedSubCategorie) ;
        } else if ((selectedCategorie!="Category")&&(selectedSubCategorie!="Brand")) {
            Products = ServiceProducts.getInstance().getProductsByBoth(selectedCategorie,selectedSubCategorie) ;
        } 
            
         
        if (Products.isEmpty()) {
            SpanLabel empt = new SpanLabel("The searched product is either not existant or no longer available, unless you have a time travel machine.") ; 
            empt.getAllStyles().setAlignment(CENTER) ; 
            empt.getAllStyles().setMargin(150,20,30,20);
            add(empt) ; 
            
            SpanLabel srSim = new SpanLabel("Search for a similar product in : ") ; 
            srSim.getAllStyles().setAlignment(CENTER) ; 
            srSim.getAllStyles().setMargin(0,70,30,20);
            add(srSim) ; 
            
            /*EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            
            Image im = URLImage.createToStorage(placeholder, "images_timeMAchine","https://scontent.xx.fbcdn.net/v/t1.15752-9/277404627_347643670649198_187637355655323331_n.png?stp=dst-png_p206x206&_nc_cat=106&ccb=1-5&_nc_sid=aee45a&_nc_ohc=NJxXsD0kA30AX_oHfVR&_nc_oc=AQm-NlVZUj-5nyBxKWQZRG6ZWWPepWKAf7_w98rWtwXZgXSY1WpsVQlPKO626rSx88mYDLzujOBWafXyiYqWLu3A&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AVLqNaiDanXtNnJAfr5ziHHUMrW-Zq1ZqWMvQ32tckXT9g&oe=626A4356",URLImage.RESIZE_SCALE) ;

            ImageViewer image = new ImageViewer(im.scaled(400, 480));
            add(image) ; */
            
            Button aliexpress = new Button("Amazon"); 
            aliexpress.setUIID("smallButtons") ;
            
             
            aliexpress.addPointerReleasedListener((evt) -> {
                new Amazon(res,this).show();
            }) ;
            
            
            add(FlowLayout.encloseCenter(aliexpress)) ; 
            
        }
        
        for (Produit prod : Products) {
             
            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            
            Image im = URLImage.createToStorage(placeholder, "images2_"+prod.getId(),prod.getImageProd(),URLImage.RESIZE_SCALE) ;

            ImageViewer image = new ImageViewer(im.scaled(400, 480));

            Label nomProd = new Label(prod.getNomProd()); 
            Label sousCategProd = new Label("Brand : "+ prod.getSousCategorieProd());
            
            Label prixProd = new Label();
            if (prod.getCategorieProd().equals("Crypto")) {
                prixProd = new Label("Price : "+prod.getPrixProd() +" ETH");
            } else {
                prixProd = new Label("Price : "+prod.getPrixProd() +" DT");
            }
            
            
            nomProd.addPointerReleasedListener((evt) -> {
                new ProductView(res,Integer.toString(prod.getId()),this,"USD").show();
            });
             
            
            if (prod.getPromoProd()>0) {
                Label promoProd = new Label("Discount : " + prod.getPromoProd()+" DT");
                if (prod.getCategorieProd().equals("Crypto")) {
                    promoProd = new Label("Discount : " + prod.getPromoProd()+" ETH");
                }
                promoProd.getAllStyles().setFgColor(0x3AB903);
                prixProd.getAllStyles().setFgColor(0xFF6565);
                prixProd.setUIID("priceDiscount") ; 
                cnt.addAll(nomProd,sousCategProd, prixProd, promoProd);
            } else {
                prixProd.getAllStyles().setFgColor(0x3AB903);
                cnt.addAll(nomProd,sousCategProd, prixProd);
            }
            holder.setLeadComponent(nomProd);
            holder.addAll(image, cnt); 
            add(holder) ; 
            
        }
         
         
        setupSideMenu(res);
    }
    
    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(),  first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }
    
    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if(first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

