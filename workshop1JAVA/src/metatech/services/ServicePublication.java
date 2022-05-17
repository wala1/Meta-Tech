/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.services;

import metatech.utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import metatech.entities.Publication;

/**
 *
 * @author abdelazizmezri
 */
public class ServicePublication implements IService<Publication> {

    Connection cnx = DataSource.getInstance().getCnx();
    ServiceUser serviceUser = new ServiceUser();
    @Override
    public void ajouter(Publication p) {
        try {
            String req = "INSERT INTO `publication`( `utilisateur_id`, `titre_publ`, `description_publ`, `image_publ`, `temps_publ`) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, serviceUser.getConnected().getId());
            ps.setString(2, p.getTitre());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getImage());
            long millis=System.currentTimeMillis();
            Date dateNow=new Date(millis);
            ps.setDate(5, (Date) dateNow);
            ps.executeUpdate();
            System.out.println("Puvlication created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `publication` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Publication deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Publication p) {
        try {
            String req = "UPDATE `publication` SET `utilisateur_id`=?,`titre_publ`=?,`description_publ`=?,`image_publ`=?,`temps_publ`=? WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, serviceUser.getConnected().getId());
            ps.setString(2, p.getTitre());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getImage());
            long millis=System.currentTimeMillis();
            Date dateNow=new Date(millis);
            ps.setDate(5, dateNow);
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            System.out.println("publicatioin updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Publication> getAll() {
        List<Publication> list = new ArrayList<>();
        try {
            String req = "SELECT `id`,  `titre_publ`, `description_publ`, `image_publ`, `temps_publ` , `utilisateur_id` FROM `publication`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Publication p = new Publication(rs.getInt(1), rs.getString(2), rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public List<Publication> searshAll(String criteria) {
        List<Publication> list = new ArrayList<>();
        try {
            String req = "SELECT `id`,  `titre_publ`, `description_publ`, `image_publ`, `temps_publ` , `utilisateur_id` FROM `publication` where `titre_publ` like '%" + criteria 
                    +"%' or  `description_publ` like '%"+criteria+"%' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Publication p = new Publication(rs.getInt(1), rs.getString(2), rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public List<Publication> getAllSorted( String sortColumn , String orientation ) {
        List<Publication> list = new ArrayList<>();
        try {
            String req = "SELECT `id`,  `titre_publ`, `description_publ`, `image_publ`, `temps_publ` , `utilisateur_id` FROM `publication`order by " + sortColumn + " " + orientation ;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Publication p = new Publication(rs.getInt(1), rs.getString(2), rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
        public List<Publication> getOtherPubs( ) {
        List<Publication> list = new ArrayList<>();
        try {
            String req = "SELECT `id`,  `titre_publ`, `description_publ`, `image_publ`, `temps_publ` , `utilisateur_id` FROM `publication` where utilisateur_id <> " + serviceUser.getConnected().getId() ;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Publication p = new Publication(rs.getInt(1), rs.getString(2), rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
        
        public List<Publication> getMyPubs( ) {
        List<Publication> list = new ArrayList<>();
        try {
            String req = "SELECT `id`,  `titre_publ`, `description_publ`, `image_publ`, `temps_publ` , `utilisateur_id` FROM `publication` where utilisateur_id = " + serviceUser.getConnected().getId() ;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Publication p = new Publication(rs.getInt(1), rs.getString(2), rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
}
