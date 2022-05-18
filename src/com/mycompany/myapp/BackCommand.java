/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.services.ServiceCommande;
import com.mycompany.myapp.services.ServicePanier;
import java.util.ArrayList;

/**
 *
 * @author Logidee
 */
public class BackCommand extends SideMenuBaseForm {
    
    public BackCommand(Resources res) {
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
                                    new Label("Manage Orders", "Title") 
                                )
                            )
                );
        tb.setTitleComponent(titleCmp);
                        
        
        Form homeForm = new Form(BoxLayout.y());
        
        Form current;
        current=this;
        this.getToolbar().addCommandToOverflowMenu("Refresh", null, (evt) -> {
            ArrayList<Commande> Commands = ServiceCommande.getInstance().getAllCommands();
            new BackCommand(res).show();
        });
        
            ArrayList<Commande> Commands = ServiceCommande.getInstance().getAllCommands();
        if(Commands.isEmpty()){
        
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
                for(Commande cmd:Commands){
                    Button button = new Button("Remove");
                    Button button2 = new Button("Update");
                    //Button button2 = new Button("Edite");
                    button.getAllStyles().setMarginRight(50);


                    Label firstname = new Label(cmd.getFirstName());
                    Font mediumPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
                    firstname.getAllStyles().setFont(mediumPlainSystemFont);
                    Label lastname = new Label(cmd.getLastName());
                    lastname.getAllStyles().setFont(mediumPlainSystemFont);
                    Label date = new Label(cmd.getDate());
                    date.getAllStyles().setFont(mediumPlainSystemFont);
                    SpanLabel place = new SpanLabel(cmd.getCity()+", "+cmd.getCountry()+" "+cmd.getCompany()+".");
                    place.getAllStyles().setFont(mediumPlainSystemFont);
                    Label subtotal = new Label(cmd.getSubtotal());
                    
                    button2.getAllStyles().setMarginRight(50);

                    Container t2 = TableLayout.encloseIn(1, place,
                            firstname,
                            lastname,
                            date);

                    add(t2);
                    button2.setUIID("LoginButton");
                    button.setUIID("SkipButton");
       
                    addAll(button2,button);
                    button2.addActionListener(evt -> {
                        ServiceCommande.getInstance().UpdateCommande(cmd);
                        double x = Double.parseDouble(cmd.getSubtotal());
                        new CommandForm(current,x,res).show();
                    });

                    button.addPointerReleasedListener((evt) -> {
                        //DELETE FROM DATABASE
                        if(Dialog.show("Command ", "Delete commande", "YES", "NO")) {
                            ServiceCommande.getInstance().RemoveCommande(cmd.getId());
                            Dialog.show("DELETE cOMMAND", "Removed Successfuly!", "OK", null);
                        }
                    });
                }
        }
        setupSideMenu(res);
    }
    
    @Override
    protected void showOtherForm(Resources res) {
    }
    
}
