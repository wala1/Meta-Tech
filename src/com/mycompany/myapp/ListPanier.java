/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServicePanier;
import com.mycompany.myapp.services.ServiceProducts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.DateSpinner;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.UIBuilder;


/**
 *
 * @author Logidee
 */


public class ListPanier extends SideMenuBaseForm {

    public ListPanier(Resources res) {
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
                                    new Label("Shopping Cart", "Title") 
                                )
                            )
                );
        
        tb.setTitleComponent(titleCmp);
        
        Form homeForm = new Form(BoxLayout.y());
        
        Form current;
        
        this.getToolbar().addCommandToOverflowMenu("Refresh", null, (evt) -> {
            ArrayList<Panier> Paniers = ServicePanier.getInstance().getAllPaniers();
            new ListPanier(res).show();
        });
        
        Label title = new Label("","Title");
        add(title);
        
        Container holder = new Container(BoxLayout.x());
        Container cnt = new Container(BoxLayout.y());
            
        
        current=this;
        Button proceed = new Button("Proceed To checkout");
        
        Button clear = new Button("Clear Shopping Cart");
        clear.addActionListener(e-> {
                //DELETE FROM DATABASE
                if(Dialog.show("Cart ", "Clear your cart shopping?", "OUI", "NON")) {
                    
                        ServicePanier.getInstance().ClearPanier(215);
                        Dialog.show("Suppression Product ", "Suppression effectuee !", "OK", null);
                    
                }
            });
       
        
        ArrayList<Panier> Paniers = ServicePanier.getInstance().getAllPaniers();
    if(Paniers.isEmpty()){
        
        Tabs walkthruTabs = new Tabs();
        walkthruTabs.hideTabs();
        
        
        Image notes = res.getImage("clear_shopping_cart.png");
        ImageViewer imagex = new ImageViewer(notes.scaled(150, 150));
        add(imagex);
        Label notesPlaceholder = new Label("","ProfilePic");
        Label notesLabel = new Label(notes, "ProfilePic");
        Component.setSameHeight(notesLabel, notesPlaceholder);
        Component.setSameWidth(notesLabel, notesPlaceholder);
        Label bottomSpace = new Label();
        
        Container tab1 = BorderLayout.centerAbsolute(BoxLayout.encloseY(
                //notesPlaceholder,
                new Label("You have no items in your shopping cart.", "WalkthruBlack"),
                new SpanLabel("Click on the button below if you want to continue shopping.\n" +
                                            "You can also proceed by phone, " +
                                            "Check our pages on social media, Thank you."),
                bottomSpace
        ));
        
        walkthruTabs.addTab("", tab1);
        
        
        add(walkthruTabs);
        
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(CENTER);
                
        
        Button skip = new Button("Continue Shopping");
        skip.setUIID("LoginButton");
        skip.addActionListener(e -> new ProfileForm(res).show());
        
        Container southLayout = BoxLayout.encloseY(
                        skip
                );
        add(BorderLayout.south(
                southLayout
        ));
        
        Component.setSameWidth(bottomSpace, southLayout);
        Component.setSameHeight(bottomSpace, southLayout);
        
        // visual effects in the first show
        addShowListener(e -> {
            notesPlaceholder.getParent().replace(notesPlaceholder, notesLabel, CommonTransitions.createFade(1500));
        });
    }else{

            
        long subtot = 0; 
        for (Panier pan : Paniers){
            String produit = pan.getProduit();
            System.out.println("Produit? "+ produit);
            String ch="";
            String c="";
            int j=0;
            int pos=0;
            for (int i = 0 ; i<produit.length() ; i++){
                if (produit.charAt(i) == ','){
                    pos= i;
                    break;
                }
            }
            
            for(int i=1; i<pos;i++){
                ch=ch+produit.charAt(i);
                if(ch == "nom_prod"){
                    j=i+2;
                    while(produit.charAt(j) != ','){
                        c=c+produit.charAt(j);
                        j++;
                    } 
                    i=pos;
                    break;
                }
            }
            
            String nomProdCh ="";
            for (int i = 9 ; i<ch.length() ; i++){
                nomProdCh= nomProdCh+ch.charAt(i);
            }
            
            int ere = produit.indexOf("prix_prod");
            
            System.out.println("Poduct price"+ produit.charAt(ere+9));
            int i=ere+10;
            String er ="";
            for(; i<produit.length();i++){
                if(produit.charAt(i)== '.'){
                    break;
                }
                er = er+produit.charAt(i);
            }
            
            /*
            String ch2 = "";
            for(int i=1; i<produit.length();i++){
                if(ch2 == "prix_prod"){
                    j=i+2;
                    while(produit.charAt(j) != ','){
                        c=c+produit.charAt(j);
                        j++;
                    } 
                    i=pos;
                    break;
                }
            }*/
            
            /*
            String imageProd="https://image.freepik.com/icones-gratuites/corbeille_318-109573.jpg";
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);
            Image im = URLImage.createToStorage(placeholder,"fileNameInStorage",imageProd,URLImage.RESIZE_SCALE) ;
            ImageViewer icon = new ImageViewer(im.scaled(150, 150));
            add(icon);*/
            
            Button button = new Button("Remove");
            //Button button2 = new Button("Edite");
            button.getAllStyles().setMarginRight(50);
            int efre = pan.getQuantite().charAt(0);
            TextField qty = new TextField("", efre+"", 16, TextArea.ANY);
            char ef = pan.getQuantite().charAt(0);
            int sub = Integer.parseInt(ef+"")*Integer.parseInt(er);
            
            Label subtotal = new Label(sub+"DT");
            Button button2 = new Button("Update");
            Label price = new Label(er+"DT");
            button2.getAllStyles().setMarginRight(50);
            
            Container t2 = TableLayout.encloseIn(6, new SpanLabel(nomProdCh),
                    price,
                    qty,
                    subtotal);
            
            add(t2);
            addAll(button2,button);
            
            styleInput(qty) ;
            
            button2.addActionListener(evt -> {
                int x = Integer.parseInt(qty.getText());
                ServicePanier.getInstance().UpdatePanier(pan.getId(),x);
            });
            
            button.addPointerReleasedListener((evt) -> {
                //DELETE FROM DATABASE
                if(Dialog.show("Product ", "Supprimer cette produit dans la base de donnee ?", "OUI", "NON")) {
                    
                        System.out.println(pan);
                        ArrayList<Panier> Panie = ServicePanier.getInstance().RemovePanier(pan.getId());
                        Dialog.show("Suppression Product ", "Suppression effectuee !", "OK", null);
                    
                }
            });
            
            subtot = subtot + sub;
        }
        Label subh = new Label("SubTotal= ");
        Font mediumPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        subh.getAllStyles().setFont(mediumPlainSystemFont);
        
        Container subtotal = FlowLayout.encloseCenter(subh,new Label(subtot+"", "WelcomeBlue"));
        
        double f = subtot;
        proceed.addActionListener(e-> new CommandForm(current,f,res).show());
        
        clear.setUIID("SkipButton");
        proceed.setUIID("LoginButton");
        addAll(subtotal, clear, proceed); 
        
        
        /*
        Button btnListDeleteByOne = new Button("Remove");
        btnListDeleteByOne.addActionListener(e -> new ServicePanier.getInstance().setRemovePanier());
        addAll(btnListDeleteByOne);
         */
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
