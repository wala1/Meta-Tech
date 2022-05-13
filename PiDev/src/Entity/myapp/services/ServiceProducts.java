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
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceProducts {

    public ArrayList<Produit> products;
    
    public static ServiceProducts instance=null;
    public boolean resultOK;
    
    private ConnectionRequest req;

    private ServiceProducts() {
         req = new ConnectionRequest();
    }

    public static ServiceProducts getInstance() {
        if (instance == null) {
            instance = new ServiceProducts();
        }
        return instance;
    }

    /*public boolean addTask(Task t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "create";
    
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("name", t.getName());
       req.addArgument("status", t.getStatus()+"");
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    } */ 

    public ArrayList<Produit> parseTasks(String jsonText){
        try {
            products =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Produit t = new Produit();
                
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                
                t.setNomProd(obj.get("nom_prod").toString());
                t.setDescProd(obj.get("desc_prod").toString());
                t.setNomProd(obj.get("nom_prod").toString());
                t.setImageProd(obj.get("image_prod").toString());
                float prixProd = Float.parseFloat(obj.get("prix_prod").toString());
                t.setPrixProd((int)prixProd);
                float promoProd = Float.parseFloat(obj.get("promo_prod").toString());
                t.setPromoProd((int)promoProd);
                float stockPord = Float.parseFloat(obj.get("stockProd").toString());
                t.setStockProd((int)stockPord);
                
                /*String categs = obj.get("categorie_prod").toString();
                String categorie = "" ; 
                
                JSONParser p = new JSONParser();
                Map<String,Object> prods = p.parseJSON(new CharArrayReader(categs.toCharArray()));
                List<Map<String,Object>> productsList = (List<Map<String,Object>>)prods.get("root");
                for(Map<String,Object> obj1 : productsList){
                    categorie = obj1.get("nom_categorie").toString() ; 
                }*/
                t.setCategorieProd(obj.get("categorie_prod").toString());
                
                 
                products.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return products;
    } 
    
    
    
    
    
    public ArrayList<Produit> getAllTasks(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"AllProducts";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                products = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }
    
    
    
    
    
    
    
    
    
    
    public ArrayList<Produit> getProductsByCategory(String categ){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"AllProducts/"+categ;
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                products = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }
}
