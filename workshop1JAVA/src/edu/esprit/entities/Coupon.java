/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 * @author mejri
 */
public class Coupon {
    int id ;
    String CodeCoupon;

    public Coupon(int id, String CodeCoupon) {
        this.id = id;
        this.CodeCoupon = CodeCoupon;
    }

    public Coupon() {
    }

    public Coupon(String CodeCoupon) {
        this.CodeCoupon = CodeCoupon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeCoupon() {
        return CodeCoupon;
    }

    public void setCodeCoupon(String CodeCoupon) {
        this.CodeCoupon = CodeCoupon;
    }

    @Override
    public String toString() {
        return "Coupon{" + "id=" + id + ", CodeCoupon=" + CodeCoupon + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
