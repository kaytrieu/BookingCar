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
public class DriverDTO implements Serializable {
    private String driverID, name, licenseNumber, licenseType;
    private int age;
    private boolean isWorking;

    public DriverDTO(String driverID,String name, String licenseNumber, String licenseType, int age, boolean isWorking) {
        this.driverID = driverID;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.licenseType = licenseType;
        this.age = age;
        this.isWorking = isWorking;
    }

    public DriverDTO(String driverID, String name, String licenseType, int age) {
        this.driverID = driverID;
        this.name = name;
        this.licenseType = licenseType;
        this.age = age;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
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
        v.add(driverID);
        v.add(name);
        v.add(age);
        v.add(licenseType);
        return v;
    }
}
