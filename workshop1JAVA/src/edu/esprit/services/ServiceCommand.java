/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Command;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Logidee
 */
public class ServiceCommand implements iService<Command> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Command cmd) {
        try {

            System.out.println("1° Order added Successfuly !!");
            String req = "INSERT INTO `commande` (`street_adress`, `payement_method`, `date`, `phone_number`, `first_name`, `last_name`, `delivery_comment`, `newsletter`, `order_comment`, `city`, `zip_postal_code`, `country`, `company`, `etat`, `code_coup`, `subtotal`, `user_commande_id`) "
                    + "VALUES ('" + cmd.getStreet_Adress() + "', '" + cmd.getPayement_method() + "', '" + cmd.getDate() + "', '" + cmd.getPhone_Number() + "', '" + cmd.getFirstName() + "', '" + cmd.getLastName() + "', '" + cmd.getDelivery_comment() + "', '" + cmd.getNewsletter() + "', '" + cmd.getOrder_comment() + "', '" + cmd.getCity() + "', '" + cmd.getZip_PostalCode() + "', '" + cmd.getCountry() + "', '" + cmd.getCompany() + "', '" + cmd.getEtat() + "', '" + cmd.getCodeCoup() + "', '" + cmd.getSubtotal() + "', '" + cmd.getUser_commande().getId() + "')";
            System.out.println("req : " + req);
            ServiceCommand sc = new ServiceCommand();
            sc.updateCode(cmd);
            System.out.println("2° Order added Successfuly !!");
            Statement st = cnx.createStatement();
            System.out.println("3° Order added Successfuly !!");
            st.executeUpdate(req);
            System.out.println("4° Order added Successfuly !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void updateCode(Command cmd) {
        try {
            double code = cmd.getSubtotal() - cmd.getCodeCoup();
            if (code < 0) {
                code = 0;
            }
            String req = "UPDATE `coupon` SET `tauxRedu`='" + code + "' WHERE `code_coup`= '" + cmd.getCodeCoup() + "'";
            System.out.println("req : " + req);
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Command cmd) {
        try {

            String req = "UPDATE `commande` SET `street_adress` = '" + cmd.getStreet_Adress() + "', `payement_method` = '" + cmd.getPayement_method() + "', `date` = '" + cmd.getDate() + "', `phone_number` = '" + cmd.getPhone_Number() + "', `first_name` = '" + cmd.getFirstName() + "', `last_name` = '" + cmd.getLastName() + "', `delivery_comment` = '" + cmd.getDelivery_comment() + "', `newsletter` = '" + cmd.getNewsletter() + "', `order_comment` = '" + cmd.getOrder_comment() + "', `city` = '" + cmd.getCity() + "', `zip_postal_code` = '" + cmd.getZip_PostalCode() + "', `country` = '" + cmd.getCountry() + "', `company` = '" + cmd.getCompany() + "', `etat` = '" + cmd.getEtat() + "', `code_coup` = '" + cmd.getCodeCoup() + "', `subtotal` = '" + cmd.getSubtotal() + "' WHERE `commande`.`id` = " + cmd.getId();
            System.out.println("req : " + req);
            ServiceCommand sc = new ServiceCommand();
            sc.updateCode(cmd);
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `commande` WHERE `commande`.`id`=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Order removed Successfuly !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Command> getAll() {
        List<Command> commands = new ArrayList<>();
        try {
            String req = "SELECT * FROM `commande`";
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            User user = new User();
            while (result.next()) {
                int id = result.getInt(1);
                String streetAdress = result.getString(2);
                String paymentMethod = result.getString(3);
                String date = result.getString(4);
                long phone = result.getLong(5);
                String firstName = result.getString(6);
                String lastName = result.getString(7);
                String deliveryCmt = result.getString(8);
                String newsletter = result.getString(9);
                String orderCmt = result.getString(10);
                String city = result.getString(11);
                int zip = result.getInt(12);
                String country = result.getString(13);
                String company = result.getString(14);
                String etat = result.getString(15);
                int codecoup = result.getInt(16);
                double subtotal = result.getDouble(17);
                int userId = result.getInt(18);

                user = getUserById(userId);
                Command cmd = new Command(id, firstName, lastName, streetAdress, city, zip, country, company, phone, paymentMethod, newsletter, date, deliveryCmt, orderCmt, etat, codecoup, subtotal, user);
                commands.add(cmd);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return commands;
    }

    public User getUserById(int id) {
        User user = new User(id, null);
        try {
            String req = "SELECT * FROM `user` where `user`.`id` =" + id;
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            String userName = result.getString(3);

            user = new User(id, userName);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return user;
    }
    
    public List<Command> getCommandByUserId(String country) {
        List<Command> commands = new ArrayList<>();
        try {
            String req = "SELECT * FROM `commande` where `country` ='" + country+"'";
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            while (result.next()) {
                    int id = result.getInt(1);
                    String streetAdress = result.getString(2);
                    String paymentMethod = result.getString(3);
                    String date = result.getString(4);
                    long phone = result.getLong(5);
                    String firstName = result.getString(6);
                    String lastName = result.getString(7);
                    String deliveryCmt = result.getString(8);
                    String newsletter = result.getString(9);
                    String orderCmt = result.getString(10);
                    String city = result.getString(11);
                    int zip = result.getInt(12);
                    String company = result.getString(14);
                    String etat = result.getString(15);
                    int codecoup = result.getInt(16);
                    double subtotal = result.getDouble(17);
                    int userId = result.getInt(18);

                    User user = getUserById(userId);
                    Command cmd = new Command(id, firstName, lastName, streetAdress, city, zip, country, company, phone, paymentMethod, newsletter, date, deliveryCmt, orderCmt, etat, codecoup, subtotal, user);

                    System.out.println("cmd" + cmd);
                    commands.add(cmd);
                    System.out.println("cmd" + cmd);
                
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return commands;

    }

}
