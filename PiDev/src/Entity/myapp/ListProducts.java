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

package com.mycompany.myapp;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources; 
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceProducts;
import java.util.ArrayList;
/**
 *
 * @author Abdelaziz
 */
public class ListProducts extends SideMenuBaseForm {
    public ListProducts(Resources res, String selectedCategorie) {
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
                        
        add(new Label("All Products", ""));
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        
        Form homeForm = new Form(BoxLayout.y());
        
        
        ArrayList<Categorie> Categories = ServiceCategories.getInstance().getAllCategories() ;
        
        ComboBox names = new ComboBox();
        for (Categorie categ : Categories) {
            names.addItem(categ.getNomCategorie()); 
        }
        
        names.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form detailsForm = new Form(BoxLayout.y());
                 
                String categ = names.getSelectedItem().toString() ; 
                ArrayList<Produit> Prods = ServiceProducts.getInstance().getProductsByCategory(categ) ;
                 
                 
                new ListProducts(res,categ).show();
                
                }
        }); 
        
        add(names);
        
        
        
        ArrayList<Produit> Products = ServiceProducts.getInstance().getAllTasks() ;
        if (selectedCategorie!="all") {
             Products = ServiceProducts.getInstance().getProductsByCategory(selectedCategorie) ;
        }  
        for (Produit prod : Products) {
             
            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            
            Image im = URLImage.createToStorage(placeholder,"fileNameInStorage",prod.getImageProd(),URLImage.RESIZE_SCALE) ;

            ImageViewer image = new ImageViewer(im.scaled(400, 480));

            Label nomProd = new Label(prod.getNomProd());
            Label descProd = new Label(prod.getDescProd());
            Label prixProd = new Label("Price : "+prod.getPrixProd() +" DT");
            if (prod.getPromoProd()>0) {
                Label promoProd = new Label("Discount : " + prod.getPromoProd()+" DT");
                cnt.addAll(nomProd, descProd, prixProd, promoProd);
            } else {
                cnt.addAll(nomProd, descProd, prixProd);
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
