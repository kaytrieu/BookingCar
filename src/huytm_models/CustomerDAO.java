/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huytm_models;

import huytm_db.MyConnection;
import huytm_dtos.CustomerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Kay
 */
public class CustomerDAO {

    private Connection conn = null;
    private PreparedStatement preStm = null;
    private ResultSet rs = null;

    private void closeConnection() throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } finally {
        }
    }

    public boolean addCustomer(CustomerDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "insert into Customer(CustomerID, Name, DayOfBirth, Type, PhoneNumber, Address, TotalSpend)"
                    + " values(?,?,?,?,?,?,?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getCustomerID());
            preStm.setString(2, dto.getName());
            preStm.setDate(3, java.sql.Date.valueOf(dto.getdOB()));
            preStm.setString(4, "NORMAL");
            preStm.setString(5, dto.getPhoneNumber());
            preStm.setString(6, dto.getAddress());
            preStm.setDouble(7, 0);
            check = preStm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean updateCustomer(CustomerDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "update Customer set name = ?, DayOfBirth = ?, PhoneNumber = ?, Address = ? where CustomerID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getName());
            preStm.setDate(2, java.sql.Date.valueOf(dto.getdOB()));
            preStm.setString(3, dto.getPhoneNumber());
            preStm.setString(4, dto.getAddress());
            preStm.setString(5, dto.getCustomerID());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public CustomerDTO viewCustomerByID(String customerID) throws Exception {
        CustomerDTO dto = null;
        try {
            String sql = "Select Name, DayOfBirth, Type, PhoneNumber, Address, TotalSpend from Customer where CustomerID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, customerID);
            rs = preStm.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                LocalDate dOB = rs.getDate("DayOfBirth").toLocalDate();
                String type = rs.getString("Type");
                String phoneNumber = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                double totalSpend = rs.getDouble("TotalSpend");
                dto = new CustomerDTO(customerID, name, type, phoneNumber, address, dOB, totalSpend);
            }
        } finally {
            closeConnection();
        }
        return dto;
        //null ~ not have this customer in db
        // if(!=null){ show infomation}
    }
    
    public List<CustomerDTO> listCustomerByName(String name) throws Exception{
        List<CustomerDTO> result = null;
        CustomerDTO dto = null;
        try{
            String sql = "Select CustomerID,  Type from Customer where Name LIKE ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, name);
            result = new ArrayList<>();
            rs = preStm.executeQuery();
            while(rs.next()){
                String customerID = rs.getString("CustomerID");
                String type = rs.getString("Type");
                dto = new CustomerDTO(customerID, name, type);
                result.add(dto);
            }
        } finally{
            closeConnection();
        }
        
        return result;
    }

    public void checkType(String customerID, double bookingTotal) throws Exception {
        boolean up = false;
        double beforeTotalSpend = 0;
        double afterTotalSpend = 0;
        String type = "";
        try {
            conn = MyConnection.getMyConnection();
            String sql = "update Customer set TotalSpend = TotalSpend + ? where CustomerID = ?";
            preStm = conn.prepareStatement(sql);
            preStm.setDouble(1, bookingTotal);
            preStm.setString(2, customerID);
            if (preStm.executeUpdate() > 0) {

                sql = "select TotalSpend, Type from Customer where CustomerID = ?";
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, customerID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    afterTotalSpend = rs.getDouble("TotalSpend");
                    beforeTotalSpend = afterTotalSpend - bookingTotal;
                    type = rs.getString("Type");
                    if ((afterTotalSpend >= 10000 && beforeTotalSpend < 10000)
                            || (afterTotalSpend >= 100000 && beforeTotalSpend < 100000)) {
                        JOptionPane.showMessageDialog(null, "You was update to " + type + "\nNow you have a Discount "
                                + (getMemberDiscount(customerID) * 100) + "% for all booking");
                    }
                }
            }
        } finally {
            closeConnection();
        }
    }

    public double getMemberDiscount(String customerID) throws Exception {
        double discountValue = 0;
        try {
            String sql = "select Type from Customer where CustomerID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, customerID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                switch (rs.getString("Type")) {
                    case "VIP":
                        discountValue = 0.1;
                        break;
                    case "PREMIUM":
                        discountValue = 0.05;
                        break;
                    case "NORMAL":
                        break;
                }
            }
        } finally {
            closeConnection();
        }
        return discountValue;
        // 0, 0.05 or 0.1(price)
    }

}
