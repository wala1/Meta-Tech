/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
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
public class ServiceCategorie implements iService<Categorie> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Categorie p) {
        try {
            String req = "INSERT INTO `categorie` (`nom_categorie`) VALUES ('" + p.getNomCategorie() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void ajouter2(Categorie p) {
        try {
            String req = "INSERT INTO `categorie` (`nom_categorie`) VALUES (?)";
            PreparedStatement ps = cnx.prepareStatement(req) ; 
            ps.setString(1, p.getNomCategorie());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `categorie` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Category deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Categorie p) {
        try {
            String req = "UPDATE `categorie` SET `nom_categorie` = '" + p.getNomCategorie()+"' WHERE `id` = '"+p.getId()+"'" ;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie> getAll() {
        List<Categorie> list = new ArrayList<>();
        try {
            String req = "Select * from categorie";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Categorie p = new Categorie(rs.getInt(1), rs.getString("nom_categorie"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public Categorie getById(int id) {
        Categorie p = new Categorie() ;
        try {
            String req = "Select * from categorie WHERE `id` = '"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while(rs.next()){ 
                p.setId(rs.getInt(1));
                p.setNomCategorie(rs.getString("nom_categorie")); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p ;
    }
    
    public Categorie getByName(String nom) {
        Categorie p = new Categorie() ;
        try {
            String req = "Select * from categorie WHERE `nom_categorie` = '"+nom+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while(rs.next()){ 
                p.setId(rs.getInt(1));
                p.setNomCategorie(rs.getString("nom_categorie")); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p ;
    }
    
    
     public List<Categorie> getBySearch(String nom) {
        List<Categorie> list = new ArrayList<>();
        try {
            String req = "Select * from categorie WHERE UPPER(`nom_categorie`) like UPPER('%"+nom+"%')";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                Categorie p = new Categorie(rs.getInt(1), rs.getString("nom_categorie"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
     
    
    

}