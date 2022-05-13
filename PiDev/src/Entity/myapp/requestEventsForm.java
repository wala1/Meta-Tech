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
import com.mycompany.myapp.services.ServiceEvents;
import com.mycompany.myapp.services.ServiceUser;
import java.util.ArrayList;

/**
 *
 * @author wala
 */
public class requestEventsForm extends SideMenuBaseForm {

    public requestEventsForm(Resources res) {
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
        Label title = new Label("Manage Requests");
        title.getAllStyles().setAlignment(CENTER);
        title.getAllStyles().setMargin(70, 70, 0, 0);
        title.getAllStyles().setFgColor(0x000000);
        Button btnAddevent = new Button("");

        Container cnt4 = new Container(BoxLayout.x());

        cnt4.addAll(title);
        Container flowLayout = FlowLayout.encloseCenter(
                cnt4
        );
        addAll(flowLayout);

        // ========================= SHOW Events =======================================
        ArrayList<Calendar> Events = ServiceEvents.getInstance().getAllEvents();

        for (Calendar ev : Events) {
            if (ev.getEtat() == 0) {

                Container holder = new Container(BoxLayout.x());
                Container cnt = new Container(BoxLayout.y());
                Container cnt2 = new Container(BoxLayout.x());

                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

                Image im = URLImage.createToStorage(placeholder, "images_" + ev.getId(), ev.getImageEvent(), URLImage.RESIZE_SCALE);

                ImageViewer image = new ImageViewer(im.scaled(400, 480));

                Label titleEvent = new Label(ev.getTitle());
                titleEvent.getAllStyles().setFgColor(0x000000);

                Label startTime = new Label("Start At: " + ev.getStart());
                Label endTime = new Label("End At: " + ev.getEnd());

                Button accept = new Button("");
                accept.setUIID("editButton");
                FontImage.setMaterialIcon(accept, FontImage.MATERIAL_DONE);
                Button refuse = new Button("");
                refuse.setUIID("deletButton");
                FontImage.setMaterialIcon(refuse, FontImage.MATERIAL_CANCEL);
                   accept.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        try {
                            if (ServiceEvents.getInstance().acceptEvent(ev.getId())) {

                                Dialog.show("Approved!", "Event"+ ev.getTitle() + " has been Accepted.", new Command("OK"));
                                System.out.println("nom:" + ev.getTitle());
                                ev.setEtat(1);
                                System.out.println("etat:" + ev.getEtat());

                                refreshTheme();
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", " ", new Command("OK"));
                        }

                    }

                });
                   refuse.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        try {
                            if (ServiceEvents.getInstance().refuseEvent(ev.getId())) {

                                Dialog.show("Refused!", "Event"+ev.getTitle() + " has been Refused.", new Command("OK"));
                                System.out.println("nom:" + ev.getTitle());
                                ev.setEtat(-1);
                                System.out.println("etat:" + ev.getEtat());

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

                cnt2.addAll(space1, accept, space, refuse);
                cnt.addAll(titleEvent, startTime, endTime, cnt2);

                //holder.setLeadComponent(nomProd);
                holder.addAll(image, cnt);
                add(holder);

            }
        }
        setupSideMenu(res);
        refreshTheme();

    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
