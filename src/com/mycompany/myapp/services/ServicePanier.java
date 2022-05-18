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
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Logidee
 */
public class ServicePanier {
    public ArrayList<Panier> paniers;
    
    public static ServicePanier instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServicePanier() {
         req = new ConnectionRequest();
    }

    public static ServicePanier getInstance() {
        if (instance == null) {
            instance = new ServicePanier();
        }
        return instance;
    }
    
    
    public ArrayList<Panier> parsePaniers(String jsonText){
        try {
            paniers =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Panier t = new Panier();
                
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setQuantite(obj.get("quantite").toString());
                t.setUser(obj.get("user_panier").toString());
                t.setProduit(obj.get("produit_panier").toString());
                System.out.println("t====="+ t);
                /*
                List<Map<String,Object>> listUser = (List<Map<String,Object>>)tasksListJson.get("user_panier");
                for(Map<String,Object> user : listUser){
                    User u = new User();
                    float idUser = Float.parseFloat(user.get("id").toString());
                    u.setId((int)idUser);
                    t.setUser(u);
                }
                List<Map<String,Object>> listProduit = (List<Map<String,Object>>)tasksListJson.get("produit_panier");
                for(Map<String,Object> produit : listProduit){
                    Produit p = new Produit();
                    float idProduit = Float.parseFloat(produit.get("id").toString());
                    p.setId((int)idProduit);
                    t.setProduit(p);
                } */
                
                        
                paniers.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return paniers;
    } 
    
    public ArrayList<Panier> getAllPaniers(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/PanierJson/197";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                paniers = parsePaniers(new String(req.getResponseData()));
                req.removeResponseListener(this);
                System.out.println("Paniers==============" + paniers);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        
        return paniers;
    }

    public ArrayList<Panier> RemovePanier(int idProduct){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/deletePanierJson/"+idProduct;
        System.out.println("===>"+url);
        req.setUrl(url);
        //req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                paniers = parsePaniers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        
        return paniers;
    }
    
    public void ClearPanier(int idUser){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/clearPanierJson/197";
        System.out.println("===>"+url);
        req.setUrl(url);
        //req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
            
    public void UpdatePanier(int idPanier, int qty){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/updatePanierJson/"+idPanier+"?quantite="+qty;
        System.out.println("===>"+url);
        req.setUrl(url);
        //req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    public void addPanierJson(int idU, int idP, int qty){
        
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/addPanierJson/"+idP+"/"+idU+"?quantite="+qty;
        System.out.println("===>"+url);
        req.setUrl(url);
        //req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
}
