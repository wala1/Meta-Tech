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
import com.codename1.messaging.Message;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Logidee
 */
public class ServiceCommande {
    public ArrayList<Commande> commands;
    boolean b= false;
    public static ServiceCommande instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCommande() {
         req = new ConnectionRequest();
    }

    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }
    
    
    public ArrayList<Commande> parseCommands(String jsonText){
        try {
            commands =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Commande t = new Commande();
                float id = Float.parseFloat(obj.get("id")+"");
                t.setId((int)id);
                t.setFirstName(obj.get("firstName")+"");
                t.setLastName(obj.get("lastName")+"");
                t.setStreetAdress(obj.get("street_Adress")+"");
                t.setCity(obj.get("city")+"");
                t.setZip_PostalCode(obj.get("zip_PostalCode")+"");
                t.setCountry(obj.get("country")+"");
                t.setCompany(obj.get("company")+"");
                t.setPhoneNumber(obj.get("phone_Number")+"");
                t.setPayementMethod(obj.get("payement_method")+"");
                t.setNewsletter(obj.get("newsletter")+"");
                t.setDate(obj.get("date")+"");
                t.setDelivery_comment(obj.get("delivery_comment")+"");
                t.setOrder_comment(obj.get("order_comment")+"");
                t.setEtat(obj.get("etat")+"");
                t.setCodeCoupon(obj.get("codeCoup")+"");
                t.setSubtotal(obj.get("subtotal")+"");
                t.setUserCommande(obj.get("user_commande")+"");
                
                System.out.println("t====="+ t);
                
                        
                commands.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return commands;
    } 
    
    public ArrayList<Commande> getAllCommands(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/CommandeJson";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commands = parseCommands(new String(req.getResponseData()));
                req.removeResponseListener(this);
                System.out.println("Commands==============" + commands);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        
        return commands;
    }
    
    
    public void ClearCommande(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/clearCommandeJson";
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
    
    public void ExcelCommands(){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/commandeExecelJson";
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
    
    public void RemoveCommande(int idCommande){
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/removeCommandeJson/"+idCommande;
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
    
            
    public void UpdateCommande(Commande commande){
        req = new ConnectionRequest();
        long phone;
        phone=commande.getPhone_Number().charAt(0)+commande.getPhone_Number().charAt(2)+
                commande.getPhone_Number().charAt(3)+
                commande.getPhone_Number().charAt(4)+
                commande.getPhone_Number().charAt(5)+
                commande.getPhone_Number().charAt(6)+
                commande.getPhone_Number().charAt(7)+
                commande.getPhone_Number().charAt(8);
        int zip = commande.getZip_PostalCode().charAt(0)+
                commande.getZip_PostalCode().charAt(1)+
                commande.getZip_PostalCode().charAt(2)+
                commande.getZip_PostalCode().charAt(3);
        
        String url = Statics.BASE_URL+"/updateCommandJson/id="+commande.getId()+"firstName="+commande.getFirstName()+"&lastName="+commande.getLastName()+"&street_Adress="+commande.getStreet_Adress()+"&city="+commande.getCity()+"&zip_PostalCode="+zip+"&country="+commande.getCountry()+"&company="+commande.getCompany()+"&phone_Number="+phone+"&payement_method="+commande.getPayement_method()+"&delivery_comment="+commande.getDelivery_comment()+"&order_comment="+commande.getOrder_comment()+"&codeCoup="+commande.getCodeCoup();                                               
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
            
    public boolean addCommand(Commande commande, double subtotal){
        
        req = new ConnectionRequest();
        String url = Statics.BASE_URL+"/addCommandJson/104?firstName="+commande.getFirstName()+"&lastName="+commande.getLastName()+"&street_Adress="+commande.getStreet_Adress()+"&city="+commande.getCity()+"&zip_PostalCode="+commande.getZip_PostalCode()+"&country="+commande.getCountry()+"&company="+commande.getCompany()+"&phone_Number="+commande.getPhone_Number()+"&payement_method="+commande.getPayement_method()+"&delivery_comment="+commande.getDelivery_comment()+"&order_comment="+commande.getOrder_comment()+"&codeCoup="+commande.getCodeCoup()+"&subtotal="+subtotal;                                               
        System.out.println("===>"+url);
        req.setUrl(url);
        //req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
                b=true;
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return b;
    }



    


}
