/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Publication;
import com.mycompany.myapp.services.ServiceComment;
import com.mycompany.myapp.services.ServicePublication;
import java.util.ArrayList;

/**
 *
 * @author louay
 */
public class ShowDetailPub extends SideMenuBaseForm {

    public ShowDetailPub(Resources res, Publication publication) {
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
                                new Label("Détail publications", "Title")
                        )
                )
        );
        Form previous = Display.getInstance().getCurrent();
        tb.setTitleComponent(titleCmp);
        Button retour = new Button("Retour");
        retour.setAlignment(LEFT);
        retour.addActionListener(l -> previous.showBack());

        add(new Label("Commentaires", ""));
        add(retour);

        String imageLink = publication.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xffff0000), true);
        Image im = URLImage.createToStorage(placeholder, imageLink, imageLink);
        ImageViewer image = new ImageViewer(im.scaled(250, 200));

        Button deleteComment = new Button("Delete");
        deleteComment.getStyle().setBgColor(0xffffff);
        deleteComment.getStyle().setFgColor(0xFF0000);
        deleteComment.getStyle().setBgTransparency(255);
        deleteComment.getStyle().setBorder(Border.createRoundBorder(30, 30));
        deleteComment.getStyle().setPadding(2, 2, 2, 2);
        deleteComment.addActionListener((l) -> {
            ServicePublication.getInstance().deletePub(publication.getId());
            if (ServicePublication.getInstance().deletePub(publication.getId())) {
                Dialog.show("Success", "Post deleted deleted", "OK", null);
                new ShowPub(res).show();
                refreshTheme();
            }
        });

        Label username = new Label("Username");
        username.getAllStyles().setFgColor(0xffffff);

        Label titre = new Label("Titre: " + publication.getTitre());
        titre.getAllStyles().setFgColor(0xffffff);

        Label description = new Label("Description: " + publication.getDescription());
        description.getAllStyles().setFgColor(0xffffff);

        Label date = new Label("Date: " + publication.getTemps());

        Container post = BoxLayout.encloseY(
                GridLayout.encloseIn(2, titre, description), date);
        Container second = GridLayout.encloseIn(1, deleteComment);
        Container pub = BoxLayout.encloseY(
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                username, image
                        )
                ),//.add(BorderLayout.WEST, pubImage),
                BoxLayout.encloseY(post, second)
        );

        pub.getStyle().setFgColor(0xffffff);
        pub.getStyle().setBgColor(0xfdb819);
        pub.getStyle().setBgTransparency(255);
        pub.getStyle().setPadding(7, 7, 7, 7);
        pub.getStyle().setMargin(20, 20, 30, 30);
        pub.getStyle().setBorder(Border.createRoundBorder(50, 50));

        add(pub);
setupSideMenu(res);
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}
