/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

 

/**
 *
 * @author Abdelaziz
 */
public class Categorie {
    private int id ; 
    private String nomCategorie ; 

    public Categorie() {
    }
    
    
    public Categorie(int id,  String nomCategorie) { 
        this.nomCategorie = nomCategorie;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
    
    
    
}
