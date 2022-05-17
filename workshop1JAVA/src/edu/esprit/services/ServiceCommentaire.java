/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;


import edu.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.esprit.entities.Commentaire;

/**
 *
 * @author abdelazizmezri
 */
public class ServiceCommentaire implements iService<Commentaire> {

    Connection cnx = DataSource.getInstance().getCnx();
    ServiceUser serviceUser = new ServiceUser();
    @Override
    public void ajouter(Commentaire p) {
        try {
            String req = "INSERT INTO `commentaire`( `id_publ_id`, `utilisateur_id`, `description_comm`, `temps_comm`) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getPubId());
            ps.setInt(2, serviceUser.getConnected().getId());
            ps.setString(3, p.getDescription());
            long millis=System.currentTimeMillis();
            Date dateNow=new Date(millis);
            ps.setDate(4, dateNow);
            ps.executeUpdate();
            System.out.println("Comment created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `commentaire` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Comment deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Commentaire p) {
        try {
            String req = "UPDATE `commentaire` SET id_publ_id`=?,`utilisateur_id`=?,`description_comm`=?,`temps_comm`=? where id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getPubId());
            ps.setInt(2, serviceUser.getConnected().getId());
            ps.setString(3, p.getDescription());
            long millis=System.currentTimeMillis();
            Date dateNow=new Date(millis);
            ps.setDate(4, dateNow);
             ps.setInt(5, p.getId());
            ps.executeUpdate();
            System.out.println("comment updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Commentaire> getAll() {
        List<Commentaire> list = new ArrayList<>();
        try {
            String req = "SELECT `id`, `description_comm`, `temps_comm` ,  `id_publ_id`, `utilisateur_id` FROM `commentaire` order by temps_comm desc";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Commentaire p = new Commentaire(rs.getInt(1) , rs.getString(2) , rs.getDate(3) , rs.getInt(4) , rs.getInt(5));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
     
    public List<Commentaire> getByPubId(int id) {
        List<Commentaire> list = new ArrayList<>();
        try {
            String req = "SELECT `id`, `description_comm`, `temps_comm` ,  `id_publ_id`, `utilisateur_id` FROM `commentaire` where id_publ_id = "+ id +" order by temps_comm desc";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Commentaire p = new Commentaire(rs.getInt(1) , rs.getString(2) , rs.getDate(3) , rs.getInt(4) , rs.getInt(5));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
}
