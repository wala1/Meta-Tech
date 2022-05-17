/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.tests;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Event;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceEvent;
import edu.esprit.services.ServiceUser;
import edu.esprit.utils.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author wala
 */
public class Main {

    public static void main(String[] str) {

       // User user1 = new User(225, "Oumaima", "oumaima", "oumaima@esprit.tn", 52800159, "test.jpg");
       // User user3 = new User(232,"test1", "test1", "test@esprit.tn", 52800159, "test.jpg");
       // ServiceUser su = new ServiceUser();
        //User u=null;
       // u=su.login1("admin@metatch.tn", "adminadmin");
       // User.session=u;
      // System.out.println( User.session.getUsername());
        //System.out.println(u);
        //System.out.println(u);
      //  String test;
        // su.login("test@esprit.tn","test1");

        // su.sendMailPass("wala.alimi@espri.tn");
        //*****ADD ADMIN***/
        //System.out.println("###########################ADD ADMIN###########################");
     //  su.ajouter(user3);
        //*****ADD User***/
        // System.out.println("###########################ADD USER###########################");
        // su.ajouter(user1);
        //* ****** DELETE User ********
        // su.supprimer(200);
       // su.delete(user3);
        //* ****** UPDATE User ********
        //System.out.println("###########################MODIFY USER###########################");
        // user1.setUsername("oumaima1");
        // su.modifier(user1);
        //* ******Block User ********
        // su.block(user1);
        //***** AFFICHER USERS *****/
        /*  List<User> userList = new ArrayList<>();

        System.out.println("###########################USERS LIST###########################");
        userList = su.getAll();

        for (User u : userList) {
            System.out.println(u.getUsername() + "   " + u.getEmail());
        }*/
        //**************recuperer password **********
        /*List<User> Users = new ArrayList();
      su.mdpoublier("test@esprit.tn");
      for (User u : Users) {
            System.out.println(u.getPassword() + "   " + u.getEmail());
      }*/
        ///////*************send code to email****
       // su.sendMailPass("wala.alimi@esprit.tn");
        //***********CHANGE PASSWORD WITH CODE *******************
        // su.changePassCode("wala.alimi@esprit.tn","837521", "walawala");     
        //***************SET CONNECTED**************
        //su.setConnected("wala.alimi@esprit.tn");
        //***************GET CONNECTED**************
        // su.getConnected();
        //***********CHECK ROLE ************
       // test = su.checkRole("dorsaf123@esprit.tn");
     //   System.out.println(test);
      //  User u1=su.login1("test@esprit.tn", "test1");
      // int test1=su.checkBlock("oumaima@esprit.tn");
      //   System.out.println(test1);
      //boolean test2;
      //test2=su.verifyCode("70000","wala.alimi@esprit.tn");
      //test2=su.findEmail("jkhg");
      // System.out.println(test2);
      
      
 //su.disconnectAll();
 
 //************************EVENT*********************
 
//Event E=new Event("request ","test0","test0","2022-04-02 10:51:41","2022-04-02 10:51:41",10,2);
      ///ServiceEvent Ev = new ServiceEvent();
     // int total;
  //  total= Ev.countRequests();
     //System.out.println(total);
     //Ev.setApproved(53);
     // int event;
     // event=Ev.checkEvent(52);
   //   System.out.println(event);
      //  Ev.AddEventRequest(E);
       // Ev.modifier(E);
       //Ev.delete(E);
     // System.out.println(Ev.getEvent(4).getNbrPartMax());
    // Ev.incrementerParticipant(47);
//Ev.participerEvenement(4, 241);
//Ev.test();

 
    }
}
