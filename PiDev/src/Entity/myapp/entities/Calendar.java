/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author ihebx
 */
public class Calendar {

    private int id;

    private String title;

    
    private String start;

    private String end;

    private String description;
    private String imageEvent;
    private int etat;
    public Calendar() {
    }

    public Calendar(int id, String title, String description,String start, String end  ,String imageEvent) {
        this.id = id;
        this.title = title;
                this.description = description;

        this.start = start;
        this.end = end;
         this.imageEvent = imageEvent;
    }

    public Calendar(String title, String start, String end, String description,String imageEvent) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
           this.imageEvent = imageEvent;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }

  

    public String getImageEvent() {
        return imageEvent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageEvent(String imageEvent) {
        this.imageEvent = imageEvent;
    }
    

    @Override
    public String toString() {
        return "Calendar{" + "id=" + id + ", title=" + title + ", start=" + start + ", end=" + end + ", description=" + description + '}';
    }

}
