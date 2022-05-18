/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Publicity;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wala
 */
public class ServicePublicity {
    public static boolean resultOk = true;

    public ArrayList<Publicity> Pub;

    public static ServicePublicity instance = null;
    public boolean resultOK;

    private ConnectionRequest req;

    private ServicePublicity() {
        req = new ConnectionRequest();
    }

    public static ServicePublicity getInstance() {
        if (instance == null) {
            instance = new ServicePublicity();
        }
        return instance;
    }

    
    public ArrayList<Publicity> parsePub(String jsonText) {
        try {
            Pub = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> PublicityJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) PublicityJson.get("root");
            for (Map<String, Object> obj : list) {
                Publicity p = new Publicity();

                float id = Float.parseFloat(obj.get("id").toString());
                p.setId((int) id);

                p.setNomPub(obj.get("nomPub").toString());

                p.setDescPub(obj.get("descPub").toString());
                p.setImageName(obj.get("imageName").toString());
                Pub.add(p);
            }

        } catch (IOException ex) {

        }
        return Pub;
    }

    
    public ArrayList<Publicity> getAllPub(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/PublicityJson";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Pub = parsePub(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Pub;
    }
  
       //ajout 
    public boolean ajoutPublicity(Publicity pub) {
        
        String url =Statics.BASE_URL+"/addPublicityJson?name="+pub.getNomPub()+"&promo="+pub.getPromoPub()+"&image="+pub.getImageName();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return true;
        
    }
    
   //Delete 
    public boolean deletePublicity(int id ) {
        String url = Statics.BASE_URL +"/deletePublicityJson/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
     
    //Update 
    public boolean modifierPublicity(Publicity pub) {
        String url = Statics.BASE_URL +"/updatePublicityJson?id="+pub.getId()+"&nom="+pub.getNomPub()+"&description="+pub.getDescPub()+"&image="+pub.getImageName()+"&price="+pub.getPrixPub()+"&image="+pub.getPromoPub();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return true;
        
    }
    
}
  