/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.util.Date;

/**
 *
 * @author Logidee
 */
public class Command {
    public int id ; 
    private String firstName ; 

    public void setStreet_Adress(String street_Adress) {
        this.street_Adress = street_Adress;
    }

    public void setPhone_Number(long phone_Number) {
        this.phone_Number = phone_Number;
    }

    public void setPayement_method(String payement_method) {
        this.payement_method = payement_method;
    }

    public void setCodeCoup(int codeCoup) {
        this.codeCoup = codeCoup;
    }

    public void setUser_commande(User user_commande) {
        this.user_commande = user_commande;
    }
    private String lastName ;
    public String street_Adress ;
    private String city ; 
    private int zip_PostalCode ;
    public String country ;
    private String company ; 
    private long phone_Number ;
    public String payement_method ;
    private String newsletter ; 
    private String date ;
    public String delivery_comment ;
    private String order_comment ; 
    private String etat ;
    public int codeCoup ;
    private double subtotal ; 
    private User user_commande ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet_Adress() {
        return street_Adress;
    }

    public void setStreetAdress(String street_Adress) {
        this.street_Adress = street_Adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip_PostalCode() {
        return zip_PostalCode;
    }

    public void setZip_PostalCode(int zip_PostalCode) {
        this.zip_PostalCode = zip_PostalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getPhone_Number() {
        return phone_Number;
    }

    public void setPhoneNumber(long phone_Number) {
        this.phone_Number = phone_Number;
    }

    public String getPayement_method() {
        return payement_method;
    }

    public void setPayementMethod(String payement_method) {
        this.payement_method = payement_method;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDelivery_comment() {
        return delivery_comment;
    }

    public void setDelivery_comment(String delivery_comment) {
        this.delivery_comment = delivery_comment;
    }

    public String getOrder_comment() {
        return order_comment;
    }

    public void setOrder_comment(String order_comment) {
        this.order_comment = order_comment;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getCodeCoup() {
        return codeCoup;
    }

    public void setCodeCoupon(int codeCoup) {
        this.codeCoup = codeCoup;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public User getUser_commande() {
        return user_commande;
    }

    public void setUserCommande(User user_commande) {
        this.user_commande = user_commande;
    }
    
    public Command() {
    }
    
    public Command(int id, String firstName, String lastName, String street_Adress, String city, int zip_PostalCode, String country, String company, long phone_Number, String payement_method, String newsletter, String date, String delivery_comment, String order_comment, String etat, int codeCoup, double subtotal, User user_commande) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street_Adress = street_Adress;
        this.city = city;
        this.zip_PostalCode = zip_PostalCode;
        this.country = country;
        this.company = company;
        this.phone_Number = phone_Number;
        this.payement_method = payement_method;
        this.newsletter = newsletter;
        this.date = date;
        this.delivery_comment = delivery_comment;
        this.order_comment = order_comment;
        this.etat = etat;
        this.codeCoup = codeCoup;
        this.subtotal = subtotal;
        this.user_commande = user_commande;
    }
    
    public Command(String firstName, String lastName, String street_Adress, String city, int zip_PostalCode, String country, String company, long phone_Number, String payement_method, String newsletter, String date, String delivery_comment, String order_comment, String etat, int codeCoup, double subtotal, User user_commande) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street_Adress = street_Adress;
        this.city = city;
        this.zip_PostalCode = zip_PostalCode;
        this.country = country;
        this.company = company;
        this.phone_Number = phone_Number;
        this.payement_method = payement_method;
        this.newsletter = newsletter;
        this.date = date;
        this.delivery_comment = delivery_comment;
        this.order_comment = order_comment;
        this.etat = etat;
        this.codeCoup = codeCoup;
        this.subtotal = subtotal;
        this.user_commande = user_commande;
    }
    
    @Override
    public String toString() {
        return "id=" + id 
                + ", firstName=" + firstName  
                + ", lastName=" + lastName 
                + ", street_Adress=" + street_Adress 
                + ", city=" + city 
                + ", zip_PostalCode=" + zip_PostalCode 
                + ", country=" + country 
                + ", company=" + company 
                + ", phone_Number=" + phone_Number 
                + ", payement_method=" + payement_method 
                + ", newsletter=" + newsletter 
                + ", date=" + date 
                + ", delivery_comment=" + delivery_comment 
                + ", order_comment=" + order_comment 
                + ", etat=" + etat 
                + ", codeCoup=" + codeCoup 
                + ", subtotal=" + subtotal 
                + ", user_commande_id=" + user_commande.getId();
    }
}
