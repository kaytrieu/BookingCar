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
public class BookingDTO  implements Serializable{
    private String bookingID, carID, customerID,
            driverID, makerID, discountCode, bookingTime;
    private LocalDate pickDate,dropDate;
    private double memberDiscount, totalPrice;
    private boolean isBooked;

    public BookingDTO(String bookingID, LocalDate pickDate, LocalDate dropDate, double totalPrice, boolean isBooked) {
        this.bookingID = bookingID;
        this.pickDate = pickDate;
        this.dropDate = dropDate;
        this.totalPrice = totalPrice;
        this.isBooked = isBooked;
    }

    
    public BookingDTO(String bookingID, String carID, String customerID, String driverID, String makerID, String discountCode, String bookingTime, LocalDate pickDate, LocalDate dropDate, double memberDiscount, double totalPrice, boolean isBooked) {
        this.bookingID = bookingID;
        this.carID = carID;
        this.customerID = customerID;
        this.driverID = driverID;
        this.makerID = makerID;
        this.discountCode = discountCode;
        this.bookingTime = bookingTime;
        this.pickDate = pickDate;
        this.dropDate = dropDate;
        this.memberDiscount = memberDiscount;
        this.totalPrice = totalPrice;
        this.isBooked = isBooked;
    }

    public boolean isIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getMakerID() {
        return makerID;
    }

    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public LocalDate getPickDate() {
        return pickDate;
    }

    public void setPickDate(LocalDate pickDate) {
        this.pickDate = pickDate;
    }

    public LocalDate getDropDate() {
        return dropDate;
    }

    public void setDropDate(LocalDate dropDate) {
        this.dropDate = dropDate;
    }

    public double getMemberDiscount() {
        return memberDiscount;
    }

    public void setMemberDiscount(double memberDiscount) {
        this.memberDiscount = memberDiscount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
    public Vector toVector(){
        Vector v = new Vector();
        v.add(bookingID);
        v.add(pickDate);
        v.add(dropDate);
        v.add(totalPrice);
        v.add(isBooked);
        return v;
    }
}
