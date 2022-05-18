/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.Preferences;

/**
 *
 * @author ihebx
 */
public class SessionManager {
    
    public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 
    
    
    
    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int id ; 
    private static String username ; 
    private static String email; 
    private static String password ;
             private static String Roles;
    private static String imageUser;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

   

    public static int getId() {
        return pref.get("id",id);// kif nheb njib id user connecté apres njibha men pref 
    }

    public static void setId(int id) {
        pref.set("id",id);//nsajl id user connecté  w na3tiha identifiant "id";
    }

    public static String getUsername() {
        return pref.get("username",username);
    }
 public static String getImageUser() {
        return pref.get("imageUser",imageUser);
    }
  public static void setImageUser(String imageUser) {
         pref.set("imageUser",imageUser);
    }
    public static void setUsername(String username) {
         pref.set("username",username);
    }

    public static String getRoles() {
        return pref.get("Roles",Roles);
    }

    public static void setRoles(String Roles) {
        pref.set("Roles",Roles);
    }

  
   
    public static String getEmail() {
        return pref.get("email",email);
    }

    public static void setEmail(String email) {
         pref.set("email",email);
    }

    public static String getPassword() {
        return pref.get("password",password);
    }

    public static void setPassword(String password) {
         pref.set("password",password);
    }

    /**public static String getPhoto() {
        return pref.get("photo",photo);
    }

    public static void setPhoto(String photo) {
         pref.set("photo",photo);
    }*/
    
    
    
    
    
    
}
