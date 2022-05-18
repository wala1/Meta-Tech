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
import com.codename1.ui.Display;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Calendar;
import com.mycompany.myapp.services.ServiceEvents;
import java.util.ArrayList;

/**
 *
 * @author wala
 */
public class EditEventForm extends SideMenuBaseForm {

    public EditEventForm(Resources res, Form previous, Calendar ev) {
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
        Label title = new Label("Edit Event");
        title.getAllStyles().setAlignment(CENTER);
        title.getAllStyles().setMargin(70, 70, 0, 0);
        title.getAllStyles().setFgColor(0x000000);

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

        Image im = URLImage.createToStorage(placeholder, "images_" + ev.getId(), ev.getImageEvent(), URLImage.RESIZE_SCALE);

        ImageViewer imageURL = new ImageViewer(im.scaled(550, 630));

        TextField titleEv = new TextField(ev.getTitle() + "", "", 50, TextArea.ANY);
        styleInput(titleEv);

        TextField desc = new TextField(ev.getDescription(), "", 50, TextArea.ANY);
        styleInput(desc);
        /*TextField start = new TextField(ev.getStart() + "", "", 50, TextArea.ANY);
        styleInput(start);
        TextField end = new TextField(ev.getEnd() + "", "", 50, TextArea.ANY);
        styleInput(end);*/
        TextField imageEvent = new TextField(ev.getImageEvent(), "", 100, TextArea.ANY);
        styleInput(imageEvent);
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setUIID("date");
        Picker datePicker1 = new Picker();
        datePicker1.setType(Display.PICKER_TYPE_DATE);
        datePicker1.setUIID("date");
        titleEv.setSingleLineTextArea(true);
        desc.setSingleLineTextArea(true);
        //start.setSingleLineTextArea(true);
        // end.setSingleLineTextArea(true);
        imageEvent.setSingleLineTextArea(true);
        Button add = new Button("Edit");
        add.getAllStyles().setMarginTop(70);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((titleEv.getText().length() == 0) || (desc.getText().length() == 0) ||(datePicker.getDate().toString().length() == 0) || (datePicker1.getDate().toString().length() == 0) || (imageEvent.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Calendar t = new Calendar(ev.getId(), titleEv.getText(), desc.getText(),datePicker.getDate().toString(), datePicker1.getDate().toString(), imageEvent.getText());

                        if (ServiceEvents.getInstance().modifierEvent(t)) {
                            Dialog.show("Success", titleEv.getText() + " has been updated successfully.", new Command("OK"));
                            new EventsBackForm(res).show();
                            refreshTheme();
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

       
        
         cntY.addAll(titleEv, imageEvent, desc);
        cntX.addAll(imageURL, cntY);
        addAll(title, cntX );
        addStringValue("Start At", datePicker);
        addStringValue("End At  ", datePicker1);
        add(add);
        setupSideMenu(res);
    }
     private void addStringValue(String date_picker, Picker datePicker) {
        add(BorderLayout.west(new Label(date_picker, "chapmsColor")).add(BorderLayout.CENTER, datePicker));
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
