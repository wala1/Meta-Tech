/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author wala
 */
public final class Session {
       private int id ; 
    private String username ; 
    private String password;
    private String email;
    private int phoneNumber;
    private String imageUser ;
    private int etat;
    public static User session;
    private ImageView img;

    public Session() {
    }

    public Session(int id, String username, String password, String email, int phoneNumber, String imageUser, int etat) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageUser = imageUser;
        this.etat = etat;
    }

    public Session(String username, String password, String email, int phoneNumber, String imageUser, int etat, ImageView img) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageUser = imageUser;
        this.etat = etat;
        this.img = img;
    }
    
    
    
    
}
