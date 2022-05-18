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
import com.codename1.components.SpanMultiButton;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
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
import com.mycompany.myapp.entities.Calendar;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceEvents;
import com.mycompany.myapp.services.ServiceUser;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author wala
 */
public class EventViewForm extends SideMenuBaseForm {

    public EventViewForm(Resources res,Calendar cal) {
                super(BoxLayout.y());

        Form previous = Display.getInstance().getCurrent();
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



           
            Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                    derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);

          

    

            Container holder = new Container(BoxLayout.x());
            Container cnt = new Container(BoxLayout.y());
            Container time = new Container(BoxLayout.y());


            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight() / 5, 0xffff0000), true);

            Image im = URLImage.createToStorage(placeholder, "images_" + cal.getId(), cal.getImageEvent(), URLImage.RESIZE_SCALE);

            ImageViewer image = new ImageViewer(im.scaled(800, 980));

            //SpanMultiButton twoLinesNoIcon = new SpanMultiButton(prod.getNomProd());
            Label title= new Label(cal.getTitle(), "redText");
            title.getAllStyles().setAlignment(CENTER);
            title.getAllStyles().setMargin(50, 50, 0, 0);
            title.getAllStyles().setFgColor(0x464646);

            SpanLabel description = new SpanLabel(cal.getDescription(),"redText");

             Label startTime1 = new Label("Start At : ","greenText");
            Label endTime1 = new Label("End At : ","greenText");
            Label startTime = new Label("" + cal.getStart());
            Label endTime = new Label("" + cal.getEnd());
          

            Button btnParticipate = new Button("Participate");
            btnParticipate.setUIID("LoginButton");

            Label space = new Label("");
            space.getAllStyles().setMarginTop(60);
      
            cnt.addAll(description);
            time.addAll(startTime1,startTime,endTime1,endTime);

            holder.add(cnt);
            addAll(title, image ,FlowLayout.encloseCenter(holder),FlowLayout.encloseCenter(time),  space, FlowLayout.encloseCenter(btnParticipate));
            //BorderLayout.centerAbsolute

        

    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}
