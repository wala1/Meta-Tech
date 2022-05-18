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
import com.mycompany.myapp.entities.Avis; 
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Abdelaziz
 */
public class ServiceAvis {
    public ArrayList<Avis> avis;
    
    public static ServiceAvis instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceAvis() {
         req = new ConnectionRequest();
    }

    public static ServiceAvis getInstance() {
        if (instance == null) {
            instance = new ServiceAvis();
        }
        return instance;
    }
    
    
    
    
    public boolean addReview(Avis t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "/AddReview?id_prod="+t.getIdProd()+"&id_user="+t.getIdUser()+"&desc="+t.getDescAvis()+"&rating="+t.getRatingAvis() ;
    
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("id_prod", t.getIdProd()+"");
       req.addArgument("id_user", t.getIdUser()+"");
       req.addArgument("desc", t.getDescAvis()+"");
       req.addArgument("rating", t.getRatingAvis()+"");
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
    
    
    public boolean EditReview(Avis t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "/EditReview/"+t.getId()+"?desc="+t.getDescAvis()+"&rating="+t.getRatingAvis() ;
    
       req.setUrl(url);
       req.setPost(false); 
       req.addArgument("desc", t.getDescAvis()+"");
       req.addArgument("rating", t.getRatingAvis()+"");
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
    
    
    public boolean DeleteReview(int id) {
         
       String url = Statics.BASE_URL + "/DeleteAvis/"+id ;
    
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
    
    
    
    public ArrayList<Avis> parseTasks(String jsonText){
        try {
            avis =new ArrayList<>();
            JSONParser j = new JSONParser();
            
            
            
            
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                
                Avis t = new Avis();
                
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                
                /*String idUserJSON = obj.get("idUser").toString() ;
                int idUser = 0 ; 
                System.out.println(idUserJSON) ; 
                
                
                
                JSONParser n = new JSONParser();
                Map<String,Object> tasks =  n.parseJSON(new CharArrayReader(idUserJSON.toCharArray()));
                List<Map<String,Object>> listIdUser = (List<Map<String,Object>>)tasks.get("root");
                for(Map<String,Object> obj2 : listIdUser){
                    float idUs = Float.parseFloat(obj2.get("id").toString());
                    idUser = ((int)idUs);   
                }
                System.out.println("ID USER : "+idUser); 
                 System.out.println("ID USER : "+idUser); */
                String test = obj.get("idUser").toString() ;
                //System.out.println(test) ; 
                int sub = test.indexOf("username") + 9 ; 
int finish = test.indexOf("password") - 2 ;
                t.setIdUser(test.substring(sub,finish));
                //System.out.println(test.substring(sub,finish)) ; 
                
                //t.setIdUser(idUser);
                t.setDescAvis(obj.get("descAvis").toString());  
                t.setRatingAvis(obj.get("ratingAvis").toString());  
                //t.setRatingAvis(obj.get("timeAvis").toString());  
                avis.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return avis;
    }
    
    
    public ArrayList<Avis> getAllAvis(String idProd){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/AllAvis/"+idProd;
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                avis = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return avis;
    }
}
