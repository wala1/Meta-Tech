/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.entities.SousCategorie;
import edu.esprit.services.iService;
import edu.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdelaziz
 */
public class ServiceSousCategorie  implements iService<SousCategorie>{
    Connection cnx= DataSource.getInstance().getCnx();
    @Override
    public void ajouter(SousCategorie p) {
        try {   
            String req = "INSERT INTO `sous_categorie` ( `nom_sous_categ`) VALUES ('"+p.getNomSousCategorie()+"')";

            Statement st = cnx.createStatement();
            st.executeUpdate(req);            
            System.out.println("Sous Categorie added ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /*public void ajouter2(Categorie p) {
        try {
            String req = "INSERT INTO `categorie` (`nom_categorie`) VALUES (?)";
            PreparedStatement ps = cnx.prepareStatement(req) ; 
            ps.setString(1, p.getNomCategorie());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `sous_categorie` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Category deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(SousCategorie p) {
        try {
            String req = "UPDATE `sous_categorie` SET `nom_sous_categ` = '" + p.getNomSousCategorie()+"' WHERE `id` = '"+p.getId()+"'" ;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("SousCategorie updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<SousCategorie> getAll() {
        List<SousCategorie> list = new ArrayList<>();
        try {
            String req = "Select * from sous_categorie";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                SousCategorie p = new SousCategorie(rs.getInt(1), rs.getString("nom_sous_categ"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    
    public SousCategorie getById(int id) {
        SousCategorie p = new SousCategorie() ;
        try {
            String req = "Select * from sous_categorie WHERE `id` = "+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while(rs.next()){ 
                p.setId(rs.getInt(1));
                p.setNomSousCategorie(rs.getString("nom_sous_categ")); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p ;
    }
    
    public SousCategorie getByName(String nom) {
        SousCategorie p = new SousCategorie() ;
        try {
            String req = "Select * from sous_categorie WHERE `nom_sous_categ` = '"+nom+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while(rs.next()){ 
                p.setId(rs.getInt(1));
                p.setNomSousCategorie(rs.getString("nom_sous_categ")); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p ;
    }
    
    public List<SousCategorie> getBySearch(String nom) {
        List<SousCategorie> list = new ArrayList<>();
        try {
            String req = "Select * from sous_categorie WHERE UPPER(`nom_sous_categ`) like UPPER('%"+nom+"%')";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                SousCategorie p = new SousCategorie(rs.getInt(1), rs.getString("nom_sous_categ"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
}
