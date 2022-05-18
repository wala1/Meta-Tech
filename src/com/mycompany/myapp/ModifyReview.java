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
import com.codename1.ui.Display;
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
import com.mycompany.myapp.services.ServiceAvis;

/**
 *
 * @author Abdelaziz
 */
public class ModifyReview extends SideMenuBaseForm {
    public ModifyReview(Resources res , Form previous, int id , String desc ) {
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
                                    new Label("Edit", "Title") 
                                )
                            )
                         
                );
         
        tb.setTitleComponent(titleCmp);
        this.getToolbar().addCommandToRightBar("Back", null, (evt) -> {
            previous.showBack();
        });
        
        
        // ==================== FORM ====================================
        // =================== AJOUT AVIS ============================
            Label descAvis = new Label("Review", "Container");
            descAvis.getAllStyles().setAlignment(CENTER);
            descAvis.getAllStyles().setMargin(200, 5, 2, 2);
            
            TextField avis = new TextField(desc, "Description", 20, TextArea.ANY); 
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
                 
            
            
                starRank3.setPreferredSize(new Dimension(fullStar3.getWidth() * 5, fullStar3.getHeight()));
                
                // ===================== END SELECTION RATING ============================
            
            
            
            Button btnValider = new Button("Edit Review"); 
            btnValider.setUIID("ProduitAdd") ;
            
            btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((avis.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        //Dialog.show("Success","Thank you for your review.",new Command("OK"));
                        Avis t = new Avis( id , 1, "Abdelaziz" ,avis.getText(), starRank3.getProgress()+"");
                        System.out.println("PROGRESS : "+starRank3.getProgress());
                        if( ServiceAvis.getInstance().EditReview(t))
                        {
                           Dialog.show("Success","Your review has been edited successfully..",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
            
            addAll(descAvis,FlowLayout.encloseCenter(starRank3),avis,btnValider ) ; 
            
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

