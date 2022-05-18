/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Abdelaziz
 */
public class SousCategorie {
    private int id ; 
    private String nomSousCategorie ; 

    public SousCategorie() {
    }

    public SousCategorie(int id, String nomSousCategorie) {
        this.id = id;
        this.nomSousCategorie = nomSousCategorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomSousCategorie() {
        return nomSousCategorie;
    }

    public void setNomSousCategorie(String nomSousCategorie) {
        this.nomSousCategorie = nomSousCategorie;
    }

    @Override
    public String toString() {
        return "SousCategorie{" + "id=" + id + ", nomSousCategorie=" + nomSousCategorie + '}';
    }
    
    
}
