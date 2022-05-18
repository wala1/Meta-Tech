/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Logidee
 */
public class Commande {
    private int id ; 
    private String firstName ; 
    private String lastName ;
    public String street_Adress ;
    private String city ; 
    private String zip_PostalCode ;
    public String country ;
    private String company ; 
    private String phone_Number ;
    public String payement_method ;
    private String newsletter ; 
    private String date ;
    public String delivery_comment ;
    private String order_comment ; 
    private String etat ;
    public String codeCoup ;
    private String subtotal ; 
    private String user_commande ;

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

    public String getZip_PostalCode() {
        return zip_PostalCode;
    }

    public void setZip_PostalCode(String zip_PostalCode) {
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

    public String getPhone_Number() {
        return phone_Number;
    }

    public void setPhoneNumber(String phone_Number) {
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

    public String getCodeCoup() {
        return codeCoup;
    }

    public void setCodeCoupon(String codeCoup) {
        this.codeCoup = codeCoup;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getUser_commande() {
        return user_commande;
    }

    public void setUserCommande(String user_commande) {
        this.user_commande = user_commande;
    }
    
    public Commande() {
    }
    
    public Commande(int id, String firstName, String lastName, String street_Adress, String city, String zip_PostalCode, String country, String company, String phone_Number, String payement_method, String newsletter, String date, String delivery_comment, String order_comment, String etat, String codeCoup, String subtotal, String user_commande) {
        this.id = id;
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
        return "Commande{" + "id=" + id + ", firstName=" + firstName + '}';
    }
}
