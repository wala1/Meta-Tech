/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Calendar;
import com.mycompany.myapp.services.ServiceCategories;
import com.mycompany.myapp.services.ServiceEvents;
import java.util.ArrayList;

/**
 *
 * @author ihebx
 */
public class ListEvents extends SideMenuBaseForm {

    public ListEvents(Resources res) {
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
                                new Label("MetaTech", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);
        Label title1 = new Label("Add Your Event");
        title1.getAllStyles().setAlignment(CENTER);
        title1.getAllStyles().setMargin(70, 70, 0, 0);
        title1.getAllStyles().setFgColor(0x000000);
        Button btnAddevent = new Button("");
        FontImage.setMaterialIcon(btnAddevent, FontImage.MATERIAL_ADD);

        btnAddevent.addPointerReleasedListener((evt) -> {
            new AddEventUser(res, this).show();

        });
        Container cnt4 = new Container(BoxLayout.x());

        cnt4.addAll(title1, btnAddevent);
        Container flowLayout1 = FlowLayout.encloseCenter(
                cnt4
        );
        addAll(flowLayout1);

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        Form homeForm = new Form(BoxLayout.y());

        ArrayList<Calendar> Events = ServiceEvents.getInstance().getAllEvents();

        for (Calendar ev : Events) {
            if (ev.getEtat() == 1) {

                Container holder = new Container(BoxLayout.y());
                Container cnt = new Container(BoxLayout.y());

                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

                Image im = URLImage.createToStorage(placeholder, "images3_" + ev.getId(), ev.getImageEvent(), URLImage.RESIZE_SCALE);

                ImageViewer image = new ImageViewer(im.scaled(1000, 480)); //400 480

                Label title = new Label(ev.getTitle(), "CenterLabel");
                Label description = new Label(ev.getDescription());
                Label start = new Label(ev.getStart());
                Label end = new Label(ev.getEnd());

                Button readMore = new Button("Read More" + ev.getId());
                readMore.setUIID("LoginButton");
                title.addPointerReleasedListener((evt) -> {
                    new EventViewForm(res, ev).show();
                });
                cnt.addAll(title, description, readMore);
                Container flowLayout = FlowLayout.encloseCenter(
                        cnt
                );
                holder.setLeadComponent(title);
                holder.addAll(image, flowLayout);
                add(holder);

            }

        }
        setupSideMenu(res);
        refreshTheme();

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
