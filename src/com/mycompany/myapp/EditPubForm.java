/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
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
import com.mycompany.myapp.entities.Publicity;
import com.mycompany.myapp.services.ServicePublicity;
import java.util.ArrayList;

/**
 *
 * @author ihebx
 */
public class EditPubForm extends SideMenuBaseForm {

    public EditPubForm(Resources res, Form previous, Publicity ev) {
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
        // Calendar ev;
        //  ArrayList<Calendar> Events = ServiceEvents.getInstance(). getEventByID(id + "");
        //  getEventByID(id + "");

        // for (Calendar ev : Events) {
        Label title = new Label("Edit Event");
        title.getAllStyles().setAlignment(CENTER);
        title.getAllStyles().setMargin(70, 70, 0, 0);
        title.getAllStyles().setFgColor(0x000000);

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

        Image im = URLImage.createToStorage(placeholder, "images_" + ev.getId(), ev.getImageName(), URLImage.RESIZE_SCALE);

        ImageViewer imageURL = new ImageViewer(im.scaled(550, 630));
    TextField name = new TextField(ev.getNomPub()+ "", "", 50, TextArea.ANY); 
            styleInput(name) ; 
            
            TextField desc = new TextField(ev.getDescPub()+ "", "", 50, TextArea.ANY); 
            styleInput(desc) ; 
          TextField image = new TextField(ev.getImageName()+ "", "", 150, TextArea.ANY); 
          styleInput(image) ;          

            TextField price = new TextField(ev.getPrixPub()+ "", "", 50, TextArea.ANY); 
            styleInput(price) ; 
            
            TextField discount = new TextField(ev.getPromoPub()+ "", "", 50, TextArea.ANY); 
            styleInput(discount) ;
     
        name.setSingleLineTextArea(true);
        desc.setSingleLineTextArea(true);
        price.setSingleLineTextArea(true);
        discount.setSingleLineTextArea(true);
        image.setSingleLineTextArea(true);
        Button add = new Button("Edit");
        add.getAllStyles().setMarginTop(70);

        add.addActionListener(new ActionListener() {
            @Override
   public void actionPerformed(ActionEvent evt) {
                if ((name.getText().length() == 0) || (desc.getText().length() == 0) || (image.getText().length() == 0) || (price.getText().length() == 0) || (discount.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Publicity t = new Publicity(ev.getId(), name.getText(), desc.getText(), image.getText(), 2000,1000);

                        if (ServicePublicity.getInstance().modifierPublicity(t)) {
                            Dialog.show("Success", name.getText() + " has been updated successfully.", new Command("OK"));
                            new PubBack(res).show();
                        } else {
                            Dialog.show("Error", "Make sure to fill the fields properly.", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));

             }

                }
            }
        });

        Container cntX = new Container(BoxLayout.x());
        Container cntY = new Container(BoxLayout.y());

        cntY.addAll(name, price, discount);
        cntX.addAll(imageURL, cntY);

        addAll(title, cntX, image, desc, add);
                  setupSideMenu(res);

    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        loginStyle.setFgColor(0x464646);
    }

}
