/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.entities.SousCategorie;
import edu.esprit.services.iService;
import edu.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelaziz
 */
public class ServiceAvis  implements iService<Avis>{
    Connection cnx= DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Avis p) {
        try {   
            
             Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String req = "INSERT INTO `avis` ( `id_prod_id`,`id_user_id` ,`rating_avis`,`desc_avis`,`time_avis`) VALUES ('"+p.getIdProd()+"','"+p.getIdUser()+"','"+p.getRatingAvis()+"','"+p.getDescAvis()+"','"+timestamp+ "')";

            Statement st = cnx.createStatement();
            st.executeUpdate(req);            
            System.out.println("Avis added ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `avis` WHERE `id` = '" + id+"'";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Avis deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Avis p) {
        try { 
            String req = "UPDATE `avis` SET `rating_avis` = '"+p.getRatingAvis()+"',`desc_avis` = '"+p.getDescAvis()+"' WHERE `id`='"+p.getId()+"'" ;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Avis updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Avis> getAll() {
        List<Avis> list = new ArrayList<>();
        try {
            String req = "Select * from avis";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                ServiceProduit sp = new ServiceProduit();  
                
                Produit p = new Produit(); 
                p = sp.getById(rs.getInt("id_prod_id")) ; 
                
                Avis a = new Avis(rs.getInt(1),rs.getInt(2) ,rs.getInt(3),rs.getInt(4), rs.getString("desc_avis") );
                list.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    
    public List<Avis> getByIdProd(int id) {
        List<Avis> list = new ArrayList<>();
        try {
            String req = "Select * from avis WHERE `id_prod_id` = '"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                ServiceProduit sp = new ServiceProduit();  
                
                Produit p = new Produit(); 
                p = sp.getById(rs.getInt("id_prod_id")) ; 
                
                Avis a = new Avis(rs.getInt(1),rs.getInt(2) ,rs.getInt(3),rs.getInt(4), rs.getString("desc_avis") );
                list.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    
}
