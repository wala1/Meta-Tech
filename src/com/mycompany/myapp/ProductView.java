/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.SpanMultiButton;
import com.codename1.ui.BrowserComponent;
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
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import static com.codename1.ui.layouts.FlowLayout.encloseCenter;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources; 
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.myapp.StatsForm;
import com.mycompany.myapp.entities.Avis;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ServiceAvis;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServicePanier;
import com.mycompany.myapp.services.ServiceProducts;
import java.io.IOException;
import java.util.ArrayList;

 
/**
 *
 * @author Abdelaziz
 */
public class ProductView extends SideMenuBaseForm {
    public ProductView(Resources res,  String idProd, Form previous, String conv) {
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
                                    new Label("Details", "Title") 
                                )
                            )
                         
                );
         
        tb.setTitleComponent(titleCmp);
        this.getToolbar().addCommandToRightBar("Back", null, (evt) -> {
            previous.showBack();
        });
        
        
        ArrayList<Produit> Products = ServiceProducts.getInstance().getProductByID(idProd) ;
        
        for (Produit prod : Products) {
            
            Slider starRank = new Slider();
            starRank.setEditable(true);
            starRank.setMinValue(0);
            starRank.setMaxValue(10);
            Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                    derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);

            Style s = new Style(0xffff33, 0, fnt, (byte)0);
            Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
            s.setOpacity(0);
            s.setFgColor(100);
            Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
            initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
            initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
            initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
            initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);

            /*String str = prod.getRate() ; 
            String[] arr = str.split("4",2) ; */
            System.out.println("RATEEEED: " ) ; 

            String test = prod.getRate() ;
                    int sub = test.indexOf(".")   ; 
                    String last = test.substring(0,sub);


            int wid = (int)Integer.parseInt(last) ;
            System.out.println("RATEEEE : "+wid ) ; 
            starRank.setPreferredSize(new Dimension(fullStar.getWidth() * wid, fullStar.getHeight()));
            
             
            
            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            
            Image im = URLImage.createToStorage(placeholder,"images_"+prod.getId(),prod.getImageProd(),URLImage.RESIZE_SCALE) ;

            ImageViewer image = new ImageViewer(im.scaled(800, 980));  
            
            //SpanMultiButton twoLinesNoIcon = new SpanMultiButton(prod.getNomProd());
            Label nomProd = new Label(prod.getNomProd());
            nomProd.getAllStyles().setAlignment(CENTER) ; 
            nomProd.getAllStyles().setMargin(50, 50, 0, 0);
            nomProd.getAllStyles().setFgColor(0x464646) ; 
            
            SpanLabel  descProd = new SpanLabel(prod.getDescProd());
             
             
            String currency = "DT"  ;
            String rate  = ServiceProducts.getInstance().convertCurrency(conv) ;
            if (prod.getCategorieProd().equals("Crypto")) {
                currency = "ETH" ; 
                 rate  = ServiceProducts.getInstance().convertCrypto() ;
                String test2 = rate ;
                int sub2 = 2 ; 
                int finish2 = test2.length()-4 ; 
                String test3  = test2.substring(sub2,finish2) ;
                
                 
                String arr1 = test3.replace(',','.');
                 
                
                
                rate = arr1;
            }
            
            
            Label stockProd = new Label("Stock : " + prod.getStockProd()) ; 
            Label sousCategProd = new Label("Brand : "+ prod.getSousCategorieProd());
            Label prixProd = new Label("Price : "+prod.getPrixProd() +currency);
            if (prod.getPromoProd()>0) {
                Label promoProd = new Label("Discount : " + prod.getPromoProd()+currency);
                promoProd.getAllStyles().setFgColor(0x3AB903);
                prixProd.getAllStyles().setFgColor(0xFF6565);
                prixProd.setUIID("priceDiscount") ; 
                
                
                Label convertUSD = new Label("") ; 
                if (!prod.getCategorieProd().equals("Crypto")) {
                    convertUSD = new Label(conv+" : " + Float.parseFloat(rate)*prod.getPromoProd()) ;
                } else {
                    convertUSD = new Label(conv+" : " + Float.parseFloat(rate)*prod.getPromoProd()*1000) ;
                }
                convertUSD.getAllStyles().setFgColor(0x3AB903);
                
                cnt.addAll(descProd,stockProd,sousCategProd, prixProd, promoProd,convertUSD);
            } else {
                prixProd.getAllStyles().setFgColor(0x3AB903);
                
                Label convertUSD = new Label("") ; 
                if (!prod.getCategorieProd().equals("Crypto")) {
                    convertUSD = new Label(conv+" : " + Float.parseFloat(rate)*prod.getPromoProd()) ;
                } else {
                    convertUSD = new Label(conv+" : " + Float.parseFloat(rate)*prod.getPromoProd()*1000) ;
                }
                
                cnt.addAll(descProd,stockProd,sousCategProd, prixProd, convertUSD);
            }
            // ================== API CONVERT USD =====================================
            holder.add(cnt); 
            addAll(nomProd,image, FlowLayout.encloseCenter(starRank), holder) ; 
            
            
            if (currency.equals("DT")){
                ComboBox rates = new ComboBox(); 
                rates.addItem("USD"); 
                //rates.addItem("TND"); 
                rates.addItem("EUR"); 

                rates.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                         String rate  = ServiceProducts.getInstance().convertCurrency(rates.getSelectedItem().toString()) ;
                         Label convertUSD = new Label("USD : " + Float.parseFloat(rate)*prod.getPromoProd()) ;
                         cnt.add(convertUSD);

                         if (!rates.getSelectedItem().toString().equals(conv)) { 
                            new ProductView( res, idProd, previous, rates.getSelectedItem().toString()).show();
                        }  




                    }
                });
                add(rates) ; 
            } else if (currency.equals("ETH")) {
                 

                /*rates.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                         String rate  = ServiceProducts.getInstance().convertCrypto() ;
                         //Label convertUSD = new Label("USD : " + Float.parseFloat(rate)*prod.getPromoProd()) ;
                         //cnt.add(convertUSD);

                         if (!rates.getSelectedItem().toString().equals(conv)) { 
                             
                            String test2 = rate ;
                            int sub2 = 1 ; 
                            int finish2 = test2.length()-1 ;
                                            
                
                            new ProductView( res, idProd, previous, test2.substring(sub2,finish2)).show();
                        }  




                    }
                });*/
            }
            
            
            Button btnCart = new Button("Add To Cart"); 
            btnCart.setUIID("ProduitAdd");
            
            Label space = new Label("") ; 
            space.getAllStyles().setMarginTop(60) ; 
            
            btnCart.addActionListener(
                    (e)->{
                    ServicePanier.getInstance().addPanierJson(197,prod.getId(),prod.getStockProd());
                    }
            
            );
            
            
            //holder.setLeadComponent(nomProd);
            addAll( space,FlowLayout.encloseCenter(btnCart)) ;
            //BorderLayout.centerAbsolute
             
            
            
            // =================== AJOUT AVIS ============================
            Label descAvis = new Label("Review", "Container");
            descAvis.getAllStyles().setAlignment(CENTER);
            descAvis.getAllStyles().setMargin(200, 5, 2, 2);
            
            TextField avis = new TextField("", "Description", 20, TextArea.ANY); 
            Style loginStyle = avis.getAllStyles();
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
            descAvis.getAllStyles().setAlignment(CENTER);
            descAvis.getAllStyles().setMargin(100, 5, 2, 2);
            
             
            
            
            // ========== SELECTION RATING ================
                Slider starRank3 = new Slider();
                starRank3.setEditable(true);
                starRank3.setMinValue(0);
                starRank3.setMaxValue(5);
                Font fnt3 = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                        derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);

                Style s3 = new Style(0xffff33, 0, fnt3, (byte)0);
                Image fullStar3 = FontImage.createMaterial(FontImage.MATERIAL_STAR, s3).toImage();
                s3.setOpacity(100);
                s3.setFgColor(0);
                Image emptyStar3 = FontImage.createMaterial(FontImage.MATERIAL_STAR, s3).toImage();
                initStarRankStyle(starRank3.getSliderEmptySelectedStyle(), emptyStar3);
                initStarRankStyle(starRank3.getSliderEmptyUnselectedStyle(), emptyStar3);
                initStarRankStyle(starRank3.getSliderFullSelectedStyle(), fullStar3);
                initStarRankStyle(starRank3.getSliderFullUnselectedStyle(), fullStar3);
                 
            
            
                starRank3.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
                
                // ===================== END SELECTION RATING ============================
            
            
            
            Button btnValider = new Button("Add Review"); 
            btnValider.setUIID("ProduitAdd");
            
            btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((avis.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Avis t = new Avis( 1, Integer.parseInt(idProd), "walaAlimi" ,avis.getText(), starRank3.getProgress()+"");
                        System.out.println("PROGRESS : "+starRank3.getProgress());
                        if( ServiceAvis.getInstance().addReview(t))
                        {
                           Dialog.show("Success","Thank you for your review.",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
            
            
            addAll(descAvis,FlowLayout.encloseCenter(starRank3),avis,btnValider) ;
            
            // ========================== SHOW AVIS ======================================
            Label Testimonials = new Label("Testimonials");
            Testimonials.getAllStyles().setMargin(250,40,0,0);
            Testimonials.getAllStyles().setFgColor(0x000000);
            Testimonials.getAllStyles().setAlignment(CENTER);
            add(Testimonials) ;
            ArrayList<Avis> Avis = ServiceAvis.getInstance().getAllAvis(idProd) ;
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
            
            
                starRank2.setPreferredSize(new Dimension(fullStar.getWidth() * wid2, fullStar.getHeight()));
                
                 
                
                // ========== Description ================
                
                Label descAvisUser = new Label(avi.getDescAvis().toString());
                Container cnt2 = new Container(BoxLayout.x());
                cnt2.addAll(nomUser,FlowLayout.encloseCenter(starRank2)); 
                
                addAll(cnt2, descAvisUser) ; 
                
                String tested = "walaAlimi" ; 
                // ============ MODIFY and DELETE===============
                System.out.println("GETTING USERR:"+avi.getIdUser()+"n") ; 
                if ((avi.getIdUser()).equals(tested)){ 
                    System.out.println("USER = " + avi.getIdUser()) ; 
                    Button btnModify = new Button(""); 
                    btnModify.setUIID("editButtons") ;
                    Button btnDelete = new Button("");
                    btnDelete.setUIID("deleteButtons") ;
                    
                    FontImage.setMaterialIcon(btnModify, FontImage.MATERIAL_EDIT);
                    FontImage.setMaterialIcon(btnDelete, FontImage.MATERIAL_DELETE);

                    // ===== edit
                    btnModify.addPointerReleasedListener((evt) -> {
                                new ModifyReview(res,this, avi.getId() , avi.getDescAvis()).show();
                    }) ; 
                            
                    
                    
                    
                    
                    // ===== delete
                    btnDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                       
                            try {
                                if( ServiceAvis.getInstance().DeleteReview(avi.getId()))
                                {
                                   Dialog.show("Success","Your Review has been deleted.",new Command("OK"));
                                }else
                                    Dialog.show("ERROR", "Server error", new Command("OK"));
                            } catch (NumberFormatException e) {
                                Dialog.show("ERROR", " ", new Command("OK"));
                            }

                        }
 
                    });
                    
                    
                    
                    
                    Container cnt4 = new Container(BoxLayout.x()) ; 
                    cnt4.addAll(btnModify, btnDelete) ;
                    add(cnt4) ; 
                }
                       
            }
             
            
            
            
            
            
            
 
        }
        
         setupSideMenu(res);
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

