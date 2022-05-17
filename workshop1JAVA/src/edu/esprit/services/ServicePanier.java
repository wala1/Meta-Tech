/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.iService;
import edu.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Logidee
 */
public class ServicePanier implements iService<Panier>{
    Connection cnx= DataSource.getInstance().getCnx();
    
    @Override
    public void ajouter(Panier panier) {
        try {               
            String req = "INSERT INTO `panier` (`id`, `user_panier_id`, `produit_panier_id`, `quantite`) VALUES ('"+
                        panier.getId()+"', '"+
                        panier.getUser().getId()+"', '"+
                        panier.getProduit().getId()+"', '"+
                        panier.getQuantite()+
                    "')";

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Product added Successfuly to your cart!!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Panier panier) {
        try {               
            
            String req = "UPDATE `panier` SET `quantite` = '"+panier.getQuantite()+
                    "' WHERE `panier`.`id` ="+panier.getId();

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Cart updated Successfuly !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public void supprimer(int id) {
        try {           
            String req = "DELETE FROM `panier` WHERE `panier`.`id` ="+id;

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Cart removed Successfuly !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
            
    public void clearPanier(int idUser) {
        try {           
            String req = "DELETE FROM `panier` WHERE `user_panier_id` ="+idUser;

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Cart removed Successfuly !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Panier> getAll() {
        List<Panier> paniers = new ArrayList<>();
        try {               
            String req = "SELECT * FROM `panier`";
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            User user = new User();
            Produit produit = new Produit();
            while (result.next()){
                int userId = result.getInt(2);
                int produitId = result.getInt(3);
                int quantite = result.getInt("quantite");
                user = getUserById(userId);
                produit = getProduitById(produitId);
                
                Panier panier = new Panier(user, produit, quantite);
                paniers.add(panier);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return paniers;       
        
    }
    /*metier api strem tri recherche filter+ api externe interface */
    public User getUserById(int id) {
        User user = new User();
        try {               
            String req = "SELECT * FROM `user` where `user`.`id` ="+ id;
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            if(result.next()){
                String username = result.getString(2);
                user = new User(id, username);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return user;
    }
    
    public Produit getProduitById(int id){
        Produit produit = new Produit();
        try {
            String req = "SELECT * FROM `produit` where `produit`.`id` = "+ id;
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            
            if(result.next()){
                String nomProd = result.getString("nom_prod");
                String imageProd = result.getString("image_prod");
                float prixProd = result.getFloat("prix_prod");
                produit = new Produit(id, nomProd, imageProd,prixProd);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produit;
    }
    
    public List<Panier> getPanier(int idUser){
        List<Panier> paniers = new ArrayList<>();
        try {               
            String req = "SELECT * FROM `panier` WHERE `user_panier_id` = "+idUser;
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            while (result.next()){
                    int id = result.getInt(1);
                    int userId = result.getInt(2);
                    int produitId = result.getInt(3);
                    int quantite = result.getInt("quantite");
                    User user = getUserById(userId);
                    Produit produit = getProduitById(produitId);
                    Panier panier = new Panier(id,user, produit, quantite);
                    paniers.add(panier);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return paniers;
    }
    
    public List<Produit> getProduitPanier(int idUser){
        List<Produit> produits = new ArrayList<>();
        try {               
            String req = "SELECT * FROM `panier` WHERE `user_panier_id` = "+idUser;
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            Produit produit = new Produit();
            while (result.next()){
                int produitId = result.getInt(3);
                System.out.println("produitId : "+produitId);
                produit = getProduitById(produitId);
                System.out.println("produit : "+produit);
                produits.add(produit);
            }
            
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produits;
    }
    
    
    
    public List<Produit> getProduits(){
        List<Produit> produits = new ArrayList<>();
        try {               
            String req = "SELECT * FROM `produit` ";
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            while (result.next()){
                int id = result.getInt(1);
                String nomProd = result.getString(4);
                String imageProd = result.getString(6);
                float price = result.getFloat(7);
                
                Produit produit = new Produit(id,nomProd,imageProd,price);
                System.out.println("produit :" + produit);
                produits.add(produit);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produits;
    }
    
}
