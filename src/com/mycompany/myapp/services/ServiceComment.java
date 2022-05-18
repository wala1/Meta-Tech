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
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Publication;
import com.mycompany.myapp.utils.Statics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author louay
 */
public class ServiceComment {

    public static ServiceComment instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private boolean resultat;
    public ArrayList<Commentaire> Commentaires;

    public static ServiceComment getInstance() {
        if (instance == null) {
            instance = new ServiceComment();
        }
        return instance;
    }

    public ServiceComment() {
        req = new ConnectionRequest();
    }

    public void addComment(Commentaire commentaire, Publication publication) {
//id mtaa user khallitou statique lyn tsir l'intégration
        int userId = 1;
        String url = Statics.BASE_URL + "/mobile/addComment?id_publ=" + publication.getId() + "&description_comm=" + commentaire.getDescription() + "&user=" + userId;
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean deleteComment(int id) {
        String url = Statics.BASE_URL + "/mobile/deleteComment?id_comm=" + id;

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

    public boolean editPub(Commentaire commentaire) {
        String url = Statics.BASE_URL + "/mobile/editComment?id_comm=" + commentaire.getId() + "&description_comm=" + commentaire.getDescription();
        req.setUrl(url); //Insert url in connection request

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

    public ArrayList<Commentaire> showComments(Publication publication) {
        ArrayList<Commentaire> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/mobile/showPubComment?id_Publ=" + publication.getId();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapReclamations.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Commentaire re = new Commentaire();

                        float id = Float.parseFloat(obj.get("id").toString());

                        String description = obj.get("descriptionComm").toString();

                        re.setId((int) id);
                        re.setDescription(description);

                        //Date 
                        String DateConverter = obj.get("tempsComm").toString().substring(obj.get("tempsComm").toString().indexOf("timestamp") + 10, obj.get("tempsComm").toString().lastIndexOf("}"));

                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String dateString = formatter.format(currentTime);
                        re.setTemps(dateString);

                        //insert data into ArrayList result
                        result.add(re);
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
