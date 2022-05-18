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
public class Avis {
    private int id ; 
    private int idProd ; 
    private String idUser ; 
    private String descAvis ; 
    private String ratingAvis ;
    private String timeAvis ;

    public Avis(int id, int idProd, String idUser, String descAvis, String ratingAvis ) {
        this.id = id;
        this.idProd = idProd;
        this.idUser = idUser;
        this.descAvis = descAvis;
        this.ratingAvis = ratingAvis;
        
    }

    public Avis() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

     

    public String getDescAvis() {
        return descAvis;
    }

    public void setDescAvis(String descAvis) {
        this.descAvis = descAvis;
    }

    public String getRatingAvis() {
        return ratingAvis;
    }

    public void setRatingAvis(String ratingAvis) {
        this.ratingAvis = ratingAvis;
    }

    public String getTimeAvis() {
        return timeAvis;
    }

    public void setTimeAvis(String timeAvis) {
        this.timeAvis = timeAvis;
    }

    @Override
    public String toString() {
        return "Avis{" + "id=" + id + ", idProd=" + idProd + ", idUser=" + idUser + ", descAvis=" + descAvis + ", ratingAvis=" + ratingAvis + ", timeAvis=" + timeAvis + '}';
    }
    
    
    
    

}
