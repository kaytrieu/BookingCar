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
public class StaffDTO implements Serializable {
    private String staffID, password, name, address;
    private boolean isManager;
    private int age;
    private boolean isWorking;

    public StaffDTO(String staffID, String name, String address, boolean isManager, int age) {
        this.staffID = staffID;
        this.name = name;
        this.address = address;
        this.isManager = isManager;
        this.age = age;
    }

    
    public StaffDTO(String staffID, String password, String name, String address, boolean isManager, int age, boolean isWorking) {
        this.staffID = staffID;
        this.password = password;
        this.name = name;
        this.address = address;
        this.isManager = isManager;
        this.age = age;
        this.isWorking = isWorking;
    }
    
    

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isIsWorking() {
        return isWorking;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }
    
    public Vector toVector(){
        Vector v = new Vector();
        v.add(staffID);
        v.add(name);
        v.add(isManager);
        v.add(age);
        return v;
    }
}
