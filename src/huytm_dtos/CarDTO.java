/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_dtos;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Kay
 */
public class CarDTO implements Serializable {

    private String carID, imageLink, model, brand;
    private int seat;
    private double pdPrice;
    private boolean isUsing;

    public CarDTO(String carID, String imageLink, String model, String brand, int seat, double pdPrice, boolean isUsing) {
        this.carID = carID;
        this.imageLink = imageLink;
        this.model = model;
        this.brand = brand;
        this.seat = seat;
        this.pdPrice = pdPrice;
        this.isUsing = isUsing;
    }

    public CarDTO() {

    }

    public boolean isIsUsing() {
        return isUsing;
    }

    public void setIsUsing(boolean isUsing) {
        this.isUsing = isUsing;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPdPrice() {
        return pdPrice;
    }

    public void setPdPrice(double pdPrice) {
        this.pdPrice = pdPrice;
    }

    public Vector toVector() {
        Vector v = new Vector();
        v.add(carID);
        v.add(model);
        v.add(brand);
        v.add(seat);
        v.add(pdPrice);
        return v;

    }

}
