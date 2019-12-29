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
public class CustomerDTO implements Serializable {
    private String customerID, name, type, phoneNumber, address;
    private LocalDate dOB;
    private double totalSpend;

    public CustomerDTO(String customerID, String name, String type, String phoneNumber, String address, LocalDate dOB, double totalSpend) {
        this.customerID = customerID;
        this.name = name;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dOB = dOB;
        this.totalSpend = totalSpend;
    }

    public CustomerDTO(String customerID, String name, String type) {
        this.customerID = customerID;
        this.name = name;
        this.type = type;
    }
    
    

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getdOB() {
        return dOB;
    }

    public void setdOB(LocalDate dOB) {
        this.dOB = dOB;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }
    public Vector toVector(){
        Vector v= new Vector();
        v.add(customerID);
        v.add(name);
        v.add(type);
        return v;
    }
}
