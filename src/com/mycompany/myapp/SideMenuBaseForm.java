/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.myapp;

import com.codename1.components.ToastBar;
import com.codename1.io.Storage;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;

/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("users.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("  Welcome To MetaTech", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop1");
       

        getToolbar().addComponentToSideMenu(sidemenuTop);
 String r = SessionManager.getRoles();
        if (r.equals("[ROLE_ADMIN, ROLE_USER]")) {
            getToolbar().addMaterialCommandToSideMenu("  Home", FontImage.MATERIAL_ACCESS_TIME, e -> new ProfileForm(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Users", FontImage.MATERIAL_TRENDING_UP, e -> new UsersForm(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Products", FontImage.MATERIAL_SETTINGS, e -> new ProduitBackForm(res, new ProfileForm(res)).show());
            getToolbar().addMaterialCommandToSideMenu(" Command", FontImage.MATERIAL_SETTINGS, e -> new BackCommand(res).show());

            getToolbar().addMaterialCommandToSideMenu("  Events", FontImage.MATERIAL_TRENDING_UP, e -> new EventsBackForm(res).show());
            getToolbar().addMaterialCommandToSideMenu(" Requests", FontImage.MATERIAL_EDIT_NOTIFICATIONS, e -> new requestEventsForm(res).show());
          getToolbar().addMaterialCommandToSideMenu(" Publicity", FontImage.MATERIAL_TRENDING_UP, e -> new PubBack(res).show());

            getToolbar().addMaterialCommandToSideMenu("  Posts", FontImage.MATERIAL_POST_ADD, e -> new ShowPubAdmin(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
                new LoginForm(res).show();
                SessionManager.pref.clearAll();
                Storage.getInstance().clearStorage();
                Storage.getInstance().clearCache();
                System.out.println(SessionManager.getUsername());

            });

        } else {
            getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, e -> new AccountForm(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Home", FontImage.MATERIAL_ACCESS_TIME, e -> new ProfileForm(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Products", FontImage.MATERIAL_STORE, e -> new ListProducts(res, "Category", "Brand", "").show());
            getToolbar().addMaterialCommandToSideMenu("  Shopping Cart", FontImage.MATERIAL_STORE, e -> new ListPanier(res).show());

            getToolbar().addMaterialCommandToSideMenu("  Events", FontImage.MATERIAL_TRENDING_UP, e -> new ListEvents(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Blog", FontImage.MATERIAL_POST_ADD, e -> new ShowPub(res).show());
            getToolbar().addMaterialCommandToSideMenu("  Contact Us", FontImage.MATERIAL_QUESTION_ANSWER, e -> new ReclamationForm(res).show());
           // getToolbar().addMaterialCommandToSideMenu("  Activity", FontImage.MATERIAL_TRENDING_UP, e -> new ProfileForm(res).show());
             getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
                new LoginForm(res).show();
                SessionManager.pref.clearAll();
                Storage.getInstance().clearStorage();
                Storage.getInstance().clearCache();
                System.out.println(SessionManager.getUsername());

            });

        }
        refreshTheme();
    }

    protected abstract void showOtherForm(Resources res);
}