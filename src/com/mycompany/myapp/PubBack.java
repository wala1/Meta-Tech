/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.util.Resources;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Publicity;
import com.mycompany.myapp.services.ServicePublicity;

import java.util.ArrayList;

/**
 *
 * @author Mejri
 */
public class PubBack extends SideMenuBaseForm {

    public PubBack(Resources res) {
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
        Form previous = Display.getInstance().getCurrent();

        this.getToolbar().addCommandToRightBar("Back", null, (evt) -> {
            previous.showBack();
        });
        // ====================== Add Events=====================================
        Label crudtit = new Label("Manage Publicity");
       crudtit.setUIID("crudtit");

        crudtit.getAllStyles().setAlignment(CENTER);
        crudtit.getAllStyles().setMargin(70, 70, 0, 0);
        crudtit.getAllStyles().setFgColor(0x000000);
        Button btnAddevent = new Button("");
        btnAddevent.setUIID("addButton");
                     FontImage.setMaterialIcon(btnAddevent,FontImage.MATERIAL_ADD);

        btnAddevent.addPointerReleasedListener((evt) -> {
            new AddPubForm(res, this, 1).show();
        });
        Container cnt4 = new Container(BoxLayout.x());
       
     // Container cnt5 = new Container(BoxLayout.y());
      //cnt5.addAll(title);
        cnt4.addAll(crudtit,btnAddevent);
          Container flowLayout = FlowLayout.encloseCenter(
                    cnt4
            );
        addAll( flowLayout);

        // ========================= SHOW PRODUCTS =======================================
        ArrayList<Publicity> pub = ServicePublicity.getInstance().getAllPub();

        for (Publicity p : pub) {

            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            Container cnt2 = new Container(BoxLayout.x());

            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

            Image im = URLImage.createToStorage(placeholder, "images_" + p.getId(), p.getImageName(), URLImage.RESIZE_SCALE);

            ImageViewer image = new ImageViewer(im.scaled(400, 480));

            Label name = new Label(p.getNomPub());
            name.getAllStyles().setFgColor(0x000000);

            Label price = new Label("PRICE: " + p.getPrixPub());
            Label promo = new Label("DISCOUNT: " + p.getPromoPub());

            Button edit = new Button("");
            edit.setUIID("editButton");
            FontImage.setMaterialIcon(edit,FontImage.MATERIAL_EDIT);
            Button delete = new Button("");
            delete.setUIID("deletButton");
           FontImage.setMaterialIcon(delete,FontImage.MATERIAL_DELETE);


          
             edit.addPointerReleasedListener((evt) -> {
                new EditPubForm(res,this,p).show();
            }) ;
            // ===delete
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    try {
                        if (ServicePublicity.getInstance().deletePublicity(p.getId())) {

                            Dialog.show("Success", p.getNomPub()+ " has been deleted.", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }

                }

            });
  Label space= new Label(" ");
    Label space1= new Label(" ");

            cnt2.addAll(space1,edit, space,delete);
            cnt.addAll(name,price,promo, cnt2);

            //holder.setLeadComponent(nomProd);
            holder.addAll(image, cnt);
            add(holder);

        }

    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
