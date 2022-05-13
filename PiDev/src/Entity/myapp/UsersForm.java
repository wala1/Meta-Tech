/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
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
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Calendar;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceEvents;
import com.mycompany.myapp.services.ServiceUser;
import java.util.ArrayList;

/**
 *
 * @author ihebx
 */
public class UsersForm extends SideMenuBaseForm {

    public UsersForm(Resources res) {
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
        // ====================== Add Users=====================================
        Label title = new Label("Manage Users");
        title.getAllStyles().setAlignment(CENTER);
        title.getAllStyles().setMargin(70, 70, 0, 0);
        title.getAllStyles().setFgColor(0x000000);
        Button btnAddUser = new Button("");
        FontImage.setMaterialIcon(btnAddUser, FontImage.MATERIAL_ADD);

        /*
       
        btnAddProduct.addPointerReleasedListener((evt) -> {
            new AddProductForm(res,this,1,1).show();
        }) ;*/
        Container cnt4 = new Container(BoxLayout.x());

        cnt4.addAll(title, btnAddUser);
        Container flowLayout = FlowLayout.encloseCenter(
                cnt4
        );
        addAll(flowLayout);
        // ========================= SHOW Users =======================================
        ArrayList<User> Users = ServiceUser.getInstance().getAllUsers();

        for (User u : Users) {

            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            Container cnt2 = new Container(BoxLayout.x());
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

            Image im = URLImage.createToStorage(placeholder, "images_" + u.getId(), u.getImageUser(), URLImage.RESIZE_SCALE);

            ImageViewer image = new ImageViewer(im.scaled(400, 480)); //400 480

            Label username = new Label(u.getUsername());
            username.getAllStyles().setFgColor(0x000000);

            Label email = new Label(u.getEmail());

            Button unblock = new Button("");
            unblock.setUIID("editButton");
            FontImage.setMaterialIcon(unblock, FontImage.MATERIAL_BLOCK);

            Button blockUnblock = new Button("");
            if (u.getEtat() == 0)// not blocked
            {
                blockUnblock.setUIID("editButton");
                FontImage.setMaterialIcon(blockUnblock, FontImage.MATERIAL_BLOCK);
                //=====block
                blockUnblock.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        try {
                            if (ServiceUser.getInstance().blockUser(u.getId())) {

                                Dialog.show("Success", u.getUsername() + " has been Blocked.", new Command("OK"));
                                System.out.println("nom:" + u.getUsername());
                                u.setEtat(1);
                                System.out.println("etat:" + u.getEtat());

                                refreshTheme();
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", " ", new Command("OK"));
                        }

                    }

                });

            } else // is blocked
            {
                blockUnblock.setUIID("deletButton");
                FontImage.setMaterialIcon(blockUnblock, FontImage.MATERIAL_BLOCK);
                //=====block
                blockUnblock.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        try {
                            if (ServiceUser.getInstance().unblockUser(u.getId())) {

                                Dialog.show("Success", u.getUsername() + " has been UnBlocked.", new Command("OK"));
                                System.out.println("nom:" + u.getUsername());
                                u.setEtat(0);
                                System.out.println("etat:" + u.getEtat());

                                refreshTheme();
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", " ", new Command("OK"));
                        }

                    }

                });
            }
            Button delete = new Button("");
            delete.setUIID("deletButton");
            FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);

            // ===delete
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    try {
                        if (ServiceUser.getInstance().deleteUser(u.getId())) {

                            Dialog.show("Success", u.getUsername() + " has been deleted.", new Command("OK"));
                            refreshTheme();
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }

                }

            });
            Label space = new Label(" ");
            Label space1 = new Label(" ");

            cnt2.addAll(space1, blockUnblock, space, delete);
            cnt.addAll(username, email, cnt2);

            //holder.setLeadComponent(nomProd);
            holder.addAll(image,
                     cnt);
            add(holder);

        }
        refreshTheme();
        setupSideMenu(res);

    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
