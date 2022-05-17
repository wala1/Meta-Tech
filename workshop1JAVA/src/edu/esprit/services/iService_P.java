/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.publicity;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author mejri
 */
public interface iService_P  <T>{
    public void ajouter(T p );
    public void modifier (T p,int id_pub);
    public void supprimer (int id);
    public List<T> getAll();

 
    public ObservableList<publicity> Affichertout(); // type observableList

}
