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
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Abdelaziz
 */
public class ServiceReclamations {
    public ArrayList<Reclamation> categories;
    public static ServiceReclamations instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceReclamations() {
         req = new ConnectionRequest();
    }

    public static ServiceReclamations getInstance() {
        if (instance == null) {
            instance = new ServiceReclamations();
        }
        return instance;
    }
    
    
    public boolean addReclamation(Reclamation t) {
        System.out.println(t);
        System.out.println("********");
       //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
       String url = Statics.BASE_URL + "/AddReport?nameReclamation="+t.getNameReclamation()+"&emailReclamation="+t.getEmailReclamation()+"&descReclamation="+t.getDescReclamation();
    
       req.setUrl(url);
       req.setPost(false);
       req.addArgument("nameReclamation", t.getNameReclamation());
       req.addArgument("emailReclamation", t.getEmailReclamation()+"");
       req.addArgument("descReclamation", t.getDescReclamation()+"");
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
