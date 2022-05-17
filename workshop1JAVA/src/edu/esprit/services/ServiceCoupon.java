/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Coupon;
import edu.esprit.entities.publicity;
import edu.esprit.utils.DataSource;
import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mejri
 */
public class ServiceCoupon implements iService_P<Coupon>{
        private DataSource ds = DataSource.getInstance();

        Connection cnx= DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Coupon p) {
   try {   
            String req = "INSERT INTO `coupon` ( `code_coup`) VALUES ('"+p.getCodeCoupon()+"')";

            Statement st = cnx.createStatement();
            st.executeUpdate(req);

            System.out.print("                         *************************************************\n");
            System.out.print("                            *******  COUPON ADDED SUCCEFULLY  *******\n");
            System.out.print("                         ************************************************\n\n\n");       
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }    }

    @Override
    public void modifier(Coupon p,int id) {
    try {
            String req = "UPDATE `coupon` SET `nom` = '" + p.getCodeCoupon()+ "' WHERE `coupon`.`id` = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.print("                         *************************************************\n");
            System.out.print("                                ********  COUPON UPDATED SUCCEFULLY  **********\n");
            System.out.print("                         ************************************************\n\n\n");         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `coupon` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);

            System.out.print("                         *************************************************\n");
            System.out.print("                                ********  COUPON DELETED SUCCEFULLY  **********\n");
            System.out.print("                         ************************************************\n\n\n");   
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }      }

    @Override
    public List<Coupon> getAll() {
        List<Coupon> list = new ArrayList<>();
        try {
            String req = "Select * from coupon";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
          
            System.out.print("                         *************************************************\n");
            System.out.print("                                ********  ALL COUPON    **********\n");
            System.out.print("                         ************************************************\n\n\n");

            while(rs.next()){
                Coupon c = new Coupon(rs.getInt(1), rs.getString("code_coup"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    
    public String RandomCode() {
                   String randomString = "";
         try {
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                int length = 5;
                Random rand = new Random();
                char[] text = new char[length];
                for(int i=0;i <length;i++){
                    text[i]= characters.charAt(rand.nextInt(characters.length()));
                }
                for(int i=0;i<text.length;i++){
                    randomString += text[i];
                }
                // System.out.println("Code Coupon for -20% : ");
                String query ="INSERT INTO `coupon` ( `code_coup`) VALUES ('"+randomString+"')";
                Statement st = cnx.createStatement();
                st.executeUpdate(query);
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
            return randomString;
  
                                      
    }



 public ObservableList<Coupon> afficher() {
        ObservableList<Coupon> coupous = FXCollections.observableArrayList();

        try {
            String querry = "SELECT * FROM `coupon`";

            PreparedStatement st;
            st = ds.getCnx().prepareStatement(querry);

            ResultSet rs = st.executeQuery(querry);

            while (rs.next()) {
                Coupon c = new Coupon();

                c.setId(rs.getInt("id"));
                c.setCodeCoupon(rs.getString("code_coup"));
   

                coupous.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return coupous;

    }

    @Override
    public ObservableList<publicity> Affichertout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     public Coupon getCoupon(int ref) {
        Coupon e = new Coupon();

        try {

            String req = "SELECT * FROM `coupon` where id=" + ref;
            PreparedStatement st;
            st = ds.getCnx().prepareStatement(req);

            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                e.setId(rs.getInt("id"));
                e.setCodeCoupon(rs.getString("code_coupon"));


            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return e;

    }
 
}
