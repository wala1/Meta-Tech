/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author louay
 */
public class Commentaire {

    private int id;
    private String description;
    private int id_publ;
    private String user;
    private String temps;

    public Commentaire() {
    }

    public Commentaire(String description, int id_publ, String user) {
        this.description = description;
        this.id_publ = id_publ;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public int getIdPubl() {
        return id_publ;
    }

    public void setIdPubl(int id_publ) {
        this.id_publ = id_publ;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", id_publ=" + id_publ + ", description_comm=" + description + ", user=" + user + "}";
    }
}
