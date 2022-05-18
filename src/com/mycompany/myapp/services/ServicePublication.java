/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Publication;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author louay
 */
public class ServicePublication {

    public static ServicePublication instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private boolean resultat;
    public ArrayList<Publication> Publications;

    public static ServicePublication getInstance() {
        if (instance == null) {
            instance = new ServicePublication();
        }
        return instance;
    }

    public ServicePublication() {
        req = new ConnectionRequest();
    }

    public void addPub(Publication publication) {
        String url = Statics.BASE_URL + "/mobile/addPub?titre_publ=" + publication.getTitre() + "&description_publ=" + publication.getDescription() + "&image_publ=" + publication.getImage() + "&user=" + publication.getUser();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean deletePub(int id) {
        String url = Statics.BASE_URL + "/mobile/deletePub?pubId=" + id;

        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultat = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;
    }

    public Publication DetailPublication(int id, Publication publication) {

        String url = Statics.BASE_URL + "/mobile/detailPub?" + id;
        req.setUrl(url);

        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {

            JSONParser jsonp = new JSONParser();
            try {

                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                publication.setId(id);

            } catch (IOException ex) {
                System.out.println("error related to sql :( " + ex.getMessage());
            }

            System.out.println("data === " + str);

        }));

        NetworkManager.getInstance().addToQueueAndWait(req);

        return publication;

    }

    public void editPub(Publication publication) {
        String url = Statics.BASE_URL + "/mobile/editPub?titre_publ=" + publication.getTitre() + "&description_publ=" + publication.getDescription() + "&image_publ=" + publication.getImage() + "&pubId=" + publication.getId();
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<Publication> showPubs() {
        ArrayList<Publication> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/mobile/showPub";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapPublications = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPublications.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Publication pub = new Publication();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());

                        String titre = obj.get("titrePubl").toString();
                        String description = obj.get("descriptionPubl").toString();
                        String image = obj.get("imagePubl").toString();

                        pub.setId((int) id);
                        pub.setTitre(titre);
                        pub.setDescription(description);
                        pub.setImage(image);

                        //Date 
                        String DateConverter = obj.get("tempsPubl").toString().substring(obj.get("tempsPubl").toString().indexOf("timestamp") + 10, obj.get("tempsPubl").toString().lastIndexOf("}"));

                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String dateString = formatter.format(currentTime);
                        pub.setTemps(dateString);

                        //insert data into ArrayList result
                        result.add(pub);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;

    }

    public ArrayList<Publication> findPubs(String search) {
        ArrayList<Publication> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/mobile/findPub?titre_publ="+search;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapPublications = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPublications.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Publication pub = new Publication();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());

                        String titre = obj.get("titrePubl").toString();
                        String description = obj.get("descriptionPubl").toString();
                        String image = obj.get("imagePubl").toString();

                        pub.setId((int) id);
                        pub.setTitre(titre);
                        pub.setDescription(description);
                        pub.setImage(image);

                        //Date 
                        String DateConverter = obj.get("tempsPubl").toString().substring(obj.get("tempsPubl").toString().indexOf("timestamp") + 10, obj.get("tempsPubl").toString().lastIndexOf("}"));

                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String dateString = formatter.format(currentTime);
                        pub.setTemps(dateString);

                        //insert data into ArrayList result
                        result.add(pub);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;

    }
}
