/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.ConnectionRequest;
import com.mycompany.myapp.utils.Statics;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.ProfileForm;
import com.mycompany.myapp.SessionManager;
import com.mycompany.myapp.SignUpForm;
import com.mycompany.myapp.WalkthruForm;
import com.mycompany.myapp.entities.Calendar;
import java.util.Map;
import com.mycompany.myapp.entities.User;
import static com.mycompany.myapp.services.ServiceEvents.resultOk;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wala
 */
public class ServiceUser {
    public ArrayList<User> Users;

    String json;

    //singleton 
    public static ServiceUser instance = null;

    public static boolean resultOk = true;
    //String json;

    //initilisation connection request 
    private ConnectionRequest req;

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public ServiceUser() {
        req = new ConnectionRequest();

    }

    //Signup
    public void signup(TextField username, TextField password, TextField email,TextField imageUser, TextField confirmPassword, Resources res) {

        String url = Statics.BASE_URL + "/signup?username=" + username.getText().toString() + "&email=" + email.getText().toString()+"&imageUser="+imageUser.getText().toString()+
                "&password=" + password.getText().toString();

        req.setUrl(url);

        //Control saisi
        if (username.getText().equals(" ") && password.getText().equals(" ") && email.getText().equals(" ")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        }

        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e) -> {

            //njib data ly7atithom fi form 
            byte[] data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 

            System.out.println("data ===>" + responseData);
        }
        );

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    //SignIn
    public void signin(TextField email, TextField password, Resources theme) {

        String url = Statics.BASE_URL + "/signin?email=" + email.getText().toString() + "&password=" + password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";

             
            try{
            if(json.equals("failed")) {
                Dialog.show("Echec d'authentification","email ou mot de passe invalide","OK",null);
            }
               
                else {
                    System.out.println("data ==" + json);

                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    //ajouter session
                    float id = Float.parseFloat(user.get("id").toString());
                    SessionManager.setId((int) id); //jbt id te3 user la3Ml login w sajltha f session te3i
                    SessionManager.setPassword(user.get("password").toString());
                    SessionManager.setUsername(user.get("username").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    SessionManager.setRoles(user.get("Roles").toString());
                    SessionManager.setImageUser(user.get("imageUser").toString());

                    //photo
                    /**
                     * if(user.get("Photo") != null) {
                     * SessionManager.setPhoto(user.get("photo").toString()); }
                     */
                    System.out.println("current user ==> Email: " + SessionManager.getEmail() + ",Username:  " + SessionManager.getUsername() + ",Password: " + SessionManager.getPassword()+", Roles: " + SessionManager.getRoles());

                    if (user.size() > 0) // l9a user
                    {
                        Dialog.show("Sucess", "you have successfull connected", "OK", null);

                        new WalkthruForm(theme).show();
                    }

                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    //edit User
    public static void EditUser(String username, String password, String email) {
        String url = Statics.BASE_URL + "/editAccountUser?username=" + username + "&email=" + email + "&password=" + password;
        MultipartRequest req = new MultipartRequest();
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("id", String.valueOf(SessionManager.getId()));
        req.addArgument("username", username);
        req.addArgument("email", email);
        req.addArgument("password", password);
        System.out.println(email);
        req.addResponseListener((response) -> {
            byte[] data = (byte[]) response.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (s.equals("success")) {

                Dialog.show("SUCCESS", "Changed was saved", "OK", null);

            } else {
                // Dialog.show("Erreur", "Echec de modification", "OK", null);

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public String getPasswordByEmail(String email, Resources rs) {

        String url = Statics.BASE_URL + "/getPasswordByEmail?email=" + email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            json = new String(req.getResponseData()) + "";

            try {

                System.out.println("data ==" + json);

                Map<String, Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }
    
    public ArrayList<User> parseUsers(String jsonText) {
        try {
            Users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            for (Map<String, Object> obj : list) {
                User t = new User();

                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);

                t.setUsername(obj.get("username").toString());

                t.setEmail(obj.get("email").toString());
                t.setPassword(obj.get("password").toString());
                t.setImageUser(obj.get("imageUser").toString());
                 float etat = Float.parseFloat(obj.get("etat").toString());
                t.setEtat((int) etat);

                Users.add(t);
            }

        } catch (IOException ex) {

        }
        return Users;
    }
    
    
    public ArrayList<User>getAllUsers() {
        req = new ConnectionRequest();
     
        String url = Statics.BASE_URL + "/UserList";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Users;
    }
//Delete 

    public boolean deleteUser(int id) {
        String url = Statics.BASE_URL + "/deleteUser1/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    public boolean blockUser(int id) {
        String url = Statics.BASE_URL + "/blockUser1/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    public boolean unblockUser(int id) {
        String url = Statics.BASE_URL + "/UnblockUser1/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
}
