/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ServiceProducts;
import java.util.ArrayList;

/**
 *
 * @author Abdelaziz
 */
public class Amazon extends SideMenuBaseForm {
    public Amazon(Resources res, Form previous) {
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
        this.getToolbar().addCommandToRightBar("Back", null, (evt) -> {
            previous.showBack();
        });
        add(new Label("Amazon Products", ""));
         
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        
        Form homeForm = new Form(BoxLayout.y());
        
        // ========================= SHOW PRODS =======================================
        Label title = new Label("Manage Products") ; 
        title.getAllStyles().setAlignment(CENTER) ; 
        title.getAllStyles().setMargin(70,70,0,0) ; 
        title.getAllStyles().setFgColor(0x000000) ; 
        
        add(title) ; 
        
        ArrayList<Produit> Products  = ServiceProducts.getInstance().getAllAmazon() ;
        
        for (Produit prod : Products) {
             
            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            
            Image im = URLImage.createToStorage(placeholder, "imagesAMAZON_"+prod.getId(),prod.getImageProd(),URLImage.RESIZE_SCALE) ;

            ImageViewer image = new ImageViewer(im.scaled(400, 480));

            SpanLabel nomProd = new SpanLabel(prod.getNomProd()); 
             
            Label prixProd = new Label();
            prixProd = new Label("Price : "+prod.getPrixProd() +" USD");
            prixProd.getAllStyles().setFgColor(0x3AB903);
            cnt.addAll(nomProd, prixProd);
            
             
              
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
