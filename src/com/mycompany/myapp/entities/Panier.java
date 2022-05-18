/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.ArrayList;

/**
 *
 * @author Logidee
 */
public class Panier {
    private int id ; 
    private String quantite ; 
    private String user ;
    public String produit ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }
    
    public Panier() {
    }
    
    public Panier(int id, String quantite, String user, String produit) {
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
