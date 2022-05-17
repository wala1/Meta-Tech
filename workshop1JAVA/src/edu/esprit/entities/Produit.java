/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author Abdelaziz
 */
public class Produit {
    
    private ImageView img ; 
    
    private int id ; 
    private String nomProd ; 
    private String descProd ;
    
    private String imageProd ; 
    private float prixProd ;
    private float promoProd ;     
    private int stockProd ; 
    private Categorie categorieProd ;
    private SousCategorie sousCategorieProd ;
    private String rate ;
    
    public Produit() {
    }
    
    

    public Produit(int id, String nomProd, String descProd, String imageProd, float prixProd, float promoProd, int stockProd, Categorie categorieProd, SousCategorie sousCategorieProd, String rate) {
        this.id = id;
        this.nomProd = nomProd;
        this.descProd = descProd;
        this.imageProd = imageProd;
        this.prixProd = prixProd;
        this.promoProd = promoProd;
        this.stockProd = stockProd;
        this.categorieProd = categorieProd;
        this.sousCategorieProd = sousCategorieProd;
        this.rate = rate;
    }

    public Categorie getCategorieProd() {
        return categorieProd;
    }

    public void setCategorieProd(Categorie categorieProd) {
        this.categorieProd = categorieProd;
    }
    
    public int getId() {
        return id;
    }

 

     
    

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getDescProd() {
        return descProd;
    }

    public void setDescProd(String descProd) {
        this.descProd = descProd;
    }

    public String getImageProd() {
        return imageProd;
    }

    public void setImageProd(String imageProd) {
        this.imageProd = imageProd;
    }

    public float getPrixProd() {
        return prixProd;
    }

    public void setPrixProd(float prixProd) {
        this.prixProd = prixProd;
    }

    public float getPromoProd() {
        return promoProd;
    }

    public void setPromoProd(float promoProd) {
        this.promoProd = promoProd;
    }

    public int getStockProd() {
        return stockProd;
    }

    public void setStockProd(int stockProd) {
        this.stockProd = stockProd;
    }

    public SousCategorie getSousCategorieProd() {
        return sousCategorieProd;
    }

    public void setSousCategorieProd(SousCategorie sousCategorieProd) {
        this.sousCategorieProd = sousCategorieProd;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    
    public Produit(int id, String nomProd, String imageProd, float prixProd){
        this.id=id;  
        this.nomProd=nomProd;
        this.imageProd=imageProd;
        this.prixProd=prixProd;
    }
    
    
    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nomProd=" + nomProd + ", descProd=" + descProd + ", imageProd=" + imageProd + ", prixProd=" + prixProd + ", promoProd=" + promoProd + ", stockProd=" + stockProd + ", categorieProd=" + categorieProd + ", sousCategorieProd=" + sousCategorieProd + ", rate=" + rate + '}';
    }

     

    
    
}
