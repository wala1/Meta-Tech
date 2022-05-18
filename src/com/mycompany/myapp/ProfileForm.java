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
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Publicity;
import com.mycompany.myapp.services.ServicePublicity;
import java.util.ArrayList;

/**
 * Represents a user profile in the app, the first form we open after the walkthru
 *
 * @author Shai Almog
 */
public class ProfileForm extends SideMenuBaseForm {
    public ProfileForm(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
      //  tb.setTitleCentered(false);
       // Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
       //// profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
       // Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
      //  profilePicLabel.setMask(mask.createMask());

      
      
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
      
              
              
              
        Container completedTasks = BoxLayout.encloseY(
                        new Label("32", "CenterTitle"),
                        new Label("completed tasks", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");
 
        
    Button wifi = new Button("");
       wifi.setUIID("head");
             FontImage.setMaterialIcon(wifi, FontImage.MATERIAL_WIFI);
 Button batt = new Button("");
        batt.setUIID("head");
             FontImage.setMaterialIcon(batt, FontImage.MATERIAL_BATTERY_FULL);
     
        
  Button store = new Button("");
        store.setUIID("store");
             FontImage.setMaterialIcon(store, FontImage.MATERIAL_LOCATION_PIN);
               store.addActionListener(e -> new Maps().show());

  Button search = new Button("");
        search.setUIID("store");
             FontImage.setMaterialIcon(search, FontImage.MATERIAL_SEARCH);

 Button signout = new Button("Sign Out");
        signout.setUIID("store");       
             FontImage.setMaterialIcon(signout, FontImage.MATERIAL_OPEN_WITH);
String r = SessionManager.getRoles();
       
  Button back = new Button("");
        back.setUIID("menu");
        back.addActionListener(e -> new UsersForm(res).show());
             FontImage.setMaterialIcon(back, FontImage.MATERIAL_ADMIN_PANEL_SETTINGS);
        
        
     Button home = new Button("");
        home.setUIID("menu");
     FontImage.setMaterialIcon(home, FontImage.MATERIAL_HOME);
 
        
     Button product = new Button("");
        product.setUIID("menu");
     FontImage.setMaterialIcon(product, FontImage.MATERIAL_STORE);

      product.addActionListener(e -> new ListProducts(res,"Category","Brand","" ).show());
    Button Events = new Button("");
        Events.setUIID("menu");
           FontImage.setMaterialIcon(Events, FontImage.MATERIAL_EVENT);

       Events.addActionListener(e -> new ListEvents(res).show());
    Button Blog = new Button("");
        Blog.setUIID("menu");
    FontImage.setMaterialIcon(Blog, FontImage.MATERIAL_COMMENT);
     Blog.addActionListener(e -> new ShowPub(res).show());
       Button cart = new Button("");
       cart.setUIID("menu");
     FontImage.setMaterialIcon(cart, FontImage.MATERIAL_ADD_SHOPPING_CART);
          cart.addActionListener(e -> new ListPanier(res).show());

     
     Button Contact = new Button("");
      Contact.setUIID("menu");
      FontImage.setMaterialIcon(Contact, FontImage.MATERIAL_CONTACT_MAIL);

      // store.addActionListener(e -> new Maps(res).show());
    //Button news = new Button("");
    // news.setUIID("menu");
    Image logo = res.getImage("logo.png");
                                  
        Container titleCmp = BoxLayout.encloseY(
                             //   FlowLayout.encloseIn(menuButton),

                
                          BoxLayout.encloseX(
                                  new Label("13:00", "SubTitle"),
                                  // new Label(logo, "logo"),

                                  FlowLayout.encloseIn(wifi) 

                                        ),
                              
                
                      BoxLayout.encloseX(

                                  FlowLayout.encloseIn(store)
                                 
                                  // FlowLayout.encloseIn(back)

                                        ),
                              
                     BoxLayout.encloseX(
                                   FlowLayout.encloseIn(home),
                                      FlowLayout.encloseIn(product),
                                        FlowLayout.encloseIn(Events),
                                        FlowLayout.encloseIn(Blog),
                                       //FlowLayout.encloseIn(cart),
                                      FlowLayout.encloseIn(back)
                                              


                       //)   
                               
                        ));

       
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_SEARCH);
      fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
    fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
       tb.setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, CENTER));
                        
        
        
        
        add(new Label("Coming Soon", "TodayTitle"));

      
 
 
  FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        
        Form homeForm = new Form(BoxLayout.y());
        
        
        ArrayList<Publicity> pub = ServicePublicity.getInstance().getAllPub();
      
        for (Publicity p : pub) {
             
            Container holder = new Container(BoxLayout.y());
            Container cnt = new Container(BoxLayout.y());
            
             EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

            Image im = URLImage.createToStorage(placeholder, "images4_" + p.getId(), p.getImageName(), URLImage.RESIZE_SCALE);

            ImageViewer image = new ImageViewer(im.scaled(850, 700)); //400 480

            Label nomProd = new Label(p.getNomPub());
            Label descProd = new Label(p.getDescPub());
            /* Button participer = new Button("Check Products");
                              participer.setUIID("shop");

           participer.addActionListener(e -> new ListProducts(res,"Category","Brand","" ).show());*/
        if (p.id == 1|| p.id == 3 /*|| p.id == 11*/){
            cnt.addAll(nomProd, descProd);
            holder.setLeadComponent(nomProd);
            holder.addAll(image,cnt); 
            add(holder) ; 
        }
        }
        
                add(new Label("More Department", "TodayTitle"));

        
        
        
      
        for (Publicity p : pub) {
             
            Container holder = new Container(BoxLayout.y());
            Container cnt = new Container(BoxLayout.y());
            
             EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

            Image im = URLImage.createToStorage(placeholder, "images_" + p.getId(), p.getImageName(), URLImage.RESIZE_SCALE);

            ImageViewer image = new ImageViewer(im.scaled(800, 600)); //400 480

            Label nomProd = new Label(p.getNomPub());
          //  Label descProd = new Label(p.getDescPub());
           
           Button participer = new Button("Check Products");
                              participer.setUIID("LoginButton");

           participer.addActionListener(e -> new ListProducts(res,"Category","Brand","" ).show());
        if (p.id == 4 || p.id == 5 ){
            //cnt.addAll(nomProd);
            holder.setLeadComponent(nomProd);
            holder.addAll(image, participer); 
            add(holder) ; 
        }
        }
        
        
          //       add(new Label("", "TodayTitle"));

        
        
        
       
        for (Publicity p : pub) {
             
            Container holder = new Container(BoxLayout.y());
            Container cnt = new Container(BoxLayout.y());
            
             EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

            Image im = URLImage.createToStorage(placeholder, "images_" + p.getId(), p.getImageName(), URLImage.RESIZE_SCALE);

            ImageViewer image = new ImageViewer(im.scaled(800, 600)); //400 480

            Label nomProd = new Label(p.getNomPub());
            Button participer = new Button("Check Events","LoginButton");
                              participer.setUIID("LoginButton");

           participer.addActionListener(e -> new ListEvents(res).show());
          //  Label descProd = new Label(p.getDescPub());
        if (p.id == 6 || p.id == 7){
            //cnt.addAll(nomProd);
            holder.setLeadComponent(nomProd);
            holder.addAll(image, participer); 
            add(holder) ; 
        }
        }
      
               add(new Label("Best Deals", "TodayTitle"));
                for (Publicity p : pub) {
             
            Container holder = new Container(BoxLayout.y());
            Container cnt = new Container(BoxLayout.y());
            
             EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

            Image im = URLImage.createToStorage(placeholder, "images_" + p.getId(), p.getImageName(), URLImage.RESIZE_SCALE);

            ImageViewer image = new ImageViewer(im.scaled(800, 600)); //400 480

            Label nomProd = new Label(p.getNomPub());
          //  Label descProd = new Label(p.getDescPub());
          
        if (p.id == 9 || p.id == 8){
            cnt.addAll(nomProd);
            holder.setLeadComponent(nomProd);
            holder.addAll(image, cnt); 
            add(holder) ; 
        }
        }

        setupSideMenu(res);
       
       
    }

    
    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
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
