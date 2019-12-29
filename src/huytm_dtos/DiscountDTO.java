/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

/**
 *
 * @author Kay
 */
public class DiscountDTO implements Serializable {
    private String discountCode, name;
    private LocalDate experyDate;
    private double discountValue;
    private int remainNumber;

    public DiscountDTO(String discountCode, String name, LocalDate experyDate, double discountValue, int remainNumber) {
        this.discountCode = discountCode;
        this.name = name;
        this.experyDate = experyDate;
        this.discountValue = discountValue;
        this.remainNumber = remainNumber;
    }

    public DiscountDTO(String discountCode, String name, double discountValue) {
        this.discountCode = discountCode;
        this.name = name;
        this.discountValue = discountValue;
    }
    
    

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExperyDate() {
        return experyDate;
    }

    public void setExperyDate(LocalDate experyDate) {
        this.experyDate = experyDate;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public int getRemainNumber() {
        return remainNumber;
    }

    public void setRemainNumber(int remainNumber) {
        this.remainNumber = remainNumber;
    }
    
     public Vector toVector(){
         Vector v = new Vector();
         v.add(discountCode);
         v.add(name);
         v.add(discountValue);
         return v;
     }
}
