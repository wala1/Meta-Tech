/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author mejri
 */
public class publicity {
         public int id ; 
    private String nomPub; 
    private String descPub ; 
    private String imagePub; 
    private String imageName; 
    private float prixPub ;
    private float promoPub ;     
    private ImageView photo;
    private ImageView img;

    public publicity() {
    }

    public publicity(int id, String nomPub, String descPub, String imagePub, String imageName, float prixPub, float promoPub) {
        this.id = id;
        this.nomPub = nomPub;
        this.descPub = descPub;
        this.imagePub = imagePub;
        this.imageName = imageName;
        this.prixPub = prixPub;
        this.promoPub = promoPub;
    }

    public publicity(int id, String nomPub, String descPub, float prixPub, float promoPub, String imageName) {
        this.id = id;
        this.nomPub = nomPub;
        this.descPub = descPub;
        this.prixPub = prixPub;
        this.promoPub = promoPub;    
        this.imageName = imageName;

    }

    public publicity(ImageView photo ,int id, String nomPub, String descPub, float prixPub, float promoPub, String imageName) {
        this.photo = photo;
        this.id = id;
        this.nomPub = nomPub;
        this.descPub = descPub;
        this.prixPub = prixPub;
        this.promoPub = promoPub;    
        this.imageName = imageName;

    } 

    public publicity(Integer queryProductI0, String name, String desc, String prix, String disc, String image) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  

    
 
 

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomPub() {
        return nomPub;
    }

    public void setNomPub(String nomPub) {
        this.nomPub = nomPub;
    }

    public String getDescPub() {
        return descPub;
    }

    public void setDescPub(String descPub) {
        this.descPub = descPub;
    }

    public String getImagePub() {
        return imagePub;
    }

    public void setImagePub(String imagePub) {
        this.imagePub = imagePub;
    }

    public float getPrixPub() {
        return prixPub;
    }

    public void setPrixPub(float prixPub) {
        this.prixPub = prixPub;
    }

    public float getPromoPub() {
        return promoPub;
    }

    public void setPromoPub(float promoPub) {
        this.promoPub = promoPub;
    }

    @Override
    public String toString() {
        return "Publicity{" + "id=" + id + ", nomPub=" + nomPub + ", descPub=" + descPub + ", imagePub=" + imagePub + ", imageName=" + imageName + ", prixPub=" + prixPub + ", promoPub=" + promoPub + '}';
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }


 
}
