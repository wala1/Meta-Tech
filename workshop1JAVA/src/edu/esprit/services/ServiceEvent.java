/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Event;
import edu.esprit.utils.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wala
 */
public class ServiceEvent implements iService<Event> {

    private DataSource ds = DataSource.getInstance();

    @Override
    public void ajouter(Event p) {

        try {
            String requete = "INSERT INTO calendar (title, start,end,description,image_event,nbr_max_part,etat) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(requete);
            st.setString(1, p.getTitle());
            st.setString(2, (p.getDateDebut()));
            st.setString(3, p.getDateFin());
            st.setString(4, p.getDescription());
            st.setString(5, p.getImageEvent());
            st.setInt(6, p.getNbrPartMax());
            st.setInt(7, 1);

            System.out.println("Event was added successfully");

            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int checkEvent(int id) {
        int etat = 0;
        try {
            String req;
            req = "select etat from calendar where id=?";
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(req);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                if (rs.getInt("etat") == 1) {
                    return 1; // approved

                } else if (rs.getInt("etat") == 0) {
                    return 0; //not yet
                }
            }

        } catch (SQLException ex) {
        }
        return etat;
    }

    public void AddEventRequest(Event p) {

        try {
            String requete = "INSERT INTO calendar (title, start,end,description,image_event,nbr_max_part,etat) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(requete);
            st.setString(1, p.getTitle());
            st.setString(2, (p.getDateDebut()));
            st.setString(3, p.getDateFin());
            st.setString(4, p.getDescription());
            st.setString(5, p.getImageEvent());
            st.setInt(6, p.getNbrPartMax());
            st.setInt(7, 0);

            System.out.println("Event request was sent to Admin");

            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void modifier(Event p) {
        try {
            String req = "UPDATE `calendar` SET `title` = '" + p.getTitle() + "', `start` = '" + p.getDateDebut() + "', `end` = '" + p.getDateFin() + "', `description` = '" + p.getDescription() + "', `image_event` = '" + p.getImageEvent() + "', `nbr_part` = '" + p.getNbrPart() + "', `nbr_max_part` = '" + p.getNbrPartMax() + "' WHERE `calendar`.`id` = " + p.getId();
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Event was updated suuccessfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier1(Event p, int id) {
        try {
            String req = "UPDATE `calendar` SET `title` = '" + p.getTitle() + "', `start` = '" + p.getDateDebut() + "', `end` = '" + p.getDateFin() + "', `description` = '" + p.getDescription() + "', `image_event` = '" + p.getImageEvent() + "', `nbr_part` = '" + p.getNbrPart() + "', `nbr_max_part` = '" + p.getNbrPartMax() + "' WHERE `calendar`.`id` = " + id;
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Event was updated suuccessfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `calendar` WHERE `calendar`.`id` =" + id;
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Event was removed Successfuly !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public boolean delete(Event p) {
        String req = "DELETE FROM calendar WHERE id=?";
        boolean delete = false;
        try {
            PreparedStatement ps = new DataSource().getCnx().prepareStatement(req);
            ps.setInt(1, p.getId());
            delete = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return delete;
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        try {
            String req = "SELECT * FROM `calendar` WHERE `etat`=1";
            //SELECT * FROM `calendar`
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(req);
          //  st.setInt(1, 0);
            ResultSet result = st.executeQuery(req);

            while (result.next()) {

                Event e = new Event(result.getInt("id"), result.getString("title"), result.getString("description"), result.getString("image_event"), result.getString("start"), result.getString("end"), result.getInt("nbr_part"), result.getInt("nbr_max_part"));
                events.add(e);

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return events;
    }

    public void participerEvenement(int ref_evenement, int id_user) {
        String sql = "insert into participants(event_id,user_id) VALUES('" + ref_evenement + "','" + id_user + "')";
        //"INSERT INTO participants( event_id, user_id) VALUES (?,?)";
        //"insert into prticipants(event_id,user_id) VALUES(" + ref_evenement + "," + id_user + "')";
        Event e = getEvent(ref_evenement);
        if (e.getNbrPart() < e.getNbrPartMax()) {
            try {
                PreparedStatement st;
                st = ds.getCnx().prepareStatement(sql);

                st.executeUpdate(sql);
                System.out.println("participer");
                incrementerParticipant(ref_evenement);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());

            }
        } else {
            System.out.println("nbr max  atteint");
        }
    }

    public void incrementerParticipant(int ref_evenement) {
        String sql = "update calendar set nbr_part=nbr_part+1 where id=" + ref_evenement;
        try {
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(sql);
            st.executeUpdate(sql);
            System.out.println("un participant a été ajouté..");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public Event getEvent(int ref) {
        Event e = new Event();

        try {

            String req = "SELECT * FROM `calendar` where id=" + ref;
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(req);

            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDateDebut(rs.getString("start"));
                e.setDateFin(rs.getString("end"));
                e.setDescription(rs.getString("description"));
                e.setImageEvent(rs.getString("image_event"));
                e.setNbrPart(rs.getInt("nbr_part"));
                e.setNbrPartMax(rs.getInt("nbr_max_part"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return e;

    }

    public ObservableList<Event> afficher() {
        ObservableList<Event> evenements = FXCollections.observableArrayList();

        try {
            String querry = "SELECT * FROM `calendar` WHERE `etat`=1";

            PreparedStatement st;
            st = ds.getCnx().prepareStatement(querry);

            ResultSet rs = st.executeQuery(querry);

            while (rs.next()) {
                Event e = new Event();

                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDateDebut(rs.getString("start"));
                e.setDateFin(rs.getString("end"));
                e.setDescription(rs.getString("description"));
                e.setImageEvent(rs.getString("image_event"));
                e.setNbrPart(rs.getInt("nbr_part"));
                e.setNbrPartMax(rs.getInt("nbr_max_part"));

                evenements.add(e);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return evenements;

    }
     public ObservableList<Event> afficherRequests() {
        ObservableList<Event> evenements = FXCollections.observableArrayList();

        try {
            String querry = "SELECT * FROM `calendar` WHERE `etat`=0";

            PreparedStatement st;
            st = ds.getCnx().prepareStatement(querry);

            ResultSet rs = st.executeQuery(querry);

            while (rs.next()) {
                Event e = new Event();

                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDateDebut(rs.getString("start"));
                e.setDateFin(rs.getString("end"));
                e.setDescription(rs.getString("description"));
                e.setImageEvent(rs.getString("image_event"));
                e.setNbrPart(rs.getInt("nbr_part"));
                e.setNbrPartMax(rs.getInt("nbr_max_part"));

                evenements.add(e);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return evenements;

    }
     public void setApproved(int id){
           String sql = "update calendar set etat=1 where id=" + id;
        try {
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(sql);
            st.executeUpdate(sql);
            System.out.println("event was approved to be shared");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
         
         
     }
     public int  countRequests(){
         int total=0;
         String sql = "SELECT COUNT(*) as total FROM calendar where etat=0" ;
        try {
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(sql);
             ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                
                total=rs.getInt("total");
                return total;
           
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return total;

     }


}
