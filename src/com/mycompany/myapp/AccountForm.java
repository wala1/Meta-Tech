/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author wala
 */
public class AccountForm extends SideMenuBaseForm {

    private static String i;

    public AccountForm(Resources res) {

        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Label welcome=new Label("Welcome");
        //String ph = SessionManager.getImageUser();
        //System.out.println(ph);
        //Image profilePic = res.getImage(SessionManager.getImageUser());
       // Image mask = res.getImage("round-mask.png");
       // profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
       // Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
       // profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container remainingTasks = BoxLayout.encloseY(
                new Label("My Events", "CenterTitle")
        //new Label("remaining tasks", "CenterSubTitle")
        );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                new Label("POSTS", "CenterTitle")
        //new Label("completed tasks", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");
        String name = SessionManager.getUsername();
        
        String emailUser = SessionManager.getEmail();
          Container cnt4 = new Container(BoxLayout.y());
        
                                
        cnt4.addAll(new Label(name));
 Container flowLayout1 = FlowLayout.encloseCenter(
                cnt4
        );
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                               flowLayout1
                        )
                )
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        Button modify = new Button("Save Changes");
        //Button picture=new Button("Photo");
        String r= SessionManager.getUsername();
        
      
                     add(new Label("General Account Settings", "TodayTitle"));

        
        String us = SessionManager.getUsername();
        System.out.println(us);
        Label usernameLabel = new Label("Username", "editProfileLabels");
        // usernameLabel.setUIID("modifyProfile");
        TextField username = new TextField(us, "username", 20, TextField.ANY);
         username.setUIID("donnesUser");
        // addStringValue("username", username);
        Label passwordLabel = new Label("Password", "editProfileLabels");
        // passwordLabel.setUIID("passwordLabel");
        TextField password = new TextField(SessionManager.getPassword(), "password", 20, TextField.PASSWORD);
        password.setUIID("donnesUser");
        Label emailLabel = new Label("Email", "editProfileLabels");
        
        //emailLabel.setUIID("emailLabel");
        TextField email = new TextField(SessionManager.getEmail(), "email", 20, TextField.EMAILADDR);
         email.setUIID("donnesUser");
        modify.setUIID("LoginButton");
styleInput(username);
styleInput(password);
styleInput(email);

        Container cnt = new Container(BoxLayout.y());
        Container buttonCnt = new Container(BoxLayout.y());

        cnt.addAll(usernameLabel, username, emailLabel, email, passwordLabel, password);
        buttonCnt.add(modify);
        Container flowLayout = FlowLayout.encloseCenter(
                cnt,
                buttonCnt
        );

        add(flowLayout);
        //addStringValue("",picture)
        //  addStringValue("", modify);
        /* TextField path= new TextField("");
       picture.addActionListener(e->{
       i=Capture.capturePhoto(Display.getInstance().getDisplayWidth(),-1);
       if(i != null){
           Image im;
           try{
           im=Image.createImage(i);
           im=im.scaled(res.getImage("photo-profile.jpg").getWidth(),res.gaetImage("photo-profile.jpg").getHeight());
           System.out.println(i);
           path.setText(i);
           
           }
           catch(IOException ex) {
               System.out.println("Could not load image");
           
           }
       
       }
   
       });*/
        modify.addActionListener((edit) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInfiniteBlocking();
            ServiceUser.EditUser(username.getText(), password.getText(), email.getText());
            SessionManager.setUsername(username.getText());
            SessionManager.setPassword(password.getText());
            SessionManager.setEmail(email.getText());
            // name = SessionManager.getUsername();
            //SessionManager.setPhoto(username.getText()+".jpg");
            //Dialog.show("SUCCESS","Changed was saved","OK",null);
            ipDlg.dispose();
            refreshTheme();

        });

        /**
         * addButtonBottom(arrowDown, "Finish landing page concept", 0xd997f1,
         * true); addButtonBottom(arrowDown, "Design app illustrations",
         * 0x5ae29d, false); addButtonBottom(arrowDown, "Javascript training ",
         * 0x4dc2ff, false); addButtonBottom(arrowDown, "Surprise Party for
         * Matt", 0xffc06f, false);
         */
        setupSideMenu(res);
    }

    private Label createSeparator() {
        Label sep = new Label();
        sep.setUIID("Separator");
        // the separator line is implemented in the theme using padding and background color, by default labels
        // are hidden when they have no content, this method disables that behavior
        sep.setShowEvenIfBlank(true);
        return sep;
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

    /*private void addStringValue(String s, TextField username0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).add(BorderLayout.CENTER, v));
        //  add(createLineSeparator(0xeeeeee));
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
        loginStyle.setFgColor(0x464646) ;
    }
    /**
     * Component createLineSeparator(int i) { throw new
     * UnsupportedOperationException("Not supported yet."); //To change body of
     * generated methods, choose Tools | Templates. }
     */
}
