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
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.entities.SousCategorie;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Abdelaziz
 */
public class ServiceSubCategories {
     public ArrayList<SousCategorie> sousCategories;
    
    public static ServiceSubCategories instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceSubCategories() {
         req = new ConnectionRequest();
    }

    public static ServiceSubCategories getInstance() {
        if (instance == null) {
            instance = new ServiceSubCategories();
        }
        return instance;
    }
    
    
    public ArrayList<SousCategorie> parseTasks(String jsonText){
        try {
            sousCategories =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                SousCategorie t = new SousCategorie();
                
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                
                t.setNomSousCategorie(obj.get("nomSousCateg").toString()); 
                
                 
                sousCategories.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return sousCategories;
    }
    
    
    public ArrayList<SousCategorie> getAllSubCategories(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/AllSubCategories";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                sousCategories = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return sousCategories;
    }
    
    
    public boolean DeleteSubCategory(int id) {
         
       String url = Statics.BASE_URL + "/DeleteSubCategory/"+id ;
    
       req.setUrl(url);
       req.setPost(false); 
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
    public boolean addSubCategory(SousCategorie t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "/AddSubCategory" ;
    
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("nom", t.getNomSousCategorie()+""); 
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
    public boolean editSubCategory(SousCategorie t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "/ModifySubCategory/"+t.getId() ;
    
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("nom", t.getNomSousCategorie()+""); 
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
