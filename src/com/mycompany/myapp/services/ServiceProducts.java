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
import com.mycompany.myapp.entities.SousCategorie;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random; 
 
/**
 *
 * @author bhk
 */
public class ServiceProducts {
    
    String rate ; 
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
                t.setPrixProd((float)prixProd);
                float promoProd = Float.parseFloat(obj.get("promo_prod").toString());
                t.setPromoProd((float)promoProd);
                float stockPord = Float.parseFloat(obj.get("stockProd").toString());
                t.setStockProd((int)stockPord);
                
                
                
                if (Objects.nonNull(obj.get("ratingProd"))) { 
                    t.setRate(obj.get("ratingProd").toString());
                } 
                
                String test2 = obj.get("categorie_prod").toString() ;
                int sub2 = test2.indexOf("e=") + 2 ; 
                int finish2 = test2.length()-1 ;
                t.setCategorieProd(test2.substring(sub2,finish2));
                
                
                String test = obj.get("sousCategProd").toString() ;
                int sub = test.indexOf("g=") + 2 ; 
                int finish = test.length()-1 ;
                t.setSousCategorieProd(test.substring(sub,finish));
                
                 
                products.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return products;
    } 
    
    
    
    
    
    
    
    
    
    public ArrayList<Produit> getAllTasks(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/AllProducts";
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
    
    
    public ArrayList<Produit> getProductsBySearch(String categ){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/SearchProduct/"+categ;
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
        String url = Statics.BASE_URL+"/AllProducts/"+categ;
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
    
    
    public ArrayList<Produit> getProductsBySubCategory(String categ){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/AllProductsSub/"+categ;
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
    
    
    public ArrayList<Produit> getProductsByBoth(String categ, String subCateg){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/AllProductsSubandCat?categorie="+categ+"&sous_categorie="+subCateg;
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
    
    
    
    
    
    
    
    public ArrayList<Produit> getProductByID(String id){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/ProductById/"+id;
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
    
    
    /*public String getProductRating(String id){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"ProductAvis/"+id;
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        String rate   ; 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //rate = (new String(req.getResponseData())) ;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return rate;
    }*/
    
    
    public boolean DeleteProduct(int id) {
         
       String url = Statics.BASE_URL + "/DeleteProduct/"+id ;
    
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
    
    
    public boolean addProduct(Produit t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "/AddProduct?nom="+t.getNomProd()+"&desc="+t.getDescProd()+"&prix="+t.getPrixProd()+"&promo="+t.getPromoProd()+"&image="+t.getImageProd()+"&categorie="+t.getCategorieProd()+"&souscategorie="+t.getSousCategorieProd()+"&stock="+t.getStockProd() ;
    
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("nom", t.getNomProd()+""); 
       req.addArgument("desc", t.getDescProd()+""); 
       req.addArgument("prix", t.getPrixProd()+""); 
       req.addArgument("promo", t.getPromoProd()+""); 
       req.addArgument("image", t.getImageProd()+""); 
       req.addArgument("categorie", t.getCategorieProd()+""); 
       req.addArgument("souscategorie", t.getSousCategorieProd()+""); 
       req.addArgument("stock", t.getStockProd()+""); 
       
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
    
    public boolean editProduct(Produit t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "/ModifyProduct/"+t.getId()+"?nom="+t.getNomProd()+"&desc="+t.getDescProd()+"&prix="+t.getPrixProd()+"&promo="+t.getPromoProd()+"&image="+t.getImageProd()+"&categorie="+t.getCategorieProd()+"&souscategorie="+t.getSousCategorieProd()+"&stock="+t.getStockProd() ;
    System.out.println(url);
       req.setUrl(url); 
       req.setPost(false);
       req.addArgument("nom", t.getNomProd()+""); 
       req.addArgument("desc", t.getDescProd()+""); 
       req.addArgument("prix", t.getPrixProd()+""); 
       req.addArgument("promo", t.getPromoProd()+""); 
       req.addArgument("image", t.getImageProd()+""); 
       req.addArgument("categorie", t.getCategorieProd()+""); 
       req.addArgument("souscategorie", t.getSousCategorieProd()+""); 
       req.addArgument("stock", t.getStockProd()+""); 
       
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
    
    
    
    public String convertCurrency(String id){
        req = new ConnectionRequest();
         
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/ConvertRate/"+id;
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                rate = new String(req.getResponseData());
                System.out.println(rate) ; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return rate;
    }
    
    
    
    public String convertCrypto(){
        req = new ConnectionRequest();
         
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/ConvertCrypto";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                rate = new String(req.getResponseData());
                System.out.println(rate) ; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return rate;
    }
    
    
    
    
    // ==================================== ALIEXPRESS ==========================================
    
    public ArrayList<Produit> parseAmazon(String jsonText){
        try {
            products =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            int id = 1 ; 
            for(Map<String,Object> obj : list){
                Produit t = new Produit();
                
                 
                t.setId(id);
                id++ ; 
                t.setNomProd(obj.get("title").toString());
                t.setDescProd(""); 
                t.setImageProd(obj.get("thumbnail").toString()); 
                Random rand = new Random();
                int rand_int1 = rand.nextInt(1000);
                //int nombreAleatoire = 800 + (int)(Math.rand() * ((1400 - 800) + 1));
                t.setPrixProd((float)rand_int1+1000);
                
                t.setPromoProd(0);
                 
                t.setStockProd(10);
                
                  
                 
                products.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return products;
    } 
    
    
    
    
    
    public ArrayList<Produit> getAllAmazon(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/AllAmazon";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                products = parseAmazon(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }
    
}
