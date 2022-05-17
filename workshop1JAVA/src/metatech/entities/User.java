/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author wala
 */
public class User {
    private int id ; 
    private String username ; 
    private String password;
    private String email;
    private int phoneNumber;
    private String imageUser ;
    private int etat;
    public static User session;
    private ImageView img;


    
    
    
    
    public User() {
    }


    public User(int id, String username, String password, String email, int phoneNumber, String imageUser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageUser = imageUser;


    }
    
    public User( String username, String password, String email, int phoneNumber, String imageUser) {
       
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageUser = imageUser;


    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

   
    public int getId() {
        return id;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getEtat() {
        return etat;
    }

  
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", phoneNumber=" + phoneNumber + '}';
    }
   

    
}
