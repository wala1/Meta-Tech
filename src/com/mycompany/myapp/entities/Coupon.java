/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author mejri
 */
public class Coupon {
     private int id ; 
    private float codeCoupon ;

    public Coupon() {
    }

    public Coupon(int id, float codeCoupon) {
        this.id = id;
        this.codeCoupon = codeCoupon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCodeCoupon() {
        return codeCoupon;
    }

    public void setCodeCoupon(float codeCoupon) {
        this.codeCoupon = codeCoupon;
    }

    @Override
    public String toString() {
        return "Coupon{" + "id=" + id + ", codeCoupon=" + codeCoupon + '}';
    }

















}
