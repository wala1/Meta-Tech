/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.entities;

import metatech.entities.User;

/**
 *
 * @author wala
 */
public class Admin extends User {
       
   

   
    public Admin() {
    }

    public Admin( String username, String password, String email, int phoneNumber, String imageUser) {
        super( username, password, email, phoneNumber, imageUser);
    }

    public Admin(int id, String username, String password, String email, int phoneNumber, String imageUser) {
        super(id, username, password, email, phoneNumber, imageUser);
    }

 
        
        

}
