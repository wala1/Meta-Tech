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
import com.mycompany.myapp.entities.Calendar;
import static com.mycompany.myapp.services.ServiceUser.resultOk;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wala
 */
public class ServiceEvents {

    public ArrayList<Calendar> Events;

    public static ServiceEvents instance = null;
    public static boolean resultOk = true;

    private ConnectionRequest req;

    private ServiceEvents() {
        req = new ConnectionRequest();
    }

    public static ServiceEvents getInstance() {
        if (instance == null) {
            instance = new ServiceEvents();
        }
        return instance;
    }

    public ArrayList<Calendar> parseEvents(String jsonText) {
        try {
            Events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> eventsListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) eventsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Calendar t = new Calendar();

                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);

                t.setTitle(obj.get("title").toString());

                t.setDescription(obj.get("description").toString());
                t.setImageEvent(obj.get("imageEvent").toString());
                t.setStart(obj.get("start").toString());
                t.setEnd(obj.get("end").toString());
                
                float etat = Float.parseFloat(obj.get("etat").toString());
                t.setEtat((int) etat);

                Events.add(t);
            }

        } catch (IOException ex) {

        }
        return Events;
    }

    public ArrayList<Calendar> getAllEvents() {
        req = new ConnectionRequest();

        String url = Statics.BASE_URL + "EventsList";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Events;
    }

    public ArrayList<Calendar> getEventByID(String idEvent) {
        req = new ConnectionRequest();

        String url = Statics.BASE_URL + "EventView/"+idEvent;
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Events;
    }

    public boolean ajoutEvent(Calendar event) {

        String url = Statics.BASE_URL + "addEvent?title=" + event.getTitle() + "&description=" + event.getDescription() + "&start=" + event.getStart() + "&end=" + event.getEnd() + "&imageEvent=" + event.getImageEvent();

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;

    }
    public boolean ajoutEventser(Calendar event) {

        String url = Statics.BASE_URL + "addEventUser?title=" + event.getTitle() + "&description=" + event.getDescription() + "&start=" + event.getStart() + "&end=" + event.getEnd() + "&imageEvent=" + event.getImageEvent();

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;

    }
    //Delete 

    public boolean deleteEvent(int id) {
        String url = Statics.BASE_URL + "deleteEvent1/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public boolean modifierEvent(Calendar calendar) {
        String url = Statics.BASE_URL + "updateEvent?id=" + calendar.getId() + "&title=" + calendar.getTitle()+ "&description=" + calendar.getDescription() + "&start=" + calendar.getStart() + "&end=" + calendar.getEnd() + "&imageEvent=" + calendar.getImageEvent();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return resultOk;

    }

    public Calendar DetailCalendar(int id, Calendar ev) {

        String url = Statics.BASE_URL + "/detailEvent?" + id;
        req.setUrl(url);

        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {

            JSONParser jsonp = new JSONParser();
            try {

                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                ev.setTitle(obj.get("title").toString());
                ev.setDescription(obj.get("description").toString());
                ev.setStart(obj.get("start").toString());

                ev.setEnd(obj.get("end").toString());

                ev.setImageEvent(obj.get("imageEvent").toString());

            } catch (IOException ex) {
                System.out.println("error related to sql :( " + ex.getMessage());
            }

            System.out.println("data === " + str);

        }));

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return ev;

    }
    public boolean acceptEvent(int id) {
        String url = Statics.BASE_URL + "acceptRequest1/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    public boolean refuseEvent(int id) {
        String url = Statics.BASE_URL + "refuseRequest1/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

}
