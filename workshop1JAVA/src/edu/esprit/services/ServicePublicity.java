/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.publicity;
import edu.esprit.utils.DataSource;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author mejri
 */
public class ServicePublicity implements iService_P<publicity> {

    Connection cnx = DataSource.getInstance().getCnx();

    //***************************************************************//
    //**************************** AJOUT ****************************//
    //***************************************************************//
    @Override
    public void ajouter(publicity p) {
        try {
            String req = "INSERT INTO `pub_back` ( `nom_pub`, `desc_pub`, `prix_pub`, `promo_pub`, `image_name`) VALUES ('" + p.getNomPub() + "','" + p.getDescPub() + "','" + p.getPrixPub() + "','" + p.getPromoPub() + "','" + p.getImageName() + "')";

            Statement st = cnx.createStatement();
            st.executeUpdate(req);

            System.out.print("                         *************************************************\n");
            System.out.print("                            *******  PUBLICITY ADDED SUCCEFULLY  *******\n");
            System.out.print("                         ************************************************\n\n\n");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    //***************************************************************//
    //**************************** MODIFICATION *********************//
    //***************************************************************//

    @Override
    public void modifier(publicity p, int id_pub) {
        // try {
//            String req = "UPDATE `pub_back` SET `nom` = '" + p.getNomPub()+ "', `desc` = '" + p.getDescPub()+"', `image` = '" + p.getImageName()+"', `prix` = '" + p.getPrixPub()+"', `promo` = '" + p.getPromoPub()+ "' WHERE `pub_back`.`id` = " + id_pub;
//            Statement st = cnx.createStatement();
//            st.executeUpdate(req);
//            System.out.print("                         *************************************************\n");
//            System.out.print("                                ********  PUBLICITY UPDATED SUCCEFULLY  **********\n");
//            System.out.print("                         ************************************************\n\n\n"); 
        //    } catch (SQLException ex) {
//            System.out.println(ex.getMessage());

        // }
        PreparedStatement ps;
        String query = "UPDATE `pub_back` SET `nom_pub`=?,`desc_pub`=?,`image_name`=?,`prix_pub`=?,`promo_pub`=? WHERE `id`=?";
        try {

            ps = cnx.prepareStatement(query);
            ps.setString(1, p.getNomPub());
            ps.setString(2, p.getDescPub());
            ps.setString(3, p.getImageName());
            ps.setFloat(4, p.getPrixPub());
            ps.setFloat(5, p.getPromoPub());
            ps.setInt(6, id_pub);
            ps.execute();
            System.out.print("                         *************************************************\n");
            System.out.print("                                ********  PUBLICITY UPDATED SUCCEFULLY  **********\n");
            System.out.print("                         ************************************************\n\n\n");
        } catch (Exception e) {
        }
    }
    //**********************************************************//
    //**************************** SUPPRISSION  **********************//
    //**********************************************************//

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `pub_back` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);

            System.out.print("                         *************************************************\n");
            System.out.print("                                ********  PUBLICITY DELETED SUCCEFULLY  **********\n");
            System.out.print("                         ************************************************\n\n\n");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //***************************************************************//
    //**************************** AFFICHAGE ****************************//
    //***************************************************************//
    @Override
    public List<publicity> getAll() {
        List<publicity> list = new ArrayList<>();
        try {
            String req = "Select * from pub_Back";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            System.out.print("                         *************************************************\n");
            System.out.print("                                ********  ALL PUBLICITY    **********\n");
            System.out.print("                         ************************************************\n\n\n");

            while (rs.next()) {
                publicity p = new publicity(rs.getInt(1), rs.getString("nom_Pub"), rs.getString("desc_Pub"), rs.getFloat("prix_Pub"), rs.getFloat("promo_Pub"), rs.getString("image_name"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    /**
     * ************ AfficherTout : listView
     */
    @Override
    public ObservableList<publicity> Affichertout() {
        ObservableList<publicity> list = FXCollections.observableArrayList();
        String requete = "SELECT * FROM pub_back";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new publicity(rs.getInt("id"), rs.getString("nom_Pub"), rs.getString("desc_Pub"), rs.getFloat("prix_Pub"), rs.getFloat("promo_Pub"), rs.getString("image_name")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;

    }

    public int AboveOneHundred() throws SQLException {

        String req = "SELECT COUNT(*) FROM pub_back where prix_Pub > 100 ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        rs.next();
        int count = rs.getInt(1);

        return count;
    }

    public int UnderOneHundred() throws SQLException {

        String req = "SELECT COUNT(*) FROM pub_back where prix_Pub < 100 ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        rs.next();
        int count = rs.getInt(1);

        return count;
    }

   
   
}
