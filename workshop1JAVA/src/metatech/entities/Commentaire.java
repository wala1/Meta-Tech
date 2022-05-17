/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metatech.entities;

import java.sql.Date;

/**
 *
 * @author Abn
 */
public class Commentaire {
    private int id ;
    private String description ;
    private Date temps ;
    private int pubId ;
    private int userId ;

    public Commentaire(int id, String description, Date temps, int pubId, int userId) {
        this.id = id;
        this.description = description;
        this.temps = temps;
        this.pubId = pubId;
        this.userId = userId;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTemps() {
        return temps;
    }

    public void setTemps(Date temps) {
        this.temps = temps;
    }

    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", description=" + description + ", temps=" + temps + ", pubId=" + pubId + ", userId=" + userId + '}';
    }
    
}
