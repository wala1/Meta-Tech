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
import java.sql.PreparedStatement;
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
 * @author mejri
 */
public class ServiceProduit  implements iService<Produit>{
    Connection cnx= DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Produit p) {
        try {  
            /*String req ="INSERT INTO `produit`  ( `nom_prod`, `desc_prod`, `image_prod`, `prix_prod`, `promo_prod`, `stock_prod`,`categorie_prod_id`,`sous_categ_prod_id`) VALUES  ('?','?','?','?','?','?','?','?')" ;
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, p.getNomProd());
            st.setString(2, p.getDescProd());
            st.setString(3, p.getImageProd());
            st.setFloat(4, p.getPrixProd());
            st.setFloat(5, p.getPromoProd()); 
            st.setInt(6, p.getStockProd());
            st.setString(7, p.getCategorieProd()+"");            
            st.setString(8, p.getSousCategorieProd()+"");          
            st.executeUpdate();*/


            String req = "INSERT INTO `produit` ( `nom_prod`, `desc_prod`, `image_prod`, `prix_prod`, `promo_prod`, `rating_prod`,`stock_prod`,`categorie_prod_id`,`sous_categ_prod_id`) VALUES ('"+p.getNomProd()+"','"+p.getDescProd()+"','"+p.getImageProd()+"','"+p.getPrixProd()+"','"+p.getPromoProd()+"',0,'"+p.getStockProd()+"','"+(p.getCategorieProd()).getId()+"','"+(p.getSousCategorieProd()).getId()+"')";                  

            Statement st = cnx.createStatement();
            st.executeUpdate(req);            
            System.out.println("Produit added ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `produit` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Produit deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Produit p) {
        try {
             Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            //String reqz = "UPDATE `produit` SET ( `nom_prod`, `desc_prod`, `image_prod`, `prix_prod`, `promo_prod`, `stock_prod`) = ('"+p.getNomProd()+"','"+p.getDescProd()+"','"+p.getImageProd()+"','"+p.getPrixProd()+"','"+p.getPromoProd()+"','"+p.getStockProd()+"') WHERE `id`= '"+p.getId()+"'" ; 
            String req = "UPDATE `produit` SET `nom_prod`='"+p.getNomProd()+"', `desc_prod` = '"+p.getDescProd()+"', `image_prod` = '"+p.getImageProd()+"', `promo_prod` = '"+p.getPromoProd()+"', `prix_prod` = '"+p.getPrixProd()+"', `stock_prod` = '"+p.getStockProd()+"',`categorie_prod_id` = '"+p.getCategorieProd().getId()+"' ,`sous_categ_prod_id`='"+p.getSousCategorieProd().getId()+"'   WHERE `id` =  '"+p.getId()+"'" ;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Produit updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> list = new ArrayList<>();
        try {
            String req = "Select * from produit";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){ 
                //int id, String nomProd, String descProd, String imageProd, float prixProd, float promoProd, int stockProd, String categorieProd, String sousCategorieProd, String rate
                ServiceCategorie sp = new ServiceCategorie(); 
                ServiceSousCategorie sp2 = new ServiceSousCategorie();  
                
                Categorie c = new Categorie(); 
                c = sp.getById(rs.getInt("categorie_prod_id")) ; 
                
                SousCategorie c2 = new SousCategorie(); 
                c2 = sp2.getById(rs.getInt("sous_categ_prod_id")) ; 
                Produit p = new Produit(rs.getInt("id"),rs.getString("nom_prod"),rs.getString("desc_prod"),rs.getString("image_prod"),rs.getFloat("prix_prod"), rs.getFloat("promo_prod"), rs.getInt("stock_prod"), c , c2, "0" );
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    
    public Produit getById(int id) {
        Produit p = new Produit() ;
        try {
            String req = "Select * from produit WHERE `id` = '"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while(rs.next()){ 
                p.setId(rs.getInt(1));
                p.setNomProd(rs.getString("nom_prod")); 
                p.setDescProd(rs.getString("desc_prod"));
                p.setImageProd(rs.getString("image_prod"));
                p.setPrixProd(rs.getFloat("prix_prod"));
                p.setPromoProd(rs.getFloat("promo_prod"));
                p.setStockProd(rs.getInt("stock_prod"));
                p.setRate(rs.getString("rating_prod"));
                
                ServiceCategorie sp = new ServiceCategorie(); 
                ServiceSousCategorie sp2 = new ServiceSousCategorie();  
                
                Categorie c = new Categorie(); 
                c = sp.getById(rs.getInt("categorie_prod_id")) ; 
                
                SousCategorie c2 = new SousCategorie(); 
                c2 = sp2.getById(rs.getInt("sous_categ_prod_id")) ; 
                
                p.setCategorieProd(c);
                p.setSousCategorieProd(c2);
    
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p ;
    }
    
    
    
    
    
    public List<Produit> getByCategorie(int id ) {
        List<Produit> list = new ArrayList<>();
        try {
            String req = "Select * from produit where `categorie_prod_id`= '"+id+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                //int id, String nomProd, String descProd, String imageProd, float prixProd, float promoProd, int stockProd, String categorieProd, String sousCategorieProd, String rate
                ServiceCategorie sp = new ServiceCategorie(); 
                ServiceSousCategorie sp2 = new ServiceSousCategorie();  
                
                Categorie c = new Categorie(); 
                c = sp.getById(rs.getInt("categorie_prod_id")) ; 
                
                SousCategorie c2 = new SousCategorie(); 
                c2 = sp2.getById(rs.getInt("sous_categ_prod_id")) ; 
                Produit p = new Produit(rs.getInt("id"),rs.getString("nom_prod"),rs.getString("desc_prod"),rs.getString("image_prod"),rs.getFloat("prix_prod"), rs.getFloat("promo_prod"), rs.getInt("stock_prod"), c , c2, "0" );
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    
    
    
    public List<Produit> getBySearch(String id ) {
        List<Produit> list = new ArrayList<>();
        try {
            String req = "Select * from produit where UPPER(`nom_prod`) like UPPER('%"+id+"%')";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                //int id, String nomProd, String descProd, String imageProd, float prixProd, float promoProd, int stockProd, String categorieProd, String sousCategorieProd, String rate
                ServiceCategorie sp = new ServiceCategorie(); 
                ServiceSousCategorie sp2 = new ServiceSousCategorie();  
                
                Categorie c = new Categorie(); 
                c = sp.getById(rs.getInt("categorie_prod_id")) ; 
                
                SousCategorie c2 = new SousCategorie(); 
                c2 = sp2.getById(rs.getInt("sous_categ_prod_id")) ; 
                Produit p = new Produit(rs.getInt("id"),rs.getString("nom_prod"),rs.getString("desc_prod"),rs.getString("image_prod"),rs.getFloat("prix_prod"), rs.getFloat("promo_prod"), rs.getInt("stock_prod"), c , c2, "0" );
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    
    // ============= convert currency ==========================
     
    
    
    
    
    
}
