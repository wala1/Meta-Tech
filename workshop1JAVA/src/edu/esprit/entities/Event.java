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
public class Event {
    private int id;
    private String title;
    private String description;
    private String imageEvent;
    private String dateDebut;
    private String dateFin;
    private int nbrPartMax;
    private int nbrPart;
    
   private ImageView img;

    public Event() {
    }

    public Event(String title, String description, String imageEvent, String dateDebut, String dateFin, int nbrPartMax) {
        this.title = title;
        this.description = description;
        this.imageEvent = imageEvent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPartMax = nbrPartMax;
    }

    public Event(int id, String title, String description, String imageEvent, String dateDebut, String dateFin, int nbrPartMax) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageEvent = imageEvent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPartMax = nbrPartMax;
    }

    public Event(int id, String title, String description, String imageEvent, String dateDebut, String dateFin, int nbrPart ,int nbrPartMax) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageEvent = imageEvent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPartMax = nbrPartMax;
        this.nbrPart = nbrPart;
    }

    public Event(String title, String description, String imageEvent, String dateDebut, String dateFin, int nbrPartMax, int nbrPart) {
        this.title = title;
        this.description = description;
        this.imageEvent = imageEvent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPartMax = nbrPartMax;
        this.nbrPart = nbrPart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageEvent() {
        return imageEvent;
    }

    public void setImageEvent(String imageEvent) {
        this.imageEvent = imageEvent;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbrPartMax() {
        return nbrPartMax;
    }

    public void setNbrPartMax(int nbrPartMax) {
        this.nbrPartMax = nbrPartMax;
    }

    public int getNbrPart() {
        return nbrPart;
    }

    public void setNbrPart(int nbrPart) {
        this.nbrPart = nbrPart;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", title=" + title + ", description=" + description + ", imageEvent=" + imageEvent + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", nbrPartMax=" + nbrPartMax + ", nbrPart=" + nbrPart + '}';
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    
    
}
