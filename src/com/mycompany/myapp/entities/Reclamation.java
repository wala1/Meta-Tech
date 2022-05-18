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
public class Reclamation { 
    private String nameReclamation ; 
    private String emailReclamation ;
    private String descReclamation ; 

    public Reclamation( String nameReclamation, String emailReclamation, String descReclamation) { 
        this.nameReclamation = nameReclamation;
        this.emailReclamation = emailReclamation;
        this.descReclamation = descReclamation;
    }

    public Reclamation() {
    }

     

    public String getNameReclamation() {
        return nameReclamation;
    }

    public void setNameReclamation(String nameReclamation) {
        this.nameReclamation = nameReclamation;
    }

    public String getEmailReclamation() {
        return emailReclamation;
    }

    public void setEmailReclamation(String emailReclamation) {
        this.emailReclamation = emailReclamation;
    }

    public String getDescReclamation() {
        return descReclamation;
    }

    public void setDescReclamation(String descReclamation) {
        this.descReclamation = descReclamation;
    }

    @Override
    public String toString() {
        return "Reclamation{ nameReclamation=" + nameReclamation + ", emailReclamation=" + emailReclamation + ", descReclamation=" + descReclamation + '}';
    }
    
    
    
    
}
