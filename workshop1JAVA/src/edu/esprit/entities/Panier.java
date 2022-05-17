/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author Logidee
 */
public class Panier {
    private int id ; 
    private int quantite ; 
    private User user ;
    public Produit produit ;
    private ImageView img ;

    public void setImg(ImageView img) {
        this.img = img;
    }

    public ImageView getImg() {
        return img;
    }
            
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    public Panier() {
    }
    
    public Panier(int id, User user, Produit produit, int quantite) {
        this.id = id;
        this.quantite = quantite;
        this.user = user;
        this.produit = produit;
    }
    
    public Panier(User user, Produit produit, int quantite) {
        this.id = id;
        this.quantite = quantite;
        this.user = user;
        this.produit = produit;
    }
    
    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", quantite=" + quantite + ", user=" + user +", produit=" +  produit + '}';
    }
}
